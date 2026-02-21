package com.studentms.report;

import com.studentms.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorTest {

    @TempDir
    Path tempDir;

    private final ReportGenerator reportGenerator = new ReportGenerator();

    private List<Student> createSampleStudents() {
        return Arrays.asList(
                new Student(1, "Alice", 20, "alice@example.com", "CS", 95.0, LocalDate.of(2022, 9, 1)),
                new Student(2, "Bob", 22, "bob@example.com", "Math", 85.0, LocalDate.of(2021, 9, 1)),
                new Student(3, "Carol", 19, "carol@example.com", "CS", 88.0, LocalDate.of(2023, 1, 15))
        );
    }

    @Test
    void testGenerateCSVReportCreatesFile() throws IOException {
        Path csvFile = tempDir.resolve("test_report.csv");
        List<Student> students = createSampleStudents();

        reportGenerator.generateCSVReport(students, csvFile.toString());

        assertTrue(Files.exists(csvFile));
        String content = Files.readString(csvFile);
        assertTrue(content.contains("ID,Name,Age,Email,Course,Grade,EnrollmentDate"));
        assertTrue(content.contains("Alice"));
        assertTrue(content.contains("alice@example.com"));
    }

    @Test
    void testGenerateCSVReportContent() throws IOException {
        Path csvFile = tempDir.resolve("test_report2.csv");
        List<Student> students = createSampleStudents();

        reportGenerator.generateCSVReport(students, csvFile.toString());

        List<String> lines = Files.readAllLines(csvFile);
        assertEquals(4, lines.size()); // header + 3 students
        assertEquals("ID,Name,Age,Email,Course,Grade,EnrollmentDate", lines.get(0));
        assertTrue(lines.get(1).startsWith("1,Alice,20,alice@example.com,CS,"));
    }

    @Test
    void testGenerateCSVReportEmptyList() throws IOException {
        Path csvFile = tempDir.resolve("empty_report.csv");
        reportGenerator.generateCSVReport(Collections.emptyList(), csvFile.toString());

        assertTrue(Files.exists(csvFile));
        List<String> lines = Files.readAllLines(csvFile);
        assertEquals(1, lines.size()); // only header
        assertEquals("ID,Name,Age,Email,Course,Grade,EnrollmentDate", lines.get(0));
    }

    @Test
    void testGenerateSummaryReportCreatesFile() throws IOException {
        Path summaryFile = tempDir.resolve("summary.txt");
        List<Student> students = createSampleStudents();

        reportGenerator.generateSummaryReport(students, summaryFile.toString());

        assertTrue(Files.exists(summaryFile));
        String content = Files.readString(summaryFile);
        assertTrue(content.contains("Total Students: 3"));
        assertTrue(content.contains("Average Grade:"));
        assertTrue(content.contains("Students per Course:"));
        assertTrue(content.contains("Top Performers"));
    }

    @Test
    void testGenerateSummaryReportTopPerformers() throws IOException {
        Path summaryFile = tempDir.resolve("summary2.txt");
        List<Student> students = createSampleStudents();

        reportGenerator.generateSummaryReport(students, summaryFile.toString());

        String content = Files.readString(summaryFile);
        assertTrue(content.contains("Alice")); // grade 95 > 90
        assertFalse(content.contains("Bob")); // grade 85, not top performer
    }

    @Test
    void testGenerateSummaryReportEmptyList() throws IOException {
        Path summaryFile = tempDir.resolve("empty_summary.txt");
        reportGenerator.generateSummaryReport(Collections.emptyList(), summaryFile.toString());

        assertTrue(Files.exists(summaryFile));
        String content = Files.readString(summaryFile);
        assertTrue(content.contains("Total Students: 0"));
        assertTrue(content.contains("None"));
    }
}
