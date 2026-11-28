package com.javacs.collections;

import javax.print.attribute.standard.OrientationRequested;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/*
 * Short take away - in linked list values  are not next to each other in memory. Instead, they
 * are connected through nodes that could be in different parts of the heap. the connection is
 * established with [next] and [prev] node reference. The best use for linked list are when
 * most of our work is with the beginning or end of the list.
 */
public final class JLinkedList {
    /*
     * Linked list is a part of java collection framework.
     *
     * each element is stored in a node, containing the element and the reference to the
     * previous and next node.
     *
     * [1] The size of linked list can grow or shrink dynamically.
     * [2] It maintains the insertion order of elements.
     * [3] It also allows duplicate elements.
     *
     * Doubly linked list:
     * null <- [10] <-> [20] <-> [30] -> null
     *
     */


    /*
     * Linked list does not provide constant-time index access.
     * Accessing an element by index requires traversal.
     */
    private final List<Integer> integerList = new LinkedList<>();

    private void print() {
        // It's better to use {enhanced for} for linked lists.
        for (Integer values : integerList)
            System.out.println(values);
    }

    private final LinkedList<String> orders = new LinkedList<>();

    private void processOrders() {
        orders.clear();

        orders.add("Order-1001");
        orders.add("Order-1002");
        orders.add("Order-1003");

        orders.addFirst("Order-1000");

        orders.removeLast();
        System.out.println(orders.getFirst());

        for (String order : orders)
            System.out.println(order);
    }

    public static void main(String[] args) {
        JLinkedList demo = new JLinkedList();
        demo.processOrders();
    }

    // Double Ended Queue
    private final Deque<String> orderQueue = new LinkedList<>();

    /*
     * Front                         Rear
     *   ↓                             ↓
     * [1001] <-> [1002] <-> [1003]
     */
    private void addOrdersToQueue() {
        orderQueue.addLast("Order-1001");
        orderQueue.addLast("Order-1002");
        orderQueue.addLast("Order-1003");

        orderQueue.addFirst("Order-1000");
    }

    private void processQueue() {
        while (!orderQueue.isEmpty()) {
            String order = orderQueue.removeFirst();
            System.out.println("Processing order:" + order);
            System.out.println("Remaining orders: " + orderQueue.size());
        }
    }

}
