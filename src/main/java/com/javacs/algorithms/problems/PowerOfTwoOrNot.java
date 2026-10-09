package com.javacs.algorithms.problems;

public final class PowerOfTwoOrNot {

    private PowerOfTwoOrNot() {}

    public static boolean checkIfPowerOfTwoOrNot(final int number) {
        /*
         * The special `&` symbol is used to compare the bits of two numbers.
         * The bits of the two numbers are subjected to a logical AND operation
         * bit by bit, resulting in the formation of a new bit.
         *
         * Numbers that are powers of two follow a specific pattern: the bits at position 0 and position 1
         * (in binary representation). Numbers that are one less than these powers of two also follow a
         * distinct pattern—they are the bitwise complements of the powers of two. Performing a logical
         * AND operation between the two results in zero.
         *
         *      8  = 1000
         *      7  = 0111
         *      &  = 0000  =  0 (+)
         *
         *      16 = 10000
         *      15 = 01111
         *      &  = 00000  =  0 (+)
         *
         * [number != 0] -> to prevent edge case.
         *      0    = 0000 0000 0000 0000 0000 0000 0000 0000
         *      -1   = 1111 1111 1111 1111 1111 1111 1111 1111
         *      &    = 0000 0000 0000 0000 0000 0000 0000 0000  = 0 (???)
         */
        return number != 0 && ((number & (number - 1)) == 0);
    }
}
