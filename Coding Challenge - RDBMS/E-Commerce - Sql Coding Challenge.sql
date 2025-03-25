-- 1. DB Creation
CREATE DATABASE ecommerce;
USE ecommerce;
SHOW DATABASES;

-- 2. Table Creation
CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    description TEXT,
    stockQuantity INT NOT NULL
);

CREATE TABLE cart (
    cart_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    product_id INT,
    quantity INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_price DECIMAL(10,2) NOT NULL,
    shipping_address VARCHAR(255) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);

CREATE TABLE order_items (
    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

SHOW TABLES;

-- Data Insertion

INSERT INTO products (name, description, price, stockQuantity) VALUES
('Laptop', 'High-performance laptop', 800.00, 10),
('Smartphone', 'Latest smartphone', 600.00, 15),
('Tablet', 'Portable tablet', 300.00, 20),
('Headphones', 'Noise-canceling', 150.00, 30),
('TV', '4K Smart TV', 900.00, 5),
('Coffee Maker', 'Automatic coffee maker', 50.00, 25),
('Refrigerator', 'Energy-efficient', 700.00, 10),
('Microwave Oven', 'Countertop microwave', 80.00, 15),
('Blender', 'High-speed blender', 70.00, 20),
('Vacuum Cleaner', 'Bagless vacuum cleaner', 120.00, 10);

SELECT * FROM products;

INSERT INTO customers (firstName, lastName, name, email, password, address) VALUES
('John', 'Doe', 'John Doe', 'johndoe@example.com', 'pass123', '123 Main St, City'),
('Jane', 'Smith', 'Jane Smith', 'janesmith@example.com', 'secure456', '456 Elm St, Town'),
('Robert', 'Johnson', 'Robert Johnson', 'robert@example.com', 'robert789', '789 Oak St, Village'),
('Sarah', 'Brown', 'Sarah Brown', 'sarah@example.com', 'sarah101', '101 Pine St, Suburb'),
('David', 'Lee', 'David Lee', 'david@example.com', 'david234', '234 Cedar St, District'),
('Laura', 'Hall', 'Laura Hall', 'laura@example.com', 'laura567', '567 Birch St, County'),
('Michael', 'Davis', 'Michael Davis', 'michael@example.com', 'michael890', '890 Maple St, State'),
('Emma', 'Wilson', 'Emma Wilson', 'emma@example.com', 'emma321', '321 Redwood St, Country'),
('William', 'Taylor', 'William Taylor', 'william@example.com', 'william432', '432 Spruce St, Province'),
('Olivia', 'Adams', 'Olivia Adams', 'olivia@example.com', 'olivia765', '765 Fir St, Territory');


SELECT * FROM customers;

ALTER TABLE customers 
ADD COLUMN firstName VARCHAR(50),
ADD COLUMN lastName VARCHAR(50),
ADD COLUMN address VARCHAR(255);

ALTER TABLE orders 
CHANGE COLUMN total_price totalAmount DECIMAL(10,2);
ALTER TABLE orders 
MODIFY COLUMN shipping_address VARCHAR(255) NULL;

INSERT INTO orders (customer_id, order_date, totalAmount, shipping_address) VALUES
(1, '2023-01-05', 1200.00, NULL),
(2, '2023-02-10', 900.00, NULL),
(3, '2023-03-15', 300.00, NULL),
(4, '2023-04-20', 150.00, NULL),
(5, '2023-05-25', 1800.00, NULL),
(6, '2023-06-30', 400.00, NULL),
(7, '2023-07-05', 700.00, NULL),
(8, '2023-08-10', 160.00, NULL),
(9, '2023-09-15', 140.00, NULL),
(10, '2023-10-20', 1400.00, NULL);
SELECT * FROM orders;

ALTER TABLE order_items 
ADD COLUMN itemAmount DECIMAL(10,2);

INSERT INTO order_items (order_id, product_id, quantity, itemAmount) VALUES
(1, 1, 2, 1600.00),
(1, 3, 1, 300.00),
(2, 2, 3, 1800.00),
(3, 5, 2, 1800.00),
(4, 4, 4, 600.00),
(4, 6, 1, 50.00),
(5, 1, 1, 800.00),
(5, 2, 2, 1200.00),
(6, 10, 2, 240.00),
(6, 9, 3, 210.00);

SELECT * FROM order_items;

INSERT INTO cart (customer_id, product_id, quantity) VALUES
(1, 1, 2),
(1, 3, 1),
(2, 2, 2),
(3, 4, 4),
(3, 5, 2),
(4, 6, 1),
(5, 1, 1),
(6, 10, 2),
(6, 9, 3),
(7, 7, 2);

SELECT * FROM cart;

SET SQL_SAFE_UPDATES = 0;
SET SQL_SAFE_UPDATES = 1;

-- 1. Update refrigerator product price to 800.
UPDATE products  
SET price = 800  
WHERE name = 'Refrigerator';

SELECT * FROM products WHERE name = 'Refrigerator';

-- 2. Remove all cart items for a specific customer.
DELETE FROM cart  
WHERE customer_id = 3;

SELECT * FROM cart WHERE customer_id = 3;

-- 3. Retrieve Products Priced Below $100.
SELECT * FROM products  
WHERE price < 100;

-- 4. Find Products with Stock Quantity Greater Than 5.
SELECT * FROM products  
WHERE stockQuantity > 5;

-- 5. Retrieve Orders with Total Amount Between $500 and $1000.
SELECT * FROM orders  
WHERE totalAmount BETWEEN 500 AND 1000;

-- 6. Find Products which name end with letter ‘r’.
SELECT * FROM products  
WHERE name LIKE '%r';

-- 7. Retrieve Cart Items for Customer 5.
SELECT * FROM cart  
WHERE customer_id = 5;

-- 8. Find Customers Who Placed Orders in 2023.
SELECT DISTINCT customers.customer_id, customers.name, customers.email , DATE(orders.order_date) AS Order_Date
FROM customers  
JOIN orders ON customers.customer_id = orders.customer_id  
WHERE YEAR(orders.order_date) = 2023;

-- 9. Determine the Minimum Stock Quantity for Each Product Category.
ALTER TABLE products  
ADD COLUMN category VARCHAR(50);

UPDATE products  
SET category = 'Electronics' WHERE name IN ('Laptop', 'Smartphone', 'Tablet', 'Headphones', 'TV');

UPDATE products  
SET category = 'Kitchen Appliance' WHERE name IN ('Coffee Maker', 'Microwave Oven', 'Blender');

UPDATE products  
SET category = 'Home Appliance' WHERE name IN ('Refrigerator', 'Vacuum Cleaner');


SELECT category, MIN(stockQuantity) AS min_stock  
FROM products  
GROUP BY category;

select * from products;

-- 10. Calculate the Total Amount Spent by Each Customer.
SELECT c.customer_id, c.firstName, c.lastName, SUM(o.totalAmount) AS total_spent  
FROM customers c
JOIN orders o ON c.customer_id = o.customer_id  
GROUP BY c.customer_id, c.firstName, c.lastName  
ORDER BY total_spent DESC;

-- 11. Find the Average Order Amount for Each Customer.
SELECT c.customer_id, c.name, AVG(o.totalAmount) AS avg_order_amount  
FROM customers c  
JOIN orders o ON c.customer_id = o.customer_id  
GROUP BY c.customer_id, c.name
ORDER BY avg_order_amount DESC;

-- 12. Count the Number of Orders Placed by Each Customer.
SELECT c.customer_id, c.name, COUNT(o.order_id) AS total_orders
FROM customers c
LEFT JOIN orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.name;

-- 13. Find the Maximum Order Amount for Each Customer.
SELECT c.customer_id, c.name, MAX(o.totalAmount) AS max_order_amount
FROM customers c
LEFT JOIN orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.name;

-- 14. Get Customers Who Placed Orders Totaling Over $1000.
SELECT c.customer_id, c.name, SUM(o.totalAmount) AS total_spent
FROM customers c
JOIN orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.name
HAVING SUM(o.totalAmount) > 1000;

-- 15. Subquery to Find Products Not in the Cart.
SELECT p.product_id, p.name  
FROM products p  
WHERE p.product_id NOT IN (SELECT DISTINCT c.product_id FROM cart c);

-- 16. Subquery to Find Customers Who Haven't Placed Orders.
INSERT INTO customers (name, email, password, firstName, lastName, address)  
VALUES ('Sam Wilson', 'sam@example.com', 'password123', 'Sam', 'Wilson', '456 Green St, City');

SELECT c.customer_id, c.firstName, c.lastName  
FROM customers c  
WHERE c.customer_id NOT IN (SELECT DISTINCT o.customer_id FROM orders o);

-- 17. Subquery to Calculate the Percentage of Total Revenue for a Product.
SELECT  
    p.product_id,  
    p.name,  
    (SUM(oi.itemAmount) / (SELECT SUM(itemAmount) FROM order_items) * 100) AS revenue_percentage  
FROM products p  
JOIN order_items oi ON p.product_id = oi.product_id  
GROUP BY p.product_id, p.name  
ORDER BY revenue_percentage DESC;

-- 18. Subquery to Find Products with Low Stock.
SELECT  
    product_id,  
    name,  
    stockQuantity  
FROM products  
WHERE stockQuantity < (SELECT AVG(stockQuantity) FROM products);

-- 19. Subquery to Find Customers Who Placed High-Value Orders.
SELECT  
    c.customer_id,  
    c.name,  
    c.email  
FROM customers c  
WHERE c.customer_id IN (  
    SELECT o.customer_id  
    FROM orders o  
    WHERE o.totalAmount > (SELECT AVG(totalAmount) FROM orders)  
);
