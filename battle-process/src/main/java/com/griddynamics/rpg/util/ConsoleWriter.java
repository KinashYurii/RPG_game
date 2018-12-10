package com.griddynamics.rpg.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsoleWriter {

    public void writeLine(String line) {
        System.out.println(line);
    }

    public <T> void writeMap(Map<Integer, T> available) {
        List<Integer> sorted = available
                .keySet()
                .stream()
                .sorted()
                .collect(Collectors.toList());

        sorted.forEach(integer -> writeLine(integer + " - " + available.get(integer)));
    }

    @Deprecated
    public void writeList(List<String> strings) {
        strings.forEach(this::writeLine);
    }
}
