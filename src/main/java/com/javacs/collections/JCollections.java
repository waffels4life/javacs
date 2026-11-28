package com.javacs.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

public final class JCollections {
    public static void main(String[] args) {

        Collection<Integer> numbers = new ArrayList<>();
        numbers.add((int) 3.14);
        numbers.add(32);

        Stream.of(numbers).forEach(System.out::println);
    }
}
