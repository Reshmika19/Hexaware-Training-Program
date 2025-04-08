# Courier Management System (Collection Version)

This project demonstrates a **Courier Management System** developed using Java's **Collection Framework**. It is structured to ensure modularity, separation of concerns, and clean code practices.

## Directory Structure

The following directory structure is followed in the application:

### `entity`
- Contains **entity classes** such as `CourierC`, `EmployeeC`, `UserC`, etc.
- These classes represent the core data models of the system.
- **Note:** Entity classes do **not** contain any business logic.

### `dao`
- Contains **interfaces** to define service functionalities (e.g., `ICourierAdminServiceCollection`, `ICourierUserServiceCollection`).
- Implementation classes (e.g., `CourierAdminServiceImpCollection`, `CourierUserServiceImpCollection`) define the actual logic using the **Collection Framework**.

### `exception`
- Holds **user-defined exceptions**, such as:
  - `InvalidEmployeeIdException`
  - `TrackingNumberNotFoundException`
- Used to handle application-specific error scenarios gracefully.

### `main`
- Contains various tasks to demonstrate core Java concepts in the context of this application.
- `MainModuleCollection` implements **Task 5 to Task 8** in a **menu-driven application** format, showcasing full system functionalities.

---

## Task Breakdown

| Task Number | Description                                 |
|-------------|---------------------------------------------|
| Task 1      | Control Flow Statements                     |
| Task 2      | Loops and Iteration                         |
| Task 3      | Arrays and Data Structures                  |
| Task 4      | Strings, 2D Arrays, User-defined Functions, HashMap |
| Task 5–8    | Implemented inside `MainModuleCollection.java` demonstrating full courier system features |

> **Note:** Task 9, which involves **database interaction**, is implemented separately and is available in  
> `CourierManagementSystem-Database-Version`.

---

##  Technologies Used

- Java (JDK 8+)
- Collection Framework (ArrayList, HashMap, etc.)
- Exception Handling
- Object-Oriented Programming

---

##  How to Run

1. Open the project in any Java IDE (like IntelliJ, Eclipse).
2. Navigate to `main/MainModuleCollection.java`.
3. Run the class to explore the menu-driven courier management application.

---

## Related Project Version

If you're looking for the version that uses **database integration**, refer to:

`/Code/CourierManagementSystem-Database-Version`

---