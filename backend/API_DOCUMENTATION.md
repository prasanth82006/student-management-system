# Student Management System - API Documentation

Base URL: `http://localhost:8080/api/students`

---

## 1. Create a Student
Creates a new student record in the database.

- **Method:** `POST`
- **URL:** `/`
- **Status Code on Success:** `201 CREATED`

**Request Body (JSON):**
```json
{
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane.smith@example.com",
  "phone": "9876543210",
  "department": "Engineering",
  "course": "Computer Science",
  "year": 2024,
  "cgpa": 3.9
}
```

**Response Body (JSON):**
```json
{
  "id": 1,
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane.smith@example.com",
  "phone": "9876543210",
  "department": "Engineering",
  "course": "Computer Science",
  "year": 2024,
  "cgpa": 3.9
}
```

---

## 2. Get All Students
Retrieves a list of all students.

- **Method:** `GET`
- **URL:** `/`
- **Status Code on Success:** `200 OK`

**Response Body (JSON):**
```json
[
  {
    "id": 1,
    "firstName": "Jane",
    "email": "jane.smith@example.com",
    "...": "..."
  }
]
```

---

## 3. Get Student by ID
Retrieves a single student's details using their unique ID.

- **Method:** `GET`
- **URL:** `/{id}`
- **Status Code on Success:** `200 OK`
- **Status Code on Failure:** `404 NOT FOUND` (If ID does not exist)

---

## 4. Update a Student
Updates an existing student's data.

- **Method:** `PUT`
- **URL:** `/{id}`
- **Status Code on Success:** `200 OK`

**Request Body (JSON):**
*Must include all required fields based on validation rules.*
```json
{
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jane.doe@example.com",
  "phone": "9876543210",
  "department": "Engineering",
  "course": "Information Technology",
  "year": 2024,
  "cgpa": 4.0
}
```

---

## 5. Delete a Student
Removes a student from the database permanently.

- **Method:** `DELETE`
- **URL:** `/{id}`
- **Status Code on Success:** `204 NO CONTENT`

**Response Body:**
*Empty.*

---

## Global Error Responses

If validation fails (e.g., negative CGPA), the API will return a `400 BAD REQUEST` with the following structure:

```json
{
  "timestamp": "2024-05-15T12:00:00",
  "message": "Validation Failed",
  "details": "cgpa: CGPA cannot be negative; phone: Phone number must be exactly 10 digits; "
}
```
