
package com.javacs.projects.sof;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public final class ConcatenateArrays<T> {

    @SafeVarargs public final T @NotNull [] concatenate(T[] first, T[]... rest) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(rest);

        int totalLength = first.length;

        for (T[] array : rest) {
            Objects.requireNonNull(array);
            totalLength = Math.addExact(totalLength, array.length);
        }

        T[] result = Arrays.copyOf(first, totalLength);

        int destinationIndex = first.length;

        for (T[] array : rest) {
            System.arraycopy(
                    array,
                    0,
                    result,
                    destinationIndex,
                    array.length
            );

            destinationIndex += array.length;
        }

        return result;
    }

    public static void main(String[] args) {
        ConcatenateArrays<Integer> concatenator = new ConcatenateArrays<>();

        Integer[] result = concatenator.concatenate(
                new Integer[]{1, 2, 3},
                new Integer[]{4, 5, 6},
                new Integer[]{7, 8, 9}
        );

        System.out.println(Arrays.toString(result));
    }
}
