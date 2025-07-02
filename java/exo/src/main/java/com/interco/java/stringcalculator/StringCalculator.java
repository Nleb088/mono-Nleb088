package com.interco.java.stringcalculator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    private static final int MAX_VALUE = 1000;
    private static final String DEFAULT_DELIMITERS = ",\n";
    private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile("^//(.)\\n([\\s\\S]*)", Pattern.DOTALL);

    public int add(String input) throws NegativeNumberException {
        if (input.isEmpty()) return 0;

        List<Integer> numbers = parseNumbers(input);

        return calculateSum(numbers);
    }

    private int calculateSum(List<Integer> numbers) throws NegativeNumberException {
        int sum = 0;

        for (int n : numbers) {
            if (n < 0) throw new NegativeNumberException(numbers);
            else if (n <= MAX_VALUE) sum += n;
        }

        return sum;
    }

    private List<Integer> parseNumbers(String input) {
        Matcher matcher = CUSTOM_DELIMITER_PATTERN.matcher(input);
        String delimiters = DEFAULT_DELIMITERS;

        if (matcher.matches()) {
            delimiters += matcher.group(1);
            input = matcher.group(2);
        }

        return Arrays.stream(input.split("[" + delimiters + "]"))
                .filter(s -> !s.trim().isEmpty())
                .map(str -> {
                    try {
                        return Integer.parseInt(str);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
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
