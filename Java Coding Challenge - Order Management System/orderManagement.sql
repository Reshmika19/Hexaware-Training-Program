-- Database
CREATE DATABASE IF NOT EXISTS orderManagement;
USE orderManagement;

--  User Table
CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(10) CHECK (role IN ('Admin', 'User'))
);

-- Product Table
CREATE TABLE product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    description TEXT,
    price DOUBLE NOT NULL,
    quantity_in_stock INT NOT NULL,
    type VARCHAR(20) CHECK (type IN ('Electronics', 'Clothing'))
);

-- Electronics Table 
CREATE TABLE electronics (
    product_id INT PRIMARY KEY,
    brand VARCHAR(50),
    warranty_period INT,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

-- Clothing Table
CREATE TABLE clothing (
    product_id INT PRIMARY KEY,
    size VARCHAR(10),
    color VARCHAR(30),
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

-- Orders Table
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    product_id INT,
    quantity INT NOT NULL,
    total_amt DOUBLE NOT NULL,
    payment_mode VARCHAR(20) CHECK (payment_mode IN ('Cash', 'Card', 'UPI')),
    status VARCHAR(20) CHECK (status IN ('Placed', 'Cancelled', 'Delivered')) DEFAULT 'Placed',
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);
ALTER TABLE user ADD COLUMN address VARCHAR(255);

select * from product;
select * from electronics;
select * from clothing;
