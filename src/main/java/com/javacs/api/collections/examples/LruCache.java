package com.javacs.api.collections.examples;

import java.util.ArrayList;
import java.util.List;

public class LruCache<T> {

    private final int MAX_CAPACITY;

    public LruCache(int max_capacity) {
        if (max_capacity <= 0) throw new IllegalArgumentException(
                "max capacity should be higher than zero"
        );
        this.MAX_CAPACITY = max_capacity;
    }

    private final List<T> list = new ArrayList<>();
    private final List<T> delete = new ArrayList<>();
    private int miss = 0;
    private int hit = 0;

    public void access(T item) {

        if (!list.contains(item)) {
            miss++;
            list.addFirst(item);

            if (list.size() > MAX_CAPACITY) {

                delete.addFirst(list.getLast());

                if (delete.size() > MAX_CAPACITY)
                    delete.removeLast();

                list.removeLast();
            }
        }
        else {
            hit++;
            list.removeIf(x -> x.equals(item));
            list.addFirst(item);
        }
    }

    public int getMiss() {
        return miss;
    }

    public int getHit() {
        return hit;
    }

    public void contents() {
        System.out.println(list);
    }

    public void deleted() {
        System.out.println(delete.isEmpty()
                ? "[Empty]"
                : delete);
    }

    public static void main(String[] args) {
        LruCache<String> lruCache = new LruCache<>(3);
        lruCache.access("A");
        lruCache.access("B");
        lruCache.access("C");
        lruCache.access("A");
        lruCache.access("D");
        lruCache.access("E");
        lruCache.access("F");
        lruCache.access("G");

        lruCache.contents();
        lruCache.deleted();
        System.out.print(lruCache.getHit() + "/" + lruCache.getMiss());
    }
}
