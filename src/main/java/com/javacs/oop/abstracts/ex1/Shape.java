package com.javacs.oop.abstracts.ex1;

public abstract class Shape {
    abstract double area(double width, double height);

    void describe() {
        System.out.println("This is a shape.");
    }
}
