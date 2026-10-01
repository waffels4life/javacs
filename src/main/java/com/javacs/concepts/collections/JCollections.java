package com.javacs.concepts.collections;

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
}
