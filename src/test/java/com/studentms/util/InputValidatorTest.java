package com.studentms.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    @Test
    void testValidEmails() {
        assertTrue(InputValidator.isValidEmail("user@example.com"));
        assertTrue(InputValidator.isValidEmail("user.name+tag@sub.domain.org"));
        assertTrue(InputValidator.isValidEmail("a@b.co"));
    }

    @Test
    void testInvalidEmails() {
        assertFalse(InputValidator.isValidEmail(null));
        assertFalse(InputValidator.isValidEmail(""));
        assertFalse(InputValidator.isValidEmail("notanemail"));
        assertFalse(InputValidator.isValidEmail("missing@tld"));
        assertFalse(InputValidator.isValidEmail("@nodomain.com"));
    }

    @Test
    void testValidAges() {
        assertTrue(InputValidator.isValidAge(16));
        assertTrue(InputValidator.isValidAge(100));
        assertTrue(InputValidator.isValidAge(25));
    }

    @Test
    void testInvalidAges() {
        assertFalse(InputValidator.isValidAge(15));
        assertFalse(InputValidator.isValidAge(101));
        assertFalse(InputValidator.isValidAge(0));
        assertFalse(InputValidator.isValidAge(-1));
    }

    @Test
    void testValidGrades() {
        assertTrue(InputValidator.isValidGrade(0.0));
        assertTrue(InputValidator.isValidGrade(100.0));
        assertTrue(InputValidator.isValidGrade(75.5));
    }

    @Test
    void testInvalidGrades() {
        assertFalse(InputValidator.isValidGrade(-1.0));
        assertFalse(InputValidator.isValidGrade(101.0));
        assertFalse(InputValidator.isValidGrade(-0.1));
    }

    @Test
    void testValidNames() {
        assertTrue(InputValidator.isValidName("Alice"));
        assertTrue(InputValidator.isValidName("Alice Johnson"));
        assertTrue(InputValidator.isValidName("John"));
    }

    @Test
    void testInvalidNames() {
        assertFalse(InputValidator.isValidName(null));
        assertFalse(InputValidator.isValidName(""));
        assertFalse(InputValidator.isValidName("  "));
        assertFalse(InputValidator.isValidName("Alice123"));
        assertFalse(InputValidator.isValidName("Alice@Smith"));
    }
}
