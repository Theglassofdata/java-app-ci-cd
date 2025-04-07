package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MathUtilsTest {

    MathUtils mathUtils = new MathUtils();

    @Test
    void testAdd() {
        assertEquals(5, mathUtils.add(2, 3));
    }

    @Test
    void testSubtract() {
        assertEquals(1, mathUtils.subtract(3, 2));
    }

    @Test
    void testMultiply() {
        assertEquals(6, mathUtils.multiply(2, 3));
    }

    @Test
    void testDivide() {
        assertEquals(2, mathUtils.divide(6, 3));
    }

    @Test
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> mathUtils.divide(5, 0));
    }
}