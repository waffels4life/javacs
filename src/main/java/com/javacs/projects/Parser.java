package com.javacs.projects;

import java.util.Arrays;

public class Parser {
    private static final String command = "cmp sort -v 5,4,3,2";

    public static void parse() {
        String[] commandArray = command.split("\\s+");

        switch (commandArray[2]) {
            case "--values", "-v" -> System.out.println("values");
            case "--sort", "-s" -> System.out.println("sort");
            case "--help", "-h" -> System.out.println("help");
        }
    }

    public static void main(String[] args) {
        parse();
    }
}
