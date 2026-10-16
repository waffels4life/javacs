package com.javacs.projects.calc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Calculator tests")
public class CalculatorTest {

    private Calculator calculator;

    /**
     * The method annotated with @BeforeEach runs before each test method,
     * ensuring a fresh calculator instance
     */
    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    /**
     * 	@Test A method with this annotation defines a test method
     * 	@DisplayName provides a human-readable name for the test that appears in test reports
     */
    @Test
    @DisplayName("Multiplication of positive numbers")
    void testMultiplePositiveNumbers() {
        /*
         * 	This assertion verifies that the expected value matches the actual result
         */
        assertEquals(20,
                calculator.multiply(4, 5),
                "4 * 5 should equal 20"
        );
    }

    /**
     * @RepeatedTest runs the same test multiple times, useful for detecting intermittent failures
     */
    @RepeatedTest(5)
    @DisplayName("Multiplication with zero should return zero")
    void testMultiplyWithZero() {
        assertEquals(0,
                calculator.multiply(0, 5),
                "0 * 5 should equal 0"
        );
        assertEquals(0,
                calculator.multiply(5, 0),
                "5 * 0 should equal 0"
        );
    }

    @Test
    @DisplayName("Addition of positive numbers")
    void testAddPositiveNumbers() {
        assertEquals(9,
                calculator.add(4, 5),
                "4 + 5 should equal 9"
        );
    }

    @Test
    @DisplayName("Division should work correctly")
    void testDivision() {
        assertEquals(2.5,
                calculator.divide(5.0, 2.0),
                0.001,
                "5.0 / 2.0 should equal 2.5");
    }

    @Test
    @DisplayName("Division by zero should throw exception")
    void testDivisionByZero() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.divide(5.0, 0.0),
                "Division by zero should throw IllegalArgumentException");
    }
}
