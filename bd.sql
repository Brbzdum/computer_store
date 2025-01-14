CREATE DATABASE computer_store;
USE computer_store;

-- Таблица пользователей
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    activation_code VARCHAR(255),
    password VARCHAR(1000) NOT NULL
);

-- Связь пользователей с ролями
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL, -- Ссылка на ID пользователя
    roles VARCHAR(255) NOT NULL, -- Хранит значения перечисления (например, "ROLE_USER")
    PRIMARY KEY (user_id, roles),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Таблица производителей
CREATE TABLE manufacturers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(500)
);

-- Таблица продуктов
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL,
    price DECIMAL(15, 2) NOT NULL,
    purchase_price DECIMAL(15, 2) NOT NULL,
    image_path VARCHAR(255), -- Исправлено с main_image LONGBLOB
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    stock INT NOT NULL,
    manufacturer_id BIGINT NOT NULL,
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturers (id) ON DELETE CASCADE
);

-- Таблица продаж
CREATE TABLE sales (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    buyer_id BIGINT NOT NULL,
    manufacturer_id BIGINT NOT NULL,
    sale_price DECIMAL(15, 2) NOT NULL,
    purchase_price DECIMAL(15, 2) NOT NULL,
    sale_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    quantity INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    FOREIGN KEY (buyer_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturers (id) ON DELETE CASCADE
);

-- Таблица корзин
CREATE TABLE carts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNIQUE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Таблица элементов корзины
CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES carts (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);
