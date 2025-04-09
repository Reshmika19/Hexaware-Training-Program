# Order Management System

I was mapped to  **Order Management System** This system allows users and admins to manage products, place and cancel orders, and interact with a backend SQL database using JDBC.

---

## Project Structure
courierManagementSystem
 - ┣ dao              ➜ Contains interfaces and implementation for order & admin services  
 - ┣ entity           ➜ Plain Java classes (POJOs) for Product, User, Order
 - ┣ exception        ➜ Custom exception classes (e.g., UserNotFoundException)
 - ┣ util             ➜ Database utility classes for reading config and creating connections
 - ┣ main             ➜ Main class (`MainModule`) with a menu-driven UI

## Features Implemented

### User Functionality
- Create user account
- Login as user
- Place orders
- Cancel orders (with exception handling)
- View order history
- Update profile
- Delete account

### Admin Functionality
- Create admin account
- Login as admin
- Create and update products (Electronics & Clothing)
- View all users
- View all orders and user-specific orders
- View all products or filter by category

### Product Management
- Products are categorized as **Electronics** or **Clothing**
- Each product stores ID, name, description, price, stock quantity, etc.
- Subclass-specific fields (e.g., brand/warranty for electronics)
