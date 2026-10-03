CREATE TABLE users (
 id UUID PRIMARY KEY,
 username VARCHAR(50) NOT NULL,
 email VARCHAR(254) NOT NULL,
 password_hash VARCHAR(100) NOT NULL,
 created_at TIMESTAMPTZ NOT NULL,
 updated_at TIMESTAMPTZ NOT NULL,
 CONSTRAINT uk_users_username UNIQUE(username),
 CONSTRAINT uk_users_email UNIQUE(email)
);