package com.javacs.projects;

import java.util.Arrays;
import java.util.Random;

public class RandomWords {

    static final Random random = new Random();
    static final int min = 65;
    static final int max = 122;

    public String[] start(int count) {
        if (count <= 0) throw new IllegalArgumentException();

        String[] words = new String[count];

        for (int i = 0; i < count; i++) {

            int wordLength = random.nextInt(10) + 1;
            char[] chars = new char[wordLength];

            for (int j = 0; j < wordLength; j++) {
                int unicode = random.nextInt(max - min + 1) + min;
                chars[j] = (char) unicode;
            }

            words[i] = new String(chars);
        }

        return words;
    }
}
