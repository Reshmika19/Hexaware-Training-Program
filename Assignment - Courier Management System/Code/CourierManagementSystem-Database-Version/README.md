# Courier Management System (Database Version)

This is the **actual project** with real-time SQL database integration to manage courier services. It is designed as a **crystal-clear, scalable, and real-time application** for both Admin and User operations.

---

## Task 9 – Database Interaction

1. `DBConnection` class reads from `db.properties` and connects to SQL DB.
2. `CourierServiceDb` class in `dao` package uses this connection.
3. Includes methods to insert, update, cancel, and retrieve courier data.
4. Supports reports like delivery history, status, and revenue.

---

## Features Implemented

### User Functions
- `createUser()`, `loginUser()`, `placeCourier()`, `getOrderStatus()`, `cancelOrder()`, `viewOrderHistory()`

### Admin Functions
- `viewAllOrders()`, `updateCourierStatus()`, `viewAllPayments()`, `createEmployee()`, `createLocation()`, `createCourierCompany()`, `viewAllUsers()`, `viewAllLocations()`, `viewAllEmployees()`, `viewAllCourierCompanies()`, `assignEmployeeLocationCompanyToCourier()`, `viewCourierCompanyReport()`, `deleteEmployee()`, `deleteLocation()`, `deleteCourierCompanyCascade()`

---


## Directory Structure

The following directory structure is followed in the application:

### `entity`
- Contains entity classes such as Courier, User, Employee, Location, CourierCompany, and Payment.
- These classes represent the core data models of the system.
- **Note:** Entity classes do **not** contain any business logic.

### `dao`
- Contains **interfaces** to define service functionalities (e.g., `ICourierAdminService`, `ICourierUserService`).
- Implementation classes (e.g., `CourierAdminServiceImp`, `CourierUserServiceImp` , `CourierServiceDB`) .
- These classes perform actual database operations using JDBC (SQL).

### `exception`
- Holds **user-defined exceptions**, such as:
  - `InvalidEmployeeIdException`
  - `UserNotFoundException`
  - `TrackingNumberNotFoundException`
- Used to handle application-specific error scenarios gracefully.

### `main`
- Contains the main class CourierApp.java, which serves as the entry point to the system
- Implements a menu-driven console interface for both admin and user operations.
- Also includes TestDB.java for checking DB connection setup.

### `util`
- Contains the DBConnection utility class used for establishing SQL database connections.
- This class reads database credentials from the db.properties file inside the resources folder.

---

## Technologies Used

- Java (JDK 8+)
- SQL
- Exception Handling
- Object-Oriented Programming

---

## How to Run

1. Open the project in any Java IDE (like IntelliJ, Eclipse).
2. Navigate to `main/CourierApp.java`.
3. Run the class to explore the menu-driven courier management application.

---

## resources/db.properties

- Contains the database connection details like URL, username, and password.
- Used by `DBConnection` to establish connection dynamically.

        -  db.url=jdbc:mysql://localhost:3306/db_name  
        -  db.user=root  
        -  db.password=your_password  
        -  db.driver=com.mysql.cj.jdbc.Driver

- You can update db_name, user, and password as per your local MySQL setup.
- Make sure MySQL server is running and the database is created before running the application.


---

## Related Project Version

If you're looking for the version that uses **Collection Framework**, refer to:  
`/Code/CourierManagementSystem-Collection-Version`
