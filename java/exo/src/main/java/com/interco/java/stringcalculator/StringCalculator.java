package com.interco.java.stringcalculator;

import java.util.Arrays;

public class StringCalculator {

    public int add(String input) {
        if (input.isEmpty()) return 0;
        String DELIMITERS = "[,\n]";


        return Arrays.stream(input.split(DELIMITERS)).mapToInt(Integer::parseInt).reduce(0, Integer::sum);
    }
}
