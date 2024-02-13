CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE t_users
(
    user_id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email      VARCHAR(50)                 NOT NULL,
    username   VARCHAR(50)                 NOT NULL,
    status     VARCHAR(50)                 NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE t_user_contacts
(
    owner_id    UUID REFERENCES t_users (user_id),
    contact_id UUID REFERENCES t_users (user_id),
    PRIMARY KEY (owner_id, contact_id)
);
