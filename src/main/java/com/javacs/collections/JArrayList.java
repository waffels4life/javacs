package com.javacs.collections;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class JArrayList<T> {

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

    /**
     * <h5>List[T]</h5>
     * <p>The list interface is a part of java.util package.</p>
     * <p>Represent an order collection of elements and maintain that orders.</p>
     * <p>It allows duplicate elements and multiple null values in implementation.</p>
     */


    List<T> mylist = new ArrayList<T>();
    private void mainMethods() {
        List<Integer> integerList = new ArrayList<>();

        // adding elements
        for (int i = 0; i < 10; i++)
            integerList.add(i);

        // add all
        integerList.addAll(new ArrayList<>(Arrays.asList(1,2,3)));

        // insert at specific index
        integerList.add(1, 4);
        System.out.println("Element at index 0: " + integerList.getFirst());

        // update element in specific index
        integerList.set(2, 5);

        // remove an element by index
        integerList.removeFirst();
        // integerList.remove(0);

        // checks an element
        boolean b = integerList.contains(0b10);

        // index of an element
        int index = integerList.indexOf(4);
        int lastIndex = integerList.lastIndexOf(5);


    }

    public static void main(String[] args) {
        System.out.println(removeDuplicates(list));
        System.out.println(unoReverse(list));
    }
}