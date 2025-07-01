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
        assertEquals(sum,3);
    }
}
