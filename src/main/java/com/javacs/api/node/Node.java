package com.javacs.api.node;

public class Node<T> {
    /**
     * data >> information that node holds
     * node >> reference to another node object
     */
    T data;
    Node<T> node;

    public Node(T data) {
        this.data = data;
    }
}
