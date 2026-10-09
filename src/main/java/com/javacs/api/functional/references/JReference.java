package com.javacs.api.functional.references;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <h5>Method Reference</h5>
 * <p>Reference a method with this name as a usable function.</p>
 * {@code ObjectReference::MethodName}
 */
public class JReference {
    /**
     * <p>Same as <b>text -> System.out.println(text);</b></p>
     * <p>Execute the referenced method whenever necessary.</p>
     */
    Consumer<String> print = System.out::println;
    /**
     * Function<String, Integer> lengthFunction = text -> text.length();
     */
    Function<String, Integer> lengthFunction = String::length;
    public Integer getWordLength(String word) {
        return lengthFunction.apply(word);
    }
    private final String prefix = "Hello";
    /**
     * Function<String, String> addPrefix = text -> prefix.concat(text);
     */
    Function<String, String> addPrefix = prefix::concat;
    public String addPrefix(String word) {
        return addPrefix.apply(word);
    }
    Supplier<ArrayList<Integer>> listSupplier = ArrayList::new;
    Function<String, StringBuilder> builderFunction = StringBuilder::new;
    public List<Integer> stringToIntegerList(List<String> list) {
        return list.stream()
                .map(Integer::parseInt)
                .toList();
    }
    private final Function<Object, String> foo = String::valueOf;
    public String convertToString(Object listOfThings) {
        return foo.apply(listOfThings);
    }
    protected final BinaryOperator<Integer> max = Math::max;
    protected final BinaryOperator<Integer> min = Math::min;

    BiFunction<Double, Double, Double> pow = Math::pow;

    public Integer sumOfList(List<Integer> list) {
        return list.stream()
                .reduce(0, Integer::sum);
    }
    public void sortOfList(List<Integer> list) {
        list.sort(Integer::compare);
    }

    Comparator<Integer> comp = Integer::compareTo;

    protected List<?> checkNotNull(List<?> list) {
        return list.stream()
                .filter(Objects::nonNull)
                .toList();
    }

    List<Boolean> booleanList = new ArrayList<>();
    Consumer<Boolean> booleanConsumerAdder = booleanList::add;

    Map<String, Integer> map = new HashMap<>();
    Function<String, Integer> getter = map::get;
    BiConsumer<String, Integer> putter = map::put;
    record User(
            String name,
            int age,
            String email,
            boolean active,
            List<String> interests
    ) {}
    public List<User> activeUsers(List<User> userList) {
        return userList.stream()
                .filter(User::active)
                .toList();
    }
    public List<User> filterByAge(
            List<User> userList,
            int minAge,
            int maxAge) {
        Predicate<User> ageRange = user ->
                user.age() >= minAge && user.age <= maxAge;
        return userList.stream()
                .filter(ageRange)
                .toList();

    }
    public List<User> sortByAge(List<User> userList) {
        return userList.stream()
                .sorted(Comparator.comparing(User::age))
                .toList();
    }
    public List<User> sortUsersByNameDescending(List<User> users) {
        return users.stream()
                .sorted(Comparator.comparing(User::name).reversed())
                .toList();
    }
    public Map<String, List<User>> groupByAgeCategory(List<User> users) {
        Function<User, String> ageCategory = user -> {
            if (user.age() < 18) return "Teen";
            else if (user.age() < 30) return "Young Adult";
            else if (user.age() < 50) return "Adult";
            else return "Senior";
        };
        return users.stream()
                .collect(Collectors.groupingBy(ageCategory));
    }
    public double calculateAverageAge(List<User> users) {
        return users.stream()
                .collect(Collectors.averagingInt(User::age));
    }
    public static final Function<User, String> TO_EMAIL_DOMAIN =
            user -> user.email().split("@")[1];

}
