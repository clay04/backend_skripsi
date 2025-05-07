-- Extension install
CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS t_user
(
    uuid                    VARCHAR(36)                NOT NULL,
    first_name            VARCHAR(25),
    last_name             VARCHAR(25),
    phone_number          VARCHAR(16),
    user_name             VARCHAR(25),
    role_id               VARCHAR(2),
    date_of_birth           date,
    age                     INTEGER,
    email                 VARCHAR(50),
    password              VARCHAR(255),
    token                 VARCHAR(255),
    exp_token             TIMESTAMP WITHOUT TIME ZONE,
    verification_code     VARCHAR(255),
    exp_verification_code TIMESTAMP WITHOUT TIME ZONE,
    status                BOOLEAN,
    created_by            VARCHAR(32)                NOT NULL,
    created_at            TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by            VARCHAR(32)                NOT NULL,
    updated_at            TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    is_deleted            BOOLEAN                     NOT NULL,
    deleted_at            TIMESTAMP WITHOUT TIME ZONE,
    deleted_by            VARCHAR(32),
    CONSTRAINT pk_t_user PRIMARY KEY (uuid),
    CONSTRAINT uc_t_user_email UNIQUE (email)
    );

CREATE TABLE IF NOT EXISTS t_roles
(
    role_id     VARCHAR(2)                NOT NULL,
    name        VARCHAR(100),
    description VARCHAR(255),
    created_by  VARCHAR(32)                NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by  VARCHAR(32)                NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    is_deleted  BOOLEAN                     NOT NULL,
    deleted_at  TIMESTAMP WITHOUT TIME ZONE,
    deleted_by  VARCHAR(32),
    CONSTRAINT pk_t_roles PRIMARY KEY (role_id)
    );