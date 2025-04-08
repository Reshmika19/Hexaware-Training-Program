# Courier Management System
This repository contains **two versions** of the Courier Management System project, each designed to demonstrate different approaches to data storage and system architecture.

---

## Versions Overview

### 1️. `CourierManagementSystem-Collection-Version`
- Data is managed using **Java Collection Framework** like `ArrayList`, `HashMap`, etc.
- Useful for understanding **core Java concepts** such as:
  - OOPs
  - Exception Handling
  - Interfaces & Abstraction
- Ideal for learning object interactions and system logic **without using a database**.

### 2️. `CourierManagementSystem-Database-Version`
- Fully integrated with **SQL Database**.
- Uses JDBC for real-time **CRUD operations**.
- Demonstrates how to connect Java applications with external databases using:
  - `db.properties` for configuration
  - `DBConnection` class for dynamic connections
- Implements **real-world features** like:
  - Courier tracking
  - Revenue reports
  - Employee management
  - Company-wise delivery stats

---

## Purpose

The goal of creating two versions is to help understand:
- How to manage data in-memory (Collections)
- How to transition and scale into **real-time, persistent storage** using databases.

Both versions follow clean architecture with proper package structuring, reusable services, and menu-driven console applications.

---

## Technologies Used
- Java (JDK 8+)
- MySQL
- JDBC
- OOPs Concepts
- Exception Handling

---

