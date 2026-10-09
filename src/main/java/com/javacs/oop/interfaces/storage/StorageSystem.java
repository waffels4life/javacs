package com.javacs.oop.interfaces.storage;

import java.util.List;

public interface StorageSystem<T> {
    List<T> getMemory();
    void save(T... files);
    void delete(T file);
    boolean exist(T file);
}
