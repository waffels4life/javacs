package com.javacs.OOP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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

    static class Playlist {

        private static final int MAX_SONGS = 100;
        private final Map<String, String> songs = new LinkedHashMap<>();

        public void add(String song) {

            Objects.requireNonNull(song);
            if (songs.size() > 100)
                throw new ArrayStoreException(
                        "The limit for the number of songs in a playlist is 100"
                );

            String name = song.strip();

            if (name.isEmpty())
                throw new IllegalArgumentException(
                        "Song name must not be blank"
                );

            if (songs.size() == MAX_SONGS)
                throw new IllegalStateException(
                        "Playlist is full (max "
                                + MAX_SONGS
                                + " songs)"
                );

            String key = name.toLowerCase(Locale.ROOT);

            if (songs.putIfAbsent(key, name) != null)
                throw new IllegalArgumentException(
                        "Duplicate song: " + name
                );

        }

        public List<String> getSongs() {
            return List.copyOf(songs.values());
        }
    }

    record Song(String name) {

        Song {

            Objects.requireNonNull(name, "name must not be null");

            name = name.strip();
            if (name.isEmpty())
                throw new IllegalArgumentException(
                        "Song name must not be blank"
                );
        }

        @Override public boolean equals(Object o) {
            return o instanceof Song(String Other)
                    && name.equalsIgnoreCase(Other);
        }

        @Override public int hashCode() {
            return name.toLowerCase(Locale.ROOT).hashCode();
        }
    }
}
