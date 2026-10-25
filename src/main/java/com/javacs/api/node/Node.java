package com.javacs.api.node;

/*
 * [data | next] → [data | next] → [data | next] → null
 */
public final class Node<T> {
    private final T data;
    private Node<T> next;

    public Node(T data) {
        this.data = data;
    }

    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }
    public Node<T> getNext() {
        return next;
    }
    public void setNext(Node<T> next) {
        this.next = next;
    }

    @Override public String toString() {
        return String.valueOf(data);
    }
}