CREATE TABLE IF NOT EXISTS t_groups
(
    group_id          UUID,
    creator_id        UUID,
    group_name        TEXT,
    group_description TEXT,
    is_public         BOOLEAN,
    created_at        DATE,
    PRIMARY KEY ((group_id))
);

CREATE TABLE IF NOT EXISTS t_group_members
(
    group_id   UUID,
    member_id  UUID,
    role_name  TEXT,
    is_super   BOOLEAN,
    role_level SMALLINT,
    joined_at  DATE,
    PRIMARY KEY ((group_id), member_id)
);

CREATE TABLE IF NOT EXISTS t_group_messages
(
    group_message_id UUID,
    sender_id        UUID,
    group_id         UUID,
    content          TEXT,
    viewed_by_users  SET<UUID>,
    deleted          BOOLEAN,
    edited           BOOLEAN,
    created_at       TIMESTAMP,
    PRIMARY KEY ((group_id), group_message_id)
);

CREATE TABLE IF NOT EXISTS t_user_messages
(
    user_message_id UUID,
    sender_id       UUID,
    receiver_id     UUID,
    content         TEXT,
    viewed          BOOLEAN,
    deleted         BOOLEAN,
    edited          BOOLEAN,
    created_at      TIMESTAMP,
    PRIMARY KEY ((sender_id, receiver_id), user_message_id)
);