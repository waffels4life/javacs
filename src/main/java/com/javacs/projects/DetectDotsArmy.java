package com.javacs.projects;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class DetectDotsArmy {

    public static final String packageName = "package..src.main";

    /*
     * the only purpose of this class is to detect dot next to each other.
     * I have already implemented a system for detecting other illegal dots and inputs.
     *
     * it doesn't matter how many dots are near each other; if they are, then we throw an exception.
     * @param packageName
     */
    public static void resolver(String packageName) {
        Objects.requireNonNull(packageName, "packageName");

        final List<String> charList = IntStream.range(0, packageName.length())
                .mapToObj(i -> String.valueOf(packageName.charAt(i)))
                .toList();

        for (int i = 0; i < packageName.length() - 1; i++)
            if (
                    charList.get(i).equals(".")
                    &&
                    charList.get(i + 1).equals(".")
            )
                throw new IllegalArgumentException(
                        "Incorrect package format: consecutive dots."
                );

    }

    public static void main(String[] args) {
        resolver(packageName);
    }
}
