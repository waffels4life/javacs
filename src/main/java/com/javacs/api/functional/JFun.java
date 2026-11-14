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

    /**
     * <h5>Producer Extends, Consumer Super</h5>
     * @param supplier
     * @param function
     * @param predicate
     * @param consumer
     * @param <T>
     * @param <R>
     */
    public static <T, R> void pipeline(
            // Whatever the supplier returns, it must be a Collection<T>
            // or a subclass of Collection<T>.
            Supplier<? extends Collection<T>> supplier,
            // The function accepts T or one of its supertypes as input.
            // The function returns R or one of its subtypes as output.
            Function<? super T, ? extends R> function,
            // The predicate accepts T or one of its supertypes
            // and returns a boolean result.
            Predicate<? super T> predicate,
            // The consumer accepts R or one of its supertypes.
            // It consumes the result produced by the function.
            Consumer<? super R> consumer
    ) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(function);
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(consumer);

        Collection<T> collection = Objects.requireNonNull(supplier.get());

        collection.stream()
                .filter(predicate)
                .map(function)
                .forEach(consumer);
    }

    public static void main(String[] args) {
        pipeline(
                () -> List.of(1, 2, 3, 4, 5),
                number -> "Number: " + number,
                number -> number % 2 == 0,
                System.out::println
        );
    }
}
