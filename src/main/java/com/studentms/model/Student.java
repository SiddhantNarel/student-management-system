package com.studentms.model;

import java.time.LocalDate;
import java.util.Objects;

public class Student {
    private int id;
    private String name;
    private int age;
    private String email;
    private String course;
    private double grade;
    private LocalDate enrollmentDate;

    public Student(String name, int age, String email, String course, double grade, LocalDate enrollmentDate) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.course = course;
        this.grade = grade;
        this.enrollmentDate = enrollmentDate;
    }

    public Student(int id, String name, int age, String email, String course, double grade, LocalDate enrollmentDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.course = course;
        this.grade = grade;
        this.enrollmentDate = enrollmentDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public double getGrade() { return grade; }
    public void setGrade(double grade) { this.grade = grade; }

    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", course='" + course + '\'' +
                ", grade=" + grade +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id &&
                age == student.age &&
                Double.compare(student.grade, grade) == 0 &&
                Objects.equals(name, student.name) &&
                Objects.equals(email, student.email) &&
                Objects.equals(course, student.course) &&
                Objects.equals(enrollmentDate, student.enrollmentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email, course, grade, enrollmentDate);
    }
}
