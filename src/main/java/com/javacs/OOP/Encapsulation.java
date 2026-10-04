package com.javacs.OOP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Encapsulation {
    /*
     * Encapsulation >> Bundle the data and the behavior that operates on it together,
     *                  and hide the internal details so that the outside world interacts
     *                  with it only through a controlled interface.
     *
     * Primary goal  >> Maintaining the invariant (a rule that must always hold true).
     */
    static class Account {

        private long balanceInCents;

        public Account(long initBalanceInCents) {
            if (initBalanceInCents < 0)
                throw new IllegalArgumentException(
                        "Initial balance cannot be negative"
                );

            this.balanceInCents = initBalanceInCents;
        }

        public void deposit(long amountInCents) {
            if (amountInCents <= 0)
                throw new IllegalArgumentException(
                        "Deposit must be positive"
                );

            balanceInCents += amountInCents;
        }

        public void withdraw(long amountInCents) {
            if (amountInCents <= 0)
                throw new IllegalArgumentException(
                        "Withdraw must be positive"
                );

            if (amountInCents > balanceInCents)
                throw new IllegalStateException(
                        "Insufficient funds"
                );

            balanceInCents -= amountInCents;
        }

        public long getBalanceInCents() {
            return balanceInCents;
        }
    }

    /*
     * [1] Encapsulation means preserving invariants, not just using `private` fields.
     * [2] Don't write automatic getters and setters; expose only what is necessary.
     * [3] Instead of `setX`, write methods that reflect the domain logic.
     * [4] Never directly expose an internal mutable object.
     * [5] Make illegal states unrepresentable.
     * */

    static class Library {
        /*
         * `private` only hides the field name, not the object itself.
         */
        private final List<String> books = new ArrayList<>();

        /*
         * [DANGER] Hidden Pitfall: Leaking Internals
         */
        public List<String> getBooksUnsafe() {
            return books;
        }

        public List<String> getBooks() {
            return Collections.unmodifiableList(books); // read only
        }
    }
}
