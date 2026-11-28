package com.javacs.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class JIterator {
    /**
     * Iterator is an interface responsible for looping through elements of collection in order.
     *
     * In simple terms, we don't look for each individual index.
     */

    private void run() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);

        Iterator<Integer> iterator = numbers.iterator();

        while (iterator.hasNext()) {
            int number = iterator.next();
            System.out.println(number);
        }

        /*
         * Iterator is useful when we want to have more control over our loop.
         *
         * Ex: direct removal from a collection may cause ConcurrentModificationException.
         */

        Iterator<Integer> iterator1 = numbers.iterator();

        while (iterator1.hasNext()) {
            int number = iterator1.next();

            if (number == 1) iterator1.remove();
        }


        numbers.removeIf(number -> number == 2);
    }
}
