package org.server.services;

import java.io.PrintStream;
import java.util.Scanner;

public class InputService {
    private static final Scanner input = new Scanner(System.in);
    private static final PrintStream output = System.out;

    public static int getInt() {
        return getInt("");
    }

    public static int getInt(String description) {
        return getInt(description, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static int getInt(String description, int minValue, int maxValue) {
        output.print(description);

        try {
            int value = input.nextInt();
            if (value >= minValue && value <= maxValue) return value;

            return maxValue + 1;
        }
        catch (Exception e) { return minValue - 1; }
    }

    public static String getString() { return getString(""); }

    public static String getString(String description) {
        output.print(description);

        try { return input.nextLine(); }
        catch (Exception e) { return null; }
    }
}
