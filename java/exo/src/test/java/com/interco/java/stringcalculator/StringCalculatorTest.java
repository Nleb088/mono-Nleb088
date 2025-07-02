package com.interco.java.stringcalculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class StringCalculatorTest {
    private final StringCalculator strCalc = new StringCalculator();

    @Test
    public void shouldReturn0WhenInputStringIsEmpty(
    ) throws StringCalculator.NegativeNumberException {
        //When
        int sum = strCalc.add("");
        //Then
        assertEquals(0, sum);
    }

    @Test
    public void shouldReturnIntegerValueForSingleDigitString() throws StringCalculator.NegativeNumberException {
        //When
        int sum = strCalc.add("1");
        //Then
        assertEquals(1, sum);
    }


    @Test
    public void shouldReturnIntegerValueForMultipleDigitString() throws StringCalculator.NegativeNumberException {
        //When
        int sum = strCalc.add("1,2");
        //Then
        assertEquals(3, sum);
    }

    @Test
    public void shouldReturnIntegerValueForAnyNumberOfDigitString() throws StringCalculator.NegativeNumberException {
        //When
        int sum = strCalc.add("1,2,3,4,5");
        //Then
        assertEquals(15, sum);
    }

    @Test
    public void shouldSupportCommaAndNewLineAsDelimiters() throws StringCalculator.NegativeNumberException {
        //When
        int sum = strCalc.add("1,2,3,4\n5");
        //Then
        assertEquals(15, sum);
    }

    @Test
    public void shouldSupportCustomDelimiters() throws StringCalculator.NegativeNumberException {
        //When
        int sum = strCalc.add("//t\n1,2,3t4\n5");
        //Then
        assertEquals(15, sum);
    }

    @Test
    public void shouldThrowAnSpecificExceptionWhenInputStringContainsNegativeNumbers() {
        //When
        Exception e = assertThrows(Exception.class, () -> strCalc.add("-5,2,-10,9"));
        //Then
        assertEquals("Les nombres négatifs ne sont pas autorisés : -5, -10", e.getMessage());
    }

    @Test
    public void shouldIgnoreNumbersOver1000WhenSummingValues() throws StringCalculator.NegativeNumberException {
        //When
        int sum = strCalc.add("5,10,1664");
        //Then
        assertEquals(15, sum);
    }
}
