CREATE TABLE users
(
    id         VARCHAR(36) PRIMARY KEY NOT NULL,
    first_name VARCHAR(30)      NOT NULL,
    last_name  VARCHAR(30)      NOT NULL,
    email      VARCHAR(80)      NOT NULL UNIQUE,
    password   VARCHAR(64)      NOT NULL
);
