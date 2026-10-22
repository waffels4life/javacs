package com.javacs.algorithms.recursion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JRecursionTest {

    private JRecursion recursion;

    @BeforeEach
    void setUp() {
        recursion = new JRecursion();
    }

    @Test
    @DisplayName("Checking recursion")
    void testRecursion() {
        assertEquals(6,
                recursion.sum(3)
        );
    }

    @Test
    @DisplayName("Checking recursion with negative minimum value")
    void shouldThrowExceptionWithNegativeMinimumValue() {
        assertThrows(IllegalArgumentException.class,
                () -> recursion.sum(-Integer.MIN_VALUE)
        );
    }

    @Test
    @DisplayName("Zero input should throw exception")
    void shouldThrowExceptionWithZeroAsInput() {
        assertThrows(IllegalArgumentException.class,
                () -> recursion.sum(0)
        );
    }

    @Test
    @DisplayName("Negative input should throw exception")
    void shouldThrowExceptionWithNegativeValueInput() {
        assertThrows(IllegalArgumentException.class,
                () -> recursion.sum(-Integer.MAX_VALUE)
        );
    }
}
