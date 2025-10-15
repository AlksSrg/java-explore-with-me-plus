CREATE TABLE IF NOT EXISTS users(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(250),
    email VARCHAR(254) UNIQUE
);

CREATE TABLE IF NOT EXISTS categories(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) UNIQUE
);


CREATE TABLE IF NOT EXISTS events(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(120),
    annotation VARCHAR(2000),
    description VARCHAR(7000),
    category_id BIGINT,
    event_date timestamp,
    initiator_id BIGINT,
    paid boolean,
    participant_limit integer,
    request_moderation boolean,
    created_on timestamp,
    published_on timestamp,
    lat real,
    lot real,
    FOREIGN KEY(category_id) REFERENCES categories(id) ON DELETE CASCADE,
    FOREIGN KEY(initiator_id) REFERENCES users(id) ON DELETE CASCADE
)
