package com.javacs.generics;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface JGenerics {

    <T> void upperBound(List<? extends T> list);

    <T> void lowerBound(Collection<? super T> collection);

    <T extends Number> void upperBoundedGeneric(T t);

    <T> T genericReturnType();

    <T, R> void functionInput(Function<? super T, R> function);

    <T, R> void functionOutput(Function<T, ? extends R> function);

    <T> void predicate(Predicate<? super T> predicate);

    <R> void consumer(Consumer<? super R> consumer);

    <T, R> void q9(Collection<? extends T> collection,
                   Function<? super T, ? extends R> function
    );

    <T> void q10(Supplier<? extends Collection<? extends T>> collectionSupplier,
                 Consumer<? super T> consumer
    );

    <T> void q11(List<? extends T> list,
                 Comparator<? super T> comparator
    );

    <T, R> void q12(
            Stream<T> stream,
            Predicate<? super T> predicate,
            Function<? super T, ? extends R> function,
            Consumer<? super R> consumer
    );

    <T extends Number> void q14(T t1, T t2);

    <T, R extends Number> void q15(Function<? super T, ? extends R> function);

    <T extends R, R> void q16();

    void q17(Map<String, ? extends Number> map);
}
