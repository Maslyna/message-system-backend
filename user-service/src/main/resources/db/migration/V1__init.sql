CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE t_users
(
    user_id    UUID PRIMARY KEY            DEFAULT gen_random_uuid(),
    email      VARCHAR(50)                                           NOT NULL,
    username   VARCHAR(50)                                           NOT NULL,
    status     VARCHAR(50)                                           NOT NULL,
    bio        VARCHAR(255)                                          NOT NULL,
    last_login TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE t_user_contacts
(
    owner_id   UUID REFERENCES t_users (user_id),
    contact_id UUID REFERENCES t_users (user_id),
    PRIMARY KEY (owner_id, contact_id)
);

CREATE TABLE t_user_settings
(
    user_id          UUID PRIMARY KEY REFERENCES t_users (user_id),
    is_public        BOOLEAN NOT NULL DEFAULT TRUE,
    receive_emails   BOOLEAN NOT NULL DEFAULT TRUE,
    receive_messages BOOLEAN NOT NULL DEFAULT TRUE
);