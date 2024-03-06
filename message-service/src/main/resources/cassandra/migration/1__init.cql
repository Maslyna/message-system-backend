CREATE TABLE IF NOT EXISTS t_group_messages
(
    group_message_id UUID,
    sender_id        UUID,
    group_id         UUID,
    content          TEXT,
    file_ids         LIST<UUID>,
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
    file_ids        LIST<UUID>,
    viewed          BOOLEAN,
    deleted         BOOLEAN,
    edited          BOOLEAN,
    created_at      TIMESTAMP,
    PRIMARY KEY ((sender_id, receiver_id), user_message_id)
);