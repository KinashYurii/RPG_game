package com.griddynamics.rpg.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.griddynamics.rpg.util.constants.UserInfoConstants.*;

public class ConsoleReader {
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private final ConsoleWriter writer = new ConsoleWriter();

    public String readWord() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public int readNumberFrom(Collection<Integer> integers) {
        while (true) {
            int numb = readNumber();
            if (integers.contains(numb)) {
                return numb;
            }
            writer.writeLine(CHOOSE_CORRECT);
        }
    }

    @Deprecated
    public boolean yesNoConfirmation() {
        while (true) {
            String input = "";
            try {
                input = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (input.equalsIgnoreCase(YES)) {
                return true;
            } else if (input.equalsIgnoreCase(NO)) {
                return false;
            }
        }
    }

    private int readNumber() {
        while (true) {
            try {
                return Integer.parseInt(readWord());
            } catch (NumberFormatException e) {
                writer.writeLine(NOT_A_NUMBER);
            }
        }
    }

    public int readNumberInRange(int max) {
        while (true) {
            int number = readNumber();
            if (number < max + 1) {
                return number;
            }
            writer.writeLine(CHOOSE_CORRECT);
        }
    }

    public int readNumberInSetOrZero(Set<Integer> integers) {
        while (true) {
            int input = readNumber();
            if (input == 0 || integers.contains(input)) {
                return input;
            } else {
                System.out.println("Incorrect input");
            }
        }
    }

    public <T> String listToString(List<T> list) {
        StringBuilder result = new StringBuilder();
        for (T item : list) {
            result.append(list.indexOf(item) + 1).append(": ").append(item).append("\n");
        }
        return result.toString();
    }
}
