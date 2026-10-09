package com.javacs.books.effectivejava;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class EffectiveJava {

    // Flyweight Pattern
    public static Boolean valueOf(boolean booleans) {
        return booleans ? Boolean.TRUE : Boolean.FALSE;
    }

    static class CoffeeOrder {

        private final String    type;
        private final int       size;
        private final boolean   hot;

        private CoffeeOrder(String type,
                           int size,
                           boolean hot) {
            this.type = type;
            this.size = size;
            this.hot  = hot;
        }

        public static CoffeeOrder espresso() {
            return new CoffeeOrder("espresso", 30, true);
        }

        public static CoffeeOrder latte(int size) {
            return new CoffeeOrder("latte", size, true);
        }

        public static CoffeeOrder iceLatte(int size) {
            return new CoffeeOrder("latte", size, false);
        }

        private static final CoffeeOrder DEFAULT_ESPRESSO
                = new CoffeeOrder(
                        "espresso", 30, true
        );

        public static CoffeeOrder defaultEspresso() {
            return DEFAULT_ESPRESSO;
        }

        @Override public String toString() {
            return (hot ? "Hot" : "Iced")
                    + " " + type
                    + "(" + size + ")";
        }

    }

    @SuppressWarnings("all") static class NutritionFood {
        /*
         * Why is it elegant?
         *
         * Every parameter has a name—nothing is ambiguous.
         * Mandatory parameters are in the Builder constructor—they won't be forgotten.
         * The final object is immutable—making it thread-safe.
         * Code readability is high—as the book puts it:
         *     - "simulates named optional parameters as found in Python and Scala."
         */
        private final int servingSize;
        private final int servings;
        private final int calories;
        private final int fat;
        private final int sodium;
        private final int carbohydrate;

        private NutritionFood(Builder builder) {
            servingSize  = builder.servingSize;
            servings     = builder.servings;
            calories     = builder.calories;
            fat          = builder.fat;
            sodium       = builder.sodium;
            carbohydrate = builder.carbohydrate;
        }

        public static class Builder {
            private final int servingSize;
            private final int servings;
            private int calories     = 0;
            private int fat          = 0;
            private int sodium       = 0;
            private int carbohydrate = 0;

            public Builder(int servingSize, int servings) {
                this.servingSize = servingSize;
                this.servings    = servings;
            }

            public Builder calories(int val)     { calories = val;     return this; }
            public Builder fat(int val)          { fat = val;          return this; }
            public Builder sodium(int val)       { sodium = val;       return this; }
            public Builder carbohydrate(int val) { carbohydrate = val; return this; }

            public NutritionFood build() {
                return new NutritionFood(this);
            }
        }
    }

    @SuppressWarnings("all") abstract class Pizza {
        public enum Topping { HAM, MUSHROOM, ONION, PEPPER, SAUSAGE }
        private final Set<Topping> toppings;

        abstract static class Builder<T extends Builder<T>> {

            EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

            public T addTopping(Topping topping) {
                toppings.add(Objects.requireNonNull(topping));
                return self();
            }

            abstract Pizza build();

            protected abstract T self();
        }

        Pizza(Builder<?> builder) {
            toppings = builder.toppings.clone();
        }
    }
}
