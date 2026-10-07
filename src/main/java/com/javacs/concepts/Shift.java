package com.javacs.concepts;

public class Shift {
    /*
     * 0xFF336699
     *
     * F    F    3    3    6    6    9    9
     * 1111 1111 0011 0011 0110 0110 1001 1001
     * └─A─────┘ └─R─────┘ └─G─────┘ └─B─────┘
     */
    int number    = 0b00000101;         // 5
    int timesTwo  = number << 1;        // 0b00001010 -> 10
    int timesFour = number << 2;        // 0b00010100 -> 20

    /*
     * number: 00000000 00000000 11111111 00110011
     * mask:   00000000 00000000 00000000 11111111
     *         ───────────────────────────────────
     * result: 00000000 00000000 00000000 00110011
     */

    public static void printBitValue() {
        int value = 1;
        for (int i = 1; i <= 16; i++) {
            value = value * 2;
            System.out.printf("%d bit -> %d values%n", i, value);
        }
    }

    public static void main(String[] args) {
        printBitValue();
    }
}
