package com.javacs.api.functional;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class JFunction {
    static class JPredicate {
        private static final Predicate<Integer> isEven = x -> x % 2 == 0;
        private static final Predicate<Integer> isOdd = isEven.negate();
        private static final Predicate<Integer> isPositive = x -> x > 0;
        private static final Predicate<Integer> isNegative = x -> x < 0;
        private static final Predicate<Integer> isZero = isPositive.or(isNegative).negate();
        private static final Predicate<Integer> isEvenAndPositive = isEven.and(isPositive);
        private static final Predicate<String> isEqualToOreo = Predicate.isEqual("Oreo");
        private static final List<Integer> numbers =
                Arrays.asList(
                        -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5
                );
        public List<Integer> homemadePredicate() {
            return numbers.stream()
                    .filter(JPredicate::isEven)
                    .filter(JPredicate::isPositive)
                    .map(number -> (int) Math.pow(number, 2))
                    .toList();
        }
        public static boolean isEven(Integer integer) {
            return isEven.test(integer);
        }
        public static boolean isOdd(Integer integer) {
            return isOdd.test(integer);
        }
        public static boolean isPositive(Integer integer) {
            return isPositive.test(integer);
        }
        public static boolean isEvenAndPositive(Integer integer) {
            return isEvenAndPositive.test(integer);
        }
        public static boolean isEqualToOreo(String string) {
            return isEqualToOreo.test(string);
        }
    }
    static class JFunctionMethod {
        private final Function<Integer, Integer> doubleIt =
                num -> Math.multiplyExact(num, 2);
        public Integer doubleIt(Integer integer) {
            return doubleIt.apply(integer);
        }
        private final Function<Integer, String> intToString =
                num -> "Number is : " + num;
        public String intToString(Integer integer) {
            return intToString.apply(integer);
        }
        private final Function<String, Boolean> isTooShort =
                name -> name.length() < 5;
        public boolean intToString(String name) {
            return isTooShort.apply(name);
        }
        final Function<Integer, Integer> addOne = x -> x + 1;
        final Function<Integer, Integer> tripleIt = x -> x * 3;
        final Function<Integer, Integer> addThenTriple = addOne.andThen(tripleIt);
        public int addThenTriple(Integer integer) {
            return addThenTriple.apply(integer);
        }
        final Function<Integer, Integer> tripleThenAdd = addOne.compose(tripleIt);
        public int tripleThenAdd(Integer integer) {
            return tripleThenAdd.apply(integer);
        }
    }
    static class JConsumer {
        // It is certainly not the most efficient way to print something.
        Consumer<String> coolerPrint = System.out::println;
        public void coolerPrint(String string) {
            coolerPrint.accept(string);
        }

        Consumer<String> first = s -> System.out.println("First: " + s);
        Consumer<String> second = s -> System.out.println("Second: " + s);

        Consumer<String> combined = first.andThen(second);
        public void combinedPrint(String string) {
            combined.accept(string);
        }
    }
}
