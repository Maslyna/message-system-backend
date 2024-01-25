package net.maslyna.security.config;

import io.jsonwebtoken.security.Keys;
import net.maslyna.security.filter.JwtAuthenticationFilter;
import net.maslyna.security.property.SecurityProperties;
import net.maslyna.security.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         ReactiveAuthenticationManager reactiveAuthenticationManager,
                                                         JwtAuthenticationFilter jwtAuthenticationFilter

    ) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .authorizeExchange(config -> {
                    config.pathMatchers("/api/v1/registration").permitAll();
                    config.pathMatchers("/api/v1/login").permitAll();
                    config.anyExchange().authenticated();
                })
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.HTTP_BASIC)
                .authenticationManager(reactiveAuthenticationManager)
                .build();
    }

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService(AccountRepository repository) {
        return username -> repository.findByUsername(username)
                .map(UserDetails.class::cast);
//                .cast(UserDetails.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService service, PasswordEncoder encoder) {
        var manager = new UserDetailsRepositoryReactiveAuthenticationManager(service);
        manager.setPasswordEncoder(encoder);
        return manager;
    }

    @Bean
    public SecretKey secretKey(SecurityProperties securityProperties) {
        String secret = Base64.getEncoder().encodeToString(
                securityProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)
        );
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
