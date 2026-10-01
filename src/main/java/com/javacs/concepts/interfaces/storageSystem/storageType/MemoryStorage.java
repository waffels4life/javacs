package com.javacs.concepts.interfaces.storageSystem.storageType;

import com.javacs.concepts.interfaces.storageSystem.StorageSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryStorage<T> implements StorageSystem<T> {

    private final List<T> memory = new ArrayList<>();

    @SafeVarargs @Override public final void save(T... files) {
        Collections.addAll(memory, files);
    }

    @Override public void delete(T file) {
        memory.removeIf(storeFiles -> storeFiles.equals(file));
    }

    @Override public boolean exist(T file) {
        return memory.stream()
                .anyMatch(storeFile -> storeFile.equals(file));
    }

    @Override public List<T> getMemory() {
        return List.copyOf(memory);
    }

    public T getByIndex(int index) {
        return memory.get(index);
    }
}
