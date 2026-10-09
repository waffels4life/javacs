package com.javacs.api.streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class JStream {

    /**
     * <h5>Class structure</h5>
     * <p>I defined these as static. All the fields depend on the
     * class design system itself.</p>
     * <p> {@code integerList} An ordered list of consecutive integers </p>
     * <p> {@code duplicateIntegerList} A list of consecutive repeating integers </p>
     * <p> {@code stringNameList} A list of strings. It contains a few names. </p>
     * <p> {@code people} An array that stores objects of the {@link Person} record class. </p>
     */
    private static final List<Integer> integerList;
    private static final List<Integer> duplicateIntegerList;
    private static final List<String> stringNameList;
    private static final List<Person> people;
    private static final List<List<String>> classroom;
    private static final List<String> sentences;
    private static final List<Shopping> orders;

    static {
        integerList = List.of(1,2,3,4,5,6,7,8,9);
        duplicateIntegerList = List.of(1,1,2,3,3,4,5,6,7,7,7,8,9,9);
        stringNameList = List.of("Arsam", "Oreo", "Ali", "Eli");
        people = List.of(
                new Person("Arsam", 21),
                new Person("Farzam", 21),
                new Person("John", 18)
        );
        classroom = List.of(
                List.of("x", "y", "z"),
                List.of("a", "b"),
                List.of("i", "j", "k")
        );
        sentences = List.of(
                "Arsam loves coffee",
                "Oreo is a black cat",
                "Java is cool"
        );
        orders = List.of(
                new Shopping("Arsam", List.of("coffee", "Cat-food", "book")),
                new Shopping("Oreo",   List.of("Cat-food", "Muffin-toy", "Mouse-toy"))
        );
    }

    public List<Integer> evenList() {
        return integerList.stream()
                .filter(item -> item % 2 == 0)
                .toList();
    }
    public long evenListSize() {
        return integerList.stream()
                .filter(item -> item % 2 == 0)
                .count();
    }

    /**
     * <b>Display a range of numbers</b>
     * <p>The stream filters the target sequence again: first, it removes all
     * numbers greater than the {@code start} input, and then it filters out all
     * numbers smaller than {@code end}.</p>
     * <p>It does not have a return value; at the end, it prints the items
     * of the filtered list one by one.</p>
     * <p>This function lacks robustness. I probably ought to write some safety
     * checks for it so it doesn't crash, but I won't—partly because I don't feel
     * like it, and partly because this is all just for practice.</p>
     * @param start
     * @param end
     */
    public void printSelectedRange(int start, int end) {
        integerList.stream()
                .filter(item -> item >= start)
                .filter(item -> item <= end)
                .forEach(System.out::println);
    }

    /**
     * <b>How many items in array are bigger than
     * the specific value</b>
     * @param value int
     */
    public long higherCount(int value) {
        try {
            return duplicateIntegerList.stream()
                    .filter(item -> item > value)
                    .count();
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <b>Stream Match</b>
     * <p>This class checks whether an item matching the requested
     * input exists in the target array.</p>
     *
     * <p> {@code matchAny} At least one item must meet the specified condition. </p>
     * <p> {@code matchAll} All items must meet the specified condition. </p>
     * <p> {@code matchNone} No item meets the specified condition. </p>
     */
    static class MatchStream {
        /**
         * <b>At least one true condition</b>
         * <p>It filters in two stages: first, it checks the length of
         * each word, and then it determines whether at least one item
         * in the list begins with the specified letter.</p>
         * @param length
         * @param value
         * @return boolean
         */
        boolean matchAny(int length, String value) {
            return stringNameList.stream()
                    .filter(item -> item.length() > length)
                    .anyMatch(item -> item.startsWith(value));
        }

        /**
         * <b>All conditions are correct.</b>
         * <p>It filters in two stages: first, it checks the length of
         * each word, and then it verifies whether all items in the list
         * begin with the specified letter.</p>
         * @param length
         * @param value
         * @return boolean
         */
        boolean matchAll(int length, String value) {
            return stringNameList.stream()
                    .filter(item -> item.length() > length)
                    .allMatch(item -> item.startsWith(value));
        }

        /**
         * <b>No item meets the condition.</b>
         * <p>It checks whether none of the items in the list satisfy
         * the specified condition. If no item meets the condition,
         * the function returns true.</p>
         * @param length
         * @param value
         * @return boolean
         */
        boolean matchNone(int length, String value) {
            return stringNameList.stream()
                    .filter(item -> item.length() > length)
                    .noneMatch(item -> item.startsWith(value));
        }

        public List<String> stringLisToUpper() {
            return stringNameList.stream()
                    .map(String::toUpperCase)
                    .toList();
        }

        static class StreamConvert {
            public List<Integer> convertStringListToInteger() {
                return stringNameList.stream()
                        .map(String::length)
                        .toList();
            }
            public List<Boolean> convertStringListToBoolean() {
                return stringNameList.stream()
                        .map(item -> item.startsWith("a"))
                        .toList();
            }
        }
    }
    void printNamesUppercase() {
        stringNameList.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }

    /**
     * <b>flatMap() vs map()</b>
     * <p>{@code map} is sufficient for nested or multidimensional lists.
     * If we want to isolate individual elements, we can use
     * {@code flatMap} to apply a condition to each member.</p>
     */
    public List<String> separateStudents() {
        return classroom.stream()
                .flatMap(Collection::stream)
                .toList();
    }
    /**
     * <p>If I had used just {@code map} here, the words would have been split
     * into their own separate arrays; however, by using {@code flatMap}, every
     * single word from the different sentences was placed into a single,
     * organized array.</p>
     * @return [Arsam, loves, coffee, Oreo, is, a, black, cat, Java, is, cool]
     */
    public List<String> separateSentence() {
        return sentences.stream()
                .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
                .toList();
    }

    record Person (String name, int age) {
        public static List<Person> validAge(List<Person> people) {
            return people.stream()
                    .filter(item -> item.age >= 18)
                    .toList();
        }
        public static List<String> names(List<Person> people) {
            return people.stream()
                    .map(Person::name)
                    .toList();
        }
    }
    /**
     * <b>Represents a simplified user data transfer object.</b>
     *
     * @param username the name of the user
     * @param isAdult  whether the user is 18 or older
     */
    record UserDTO(String username, boolean isAdult) {
        /**
         * Converts the people list into a list of {@link UserDTO} objects.
         *
         * @return a list of {@link UserDTO}
         */
        public static List<UserDTO> convert() {
            return people.stream()
                    .map(person -> new UserDTO(
                            person.name,
                            person.age >= 18
                    ))
                    .toList();
        }
    }
    record Shopping (String customer, List<String> items) {}
    static class ProcessShopping {
        public List<String> itemsOrdered() {
            return orders.stream()
                    .flatMap(item -> item.items.stream())
                    .toList();
        }

        /**
         * <b>Number of repetitions of a specific item</b>
         * <p>First, we stream the 2D list using {@code flatMap}
         * to obtain a direct, linear sequence of orders.
         * Then, we filter the list and determine the
         * frequency of each item's occurrence.</p>
         * @param order
         * @return 2 [cause Cat-food repeated twice]
         */
        public long duplicateItemOrder(String order) {
            return orders.stream()
                    .flatMap(item -> item.items.stream())
                    .filter(item -> item.equals(order))
                    .count();
        }
        public List<String> uniqueOrders() {
            return orders.stream()
                    .flatMap(item -> item.items.stream())
                    .distinct() // <-- filter only unique ones
                    .toList();
        }

        /**
         * <b>List filtering system</b>
         * <p>First, we sort the list. In the second step, we remove
         * duplicate items. In the third step, we check which items
         * start with the letter 'c', convert them to uppercase, and
         * create a list of strings from the final result.</p>
         * @return [COFFEE]
         */
        public List<String> foo() {
            return orders.stream()
                    .flatMap(item -> item.items.stream())
                    .distinct()
                    .filter(item -> item.startsWith("c"))
                    .map(String::toUpperCase)
                    .toList();
        }
    }

    /**
     * <h5>Exercise centered on Stream</h5>
     * <p>I asked AI to give me some exercises to master the concept
     * of Streams in Java. I requested that the exercises be close
     * to real-world scenarios.</p>
     */
    static class Exercise {
        public void ex1() {
            final List<Integer> numbers = List.of(
                    3, 8, 12, 5, 17, 20, 4, 9, 30
            );
            List<Integer> even = numbers.stream()
                    .filter(number -> number % 2 == 0)
                    .toList();
        }
        public void ex2() {
            final List<String> names = List.of(
                    "Ali", "Arsam", "Sara", "John", "Reza"
            );
            List<String> upper = names.stream()
                    .map(String::toUpperCase)
                    .toList();
        }
        public void ex3() {
            final List<Integer> numbers = List.of(
                    2, 7, 12, 15, 18, 21, 24, 30
            );
            List<Integer> multiCondition = numbers.stream()
                    .filter(number -> number % 2 == 0)
                    .filter(number -> number > 10)
                    .toList();
        }
        public void ex4() {
            final List<Integer> numbers = List.of(
                    10, 55, 23, 78, 91, 42, 67, 12, 88
            );
            long higher = numbers.stream()
                    .filter(number -> number > 50)
                    .count();
        }
        public void ex5() {
            final List<Integer> numbers = List.of(
                    15, 42, 7, 89, 23, 64, 91, 31
            );
            Optional<Integer> max1 = numbers.stream()
                    .max(Integer::compare);
            Integer max2 = numbers.stream()
                    .max(Integer::compare)
                    .orElse(0);
        }
        public void ex6() {
            final List<Integer> numbers = List.of(
                    9, 2, 7, 1, 5, 8, 3
            );
            var VAR = numbers.stream()
                    .sorted()
                    .limit(3)
                    .toList();
        }
        public void ex7() {
            final List<String> names = List.of(
                    "Arsam", "Ali", "Alexander", "Sara",
                    "John", "Amir", "Andrew", "Reza"
            );
            var VAR = names.stream()
                    .filter(name -> name.startsWith("A"))
                    .filter(name -> name.length() > 5)
                    .sorted()
                    .toList();
        }
        public void ex8() {
            final List<Integer> numbers = List.of(
                    1, 2, 3, 4, 5, 6, 7, 8
            );
            List<Integer> VAR = numbers.stream()
                    .filter(number -> number % 2 == 0)
                    .map(number -> (int) Math.pow(number, 2))
                    .toList();
        }
        public void ex91011() {
            record User(
                    String name,
                    int age,
                    String city
            ) {}
            List<User> users = List.of(
                    new User("Arsam", 21, "Tehran"),
                    new User("Ali", 17, "Shiraz"),
                    new User("Sara", 25, "Tehran"),
                    new User("John", 30, "Tabriz"),
                    new User("Reza", 19, "Tehran"),
                    new User("Mary", 16, "Shiraz")
            );
            List<User> legalAge = users.stream()
                    .filter(user -> user.age >= 18)
                    .toList();
            List<String> peopleInCity = users.stream()
                    .filter(user -> user.city.equals("Tehran"))
                    .map(User::name)
                    .toList();
            List<User> ageComparison = users.stream()
                    .sorted(Comparator
                            .comparing(User::age)
                            .reversed())
                    .toList();
        }
    }
    static class Final {
        record Product(
                String name,
                String category,
                double price
        ) {}
        final List<Product> products = List.of(
                new Product("Laptop", "Electronics", 1200),
                new Product("Mouse", "Electronics", 40),
                new Product("Keyboard", "Electronics", 80),
                new Product("Chair", "Furniture", 300),
                new Product("Desk", "Furniture", 500),
                new Product("Monitor", "Electronics", 400),
                new Product("Lamp", "Furniture", 60)
        );
        public List<String> electronicsName() {
            return products.stream()
                    .filter(item -> item.category().equals("Electronics"))
                    .map(Product::name)
                    .map(String::toUpperCase)
                    .toList();
        }
        public List<Double> electronicsPrice() {
            return products.stream()
                    .filter(item -> item.category().equals("Electronics"))
                    .map(Product::price)
                    .toList();
        }
        public OptionalDouble electronicsAveragePrice() {
            return products.stream()
                    .filter(item -> item.category.equals("Electronics"))
                    .mapToDouble(Product::price)
                    .average();
        }
        public Product highestPrice() {
            return products.stream()
                    .max(Comparator.comparing(Product::price))
                    .orElseThrow();
        }
    }

}
