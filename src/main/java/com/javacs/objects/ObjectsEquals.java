package com.javacs.objects;

import java.util.Objects;

public final class ObjectsEquals {
    private final int x, y;

    public ObjectsEquals(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /*
     * Without @Override, new method will be created >> overload
     */
    @Override public boolean equals(Object object) {
        /*
         * `this`   >> refers to the object on which `equals` was called.
         * `==`     >> compares the identity (memory address) of objects.
         *
         * In other words, if two variables refer to the same object, they are guaranteed
         * to be equal, so we skip the rest of the checks.
         *
         * Speed              >> Comparing addresses is much cheaper than comparing fields.
         * Reflexive contract >> `a.equals(a)` must always be `true`.
         */
        if (this == object) return true;

        /*
         * a) o instanceof Point >> It asks: "Is o of type Point?"
         *
         * If o is a String     → false
         * If o is null         → false (instanceof always returns false for null,
         *                        so there is no need to check for null separately)
         *
         * b) Point other (pattern matching, since Java 16)
         *
         * If the result is `true`, it automatically casts `o` and assigns it to a new variable
         * named `other` of type `Point`. Before Java 16, you had to write it like this:
         *
         * if (!(o instanceof Point)) return false;
         * Point other = (Point) o;   // manual cast
         *
         * A subtle point regarding scope:
         *
         * `objectsEquals` is defined only when the `instanceof` condition evaluates to true.
         * Since our condition is negated with `!`—causing the method to return if the
         * condition is false—the compiler is certain that `objectsEquals` is guaranteed
         * to have a value past that line, allowing you to use it.
         */
        if (!(object instanceof ObjectsEquals objectsEquals)) return false;

        /*
         * x == other.x     >> Compares the x-value of the current object with the x-value of the other object.
         * y == other.y     >> Does the same for y.
         * &&               >> Both must be equal. If the first condition evaluates to false, the second is
         *                     not checked at all (short-circuiting).
         */
        return x == objectsEquals.x && y == objectsEquals.y;
    }
    /*
     * Point a = new Point(1, 2);
     * Point b = new Point(1, 2);
     *
     * a.equals(b);
     * // line 3: a == b?            -> false (different objects), continue
     * // line 4: b instanceof Point -> true, other = b, continue
     * // line 5: 1 == 1 && 2 == 2   -> true
     * // result: true
     *
     * a.equals(null);
     * // line 3: false
     * // line 4: null instanceof Point -> false, !false = true -> return false
     *
     * a.equals("hello");
     * // line 4: String is not a Point -> return false
     */
}
