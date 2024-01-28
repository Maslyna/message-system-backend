package net.maslyna.security.test;

import lombok.extern.slf4j.Slf4j;
import net.maslyna.security.model.request.RegistrationRequest;
import net.maslyna.security.property.SecurityProperties;
import net.maslyna.security.repository.AccountRepository;
import net.maslyna.security.service.AccountService;
import net.maslyna.security.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        webEnvironment = RANDOM_PORT
)
@Slf4j
@DisplayName("INTEGRATION TESTS FOR API")
@Testcontainers
public class IntegrationTests {
    @LocalServerPort
    private int port;
    private WebTestClient client;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtService jwtService;

    @ServiceConnection
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.flyway.url", postgresContainer::getJdbcUrl);
        registry.add("spring.flyway.password", postgresContainer::getPassword);
        registry.add("spring.flyway.username", postgresContainer::getUsername);

        registry.add("spring.r2dbc.url", () ->
                String.format("r2dbc:pool:postgresql://%s:%d/%s",
                        postgresContainer.getHost(),
                        postgresContainer.getFirstMappedPort(),
                        postgresContainer.getDatabaseName()));
        registry.add("spring.r2dbc.username", postgresContainer::getUsername);
        registry.add("spring.r2dbc.password", postgresContainer::getPassword);
    }

    @BeforeEach
    void setup() {
        this.client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port).build();
    }

    @Nested
    @DisplayName("testing not logged user")
    class NotLogged {
        private static final RegistrationRequest defautRegistrationRequest = new RegistrationRequest("test@mail.com", "password");

        @BeforeEach
        void setup() {
            accountRepository.deleteAll().subscribe();
        }

        @Test
        @DisplayName("should return ok when registration new user")
        void shouldReturnOkWhenRegistrationNewUser() {
            client.post().uri(ServiceURI.REGISTRATION)
                    .bodyValue(defautRegistrationRequest)
                    .exchange()
                    .expectStatus()
                    .isEqualTo(HttpStatus.CREATED);
        }

        @Test
        @DisplayName("should return conflict when registration new user with existing email")
        void shouldReturn409WhenRegistrationNewUser() {
            client.post().uri(ServiceURI.REGISTRATION)
                    .bodyValue(defautRegistrationRequest)
                    .exchange()
                    .expectStatus()
                    .isEqualTo(HttpStatus.CREATED);
            client.post().uri(ServiceURI.REGISTRATION)
                    .bodyValue(defautRegistrationRequest)
                    .exchange()
                    .expectStatus()
                    .isEqualTo(HttpStatus.CONFLICT);
        }

        @Test
        @DisplayName("should return bad request when registration new user with invalid body")
        void shouldReturn400WhenRegistrationInvalidUser() {
            client.post().uri(ServiceURI.REGISTRATION)
                    .bodyValue(new RegistrationRequest("", ""))
                    .exchange()
                    .expectStatus()
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }

    @Nested
    @DisplayName("testing logged user")
    public class Logged {

        private static final String EMAIL = "test@mail.com";
        private static final String PASSWORD = "password";

        @BeforeEach
        void setup() {
            accountRepository.deleteAll().subscribe();
            accountService.save(EMAIL, PASSWORD).block();
        }

        @Test
        @DisplayName("should return ok and bearer token when user log-in")
        void shouldReturnOkWithValidTokenWhenUserLogIn() {
            client.get().uri(ServiceURI.LOG_IN)
                    .header(HttpHeaders.AUTHORIZATION, generateBasicAuthenticationHeader(EMAIL, PASSWORD))
                    .exchange()
                    .expectHeader()
                    .value(HttpHeaders.AUTHORIZATION, header -> {
                        assertThat(header).isNotBlank();
                        assertThat(jwtService.isTokenValid(jwtService.extractToken(header))).isTrue();
                    })
                    .expectStatus()
                    .isOk();
        }

        @Test
        @DisplayName("should return 401 when not valid user log-in")
        void shouldReturn401WhenUserLogInWithoutValidToken() {
            client.get().uri(ServiceURI.LOG_IN)
                    .header(HttpHeaders.AUTHORIZATION, generateBasicAuthenticationHeader("somemail", "badpassword"))
                    .exchange()
                    .expectStatus()
                    .isUnauthorized();
        }

        @Test
        @DisplayName("should return 404 when user log-in without auth header")
        void shouldReturn404WhenUserLohInWithoutAuthHeader() {
            client.get().uri(ServiceURI.LOG_IN)
                    .exchange()
                    .expectStatus()
                    .isNotFound();
        }
    }

    @Nested
    @DisplayName("token tests")
    public class Token {
        private static final String EMAIL = "test@mail.com";
        private static final String PASSWORD = "password";

        @BeforeEach
        void setup() {
            accountRepository.deleteAll().subscribe();
            accountService.save(EMAIL, PASSWORD).block();
        }


        @Test
        @DisplayName("should return 200 with user information when the token passes validation")
        void shouldReturn200WithUserInfoWhenTokenPassesValidation() {
            client.get().uri(ServiceURI.LOG_IN)
                    .header(HttpHeaders.AUTHORIZATION, generateBasicAuthenticationHeader(EMAIL, PASSWORD))
                    .exchange()
                    .expectHeader()
                    .value(HttpHeaders.AUTHORIZATION, header -> {
                        assertThat(header).isNotBlank();
                        client.get()
                                .uri(ServiceURI.VALIDATION)
                                .header(HttpHeaders.AUTHORIZATION, header)
                                .exchange()
                                .expectStatus()
                                .isOk();
                    })
                    .expectStatus()
                    .isOk();
        }
    }


    public String generateBasicAuthenticationHeader(String username, String password) {
        String credentials = username + ":" + password;
        byte[] credentialsBytes = credentials.getBytes();
        String encodedCredentials = new String(Base64.getEncoder().encode(credentialsBytes));
        return securityProperties.getBasicPrefix() + encodedCredentials;
    }
}
