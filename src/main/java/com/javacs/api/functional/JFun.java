package com.javacs.api.functional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class JFun {
    static class SupplierConsumerPipeline {
        static final Supplier<List<Character>> source = () -> List.of('a', 'b', 'c');
        static final Predicate<Character> isChar = Character::isLetter;
        static final Function<Character, Integer> asciiCode = c -> (int) c;
        static final Consumer<Integer> printer = System.out::println;

        public static void pipeline() {
            source.get().stream()
                    .filter(isChar)       // Predicate : Character -> Boolean
                    .map(asciiCode)       // Function  : Character -> Integer
                    .forEach(printer);    // Consumer  : Integer   -> void
        }
    }
}
