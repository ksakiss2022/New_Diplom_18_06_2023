-- liquibase formatted sql

-- changeset skorobeynikova:1


CREATE TABLE IF NOT EXISTS ads (
    id BIGSERIAL PRIMARY KEY,
    price DECIMAL(10, 2) NOT NULL,
    title VARCHAR(255) NOT NULL,
    author_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users (id)
    );

CREATE TABLE IF NOT EXISTS comments (
    id BIGSERIAL PRIMARY KEY,
    ALTER TABLE comments ALTER COLUMN created_at TYPE TIMESTAMP USING created_at::TIMESTAMP;
--created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    text VARCHAR(255) NOT NULL,
    ads_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    FOREIGN KEY (ads_id) REFERENCES ads (id),
    FOREIGN KEY (author_id) REFERENCES users (id)
    );

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(30) NOT NULL,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    role VARCHAR(10) NOT NULL,
    INDEX email_idx(email)
    );

CREATE TABLE IF NOT EXISTS images (
    id SERIAL PRIMARY KEY,
    fileSize BIGINT NOT NULL CHECK(fileSize >= 0),
    mediaType VARCHAR(50) NOT NULL CHECK (mediaType IN ('image/jpeg', 'image/png', 'video/mp4')),
    preview BYTEA NOT NULL,
    user_id BIGINT NOT NULL,
    ads_id BIGINT NOT NULL,
    CONSTRAINT fk_images_users
    FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_images_ads
    FOREIGN KEY (ads_id) REFERENCES ads (id)
    );

CREATE TABLE IF NOT EXISTS user_ad_images (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    ads_id BIGINT NOT NULL,
    image_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (ads_id) REFERENCES ads (id),
    FOREIGN KEY (image_id) REFERENCES images (id)
    );

