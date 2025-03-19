CREATE DATABASE FinanceDB;
USE FinanceDB;

-- Create the Users Table
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

-- Create the ExpenseCategories Table
CREATE TABLE ExpenseCategories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL UNIQUE
);

-- Create the Expenses Table
CREATE TABLE Expenses (
    expense_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    category_id INT NOT NULL,
    date DATE NOT NULL,
    description TEXT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES ExpenseCategories(category_id) ON DELETE CASCADE
);

SHOW TABLES;

-- Inserting values on Table
INSERT INTO Users (username, password, email) VALUES
('john_doe', 'password123', 'john.doe@email.com'),
('jane_smith', 'securepass', 'jane.smith@email.com'),
('mike_jones', 'mikepass', 'mike.jones@email.com'),
('emma_watson', 'emma123', 'emma.watson@email.com'),
('david_wilson', 'dwilson@pass', 'david.wilson@email.com'),
('lucas_brown', 'lucas456', 'lucas.brown@email.com'),
('olivia_clark', 'olivia@safe', 'olivia.clark@email.com'),
('sophia_martin', 'sophiamart', 'sophia.martin@email.com'),
('liam_thomas', 'liamthomas@12', 'liam.thomas@email.com'),
('charlotte_james', 'charljames', 'charlotte.james@email.com'),
('daniel_anderson', 'danielpass', 'daniel.anderson@email.com'),
('amelia_white', 'amelia@white', 'amelia.white@email.com'),
('jackson_harris', 'jackson_harris123', 'jackson.harris@email.com'),
('grace_walker', 'gracewalk', 'grace.walker@email.com'),
('henry_robinson', 'henryrob', 'henry.robinson@email.com');

INSERT INTO ExpenseCategories (category_name) VALUES
('Food & Dining'),
('Transportation'),
('Utilities'),
('Entertainment'),
('Healthcare'),
('Education'),
('Groceries'),
('Shopping'),
('Travel'),
('Fitness & Sports'),
('Insurance'),
('Investment'),
('Rent'),
('Miscellaneous'),
('Subscriptions');

INSERT INTO Expenses (user_id, amount, category_id, date, description) VALUES
(1, 120.50, 1, '2024-03-10', 'Dinner at a restaurant'),
(2, 45.00, 2, '2024-03-11', 'Taxi fare'),
(3, 80.75, 3, '2024-03-12', 'Electricity bill payment'),
(4, 200.00, 4, '2024-03-13', 'Movie tickets and snacks'),
(5, 150.00, 5, '2024-03-14', 'Doctor consultation fee'),
(6, 500.00, 6, '2024-03-15', 'Online course purchase'),
(7, 75.00, 7, '2024-03-16', 'Grocery shopping'),
(8, 250.00, 8, '2024-03-17', 'New clothing items'),
(9, 300.00, 9, '2024-03-18', 'Flight ticket booking'),
(10, 100.00, 10, '2024-03-19', 'Gym membership renewal'),
(11, 1200.00, 11, '2024-03-20', 'Annual health insurance premium'),
(12, 350.00, 12, '2024-03-21', 'Stock market investment'),
(13, 800.00, 13, '2024-03-22', 'Monthly house rent'),
(14, 60.00, 14, '2024-03-23', 'Miscellaneous expenses'),
(15, 20.00, 15, '2024-03-24', 'Netflix subscription');

INSERT INTO Users (username, password, email) VALUES
('ethan_wright', 'ethanpass', 'ethan.wright@email.com'),
('zoe_taylor', 'zoetaylor123', 'zoe.taylor@email.com'),
('ryan_cooper', 'ryan1234', 'ryan.cooper@email.com'),
('nora_lee', 'nora456', 'nora.lee@email.com'),
('mason_scott', 'mason@secure', 'mason.scott@email.com'),
('stella_baker', 'stellabake', 'stella.baker@email.com'),
('logan_hall', 'loganhallpass', 'logan.hall@email.com'),
('harper_evans', 'harperevans1', 'harper.evans@email.com'),
('lucas_adams', 'lucasadams', 'lucas.adams@email.com'),
('victoria_green', 'victoriagreen', 'victoria.green@email.com');

INSERT INTO Expenses (user_id, amount, category_id, date, description) VALUES
(16, 55.00, 1, '2024-03-25', 'Lunch at a new cafe'),
(17, 30.00, 2, '2024-03-26', 'Bus fare for a week'),
(18, 95.00, 3, '2024-03-27', 'Water and internet bill payment'),
(19, 180.00, 4, '2024-03-28', 'Concert ticket purchase'),
(20, 90.00, 5, '2024-03-29', 'Pharmacy medicine purchase'),
(21, 700.00, 6, '2024-03-30', 'Certification course enrollment'),
(22, 50.00, 7, '2024-03-31', 'Supermarket grocery shopping'),
(23, 400.00, 8, '2024-04-01', 'New shoes and handbag'),
(24, 270.00, 9, '2024-04-02', 'Train ticket for a business trip'),
(25, 150.00, 10, '2024-04-03', 'Yoga class subscription');

SELECT * FROM Users;
SELECT * FROM Expenses;
SELECT * FROM ExpenseCategories;

SELECT e.category_id, c.category_id 
FROM Expenses e 
LEFT JOIN ExpenseCategories c ON e.category_id = c.category_id 
WHERE c.category_id IS NULL;


