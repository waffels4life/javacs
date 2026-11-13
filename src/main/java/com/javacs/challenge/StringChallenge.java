package com.javacs.challenge;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class StringChallenge {

    /*
     * 1. INNER LOOP START INDEX
     *    Always start the inner loop at "i + 1", not "0".
     *    Starting at 0 makes every character compare against itself,
     *    so each char is counted as "repeated" at least once.
     *    This is a classic off-by-one / self-comparison bug.
     *
     * 2. AVOID SENTINEL-VALUE HACKS
     *    Using a magic value (like '0' or -1) to mark elements as
     *    "already processed" is fragile: the sentinel may collide
     *    with real data in the input.
     *    Prefer an explicit "boolean[] visited" or a "Set" instead.
     *    Intent is clearer and there are no collisions.
     *
     * 3. DON'T PRINT UNSET CHAR ARRAY SLOTS
     *    A "new char[n]" is filled with '\u0000' (NUL) by default.
     *    Printing the whole array dumps invisible garbage to output.
     *    Use a StringBuilder / List / Set to collect ONLY real
     *    results, or print up to the real count.
     *
     * 4. USE A SET FOR UNIQUE RESULTS
     *    LinkedHashSet<Character> preserves insertion order AND
     *    removes duplicates for free. It also avoids the
     *    ArrayIndexOutOfBoundsException risk of "duplicated[index++]"
     *    when index exceeds the array length.
     *
     * 5. LOOP BOUNDARIES: DON'T SKIP THE LAST ELEMENT
     *    "for (int i = 0; i < arr.length - 1; i++)" silently skips
     *    the last element. Only use "- 1" when you genuinely need
     *    to compare adjacent pairs (i and i+1). Otherwise, use
     *    "< arr.length".
     *
     * 6. NORMALIZE INPUT ONCE, IN ONE PLACE
     *    Chain the normalization (whitespace, punctuation,
     *    lower-casing) in a single expression and store it in a
     *    well-named local variable. This makes the algorithm body
     *    clean and prevents scattered replaceAll calls.
     *    Example: string.replaceAll("[\\s.,!?;:]", "").toLowerCase()
     *
     * 7. @NotNull IS A HINT, NOT A RUNTIME GUARANTEE
     *    JetBrains' @NotNull is only used by IDEs/static analyzers.
     *    At runtime it does nothing. For real protection, use
     *    Objects.requireNonNull(string, "input must not be null")
     *    at the start of the method.
     *
     * 8. KNOW YOUR COMPLEXITY
     *    Nested loops over the same string => O(n^2) time.
     *    For ASCII input, an "int[128] frequency array" gives O(n).
     *    For full Unicode, use "HashMap<Character, Integer>".
     *    Always ask: "Can I count in one pass?"
     *
     * 9. HANDLE EDGE CASES EXPLICITLY
     *     - empty string ""
     *     - string with only punctuation/spaces
     *     - all characters unique
     *     - all characters identical
     *     - mixed case ("aAa" must be treated as "aaa")
     *     A short test table for these cases catches most bugs
     *     before they reach production.
     *
     * 10. WRITE TESTS AS A TABLE
     *     For string problems, a simple table of
     *     (input, expected output) is faster than a full test
     *     framework and makes the bug obvious during review.
     *     Example:
     *       "Hello, world.,,,"   -> [l, o]
     *       "aabcccccaaa"        -> [a, c]
     *       "abc"                -> []
     *       "aAa"                -> [a]
     *       ""                   -> []
     */
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

    }
}
