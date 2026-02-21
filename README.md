# Student Management System

A Java console application using JDBC to manage student records with full CRUD operations, automated report generation, and comprehensive testing.

## Features

- **Add, View, Update, Delete** student records
- **Search** students by ID or name
- **Filter** students by course
- **Statistics** — total student count and average grade per course
- **Report Generation** — CSV export and summary text reports (including top performers)
- **Input Validation** — email, age, grade, and name validation
- **Prepared Statements** — protects against SQL injection

## Project Structure

```
src/
├── main/java/com/studentms/
│   ├── app/StudentManagementApp.java   # CLI entry point with menu-driven interface
│   ├── dao/StudentDAO.java             # Data access layer (CRUD + search + stats)
│   ├── db/DatabaseConnection.java      # Singleton JDBC connection manager
│   ├── model/Student.java              # Student entity (id, name, age, email, course, grade, enrollmentDate)
│   ├── report/ReportGenerator.java     # CSV and summary report generation
│   └── util/InputValidator.java        # Static input validation helpers
├── main/resources/
│   └── db.properties                   # Database connection configuration
└── test/java/com/studentms/
    ├── dao/StudentDAOTest.java          # DAO tests using H2 in-memory database
    ├── model/StudentTest.java           # Student model unit tests
    ├── report/ReportGeneratorTest.java  # Report generation tests
    └── util/InputValidatorTest.java     # Input validation tests
```

## Prerequisites

- **Java 17** or later
- **Maven 3.8+**
- **MySQL 8.0+** (for production use)

## Database Setup

1. Start your MySQL server.
2. Run the provided schema script to create the database, table, and sample data:

   ```bash
   mysql -u root -p < schema.sql
   ```

3. Update `src/main/resources/db.properties` with your database credentials if they differ from the defaults:

   ```properties
   db.url=jdbc:mysql://localhost:3306/student_management_db
   db.username=root
   db.password=
   ```

## Build & Run

```bash
# Compile and package
mvn clean package

# Run the application
java -jar target/student-management-system-1.0.0.jar
```

## Testing

Tests use an **H2 in-memory database** so no external database is required.

```bash
mvn test
```

## Technologies

| Technology | Purpose |
|---|---|
| Java 17 | Language |
| JDBC | Database access |
| MySQL | Production database |
| H2 | In-memory test database |
| JUnit 5 | Unit testing |
| Maven | Build and dependency management |
