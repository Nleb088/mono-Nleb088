package com.interco.java.stringcalculator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    final int MAX_VALUE = 1000;

    public int add(String input) throws NegativeNumberException {
        if (input.isEmpty()) return 0;
        int sum = 0;

        List<Integer> numbers = parseNumbers(input);

        for (int n : numbers) {
            if (n > 0 && n <= MAX_VALUE) sum += n;
            else if (n < 0) throw new NegativeNumberException(numbers);
        }

        return sum;
    }

    private List<Integer> parseNumbers(String input) {
        Pattern additionalDelimiterPattern = Pattern.compile("^//(.)\\n(.*)", Pattern.DOTALL);
        Matcher matcher = additionalDelimiterPattern.matcher(input);
        String delimiters = ",\n";

        if (matcher.matches()) {
            delimiters += matcher.group(1);
            input = matcher.group(2);
        }

        return Arrays.stream(input.split("[" + delimiters + "]"))
                .map(Integer::parseInt)
                .toList();
    }

    public static class NegativeNumberException extends Exception {
        public NegativeNumberException(List<Integer> numbers) {
            super("Les nombres négatifs ne sont pas autorisés : " +
                    numbers.stream()
                            .filter(n -> n < 0)
                            .map(Object::toString).collect(Collectors.joining(", ")));
        }
    }


}
