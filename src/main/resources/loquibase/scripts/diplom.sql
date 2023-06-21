-- liquibase formatted sql

-- changeset skorobeynikova:1

CREATE TABLE IF NOT EXISTS ads (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   price DECIMAL(10, 2) NOT NULL,
    title VARCHAR(255) NOT NULL,
    author_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT ads_users_fk FOREIGN KEY (author_id) REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS comments (
                                        id BIGSERIAL PRIMARY KEY,
                                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        text VARCHAR(255) NOT NULL,
    ads_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    CONSTRAINT fk_ads_id FOREIGN KEY (ads_id) REFERENCES ads(id),
    CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES users(id)
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


CREATE TABLE images (
                        id SERIAL PRIMARY KEY,
                        filePath VARCHAR(255) NOT NULL,
                        fileSize BIGINT NOT NULL CHECK(fileSize >= 0),
                        mediaType VARCHAR(50) NOT NULL CHECK (mediaType IN ('image/jpeg', 'image/png', 'video/mp4')),
                        preview BYTEA NOT NULL,
                        user_id INT NOT NULL,
                        ads_id INT NOT NULL,
                        CONSTRAINT fk_images_users
                            FOREIGN KEY (user_id) REFERENCES users (id),
                        CONSTRAINT fk_images_ads
                            FOREIGN KEY (ads_id) REFERENCES ads (id)
);