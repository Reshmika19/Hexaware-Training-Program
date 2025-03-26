-- Task2 : Select,Where
USE CourierDB;

-- 1. List all customers
SELECT * FROM User;

-- 2. List all orders for a specific customer
SELECT * FROM Courier WHERE SenderName = 'David Lee';

-- 3. List all Courier
SELECT * FROM Courier;

-- 4. List all packages for a specific order:
SELECT * FROM Courier WHERE TrackingNumber = 'TRK10012';

-- 5. List all deliveries for a specific courier:
SELECT * FROM Courier WHERE Status = 'Delivered';

-- 6. List all undelivered packages
SELECT * FROM Courier WHERE Status != 'Delivered';
-- SELECT * FROM Courier WHERE Status IN ('Pending', 'In Transit');

-- 7. List all packages that are scheduled for delivery today
SELECT * FROM Courier WHERE DeliveryDate = CURDATE();

-- 8. List all packages with a specific status:
SELECT * FROM Courier WHERE Status = 'Pending';

-- 9. Calculate the total number of packages for each courier.
SELECT SenderName, COUNT(*) AS TotalPackages FROM Courier GROUP BY SenderName;

-- 10. Find the average delivery time for each courier
SELECT c.SenderName, AVG(ABS(DATEDIFF(c.DeliveryDate, p.PaymentDate))) AS AvgDeliveryTime 
FROM Courier c
JOIN Payment p ON c.CourierID = p.CourierID
WHERE c.DeliveryDate IS NOT NULL AND p.PaymentDate IS NOT NULL
GROUP BY c.SenderName;

-- 11. List all packages with a specific weight range:
SELECT * FROM Courier WHERE Weight BETWEEN 2.0 AND 4.0;

-- 12. Retrieve employees whose names contain 'John'
SELECT * FROM Employee WHERE Name LIKE '%John%';

-- 13. Retrieve all courier records with payments greater than $50.
SELECT c.*
FROM Courier c
JOIN Payment p ON c.CourierID = p.CourierID
WHERE p.Amount > 50;
