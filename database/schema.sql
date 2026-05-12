CREATE DATABASE IF NOT EXISTS cafe_management
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE cafe_management;

DROP TABLE IF EXISTS order_details;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS cafe_tables;
DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('ADMIN', 'STAFF') NOT NULL,
    status BOOLEAN NOT NULL DEFAULT TRUE -- THÊM DÒNG NÀY
);

CREATE TABLE categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(100) NOT NULL
);

CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(150) NOT NULL,
    price DECIMAL(12, 2) NOT NULL DEFAULT 0,
    category_id INT NOT NULL,
    CONSTRAINT fk_products_categories
        FOREIGN KEY (category_id)
        REFERENCES categories(category_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE cafe_tables (
    table_id INT PRIMARY KEY AUTO_INCREMENT,
    table_name VARCHAR(50) NOT NULL,
    is_occupied BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    table_id INT NOT NULL,
    account_id INT NOT NULL,
    order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_paid BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_orders_tables
        FOREIGN KEY (table_id)
        REFERENCES cafe_tables(table_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_orders_accounts
        FOREIGN KEY (account_id)
        REFERENCES accounts(account_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE order_details (
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    unit_price DECIMAL(12, 2) NOT NULL DEFAULT 0,
    PRIMARY KEY (order_id, product_id),
    CONSTRAINT fk_order_details_orders
        FOREIGN KEY (order_id)
        REFERENCES orders(order_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_order_details_products
        FOREIGN KEY (product_id)
        REFERENCES products(product_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- 2. Sửa lại dữ liệu mẫu (thêm giá trị TRUE cho status)
INSERT INTO accounts (account_id, username, password, role, status) VALUES
(1, 'admin', 'admin123', 'ADMIN', TRUE),
(2, 'staff', 'staff123', 'STAFF', TRUE);

INSERT INTO categories (category_id, category_name) VALUES
(1, 'Ca phe'),
(2, 'Tra sua');

INSERT INTO products (product_id, product_name, price, category_id) VALUES
(1, 'Ca phe Den', 20000, 1),
(2, 'Ca phe Sua', 25000, 1),
(3, 'Tra sua Tran chau', 35000, 2);

INSERT INTO cafe_tables (table_id, table_name, is_occupied) VALUES
(1, 'Ban 1', FALSE),
(2, 'Ban 2', FALSE),
(3, 'Ban 3', FALSE),
(4, 'Ban 4', FALSE),
(5, 'Ban 5', FALSE),
(6, 'Ban 6', FALSE),
(7, 'Ban 7', FALSE),
(8, 'Ban 8', FALSE),
(9, 'Ban 9', FALSE),
(10, 'Ban 10', FALSE);

ALTER TABLE accounts AUTO_INCREMENT = 3;
ALTER TABLE categories AUTO_INCREMENT = 3;
ALTER TABLE products AUTO_INCREMENT = 4;
ALTER TABLE cafe_tables AUTO_INCREMENT = 11;
ALTER TABLE orders AUTO_INCREMENT = 1;
