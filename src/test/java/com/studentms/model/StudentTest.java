package com.studentms.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void testConstructorWithoutId() {
        LocalDate date = LocalDate.of(2022, 9, 1);
        Student s = new Student("Alice", 20, "alice@example.com", "CS", 90.0, date);
        assertEquals(0, s.getId());
        assertEquals("Alice", s.getName());
        assertEquals(20, s.getAge());
        assertEquals("alice@example.com", s.getEmail());
        assertEquals("CS", s.getCourse());
        assertEquals(90.0, s.getGrade());
        assertEquals(date, s.getEnrollmentDate());
    }

    @Test
    void testConstructorWithId() {
        LocalDate date = LocalDate.of(2022, 9, 1);
        Student s = new Student(5, "Bob", 21, "bob@example.com", "Math", 85.0, date);
        assertEquals(5, s.getId());
        assertEquals("Bob", s.getName());
    }

    @Test
    void testSetters() {
        Student s = new Student("Alice", 20, "alice@example.com", "CS", 90.0, LocalDate.now());
        s.setId(10);
        s.setName("Alice Updated");
        s.setAge(21);
        s.setEmail("new@example.com");
        s.setCourse("Physics");
        s.setGrade(95.0);
        LocalDate newDate = LocalDate.of(2023, 1, 1);
        s.setEnrollmentDate(newDate);

        assertEquals(10, s.getId());
        assertEquals("Alice Updated", s.getName());
        assertEquals(21, s.getAge());
        assertEquals("new@example.com", s.getEmail());
        assertEquals("Physics", s.getCourse());
        assertEquals(95.0, s.getGrade());
        assertEquals(newDate, s.getEnrollmentDate());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDate date = LocalDate.of(2022, 9, 1);
        Student s1 = new Student(1, "Alice", 20, "alice@example.com", "CS", 90.0, date);
        Student s2 = new Student(1, "Alice", 20, "alice@example.com", "CS", 90.0, date);
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    void testNotEquals() {
        LocalDate date = LocalDate.of(2022, 9, 1);
        Student s1 = new Student(1, "Alice", 20, "alice@example.com", "CS", 90.0, date);
        Student s2 = new Student(2, "Bob", 22, "bob@example.com", "Math", 80.0, date);
        assertNotEquals(s1, s2);
    }

    @Test
    void testToString() {
        LocalDate date = LocalDate.of(2022, 9, 1);
        Student s = new Student(1, "Alice", 20, "alice@example.com", "CS", 90.0, date);
        String str = s.toString();
        assertTrue(str.contains("Alice"));
        assertTrue(str.contains("alice@example.com"));
        assertTrue(str.contains("CS"));
    }
}
