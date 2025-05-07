CREATE TABLE IF NOT EXISTS t_announcement
(
    uuid         VARCHAR(36)                NOT NULL,
    title        VARCHAR(255),
    description  TEXT,
    category     VARCHAR(255),
    publish_date date,
    display_date date,
    created_by   VARCHAR(32)                NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by   VARCHAR(32)                NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    is_deleted   BOOLEAN                     NOT NULL,
    deleted_at   TIMESTAMP WITHOUT TIME ZONE,
    deleted_by   VARCHAR(32),
    CONSTRAINT pk_t_announcement PRIMARY KEY (uuid)
);