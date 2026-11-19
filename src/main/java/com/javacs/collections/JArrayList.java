package com.javacs.collections;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class JArrayList {

    private static final List<Integer> list = List.of(1, 2, 3, 4, 4, 5, 1, 3);
    @Contract("_ -> new")
    public static <T extends Number> @NotNull List<T> removeDuplicates(@NotNull List<? extends T> list) {
        Set<T> seen = new HashSet<>(list.size() * 2);
        List<T> result = new ArrayList<>(list.size());
        for (T item : list)
            if (seen.add(item))
                result.add(item);

        return result;
    }

    public static @NotNull List<Number> unoReverse(@NotNull List<? extends Number> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("List must not be null or empty");
        }
        List<Number> reverse = new ArrayList<>(list.size());
        for (int i = list.size() - 1; i >= 0; i--) {
            reverse.add(list.get(i));
        }
        return reverse;
    }

    public static <T extends Comparable<T>> void minMax(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List must not be null or empty");
        }

        T min = list.get(0);
        T max = list.get(0);

        for (T item : list) {
            if (item.compareTo(min) < 0) min = item;
            if (item.compareTo(max) > 0) max = item;
        }

        System.out.println("Min: " + min + ", Max: " + max);
    }

    public static void main(String[] args) {
        System.out.println(removeDuplicates(list));
        System.out.println(unoReverse(list));
    }
}