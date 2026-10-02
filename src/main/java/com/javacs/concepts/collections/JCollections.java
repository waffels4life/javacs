package com.javacs.concepts.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class JCollections {
    /*
     * Collection: an object responsible for holding other objects.
     *      [1] List<Object> things
     *      [2] Set<Integer> numbers
     *      [3] Queue<?> tasks
     *      ...
     * [NOTE] >> Map<K,V> is NOT part of the Collection.
     *        >> Map is a set of 'key-value mapping', not elements.
     *                     Iterable
     *                        │
     *                    Collection
     *                        │
     *           ┌────────────┼────────────┐
     *           │            │            │
     *          List          Set        Queue
     *           │             │           │
     *      ┌────┼────┐    ┌───┼───┐     Deque
     *      │    │    │    │   │   │
     * ArrayList │  LinkedList │ HashSet
     *           │             │
     *         Vector     LinkedHashSet
     *           │             │
     *         Stack        TreeSet
     *
     * Map
     *  │
     *  ├── HashMap
     *  ├── LinkedHashMap
     *  ├── TreeMap
     *  ├── Hashtable
     *  ├── EnumMap
     *  └── ...
     *
     * [NOTE] >> Collection != Collections
     *           │             └── a utility class
     *           └── an interface
     *
     * [NOTE] >> Its much better to implement the interface, that way our code will be
     *           dependent on contract, not implementation.
     *           List<Objects> things = new LinkedList<>();
     *                       List
     *                        ▲
     *                        │
     *              ┌─────────┼──────────┐
     *              │         │          │
     *         ArrayList  LinkedList    ...
     */
    static class LearnList<T> {
        /*
         * List >> keeps the collection order
         *      >> allowed duplicates
         *      >> comes with index
         *
         *  List
         *  ├── Ordered
         *  ├── Indexed
         *  └── Allows duplicates
         *
         */

        private final List<T> list = new ArrayList<>();

        public void add(T t) {
            list.add(t);
        }

        public List<T> get() {
            return List.copyOf(list);
        }

        public boolean contains(T t) {
            return list.contains(t);
        }

        public void remove(T t) {
            list.remove(t);
        }

        public void clear() {
            list.clear();
        }

        public void refactor(int i, T t) {
            list.set(i,t);
        }

        public void removingInteger() {
            List<Integer> integerList = new ArrayList<>();
            integerList.add(1);
            integerList.add(2);
            integerList.add(3);

            /*
             * Boxing/unboxing - overloading
             * integerList.remove(1)                  >> remove index 1 >> [2]
             * integerList.remove((Integer) 1)        >> remove value 1 >> [1]
             * integerList.remove(Integer.valueOf(1)) >> remove value 1 >> [1]
             */

            integerList.remove(Integer.valueOf(1));
        }

        public int getValueIndex(T t) {
            return list.indexOf(t);
        }

        public void listConcurrentModificationException() {

            List<Integer> nums = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));

            // not safe >> [ConcurrentModificationException]
            for (Integer n : nums)
                if (n % 2 == 0) nums.remove(n);

            // safe and clean
            nums.removeIf(n -> n % 2 == 0);

            // true
            for (Iterator<Integer> it = nums.iterator(); it.hasNext();)
                if (it.next() % 2 == 0) it.remove();
        }
    }
}
