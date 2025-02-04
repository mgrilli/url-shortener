CREATE TABLE shortened_url (
    id SERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    short_code VARCHAR(10) UNIQUE NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX idx_short_code ON shortened_url (short_code);

CREATE TABLE shortened_url_access (
    id INT NOT NULL,
    access_count BIGINT DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES shortened_url (id) ON DELETE CASCADE
);