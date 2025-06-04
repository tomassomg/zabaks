-- Izveido datubāzi
CREATE DATABASE IF NOT EXISTS Internetveikals;
USE Internetveikals;

-- Lietotāji
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) DEFAULT 'USER',
    balance DOUBLE DEFAULT 0
);

-- Apavi (shoes)
CREATE TABLE shoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    brand VARCHAR(100),
    price DOUBLE,
    gender VARCHAR(20),
    sizes TEXT, -- izmēri kā komatiem atdalīts teksts, piemēram: "37.5,38,39"
    color VARCHAR(50)
);

-- Grozs
CREATE TABLE cart_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES shoes(id)
);

-- Pasūtījumi
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DOUBLE,
    status VARCHAR(20) DEFAULT 'Processing',
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Pasūtījuma preces
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price_at_purchase DOUBLE NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES shoes(id)
);