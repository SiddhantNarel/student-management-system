CREATE DATABASE IF NOT EXISTS student_management_db;
USE student_management_db;

CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    course VARCHAR(100) NOT NULL,
    grade DOUBLE NOT NULL DEFAULT 0.0,
    enrollment_date DATE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_course ON students(course);
CREATE INDEX IF NOT EXISTS idx_email ON students(email);

INSERT INTO students (name, age, email, course, grade, enrollment_date) VALUES
('Alice Johnson', 20, 'alice.johnson@example.com', 'Computer Science', 92.5, '2022-09-01'),
('Bob Smith', 22, 'bob.smith@example.com', 'Mathematics', 85.0, '2021-09-01'),
('Carol White', 19, 'carol.white@example.com', 'Physics', 78.3, '2023-01-15'),
('David Brown', 21, 'david.brown@example.com', 'Chemistry', 88.7, '2022-01-15'),
('Eva Martinez', 23, 'eva.martinez@example.com', 'Biology', 91.2, '2020-09-01'),
('Frank Lee', 20, 'frank.lee@example.com', 'Computer Science', 76.4, '2023-09-01'),
('Grace Kim', 22, 'grace.kim@example.com', 'Mathematics', 95.1, '2021-09-01'),
('Henry Wilson', 24, 'henry.wilson@example.com', 'English', 82.6, '2020-01-15'),
('Isla Davis', 18, 'isla.davis@example.com', 'Physics', 69.8, '2023-09-01'),
('Jack Taylor', 21, 'jack.taylor@example.com', 'Chemistry', 73.5, '2022-09-01'),
('Karen Anderson', 20, 'karen.anderson@example.com', 'Biology', 87.9, '2023-01-15'),
('Liam Thomas', 22, 'liam.thomas@example.com', 'Computer Science', 93.4, '2021-09-01'),
('Mia Jackson', 19, 'mia.jackson@example.com', 'English', 79.2, '2023-09-01'),
('Noah Harris', 23, 'noah.harris@example.com', 'Mathematics', 84.6, '2020-09-01'),
('Olivia Martin', 21, 'olivia.martin@example.com', 'Computer Science', 96.0, '2022-01-15'),
('Peter Garcia', 20, 'peter.garcia@example.com', 'Physics', 71.3, '2023-09-01'),
('Quinn Martinez', 22, 'quinn.martinez@example.com', 'Chemistry', 90.5, '2021-09-01'),
('Rachel Robinson', 24, 'rachel.robinson@example.com', 'Biology', 83.7, '2020-01-15'),
('Sam Clark', 19, 'sam.clark@example.com', 'English', 77.8, '2023-09-01'),
('Tina Lewis', 21, 'tina.lewis@example.com', 'Mathematics', 89.3, '2022-09-01');
