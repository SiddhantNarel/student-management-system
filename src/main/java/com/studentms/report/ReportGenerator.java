package com.studentms.report;

import com.studentms.model.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportGenerator {

    public void generateCSVReport(List<Student> students, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("ID,Name,Age,Email,Course,Grade,EnrollmentDate");
            writer.newLine();
            for (Student s : students) {
                writer.write(String.format("%d,%s,%d,%s,%s,%.2f,%s",
                        s.getId(), s.getName(), s.getAge(), s.getEmail(),
                        s.getCourse(), s.getGrade(), s.getEnrollmentDate()));
                writer.newLine();
            }
        }
    }

    public void generateSummaryReport(List<Student> students, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("Student Management System - Summary Report");
            writer.newLine();
            writer.write("Generated: " + timestamp);
            writer.newLine();
            writer.write("==========================================");
            writer.newLine();
            writer.newLine();

            writer.write("Total Students: " + students.size());
            writer.newLine();

            double avgGrade = students.stream()
                    .mapToDouble(Student::getGrade)
                    .average()
                    .orElse(0.0);
            writer.write(String.format("Average Grade: %.2f", avgGrade));
            writer.newLine();
            writer.newLine();

            writer.write("Students per Course:");
            writer.newLine();
            Map<String, Long> perCourse = students.stream()
                    .collect(Collectors.groupingBy(Student::getCourse, Collectors.counting()));
            for (Map.Entry<String, Long> entry : perCourse.entrySet()) {
                writer.write("  " + entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }
            writer.newLine();

            writer.write("Top Performers (Grade > 90):");
            writer.newLine();
            List<Student> topPerformers = students.stream()
                    .filter(s -> s.getGrade() > 90)
                    .collect(Collectors.toList());
            if (topPerformers.isEmpty()) {
                writer.write("  None");
                writer.newLine();
            } else {
                for (Student s : topPerformers) {
                    writer.write(String.format("  %s - %.2f (%s)", s.getName(), s.getGrade(), s.getCourse()));
                    writer.newLine();
                }
            }
        }
    }
}
