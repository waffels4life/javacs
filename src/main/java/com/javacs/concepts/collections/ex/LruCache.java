package com.javacs.concepts.collections.ex;

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
    private int miss = 0;
    private int hit = 0;

    public void access(T item) {
        if (!list.contains(item)) {
            miss++;
            list.addFirst(item);
            if (list.size() > MAX_CAPACITY)
                list.removeLast();
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

    public static void main(String[] args) {
        LruCache<String> lruCache = new LruCache<>(3);
        lruCache.access("A");
        lruCache.access("B");
        lruCache.access("C");
        lruCache.access("A");
        lruCache.access("D");

        lruCache.contents();
        System.out.print(lruCache.getHit() + "/" + lruCache.getMiss());
    }
}
