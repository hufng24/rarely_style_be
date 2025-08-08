CREATE TABLE users.rel_user_roles
(
    role_id BIGINT,
    user_id BIGINT
);

CREATE TABLE users.rel_permission_roles
(
    role_id       BIGINT,
    permission_id BIGINT
);

CREATE TABLE users.permission
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50),
    value VARCHAR(255),
    description VARCHAR
);

CREATE TABLE users.role
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE users.user
(
    id           SERIAL PRIMARY KEY,
    password     VARCHAR,
    name         VARCHAR(255),
    email        VARCHAR unique,
    gender       VARCHAR,
    avatar       VARCHAR,
    phone_number VARCHAR,
    status       VARCHAR,
    is_admin     BOOLEAN DEFAULT FALSE,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted   BOOLEAN DEFAULT FALSE
)