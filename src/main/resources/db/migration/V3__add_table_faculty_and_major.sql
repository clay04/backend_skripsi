CREATE TABLE IF NOT EXISTS t_faculty
(
    uuid       VARCHAR(36)                NOT NULL,
    faculty_code          VARCHAR(4)        NOT NULL,
    name          VARCHAR(150)               NOT NULL,
    created_by VARCHAR(32)                NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by VARCHAR(32)                NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    is_deleted BOOLEAN                     NOT NULL,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(32),
    CONSTRAINT pk_t_faculty PRIMARY KEY (uuid)
);

CREATE TABLE IF NOT EXISTS t_major
(
    uuid          VARCHAR(36)                NOT NULL,
    major_code          VARCHAR(4)        NOT NULL,
    name          VARCHAR(150)               NOT NULL,
    faculity_uuid VARCHAR(36),
    created_by    VARCHAR(32)                NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_by    VARCHAR(32)                NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    is_deleted    BOOLEAN                     NOT NULL,
    deleted_at    TIMESTAMP WITHOUT TIME ZONE,
    deleted_by    VARCHAR(32),
    CONSTRAINT pk_t_major PRIMARY KEY (uuid),
    CONSTRAINT FK_T_MAJOR_ON_FACULITY_UUID FOREIGN KEY (faculity_uuid) REFERENCES t_faculty (uuid)
);