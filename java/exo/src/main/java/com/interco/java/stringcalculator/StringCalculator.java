package com.interco.java.stringcalculator;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.BaseStream;
import java.util.stream.IntStream;

public class StringCalculator {

    public int add(String input) {
        if (input.isEmpty()) return 0;

       IntStream intStream = parseStringToIntStream(input);
       return intStream.reduce(0, Integer::sum);
    }

    private IntStream parseStringToIntStream(String input){
        Pattern additionalDelimiterPattern = Pattern.compile("^//(.)\\n(.*)", Pattern.DOTALL);
        Matcher matcher = additionalDelimiterPattern.matcher(input);
        String delimiters = ",\n";

        if (matcher.matches()){
            delimiters += matcher.group(1);
            input = matcher.group(2);
        }

        return Arrays.stream(input.split("["+delimiters+"]")).mapToInt(Integer::parseInt);
    }
}
