package com.javacs.projects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Playground {

    public static final String packageName = "package.s.rc.main..";

    public static void resolver(String packageName) {

        Objects.requireNonNull(packageName, "packageName");

        final List<Character> charList = new ArrayList<>();

        for (int i = 0; i < packageName.length(); i++) {
            charList.add(packageName.charAt(i));
        }
        for (int i = 0; i < packageName.length() - 1; i++)
            if (
                    charList.get(i).toString().equals(".")
                    &&
                    charList.get(i + 1).toString().equals(".")
            )
                System.err.println("Incorrect package format.");

    }

    public static void main(String[] args) {
        resolver(packageName);
    }
}
