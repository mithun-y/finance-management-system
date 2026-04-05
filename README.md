# Finance Management System

## 📌 Description

A Spring Boot-based backend application to manage financial records including 
income and expenses. It supports authentication, role-based access control(RBAC), dashboard analytics, 
pagination, and soft delete functionality.


### Important Information
If the database is completely empty(First time using the application) a default admin will be automatically added to the database, 
so this admin is authorised to create other admin, analyst and viewers.

Default admin credentials
**email: admin@gmail.com <br/>
password: password**

Login using these credentials, and later we can delete the default admin by other created admins.

---

## 🔐 Authorization

This application uses **JWT-based authentication** and **role-based access control**.

### 🔑 Roles & Permissions

- **ADMIN**
    - Full access to all APIs
    - Can manage users and financial records

- **ANALYST**
    - Access to dashboard APIs
    - Can view and filter financial records for analysis
    - Read-only access (no modification allowed)

- **VIEWER**
    - Access only to dashboard APIs
    - Read-only access (no modification allowed)

### 🔒 Password Change Authorization

- A user can change **only their own password**
- The **ADMIN** also has permission to change any user's password

This is enforced using role-based authorization and ownership checks.

---
### 🪪 Authentication

After successful login, a JWT token is returned.
Example:
{
"email": "admin@gmail.com",
"role": "ADMIN",
"token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJyb2xlIjoiQURNSU4iLCJpYXQiOjE3NzUzNzU2NzcsImV4cCI6MTc3NTQxMTY3N30.qcJmdsNdaxOFeFJNk_1ci3kIlHyBKls34YCDoXKgFVmz0OknMehppiB2EsZ-4-DF",
"type": "Bearer"
}

---

### 📌 How to Use Token

Include the token in the request header:

---


## 🔤 Enum Handling (Case Insensitive)

The application supports case-insensitive input for enum fields such as:

- Role (`ADMIN`, `ANALYST`, `VIEWER`)
- Status (`ACTIVE`, `INACTIVE`)
- Record Type (`INCOME`, `EXPENSE`)

This means users can provide values in any case format:

Examples:
- `admin`, `Admin`, `ADMIN` → all treated as `ADMIN`
- `income`, `Income`, `INCOME` → all treated as `INCOME`

The system internally normalizes input to uppercase before processing.

---

## 🚀 Features

* User Authentication (JWT-based)
* Role-based Authorization (ADMIN, ANALYST, VIEWER)
* CRUD operations for financial records
* Pagination and Sorting
* Search with filters (type, category, date)
* Soft Delete functionality
* Dashboard analytics (monthly trends, category summary)
* Proper Error Responses
* Unit tests
---

## 🛠️ Tech Stack

* Java 21
* Spring Boot
* Spring Security (JWT)
* Spring Data JPA (Hibernate)
* PostgreSQL
* Maven

---

## ⚙️ Setup

1. Clone the repository
   `git clone https://github.com/mithun-y/finance-management-system.git`

2. Navigate to project
   `cd FinanceManagementSystem`

3. Configure database in `application.properties`

4. Run the application
   `mvn spring-boot:run`

---

## 🔑 API Endpoints

### Auth

### user login 
* POST `http://localhost:8080/auth/login` 

### Register a new user
* POST `http://localhost:8080/users`

### User

### Gets all the users
* GET `http://localhost:8080/users` 

###  Updates a specific user
* PUT `http://localhost:8080/users/{id}/update` 

### Updates the status of the user
* PATCH `http://localhost:8080/users/{id}/status` 

###  Updates the password of the user
* PUT `http://localhost:8080/users/{id}/password` 


### Records

### creates a new record
* POST `http://localhost:8080/records  

### soft deletes a record
* DELETE ` http://localhost:8080/records/{id}`  

### restores a record
* GET `http://localhost:8080/records/{id}/restore`

### Gets the records (pagination implemented)
* GET http://localhost:8080/records?page=2&size=5&sortBy=amount&sortDir=asc

### GET request to get a record
* GET `http://localhost:8080/records/{id}`

### GET request to get the list of records filter by type -> INCOME/EXPENSE
* GET `http://localhost:8080/records/type?type=income

### GET request to get the list of records filter by category
* GET `http://localhost:8080/records/category?category=Freelance`

### GET request to get the list of records filter by Date Range
* GET `http://localhost:8080/records/date-range?start=2026-01-01&end=2026-04-08`

### Update request to update a record
* PUT `http://localhost:8080/records/{id}`

### Dashboard

### GET request to get Financial record summary
* GET `http://localhost:8080/dashboard/summary`

### GET request to get data by category
* GET `http://localhost:8080/dashboard/category`

### GET request to get recent financial records
* GET `http://localhost:8080/dashboard/recent`

### GET request to get overview on montly expense and income
* GET `http://localhost:8080/dashboard/monthly-trends`


## 🔐 Security

* JWT-based authentication
* Role-based access control using `@PreAuthorize`

---

## 📌 Future Improvements

* Redis-based rate limiting
* Audit logging
* Export to CSV/PDF
