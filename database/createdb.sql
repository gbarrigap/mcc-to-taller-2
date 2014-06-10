DROP TABLE IF EXISTS pics;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    uid             INTEGER PRIMARY KEY AUTOINCREMENT,
    email           TEXT NOT NULL UNIQUE,
    display_name    TEXT NOT NULL,
    password        TEXT NOT NULL,
    last_login      TEXT NULL,
    is_admin        INTEGER NOT NULL CHECK (is_admin IN (0, 1)) DEFAULT 0 -- 0:normal 1:admin
);

CREATE TABLE pics (
    pid             INTEGER PRIMARY KEY AUTOINCREMENT,
    filename        TEXT NOT NULL,
    comment         TEXT NULL CHECK (length(comment) <= 100),
    uploaded_on     TEXT NOT NULL,
    taken_on        TEXT NULL,
    modified_on     TEXT NOT NULL
);
