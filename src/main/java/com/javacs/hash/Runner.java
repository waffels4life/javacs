package com.javacs.hash;

import java.util.Objects;

public class Runner {
    public static void main(String[] args) {
        EqualsHash<Integer> equalsHash1 = new EqualsHash<>(1);

        System.out.println(
                equalsHash1.hashCode()
        );
    }
}
