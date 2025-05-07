CREATE TABLE IF NOT EXISTS t_permission
(
    permission_id       VARCHAR(4)                NOT NULL,
    permission_group_id VARCHAR(4),
    name                VARCHAR(100),
    description         VARCHAR(255),
    created_by          VARCHAR(32)                NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by          VARCHAR(32)                NOT NULL,
    updated_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    is_deleted          BOOLEAN                     NOT NULL,
    deleted_at          TIMESTAMP WITHOUT TIME ZONE,
    deleted_by          VARCHAR(32),
    CONSTRAINT pk_t_permission PRIMARY KEY (permission_id)
);

CREATE TABLE IF NOT EXISTS t_permission_group
(
    permission_group_id VARCHAR(4)                NOT NULL,
    name                VARCHAR(100),
    created_by          VARCHAR(32)                NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by          VARCHAR(32)                NOT NULL,
    updated_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    is_deleted          BOOLEAN                     NOT NULL,
    deleted_at          TIMESTAMP WITHOUT TIME ZONE,
    deleted_by          VARCHAR(32),
    CONSTRAINT pk_t_permission_group PRIMARY KEY (permission_group_id)
);

CREATE TABLE IF NOT EXISTS t_roles_permissions
(
    role_permission_id VARCHAR(6)                NOT NULL,
    role_id            VARCHAR(2),
    permission_id      VARCHAR(4),
    created_by         VARCHAR(32)                NOT NULL,
    created_at         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by         VARCHAR(32)                NOT NULL,
    updated_at         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    is_deleted         BOOLEAN                     NOT NULL,
    deleted_at         TIMESTAMP WITHOUT TIME ZONE,
    deleted_by         VARCHAR(32),
    CONSTRAINT pk_t_roles_permissions PRIMARY KEY (role_permission_id)
);