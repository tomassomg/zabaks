CREATE DATABASE IF NOT EXISTS Internetveikals;
USE Internetveikals;

-- Users
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) DEFAULT 'USER',
    balance DOUBLE DEFAULT 0
);

-- Shoes
CREATE TABLE shoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    brand VARCHAR(100),
    price DOUBLE,
    gender VARCHAR(20),
    sizes TEXT,
    color VARCHAR(50),
    url VARCHAR(255)
);

-- Orders
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    total_price DOUBLE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Order_Items
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    shoes_id INT NOT NULL,
    shoes_size VARCHAR(10) NOT NULL,
    price DOUBLE NOT NULL,
    order_id INT DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (shoes_id) REFERENCES shoes(id),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);
