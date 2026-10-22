package com.javacs.algorithms.recursion;

import java.util.Objects;

public final class JRecursion {
    public JRecursion() {}

    public int sum(int number) {
        if (number < 0)
            throw new IllegalArgumentException(
                    "Starting number cannot be lower or equal to zero."
            );

        try {
            if (number == 1) return 1;
            else return number + sum(number - 1);
        }
        catch (StackOverflowError e) {
            throw new RuntimeException(e);
        }
    }
}
