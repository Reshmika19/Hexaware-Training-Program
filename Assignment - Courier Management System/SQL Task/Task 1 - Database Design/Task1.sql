-- Task 1 : DataBase Design

CREATE DATABASE CourierDB;
USE CourierDB;

-- User Table
CREATE TABLE User (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    ContactNumber VARCHAR(20),
    Address TEXT
);

-- Courier Table
CREATE TABLE Courier (
    CourierID INT PRIMARY KEY AUTO_INCREMENT,
    SenderName VARCHAR(255) NOT NULL,
    SenderAddress TEXT NOT NULL,
    ReceiverName VARCHAR(255) NOT NULL,
    ReceiverAddress TEXT NOT NULL,
    Weight DECIMAL(5,2) NOT NULL,
    Status VARCHAR(50) NOT NULL,
    TrackingNumber VARCHAR(20) UNIQUE NOT NULL,
    DeliveryDate DATE
);

-- Courier Services Table
CREATE TABLE CourierServices (
    ServiceID INT PRIMARY KEY AUTO_INCREMENT,
    ServiceName VARCHAR(100) NOT NULL,
    Cost DECIMAL(8,2) NOT NULL
);

-- Employee Table
CREATE TABLE Employee (
    EmployeeID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE NOT NULL,
    ContactNumber VARCHAR(20) NOT NULL,
    Role VARCHAR(50) NOT NULL,
    Salary DECIMAL(10,2) NOT NULL
);

-- Location Table
CREATE TABLE Location (
    LocationID INT PRIMARY KEY AUTO_INCREMENT,
    LocationName VARCHAR(100) NOT NULL,
    Address TEXT NOT NULL
);

-- Payment Table
CREATE TABLE Payment (
    PaymentID INT PRIMARY KEY AUTO_INCREMENT,
    CourierID INT,
    LocationID INT,
    Amount DECIMAL(10,2) NOT NULL,
    PaymentDate DATE NOT NULL,
    FOREIGN KEY (CourierID) REFERENCES Courier(CourierID),
    FOREIGN KEY (LocationID) REFERENCES Location(LocationID)
);

-- Insert data into User table
INSERT INTO User (Name, Email, Password, ContactNumber, Address) 
VALUES 
('John Doe', 'john@example.com', 'password123', '9876543210', '123 Main St'),
('Alice Smith', 'alice@example.com', 'alicepass', '8765432109', '456 Elm St'),
('Michael Brown', 'michael@example.com', 'mikepass', '7654321098', '789 Oak St'),
('Emma Wilson', 'emma@example.com', 'emmapass', '6543210987', '321 Pine St'),
('Daniel Lee', 'daniel@example.com', 'danpass', '5432109876', '654 Cedar St'),
('Sophia Green', 'sophia@example.com', 'sophiapass', '4321098765', '987 Maple St'),
('William Clark', 'william@example.com', 'willpass', '3210987654', '258 Birch St'),
('Olivia Adams', 'olivia@example.com', 'oliviapass', '2109876543', '369 Walnut St'),
('James Turner', 'james@example.com', 'jamespass', '1098765432', '741 Cherry St'),
('Charlotte Evans', 'charlotte@example.com', 'charpass', '1987654321', '852 Willow St');

-- Insert data into Courier table
INSERT INTO Courier (SenderName, SenderAddress, ReceiverName, ReceiverAddress, Weight, Status, TrackingNumber, DeliveryDate) 
VALUES 
('John Doe', '123 Main St', 'Alice Smith', '456 Elm St', 2.5, 'In Transit', 'TRK10001', '2024-03-20'),
('Alice Smith', '456 Elm St', 'Michael Brown', '789 Oak St', 1.2, 'Delivered', 'TRK10002', '2024-03-18'),
('Michael Brown', '789 Oak St', 'Emma Wilson', '321 Pine St', 3.0, 'Pending', 'TRK10003', '2024-03-22'),
('Emma Wilson', '321 Pine St', 'Daniel Lee', '654 Cedar St', 2.8, 'In Transit', 'TRK10004', '2024-03-21'),
('Daniel Lee', '654 Cedar St', 'Sophia Green', '987 Maple St', 4.1, 'Shipped', 'TRK10005', '2024-03-19'),
('Sophia Green', '987 Maple St', 'William Clark', '258 Birch St', 1.9, 'Delivered', 'TRK10006', '2024-03-17'),
('William Clark', '258 Birch St', 'Olivia Adams', '369 Walnut St', 2.2, 'Pending', 'TRK10007', '2024-03-23'),
('Olivia Adams', '369 Walnut St', 'James Turner', '741 Cherry St', 3.5, 'Shipped', 'TRK10008', '2024-03-20'),
('James Turner', '741 Cherry St', 'Charlotte Evans', '852 Willow St', 2.7, 'In Transit', 'TRK10009', '2024-03-25'),
('Charlotte Evans', '852 Willow St', 'John Doe', '123 Main St', 5.0, 'Pending', 'TRK10010', '2024-03-24');

-- Insert data into CourierServices table
INSERT INTO CourierServices (ServiceName, Cost) 
VALUES 
('Standard Delivery', 100.00),
('Express Delivery', 250.00),
('Same-Day Delivery', 500.00),
('Overnight Shipping', 400.00),
('International Shipping', 800.00),
('Priority Mail', 300.00),
('Economy Shipping', 150.00),
('Bulk Shipping', 600.00),
('Fragile Item Handling', 350.00),
('Heavy Cargo Transport', 1000.00);

-- Insert data into Employee table
INSERT INTO Employee (Name, Email, ContactNumber, Role, Salary) 
VALUES 
('David Wilson', 'david@example.com', '9876123456', 'Delivery Agent', 25000.00),
('Emma Brown', 'emma@example.com', '8765123498', 'Manager', 50000.00),
('Noah White', 'noah@example.com', '7654321890', 'Warehouse Staff', 20000.00),
('Sophia Johnson', 'sophia@example.com', '6543217890', 'Delivery Agent', 26000.00),
('Mason Scott', 'mason@example.com', '5432167890', 'Supervisor', 40000.00),
('Olivia Davis', 'olivia@example.com', '4321098765', 'HR Executive', 35000.00),
('Ethan Carter', 'ethan@example.com', '3210987654', 'IT Specialist', 55000.00),
('Isabella Lewis', 'isabella@example.com', '2109876543', 'Finance Analyst', 60000.00),
('Liam Parker', 'liam@example.com', '1098765432', 'Field Staff', 23000.00),
('Ava Martinez', 'ava@example.com', '1987654321', 'Customer Support', 28000.00);

-- Insert data into Location table
INSERT INTO Location (LocationName, Address) 
VALUES 
('Warehouse A', '789 Industrial Road'),
('Warehouse B', '321 Commercial St'),
('Hub Center', '555 Logistics Park'),
('Main Office', '100 Business Ave'),
('Sorting Facility 1', '222 Shipping Lane'),
('Sorting Facility 2', '333 Distribution St'),
('Retail Store 1', '444 Market Square'),
('Retail Store 2', '555 Plaza Blvd'),
('Drop-off Point A', '666 Highway Exit 1'),
('Drop-off Point B', '777 Main St');

-- Insert data into Payment table
INSERT INTO Payment (CourierID, LocationID, Amount, PaymentDate) 
VALUES 
(1, 1, 100.00, '2024-03-21'),
(2, 2, 250.00, '2024-03-19'),
(3, 3, 500.00, '2024-03-22'),
(4, 4, 400.00, '2024-03-20'),
(5, 5, 800.00, '2024-03-18'),
(6, 6, 300.00, '2024-03-23'),
(7, 7, 150.00, '2024-03-17'),
(8, 8, 600.00, '2024-03-25'),
(9, 9, 350.00, '2024-03-24'),
(10, 10, 1000.00, '2024-03-26');

-- Insert 10 more rows into User table
INSERT INTO User (Name, Email, Password, ContactNumber, Address) 
VALUES 
('Ethan Carter', 'ethan@example.com', 'ethanpass', '9876543100', '741 Hill St'),
('Isabella Lewis', 'isabella@example.com', 'isapass', '8765432200', '852 Valley St'),
('Liam Parker', 'liam@example.com', 'liampass', '7654321300', '963 River St'),
('Ava Martinez', 'ava@example.com', 'avapass', '6543210400', '159 Lake St'),
('Mason Scott', 'mason@example.com', 'masonpass', '5432109500', '357 Bay St'),
('Sophia Johnson', 'sophia12@example.com', 'sophiapass', '4321098600', '246 Ocean St'),
('Olivia Davis', 'olivia4@example.com', 'oliviapass', '3210987700', '753 Forest St'),
('David Wilson', 'david@example.com', 'davidpass', '2109876800', '159 Canyon St'),
('Emma Brown', 'emma1@example.com', 'emmapass', '1098765900', '357 Meadow St'),
('Noah White', 'noah1@example.com', 'noahpass', '1987654000', '951 Cliff St');

-- Insert 10 more rows into Courier table
INSERT INTO Courier (SenderName, SenderAddress, ReceiverName, ReceiverAddress, Weight, Status, TrackingNumber, DeliveryDate) 
VALUES 
('Ethan Carter', '741 Hill St', 'Isabella Lewis', '852 Valley St', 3.2, 'In Transit', 'TRK10011', '2024-03-26'),
('Isabella Lewis', '852 Valley St', 'Liam Parker', '963 River St', 2.5, 'Delivered', 'TRK10012', '2024-03-24'),
('Liam Parker', '963 River St', 'Ava Martinez', '159 Lake St', 4.1, 'Pending', 'TRK10013', '2024-03-28'),
('Ava Martinez', '159 Lake St', 'Mason Scott', '357 Bay St', 1.8, 'In Transit', 'TRK10014', '2024-03-27'),
('Mason Scott', '357 Bay St', 'Sophia Johnson', '246 Ocean St', 5.0, 'Shipped', 'TRK10015', '2024-03-25'),
('Sophia Johnson', '246 Ocean St', 'Olivia Davis', '753 Forest St', 3.6, 'Delivered', 'TRK10016', '2024-03-23'),
('Olivia Davis', '753 Forest St', 'David Wilson', '159 Canyon St', 2.9, 'Pending', 'TRK10017', '2024-03-29'),
('David Wilson', '159 Canyon St', 'Emma Brown', '357 Meadow St', 3.3, 'Shipped', 'TRK10018', '2024-03-26'),
('Emma Brown', '357 Meadow St', 'Noah White', '951 Cliff St', 2.7, 'In Transit', 'TRK10019', '2024-03-30'),
('Noah White', '951 Cliff St', 'John Doe', '123 Main St', 4.5, 'Pending', 'TRK10020', '2024-03-31');

-- Insert 10 more rows into CourierServices table
INSERT INTO CourierServices (ServiceName, Cost) 
VALUES 
('Express Plus', 600.00),
('Eco-Friendly Delivery', 350.00),
('Two-Day Delivery', 450.00),
('Weekend Special', 400.00),
('Subscription Shipping', 700.00),
('Local Express', 200.00),
('Business Priority', 900.00),
('Secure Package', 550.00),
('Air Freight', 1500.00),
('Sea Cargo', 1200.00);

-- Insert 10 more rows into Employee table
INSERT INTO Employee (Name, Email, ContactNumber, Role, Salary) 
VALUES 
('Lucas Foster', 'lucas@example.com', '9876000001', 'Delivery Agent', 26000.00),
('Harper Reed', 'harper@example.com', '8765000002', 'Manager', 52000.00),
('Daniel Evans', 'daniel@example.com', '7654000003', 'Warehouse Staff', 22000.00),
('Evelyn Scott', 'evelyn@example.com', '6543000004', 'Delivery Agent', 27000.00),
('Henry Green', 'henry@example.com', '5432000005', 'Supervisor', 42000.00),
('Lily Baker', 'lily@example.com', '4321000006', 'HR Executive', 36000.00),
('Owen King', 'owen@example.com', '3210000007', 'IT Specialist', 57000.00),
('Aria Ross', 'aria@example.com', '2100000008', 'Finance Analyst', 61000.00),
('Jack Turner', 'jack@example.com', '1090000009', 'Field Staff', 24000.00),
('Grace Hall', 'grace@example.com', '1980000010', 'Customer Support', 29000.00);

-- Insert 10 more rows into Location table
INSERT INTO Location (LocationName, Address) 
VALUES 
('Dispatch Center A', '111 Express St'),
('Dispatch Center B', '222 Rapid Ave'),
('Return Hub', '333 Refund Blvd'),
('Customer Pickup Zone', '444 Grab St'),
('Overseas Hub', '555 International Ave'),
('Regional Sorting Hub', '666 Zone 3 St'),
('Packaging Center', '777 Secure St'),
('Express Delivery Office', '888 Speedy Blvd'),
('Drone Delivery Hub', '999 Future St'),
('Logistics HQ', '1010 Control St');

-- Insert 10 more rows into Payment table
INSERT INTO Payment (CourierID, LocationID, Amount, PaymentDate) 
VALUES 
(11, 1, 110.00, '2024-03-27'),
(12, 2, 260.00, '2024-03-25'),
(13, 3, 150.00, '2024-03-29'),
(14, 4, 500.00, '2024-03-28'),
(15, 5, 700.00, '2024-03-26'),
(16, 6, 300.00, '2024-03-24'),
(17, 7, 900.00, '2024-03-30'),
(18, 8, 550.00, '2024-03-27'),
(19, 9, 1500.00, '2024-03-31'),
(20, 10, 1200.00, '2024-04-01');

-- View all records from the User table
SELECT * FROM User;

-- View all records from the Courier table
SELECT * FROM Courier;

-- View all records from the CourierServices table
SELECT * FROM CourierServices;

-- View all records from the Employee table
SELECT * FROM Employee;

-- View all records from the Location table
SELECT * FROM Location;

-- View all records from the Payment table
SELECT * FROM Payment;

SHOW TABLES;

SELECT TABLE_NAME, COLUMN_NAME, CONSTRAINT_NAME, REFERENCED_TABLE_NAME, REFERENCED_COLUMN_NAME 
FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE 
WHERE TABLE_SCHEMA = 'CourierDB' AND REFERENCED_TABLE_NAME IS NOT NULL;

INSERT INTO Courier (SenderName, SenderAddress, ReceiverName, ReceiverAddress, Weight, Status, TrackingNumber, DeliveryDate)
VALUES 
('Alice Johnson', '123 Street, NY', 'Bob Smith', '456 Lane, CA', 2.5, 'In Transit', 'TRK1001', '2025-03-18'),
('Alice Johnson', '123 Street, NY', 'Charlie Brown', '789 Road, TX', 3.2, 'Pending', 'TRK1002', '2025-03-19'),
('Michael Scott', 'Scranton, PA', 'Jim Halpert', 'Stamford, CT', 5.0, 'Delivered', 'TRK1003', '2025-03-16'),
('Pam Beesly', 'Scranton, PA', 'Dwight Schrute', 'Schrute Farms, PA', 4.8, 'Delivered', 'TRK1004', '2025-03-17'),
('Stanley Hudson', 'Scranton, PA', 'Phyllis Vance', 'St. Louis, MO', 3.5, 'Pending', 'TRK1005', NULL);

INSERT INTO Courier (SenderName, SenderAddress, ReceiverName, ReceiverAddress, Weight, Status, TrackingNumber, DeliveryDate) 
VALUES 
('David Lee', '987 Park, TX', 'Eva Green', '321 Blvd, CA', 1.8, 'Delivered', 'TRK2006', '2025-03-17'),
('David Lee', '987 Park, TX', 'Sam Wilson', '654 Ave, FL', 2.1, 'Delivered', 'TRK2007', '2025-03-17'),
('David Lee', '987 Park, TX', 'Sarah Connor', 'Skynet, CA', 2.8, 'Delivered', 'TRK2008', '2025-03-17'),
('David Lee', '987 Park, TX', 'John Wick', 'Continental Hotel, NY', 3.0, 'Delivered', 'TRK2009', '2025-03-18'),
('David Lee', '987 Park, TX', 'Luke Skywalker', 'Tatooine, Galaxy', 4.2, 'Pending', 'TRK2010', NULL);

INSERT INTO Payment (CourierID, LocationId, Amount, PaymentDate)
VALUES 
(1, 1, 120.50, '2025-03-17'),
(1, 2, 98.75, '2025-03-18'),
(2, 3, 85.30, '2025-03-19'),
(2, 4, 102.25, '2025-03-20'),
(3, 5, 110.00, '2025-03-21');

INSERT INTO Courier (SenderName, SenderAddress, ReceiverName, ReceiverAddress, Weight, Status, TrackingNumber, DeliveryDate)
VALUES 
('Olivia Adams', '555 Elm St, TX', 'Chris Nolan', '777 Sunset Blvd, CA', 4.5, 'Pending', 'TRK3001', NULL),
('Ethan Carter', '789 Oak Rd, FL', 'Sophia Turner', '999 Beach Dr, NY', 5.2, 'In Transit', 'TRK3002', '2025-03-20'),
('Tom Hardy', '100 Hollywood Blvd, LA', 'Leo DiCaprio', '101 Sunset Blvd, LA', 6.0, 'Pending', 'TRK3003', NULL),
('Robert Downey', '200 Stark Tower, NY', 'Chris Evans', '300 Avengers HQ, NY', 7.5, 'Pending', 'TRK3004', NULL),
('Scarlett Johansson', '400 Black Widow St, NY', 'Jeremy Renner', '500 Hawkeye Ave, NY', 3.9, 'In Transit', 'TRK3005', '2025-03-21');

INSERT INTO Courier (SenderName, SenderAddress, ReceiverName, ReceiverAddress, Weight, Status, TrackingNumber, DeliveryDate)
VALUES 
('Mark Evans', '888 Pine St, CA', 'Natalie Brooks', '222 River Rd, TX', 3.7, 'Scheduled', 'TRK4001', CURDATE()),
('Jessica Parker', '999 Elm St, TX', 'Matt Damon', '111 Ocean Ave, CA', 4.2, 'Scheduled', 'TRK4002', CURDATE()),
('Brad Pitt', '222 Hollywood Blvd, CA', 'Angelina Jolie', '333 Sunset Blvd, LA', 5.0, 'Scheduled', 'TRK4003', CURDATE()),
('Will Smith', '555 Hancock St, NY', 'Margot Robbie', '666 Gotham St, NY', 6.1, 'Scheduled', 'TRK4004', CURDATE()),
('Johnny Depp', '777 Caribbean St, FL', 'Orlando Bloom', '888 Pirate Bay, FL', 7.3, 'Scheduled', 'TRK4005', CURDATE());

INSERT INTO Payment (CourierID, LocationId, Amount, PaymentDate)
VALUES 
(2, 3, 75.00, '2025-03-17'),
(3, 4, 99.99, '2025-03-18'),
(4, 5, 120.50, '2025-03-19'),
(5, 1, 85.75, '2025-03-20'),
(6, 2, 150.30, '2025-03-21');

SELECT * FROM Courier;
SELECT * FROM Payment order by PaymentDate;
SELECT * FROM Employee;






