import com.interco.java.stringcalculator.StringCalculator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringCalculatorTest {
    private final StringCalculator strCalc = new StringCalculator();

    @Test
    public void shouldReturn0WhenInputStringIsEmpty(
    )  {
        //When
        int sum = strCalc.add("");
        //Then
        assertEquals(0,sum);
    }

    @Test
    public void shouldReturnIntegerValueForSingleDigitString() {
        //When
        int sum = strCalc.add("1");
        //Then
        assertEquals(1,sum);
    }


    @Test
    public void shouldReturnIntegerValueForMultipleDigitString() {
        //When
        int sum = strCalc.add("1,2");
        //Then
        assertEquals(3,sum);
    }

    @Test
    public void shouldReturnIntegerValueForAnyNumberOfDigitString() {
        //When
        int sum = strCalc.add("1,2,3,4,5");
        //Then
        assertEquals(15,sum);
    }

    @Test
    public void shouldSupportCommaAndNewLineAsDelimiters() {
        //When
        int sum = strCalc.add("1,2,3,4\n5");
        //Then
        assertEquals(15,sum);
    }

    @Test
    public void shouldSupportCustomDelimiters() {
        //When
        int sum = strCalc.add("//t\n1,2,3t4\n5");
        //Then
        assertEquals(15,sum);
    }
}
