package com.javacs.projects.monody;

import java.util.Arrays;
import java.util.Random;

public class RandomWordsExperiment {

    private static final Random RANDOM = new Random();
    private static final int MIN_CHAR_ASCII = 65;
    private static final int WORD_COUNT = 500;

    private static final int ENTRY_POINT_TEST_USE = 2;

    public static void main(String[] args) {

        String[] words = createRandomWordsArray(WORD_COUNT);
        int[] hash = toHashArray(words);

        switch (ENTRY_POINT_TEST_USE) {
            case 1 -> {
                for (int i = 0; i < WORD_COUNT; i++)
                    System.out.printf("%-20s %-10d\n", words[i], hash[i]);
            }

            case 2 -> Arrays.stream(hash)
                        .sorted()
                        .forEach(System.out::println);

        }
    }

    public static String[] createRandomWordsArray(int count) {
        if (count <= 0)
            throw new IllegalArgumentException(
                "Count cannot be zero or negative."
        );

        String[] words = new String[count];

        for (int i = 0; i < count; i++) {

            int wordLength = RANDOM.nextInt(10) + 1;
            char[] chars = new char[wordLength];

            for (int j = 0; j < wordLength; j++) {

                int unicode = RANDOM.nextBoolean()
                        ? RANDOM.nextInt(26) + MIN_CHAR_ASCII
                        : RANDOM.nextInt(26) + 97;

                chars[j] = (char) unicode;
            }

            words[i] = new String(chars);
        }

        return words;
    }

    private static int[] toHashArray(String[] array) {
        int[] hashArray = new int[array.length];

        for (int i = 0; i < array.length; i++)
            hashArray[i] = array[i].hashCode();

        return hashArray;
    }
}
