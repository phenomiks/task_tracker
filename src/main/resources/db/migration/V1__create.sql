BEGIN;

CREATE TABLE projects
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR NOT NULL,
    description VARCHAR,
    closed      BOOLEAN   DEFAULT false,
    created_at  TIMESTAMP DEFAULT current_timestamp,
    updated_at  TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    firstname  VARCHAR(50) NOT NULL,
    lastname   VARCHAR(50) NOT NULL,
    phone      VARCHAR(20) NOT NULL,
    project_id BIGINT REFERENCES projects (id),
    created_at TIMESTAMP DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE tasks
(
    id          BIGSERIAL PRIMARY KEY,
    parent_id   BIGINT,
    title       VARCHAR NOT NULL,
    description VARCHAR,
    closed      BOOLEAN   DEFAULT false,
    user_id     BIGINT REFERENCES users (id),
    created_at  TIMESTAMP DEFAULT current_timestamp,
    updated_at  TIMESTAMP DEFAULT current_timestamp
);

COMMIT;