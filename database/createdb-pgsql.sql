DROP TABLE IF EXISTS pics;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    uid             SERIAL PRIMARY KEY,
    email           VARCHAR NOT NULL UNIQUE,
    display_name    VARCHAR NOT NULL,
    password        VARCHAR NOT NULL,
    last_login      TIMESTAMP NULL,
    is_admin        BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE pics (
    pid             SERIAL PRIMARY KEY,
    uid             INTEGER NOT NULL REFERENCES users(uid),
    filename        VARCHAR NOT NULL UNIQUE,
    comment         TEXT,
    uploaded_on     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    taken_on        TIMESTAMP,
    modified_on     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
