package com.javacs.algorithms.problems;

public final class PowerOfFour {

    private PowerOfFour() {}

    public static boolean checkPowerOfFour(int number) {
        if (number <= 0) return false;

        /**
         * Here, based on the same algorithm we used to identify multiples of 2, we
         * first determine whether the number in question is even—and specifically
         * a multiple of 2—since multiples of 4 and 2 are directly related.
         */
        boolean isPowerOfTwo = (number & (number - 1)) == 0;
        /**
         * There is a specific pattern for the bits of numbers that are multiples of four.
         * The digit '1' appears only in even positions. With this in mind, we need data
         * where '1' is in an odd position and '0' is in an even position (0x55555555); this
         * way, the result of a logical AND operation becomes zero.
         *
         * 16 = 00010000
         * 0x55555555 = 01010101010101010101010101010101
         * 16 & (16-1) = 16 & 15 = 0  (power of two)
         * 16 & 0x55555555 != 0
         */
        boolean hasEvenBitPosition = (number & 0x55555555) == 0;

        return isPowerOfTwo && hasEvenBitPosition;
    }

}
