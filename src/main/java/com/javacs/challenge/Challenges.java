package com.javacs.challenge;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A code for masking card information that is tedious and not very efficient.
 */
public final class Challenges {
    private static @NotNull String creditCard(String serial) {
        Objects.requireNonNull(serial);

        String formatSerial = serial
                .replace("-", "")
                .replaceAll("\\s+", "");

        creditCardValidator(formatSerial);
        int length = formatSerial.length();
        char[] digits = formatSerial.toCharArray();

        StringBuilder card = new StringBuilder("XXXX-XXXX-XXXX-");
        for (int i = length - 4; i < length; i++)
            card.append(digits[i]);

        return card.toString();
    }

    private static void creditCardValidator(String serial) {
        Objects.requireNonNull(serial);

        int length = serial.length();
        String[] digits = new String[length];

        if (length < 16)
            throw new RuntimeException(
                    "Card number length cannot be lower than 16."
            );

        for (int i = 0; i < length; i++)
            digits[i] = String.valueOf(serial.charAt(i));

        for (String digit : digits) {
            try {
                Integer.parseInt(digit);
            }
            catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(creditCard("5425-6514-4225-3612"));
    }
}
