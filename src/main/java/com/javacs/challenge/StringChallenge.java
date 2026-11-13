package com.javacs.challenge;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class StringChallenge {

    public static void repeatedChars(@NotNull String string) {
        Objects.requireNonNull(string);
        String normalize = string
                .replaceAll("[\\s.,!?;:]", "")
                .toLowerCase();

        /*
         * if char[i] equals char[j] -> index i & j == true
         * char arrays : [true, x, y, true, y]
         *                 |___ same ___|
         *
         * This helps us reduce the search.
         */
        boolean[] visited = new boolean[normalize.length()];
        Set<Character> result = new LinkedHashSet<>();

        for (int i = 0; i < normalize.length(); i++) {
            if (visited[i]) continue;

            char c = normalize.charAt(i);
            int count = 1;
            for (int j = i + 1; j < normalize.length(); j++)
                if (!visited[j] && normalize.charAt(j) == c) {
                    visited[j] = true;
                    count++;
                }

            if (count > 1) result.add(c);
        }

        System.out.println(result);
    }

    public static void repeatedCharsFast(@NotNull String string) {
        Objects.requireNonNull(string);
        int[] freq = new int[128];
        for (char c : string.toLowerCase().toCharArray())
            if (Character.isLetterOrDigit(c)) freq[c]++;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < freq.length; i++)
            if (freq[i] > 1) sb.append((char) i).append(' ');

        System.out.println("Repeated chars: " + sb.toString().trim());
    }

    public static void main(String[] args) {
        repeatedChars("Hello, world.,,,");
        repeatedChars("aabcccccaaa");
    }
}
