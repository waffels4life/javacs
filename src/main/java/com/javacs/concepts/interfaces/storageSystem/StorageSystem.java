package com.javacs.concepts.interfaces.storageSystem;

import java.util.List;

public interface StorageSystem<T> {
    List<T> getMemory();
    void save(T... files);
    void delete(T file);
    boolean exist(T file);
}
