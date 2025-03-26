-- Scope: Inner Queries, Non Equi Joins, Equi joins,Exist,Any,All
USE CourierDB;

-- 49. Find couriers that have a weight greater than the average weight of all couriers
SELECT CourierID, SenderName, ReceiverName, Weight , TrackingNumber , Deliverydate
FROM Courier 
WHERE Weight > (SELECT AVG(Weight) FROM Courier);

-- 50. Find the names of all employees who have a salary greater than the average salary:
SELECT *
FROM Employee 
WHERE Salary > (SELECT AVG(Salary) FROM Employee);

-- 51. Find the total cost of all courier services where the cost is less than the maximum cost
SELECT SUM(Cost) AS TotalServiceCost
FROM courierservices
WHERE Cost < (SELECT MAX(Cost) FROM courierservices);

-- 52. Find all couriers that have been paid for
SELECT c.*
FROM courier c
WHERE EXISTS (
    SELECT 1 
    FROM payment p 
    WHERE p.CourierID = c.CourierID
);

-- 53. Find the locations where the maximum payment amount was made
SELECT l.* , p.AMount from Location l
JOIN Payment p on l.LocationId = p.LocationID
WHERE Amount = (SELECT MAX(Amount) FROM payment);

-- 54. Find all couriers whose weight is greater than the weight of all couriers sent by a specific sender (e.g., 'SenderName'):
SELECT * 
FROM Courier 
WHERE Weight > ALL (SELECT Weight FROM Courier WHERE SenderName = 'John Doe');
