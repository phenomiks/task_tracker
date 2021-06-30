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
    title       VARCHAR NOT NULL,
    description VARCHAR,
    closed      BOOLEAN DEFAULT false,
    user_id     BIGINT REFERENCES users (id),
    project_id  BIGINT REFERENCES projects (id),
    created_at  TIMESTAMP DEFAULT current_timestamp,
    updated_at  TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE projects_users
(
    project_id BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    PRIMARY KEY (project_id, user_id),
    FOREIGN KEY (project_id) REFERENCES projects (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

COMMIT;