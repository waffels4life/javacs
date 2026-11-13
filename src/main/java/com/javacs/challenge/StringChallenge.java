package com.javacs.challenge;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
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

    /*
     * 1. READ THE SPEC CAREFULLY: TWO DIFFERENT ALGORITHMS
     *    "Compression" here means RUN-LENGTH ENCODING:
     *      "aabcccccaaa" -> "a2b1c5a3"
     *    That is NOT the same as counting TOTAL FREQUENCY:
     *      "aabcccccaaa" -> "a5b1c5"   (wrong for this task)
     *    RLE tracks CONSECUTIVE runs; frequency counting ignores
     *    order. Mixing them up silently produces a wrong answer
     *    that still "looks compressed".
     *
     * 2. int[128] IS ONLY SAFE FOR ASCII
     *    Character.isLetterOrDigit(c) accepts Unicode letters
     *    (é, آ, ٣, ...). Their code points are > 127, so
     *    freq[c]++ throws ArrayIndexOutOfBoundsException.
     *    Example that crashes: stringCompressor("café")
     *    Either restrict input to ASCII explicitly, or switch to
     *    HashMap<Character, Integer>.
     *
     * 3. freq[(char) i] WORKS BUT IS MISLEADING
     *    Java promotes char -> int automatically, so it compiles
     *    and runs. But it hides intent, breaks if freq ever
     *    becomes a Map, and confuses readers.
     *    Write freq[i]. Save casts for when they matter.
     *
     * 4. THE CONTRACT HAS A FALLBACK — IMPLEMENT IT
     *    Spec: "if compressed is not shorter than the original,
     *    return the original." This is part of the CONTRACT,
     *    not an optimization. Omitting it returns a longer string
     *    for inputs like "abc" (-> "a1b1c1").
     *    Rule: sb.length() < original.length() ? compressed : original
     *
     * 5. DON'T FORGET THE LAST RUN
     *    Classic RLE bug: the loop only flushes a run when the
     *    next char differs. The final run has no "next char", so
     *    it must be appended AFTER the loop. Skipping it loses
     *    the tail of every input.
     *
     * 6. toLowerCase() CHANGES SEMANTICS — CHOOSE CONSCIOUSLY
     *    Lower-casing before RLE merges "Aa" into one run "a2".
     *    That may be desired for frequency counting, but for RLE
     *    it usually destroys information. Decide per task; don't
     *    copy-paste normalization from a previous exercise.
     *
     * 7. requireNonNull WITH A MESSAGE
     *    Objects.requireNonNull(string) gives a bare NPE with no
     *    context. Pass a message:
     *      Objects.requireNonNull(string, "input must not be null")
     *    Saves minutes during debugging.
     *
     * 8. trim() ON A StringBuilder WITH NO SPACES IS NOISE
     *    If the builder never contains whitespace, trim() is dead
     *    code. Build output correctly from the start instead of
     *    cleaning it up afterwards.
     *
     * 9. RLE TIME/SPACE
     *    Single pass: O(n) time, O(n) space for the builder.
     *    No need for a frequency array at all — RLE is streaming
     *    and only needs "current char + run length".
     *    If you find yourself allocating int[128], ask whether the
     *    algorithm actually needs random-access counting.
     *
     * 10. TEST TABLE THAT EXPOSES THE BUGS ABOVE
     *     "aabcccccaaa" -> "a2b1c5a3"   (basic RLE)
     *     "abc"         -> "abc"        (fallback: not shorter)
     *     "aabbcc"      -> "aabbcc"     (equal length -> original)
     *     "aaa"         -> "a3"         (shorter -> compressed)
     *     "a"           -> "a"          (single char, last-run bug)
     *     ""            -> ""           (empty edge case)
     *     "AaA"         -> "AaA"        (case sensitivity)
     *     "café"        -> must not crash (Unicode safety)
     * ============================================================
     */
    public static String stringCompressor(@NotNull String string) {
        Objects.requireNonNull(string, "input must not be null");

        if (string.isEmpty()) return string;

        StringBuilder sb = new StringBuilder();
        int count = 1;

        for (int i = 1; i < string.length(); i++) {
            if (string.charAt(i) == string.charAt(i - 1)) count++;
            else {
                sb.append(string.charAt(i - 1)).append(count);
                count = 1;
            }
        }
        sb.append(string.charAt(string.length() - 1)).append(count);

        return sb.length() < string.length() ? sb.toString() : string;
    }

    public static void main(String[] args) {
        stringCompressor("aabcccccaaa");
    }
}
