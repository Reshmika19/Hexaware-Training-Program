-- Task 4 : Inner Join,Full Outer Join, Cross Join, Left Outer Join,Right Outer Join
USE CourierDB;

-- 23. Retrieve Payments with Courier Information
SELECT p.PaymentID, p.CourierID, p.Amount, p.PaymentDate, 
       c.SenderName, c.ReceiverName , c.Weight , c.Status , c.TrackingNumber , c.DeliveryDate
FROM Payment p
INNER JOIN Courier c ON p.CourierID = c.CourierID;

-- 24. Retrieve Payments with Location Information
SELECT p.PaymentID, p.CourierID, p.LocationId, p.Amount, p.PaymentDate, 
       l.LocationName, l.Address
FROM Payment p
INNER JOIN Location l ON p.LocationId = l.LocationID;

-- 25. Retrieve Payments with Courier and Location Information
SELECT p.PaymentID, p.CourierID, p.LocationId, p.Amount, p.PaymentDate, 
       c.SenderName, c.ReceiverName, c.TrackingNumber, c.status ,
       l.LocationName, l.Address
FROM Payment p
INNER JOIN Courier c ON p.CourierID = c.CourierID
INNER JOIN Location l ON p.LocationId = l.LocationID;

-- 26. List all payments with courier details
SELECT p.PaymentID, p.CourierID, p.Amount, p.PaymentDate, 
       c.SenderName, c.ReceiverName, c.TrackingNumber, c.Status , c.DeliveryDate
FROM Payment p
LEFT JOIN Courier c ON p.CourierID = c.CourierID;

-- 27. Total payments received for each courier
SELECT p.CourierID, c.SenderName, c.ReceiverName, c.TrackingNumber, 
       SUM(p.Amount) AS TotalPayment
FROM Payment p
INNER JOIN Courier c ON p.CourierID = c.CourierID
GROUP BY p.CourierID, c.SenderName, c.ReceiverName, c.TrackingNumber;

-- 28. List payments made on a specific date
SELECT p.PaymentID, p.CourierID, p.LocationId, p.Amount, p.PaymentDate, 
       c.SenderName, c.ReceiverName, c.TrackingNumber
FROM Payment p
INNER JOIN Courier c ON p.CourierID = c.CourierID
WHERE p.PaymentDate = '2025-03-18';

-- 29. Get Courier Information for Each Payment
SELECT p.PaymentID, p.CourierID, p.LocationId, p.Amount, p.PaymentDate, 
       c.SenderName, c.SenderAddress, c.ReceiverName, c.ReceiverAddress, 
       c.Weight, c.Status, c.TrackingNumber, c.DeliveryDate
FROM Payment p
INNER JOIN Courier c ON p.CourierID = c.CourierID;

-- 30. Get Payment Details with Location
SELECT p.PaymentID, p.CourierID, p.Amount, p.PaymentDate, 
       l.LocationID, l.LocationName, l.Address
FROM Payment p
INNER JOIN Location l ON p.LocationID = l.LocationID;

-- 31. Calculating Total Payments for Each Courier
SELECT p.CourierID, SUM(p.Amount) AS TotalAmount
FROM Payment p
GROUP BY p.CourierID
ORDER BY TotalAmount DESC;

-- 32. List Payments Within a Date Range
SELECT * 
FROM Payment 
WHERE PaymentDate BETWEEN '2025-03-15' AND '2025-03-25';


-- 33. Retrieve a list of all users and their corresponding courier records, including cases where there are no matches on either side
SELECT U.UserID, U.Name,U.ContactNumber, C.CourierID, C.TrackingNumber, C.Status , C.DeliveryDate
FROM User U
LEFT JOIN Courier C ON U.UserID = C.SenderID
UNION
SELECT U.UserID, U.Name,U.ContactNumber, C.CourierID, C.TrackingNumber, C.Status , C.DeliveryDate
FROM User U
RIGHT JOIN Courier C ON U.UserID = C.SenderID;

-- 34. Retrieve a list of all couriers and their corresponding services, including cases where there are no matches on either side
SELECT C.CourierID, C.SenderName, C.ReceiverName, C.Weight, C.Status, 
       C.TrackingNumber, C.DeliveryDate, CS.ServiceID, CS.ServiceName, CS.Cost
FROM Courier C
LEFT JOIN CourierServices CS ON C.ServiceID = CS.ServiceID
UNION
SELECT C.CourierID, C.SenderName, C.ReceiverName, C.Weight, C.Status, 
       C.TrackingNumber, C.DeliveryDate, CS.ServiceID, CS.ServiceName, CS.Cost
FROM Courier C
RIGHT JOIN CourierServices CS ON C.ServiceID = CS.ServiceID;

-- 35. Retrieve a list of all employees and their corresponding payments, including cases where there are no matches on either side
SELECT E.EmployeeID, E.Name AS EmployeeName, P.PaymentID, P.Amount, P.PaymentDate
FROM Employee E
LEFT JOIN Courier C ON E.EmployeeID = C.EmployeeID
LEFT JOIN Payment P ON C.CourierID = P.CourierID
UNION
SELECT E.EmployeeID, E.Name AS EmployeeName, P.PaymentID, P.Amount, P.PaymentDate
FROM Employee E
RIGHT JOIN Courier C ON E.EmployeeID = C.EmployeeID
RIGHT JOIN Payment P ON C.CourierID = P.CourierID;

-- 36. List all users and all courier services, showing all possible combinations.
SELECT U.UserID, U.Name AS UserName, CS.ServiceID, CS.ServiceName, CS.Cost
FROM User U
CROSS JOIN CourierServices CS;

-- 37. List all employees and all locations, showing all possible combinations:
SELECT E.EmployeeID, E.Name AS EmployeeName,E.Email,E.contactNumber, L.LocationID, L.LocationName
FROM Employee E
CROSS JOIN Location L;

-- 38. Retrieve a list of couriers and their corresponding sender information (if available)
SELECT C.CourierID, C.TrackingNumber, C.Status, U.UserID, U.Name AS SenderName, U.Address AS SenderAddress
FROM Courier C
LEFT JOIN User U ON C.SenderName = U.Name;

-- 39. Retrieve a list of couriers and their corresponding receiver information (if available):
SELECT C.CourierID, C.TrackingNumber, C.Status, C.DeliveryDate ,
	U.UserID, U.Name AS ReceiverName, U.Address AS ReceiverAddress
FROM Courier C
LEFT JOIN User U ON C.ReceiverName = U.Name;

-- 40. Retrieve a list of couriers along with the courier service details (if available):
SELECT C.CourierID, C.TrackingNumber, C.Status, C.SenderName, C.ReceiverName, C.Weight, CS.ServiceID, CS.ServiceName, CS.Cost  
FROM Courier C  
LEFT JOIN CourierServices CS ON C.ServiceID = CS.ServiceID;

-- 41. Retrieve a list of employees and the number of couriers assigned to each employee:
SELECT 
    E.EmployeeID, E.Name, 
    COUNT(C.CourierID) AS TotalCouriersAssigned
FROM Employee E
LEFT JOIN Courier C ON E.EmployeeID = C.EmployeeID
GROUP BY E.EmployeeID, E.Name
ORDER BY TotalCouriersAssigned DESC;

-- 42. Retrieve a list of locations and the total payment amount received at each location:
SELECT  L.LocationID, L.LocationName, SUM(P.Amount) AS TotalPaymentReceived
FROM Location L
LEFT JOIN Payment P ON L.LocationID = P.LocationID
GROUP BY L.LocationID, L.LocationName
ORDER BY TotalPaymentReceived DESC;

-- 43. Retrieve all couriers sent by the same sender (based on SenderName).
SELECT SenderName, CourierID, TrackingNumber, ReceiverName, ReceiverAddress, Status, DeliveryDate 
FROM Courier 
ORDER BY SenderName, DeliveryDate DESC;

-- 44. List all employees who share the same role.
SELECT EmployeeID, Name, Role 
FROM Employee
WHERE Role IN (SELECT Role FROM Employee GROUP BY Role HAVING COUNT(*) > 1) 
ORDER BY Role, Name;

-- 45. Retrieve all payments made for couriers sent from the same location.
SELECT P.PaymentID, P.CourierID, P.Amount, P.PaymentDate, C.SenderAddress 
FROM Payment P 
JOIN Courier C ON P.CourierID = C.CourierID 
WHERE C.SenderAddress IN (
    SELECT SenderAddress 
    FROM Courier 
    GROUP BY SenderAddress 
    HAVING COUNT(*) > 1
) 
ORDER BY C.SenderAddress, P.PaymentDate;

-- 46. Retrieve all couriers sent from the same location (based on SenderAddress).
SELECT * FROM Courier WHERE SenderAddress IN (
    SELECT SenderAddress FROM Courier GROUP BY SenderAddress HAVING COUNT(*) > 1
);

-- 47. List employees and the number of couriers they have delivered:
SELECT EmployeeID, COUNT(*) AS NumberOfCouriersDelivered 
FROM Courier 
WHERE Status = 'Delivered' 
GROUP BY EmployeeID;

-- 48. Find couriers that were paid an amount greater than the cost of their respective courier services
SELECT c.CourierID, c.TrackingNumber, p.Amount AS PaymentAmount, cs.Cost AS ServiceCost 
FROM Payment p
JOIN Courier c ON p.CourierID = c.CourierID
JOIN CourierServices cs ON c.ServiceID = cs.ServiceID
WHERE p.Amount > cs.Cost;
