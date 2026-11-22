package com.javacs.hash;

import java.util.Objects;

public final class EqualsHash<T> {

    private final T value;

    public EqualsHash(T value) {
        this.value = value;
    }

    @Override public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof EqualsHash<?> other)) return false;

        return Objects.equals(this.value, other.value);
    }

    @Override public int hashCode() {
        return Objects.hashCode(value);
    }
}
