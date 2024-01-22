CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS t_accounts
(
    id                      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username                VARCHAR(255) NOT NULL,
    password                VARCHAR(255) NOT NULL,
    role                    SMALLINT     NOT NULL,
    account_non_expired     BOOLEAN      NOT NULL,
    account_non_locked      BOOLEAN      NOT NULL,
    credentials_non_expired BOOLEAN      NOT NULL,
    enabled                 BOOLEAN      NOT NULL
);