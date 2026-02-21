package com.studentms.dao;

import com.studentms.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOTest {

    private Connection connection;
    private StudentDAO studentDAO;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS students");
            stmt.execute("""
                CREATE TABLE students (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    age INT NOT NULL,
                    email VARCHAR(150) NOT NULL UNIQUE,
                    course VARCHAR(100) NOT NULL,
                    grade DOUBLE NOT NULL DEFAULT 0.0,
                    enrollment_date DATE NOT NULL
                )
            """);
        }
        studentDAO = new StudentDAO(connection);
    }

    private Student createTestStudent(String email) {
        return new Student("Alice Johnson", 20, email, "Computer Science", 90.0, LocalDate.of(2022, 9, 1));
    }

    @Test
    void testAddStudent() {
        Student student = createTestStudent("alice@test.com");
        assertTrue(studentDAO.addStudent(student));
        assertEquals(1, studentDAO.getTotalStudentCount());
    }

    @Test
    void testGetStudentById() {
        studentDAO.addStudent(createTestStudent("alice2@test.com"));
        List<Student> all = studentDAO.getAllStudents();
        assertFalse(all.isEmpty());
        int id = all.get(0).getId();

        Optional<Student> found = studentDAO.getStudentById(id);
        assertTrue(found.isPresent());
        assertEquals("Alice Johnson", found.get().getName());
    }

    @Test
    void testGetStudentByIdNotFound() {
        Optional<Student> found = studentDAO.getStudentById(9999);
        assertFalse(found.isPresent());
    }

    @Test
    void testGetAllStudents() {
        studentDAO.addStudent(createTestStudent("a1@test.com"));
        studentDAO.addStudent(createTestStudent("a2@test.com"));
        studentDAO.addStudent(createTestStudent("a3@test.com"));

        List<Student> students = studentDAO.getAllStudents();
        assertEquals(3, students.size());
    }

    @Test
    void testSearchStudentsByName() {
        studentDAO.addStudent(new Student("Alice Johnson", 20, "alice@test.com", "CS", 90.0, LocalDate.now()));
        studentDAO.addStudent(new Student("Bob Smith", 22, "bob@test.com", "Math", 85.0, LocalDate.now()));

        List<Student> results = studentDAO.searchStudentsByName("Alice");
        assertEquals(1, results.size());
        assertEquals("Alice Johnson", results.get(0).getName());
    }

    @Test
    void testGetStudentsByCourse() {
        studentDAO.addStudent(new Student("Alice", 20, "alice@test.com", "CS", 90.0, LocalDate.now()));
        studentDAO.addStudent(new Student("Bob", 22, "bob@test.com", "Math", 85.0, LocalDate.now()));
        studentDAO.addStudent(new Student("Carol", 21, "carol@test.com", "CS", 88.0, LocalDate.now()));

        List<Student> csStudents = studentDAO.getStudentsByCourse("CS");
        assertEquals(2, csStudents.size());
    }

    @Test
    void testUpdateStudent() {
        studentDAO.addStudent(createTestStudent("alice@test.com"));
        List<Student> all = studentDAO.getAllStudents();
        Student student = all.get(0);

        student.setName("Alice Updated");
        student.setGrade(95.0);
        assertTrue(studentDAO.updateStudent(student));

        Optional<Student> updated = studentDAO.getStudentById(student.getId());
        assertTrue(updated.isPresent());
        assertEquals("Alice Updated", updated.get().getName());
        assertEquals(95.0, updated.get().getGrade());
    }

    @Test
    void testDeleteStudent() {
        studentDAO.addStudent(createTestStudent("alice@test.com"));
        List<Student> all = studentDAO.getAllStudents();
        int id = all.get(0).getId();

        assertTrue(studentDAO.deleteStudent(id));
        assertFalse(studentDAO.getStudentById(id).isPresent());
    }

    @Test
    void testDeleteStudentNotFound() {
        assertFalse(studentDAO.deleteStudent(9999));
    }

    @Test
    void testGetTotalStudentCount() {
        assertEquals(0, studentDAO.getTotalStudentCount());
        studentDAO.addStudent(createTestStudent("a@test.com"));
        studentDAO.addStudent(createTestStudent("b@test.com"));
        assertEquals(2, studentDAO.getTotalStudentCount());
    }

    @Test
    void testGetAverageGradeByCourse() {
        studentDAO.addStudent(new Student("Alice", 20, "alice@test.com", "CS", 90.0, LocalDate.now()));
        studentDAO.addStudent(new Student("Bob", 22, "bob@test.com", "CS", 80.0, LocalDate.now()));

        double avg = studentDAO.getAverageGradeByCourse("CS");
        assertEquals(85.0, avg, 0.01);
    }
}
