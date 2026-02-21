package com.studentms.app;

import com.studentms.dao.StudentDAO;
import com.studentms.db.DatabaseConnection;
import com.studentms.model.Student;
import com.studentms.report.ReportGenerator;
import com.studentms.util.InputValidator;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class StudentManagementApp {

    private static StudentDAO studentDAO;
    private static ReportGenerator reportGenerator = new ReportGenerator();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            studentDAO = new StudentDAO(connection);
            new File("reports").mkdirs();

            System.out.println("=== Student Management System ===");

            boolean running = true;
            while (running) {
                printMenu();
                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1" -> addStudent();
                    case "2" -> viewAllStudents();
                    case "3" -> searchById();
                    case "4" -> searchByName();
                    case "5" -> filterByCourse();
                    case "6" -> updateStudent();
                    case "7" -> deleteStudent();
                    case "8" -> viewStatistics();
                    case "9" -> generateReports();
                    case "10" -> {
                        System.out.println("Exiting... Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        } finally {
            DatabaseConnection.getInstance().closeConnection();
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Search Students by Name");
        System.out.println("5. Filter Students by Course");
        System.out.println("6. Update Student Record");
        System.out.println("7. Delete Student Record");
        System.out.println("8. View Statistics");
        System.out.println("9. Generate Reports");
        System.out.println("10. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addStudent() {
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        if (!InputValidator.isValidName(name)) {
            System.out.println("Invalid name.");
            return;
        }

        System.out.print("Age: ");
        int age;
        try {
            age = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid age.");
            return;
        }
        if (!InputValidator.isValidAge(age)) {
            System.out.println("Age must be between 16 and 100.");
            return;
        }

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        if (!InputValidator.isValidEmail(email)) {
            System.out.println("Invalid email.");
            return;
        }

        System.out.print("Course: ");
        String course = scanner.nextLine().trim();

        System.out.print("Grade: ");
        double grade;
        try {
            grade = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid grade.");
            return;
        }
        if (!InputValidator.isValidGrade(grade)) {
            System.out.println("Grade must be between 0.0 and 100.0.");
            return;
        }

        System.out.print("Enrollment Date (YYYY-MM-DD): ");
        LocalDate enrollmentDate;
        try {
            enrollmentDate = LocalDate.parse(scanner.nextLine().trim());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format.");
            return;
        }

        Student student = new Student(name, age, email, course, grade, enrollmentDate);
        boolean success = studentDAO.addStudent(student);
        System.out.println(success ? "Student added successfully!" : "Failed to add student.");
    }

    private static void viewAllStudents() {
        List<Student> students = studentDAO.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(System.out::println);
        }
    }

    private static void searchById() {
        System.out.print("Enter student ID: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Optional<Student> student = studentDAO.getStudentById(id);
            student.ifPresentOrElse(System.out::println, () -> System.out.println("Student not found."));
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }

    private static void searchByName() {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine().trim();
        List<Student> students = studentDAO.searchStudentsByName(name);
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(System.out::println);
        }
    }

    private static void filterByCourse() {
        System.out.print("Enter course name: ");
        String course = scanner.nextLine().trim();
        List<Student> students = studentDAO.getStudentsByCourse(course);
        if (students.isEmpty()) {
            System.out.println("No students found for this course.");
        } else {
            students.forEach(System.out::println);
        }
    }

    private static void updateStudent() {
        System.out.print("Enter student ID to update: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return;
        }

        Optional<Student> existing = studentDAO.getStudentById(id);
        if (existing.isEmpty()) {
            System.out.println("Student not found.");
            return;
        }

        Student student = existing.get();
        System.out.println("Current: " + student);

        System.out.print("New Name (leave blank to keep): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            if (!InputValidator.isValidName(name)) {
                System.out.println("Invalid name.");
                return;
            }
            student.setName(name);
        }

        System.out.print("New Age (leave blank to keep): ");
        String ageStr = scanner.nextLine().trim();
        if (!ageStr.isEmpty()) {
            try {
                int age = Integer.parseInt(ageStr);
                if (!InputValidator.isValidAge(age)) {
                    System.out.println("Invalid age.");
                    return;
                }
                student.setAge(age);
            } catch (NumberFormatException e) {
                System.out.println("Invalid age.");
                return;
            }
        }

        System.out.print("New Email (leave blank to keep): ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) {
            if (!InputValidator.isValidEmail(email)) {
                System.out.println("Invalid email.");
                return;
            }
            student.setEmail(email);
        }

        System.out.print("New Course (leave blank to keep): ");
        String course = scanner.nextLine().trim();
        if (!course.isEmpty()) student.setCourse(course);

        System.out.print("New Grade (leave blank to keep): ");
        String gradeStr = scanner.nextLine().trim();
        if (!gradeStr.isEmpty()) {
            try {
                double grade = Double.parseDouble(gradeStr);
                if (!InputValidator.isValidGrade(grade)) {
                    System.out.println("Invalid grade.");
                    return;
                }
                student.setGrade(grade);
            } catch (NumberFormatException e) {
                System.out.println("Invalid grade.");
                return;
            }
        }

        boolean success = studentDAO.updateStudent(student);
        System.out.println(success ? "Student updated successfully!" : "Failed to update student.");
    }

    private static void deleteStudent() {
        System.out.print("Enter student ID to delete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            boolean success = studentDAO.deleteStudent(id);
            System.out.println(success ? "Student deleted successfully!" : "Student not found or could not be deleted.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }

    private static void viewStatistics() {
        System.out.println("Total Students: " + studentDAO.getTotalStudentCount());
        List<Student> all = studentDAO.getAllStudents();
        all.stream()
                .map(Student::getCourse)
                .distinct()
                .forEach(course -> {
                    double avg = studentDAO.getAverageGradeByCourse(course);
                    System.out.printf("Average grade for %s: %.2f%n", course, avg);
                });
    }

    private static void generateReports() {
        List<Student> students = studentDAO.getAllStudents();
        String csvPath = "reports/students_report.csv";
        String summaryPath = "reports/students_summary.txt";
        try {
            reportGenerator.generateCSVReport(students, csvPath);
            System.out.println("CSV report generated: " + csvPath);
            reportGenerator.generateSummaryReport(students, summaryPath);
            System.out.println("Summary report generated: " + summaryPath);
        } catch (Exception e) {
            System.err.println("Error generating reports: " + e.getMessage());
        }
    }
}
