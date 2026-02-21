package com.studentms.util;

public class InputValidator {

    private InputValidator() {}

    public static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    public static boolean isValidAge(int age) {
        return age >= 16 && age <= 100;
    }

    public static boolean isValidGrade(double grade) {
        return grade >= 0.0 && grade <= 100.0;
    }

    public static boolean isValidName(String name) {
        if (name == null || name.isBlank()) return false;
        return name.matches("^[A-Za-z ]+$");
    }
}
