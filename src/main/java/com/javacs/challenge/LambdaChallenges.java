package com.javacs.challenge;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaChallenges {

    /*
     * Ex1 — map + Function
     * - Function<T,R> = transform one value into another.
     * - String::toUpperCase is a method reference (shorthand for s -> s.toUpperCase()).
     * - map() is intermediate & lazy; only collect()/forEach() triggers execution.
     * - toSet() removes duplicates but loses order -> use toCollection(LinkedHashSet::new) to keep order.
     */
    private static void ex_1(String[] args) {
        Function<String, String> upper = String::toUpperCase;
        var result = Arrays.stream(args)
                .map(upper)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        System.out.println(result);
    }

    /*
     * NOTE: Streams are LAZY — intermediate ops like filter()/map() return a new
     * Stream object and do NOT execute anything yet. Printing a stream directly
     * (e.g. System.out.println(stream)) only prints its default Object.toString(),
     * which looks like: java.util.stream.IntPipeline$10@3feba861
     * (class name + "@" + hashCode) — NOT the elements.
     *
     * To actually run the pipeline you must call a TERMINAL operation, such as:
     *   forEach(), collect(), toArray(), sum(), average(), count(), reduce().
     *
     * IntStream specifics:
     *   - collect(Collectors.toList()) is NOT available on IntStream;
     *     call .boxed() first to get Stream<Integer>, or use toArray()/sum().
     *   - average() returns OptionalDouble -> use .orElse(0) to unwrap safely.
     *
     * Example (correct):
     *   IntStream.range(1, 10)
     *            .filter(n -> n % 2 == 0)
     *            .forEach(System.out::println);   // prints 2 4 6 8
     */
    private static void ex_2() {
        IntStream.range(1, 10)
                .filter(x -> x % 2 == 0)
                .forEach(System.out::println);
    }

    private static void ex_2_more(int[] args) {
        var result = Arrays.stream(args)
                .filter(x -> x % 2 == 0)
                .toArray();

        System.out.println(Arrays.toString(result));
    }

    private static void ex_3(String[] args) {
        Function<String, String> map = x -> "User: " + x;
        Consumer<String> print = System.out::println;

        Arrays.stream(args)
                .map(map)
                .forEach(print);
    }

    private static void ex_4(String[] args) {
        Arrays.stream(args)
                .forEach(System.out::println);
    }

    private static void ex_5() {
        Function<Integer, Integer> doubleIt = n -> n * 2;
        Function<Integer, Integer> addTen  = n -> n + 10;

        System.out.println(doubleIt.andThen(addTen).apply(5));
        System.out.println(doubleIt.compose(addTen).apply(5));
    }

    private static void ex_6() {
        var result = IntStream.range(1, 10)
                .filter(num -> num % 2 != 0)
                .map(num -> num * 2)
                .average();

        System.out.println(result.orElse(0));
    }

    private static <T, R> void process(
            Stream<T> source,
            Predicate<? super T> predicate,
            Function<? super T, ? extends R> function,
            Consumer<? super R> consumer
    ) {
        source.filter(predicate)
                .map(function)
                .forEach(consumer);
    }

    public static void main(String[] args) {
//        ex_1(new String[]{"java", "lambda", "stream"});
//        ex_2();
//        ex_2_more(new int[]{1,3,4,5,6,7,8,9,11,22,55,44});
//        ex_3(new String[]{"a", "b", "c"});
    }
}
