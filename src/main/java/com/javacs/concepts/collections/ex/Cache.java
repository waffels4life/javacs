package com.javacs.concepts.collections.ex;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cache<T> {

    private final List<T> items = new ArrayList<>();
    private final List<T> evicted = new ArrayList<>();
    private final int max;

    public Cache(int max) {
        if (max <= 0) throw new IllegalArgumentException(
                "max must be > 0"
        );
        this.max = max;
    }

    public void record(T item) {
        Objects.requireNonNull(item, "item");
        items.add(item);

        int excess = items.size() - max;
        if (excess > 0) {
            List<T> oldest = items.subList(0, excess);
            evicted.addAll(oldest);
            oldest.clear();
        }
    }

    public List<T> snapshot() {
        return List.copyOf(items);
    }

    public List<T> evictedSnapshot() {
        return List.copyOf(evicted);
    }
}