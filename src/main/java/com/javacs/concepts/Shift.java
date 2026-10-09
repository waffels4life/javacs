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

    /**
     *
     * position :  7    6   5   4   3  2  1  0
     * value    : 128  64  32  16   8  4  2  1
     *
     *
     * 1) Binary → Decimal
     *
     * Add the place values where the bit is 1.
     *
     *    1   0   1   1   0   1   1   0
     *  128  64  32  16   8   4   2   1
     *    ↓       ↓   ↓       ↓   ↓
     *  128   +  32 + 16  +   4 + 2  = 182
     *
     *
     * 2) Decimal → Binary (repeated division by 2)
     *
     * Keep dividing by 2 and write down the remainders. The answer is the remainders read from bottom to top.
     *
     * 37 ÷ 2 = 18  remainder 1   ↑
     * 18 ÷ 2 =  9  remainder 0   │
     *  9 ÷ 2 =  4  remainder 1   │  read from
     *  4 ÷ 2 =  2  remainder 0   │  bottom to top
     *  2 ÷ 2 =  1  remainder 0   │
     *  1 ÷ 2 =  0  remainder 1   │
     *
     * 37 = 100101₂   (check: 32 + 4 + 1 = 37 ✓)
     *
     * Binary → Octal (groups of 3 bits, from the right):
     *
     * 10110110 → pad on the left: 010 110 110
     *                               ↓   ↓   ↓
     *                               2   6   6   → 0o266
     *
     * Check: 2×64 + 6×8 + 6 = 128 + 48 + 6 = 182 ✓
     *
     * 3) Hexadecimal: 16 digits
     *
     * We need more than 0–9, so we use A to F as well:
     *
     * Dec  Hex  Binary        Dec  Hex  Binary
     *  0    0   0000            8    8   1000
     *  1    1   0001            9    9   1001
     *  2    2   0010           10    A   1010
     *  3    3   0011           11    B   1011
     *  4    4   0100           12    C   1100
     *  5    5   0101           13    D   1101
     *  6    6   0110           14    E   1110
     *  7    7   0111           15    F   1111
     *
     * Binary :  1011  0110
     *            ↓     ↓
     * Hex    :   B     6    → 0xB6
     *
     * Decimal check: 11×16 + 6 = 176 + 6 = 182 ✓
     *
     * Why hex matters for programmers
     *      [1] Colors: #FF8800 is three bytes: FF red, 88 green, 00 blue (R=255, G=136, B=0).
     *      [2] Memory addresses, MAC addresses, UUIDs, and hashes are all shown in hex.
     *      [3] Hex dump (your final project!) shows file contents byte by byte in hex.
     *      [4] Octal has one famous use: Unix permissions like chmod 755. Each digit is 3 bits (read/write/execute).
     */

    public static void numericSystem() {
        int a = 0b1011;      // binary → 11
        int b = 0xB6;        // hex    → 182
        int c = 0266;        // octal  → 182   ⚠️ leading zero!
        int d = 1_000_000;   // underscores are only for readability

        System.out.println(Integer.toBinaryString(182)); // 10110110
        System.out.println(Integer.toHexString(182));    // b6  (lowercase!)
        System.out.println(Integer.toOctalString(182));  // 266
        System.out.println(Integer.parseInt("B6", 16));  // 182
        System.out.println(Integer.toString(182, 2));    // any base from 2 to 36
    }

    /**
     * chmod   6        4        0
     *         ↓        ↓        ↓
     * bits   110      100      000
     *         ↓        ↓        ↓
     * perm   rw-      r--      ---
     *        owner    group    others
     */

    public static void main(String[] args) {
        printBitValue();
    }
}
