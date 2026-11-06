package com.javacs.challenge;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Challenges {

    private static final int CARD_LENGTH = 16;

    public static @NotNull String maskCreditCard(String cardNumber) {
        Objects.requireNonNull(cardNumber, "cardNumber must not be null");

        String digits = cardNumber.replaceAll("[\\s-]", "");
        validateCreditCard(digits);

        return "XXXX-XXXX-XXXX-" + digits.substring(CARD_LENGTH - 4);
    }

    private static void validateCreditCard(@NotNull String digits) {
        if (digits.length() != CARD_LENGTH) {
            throw new IllegalArgumentException(
                    "Card number must be exactly " + CARD_LENGTH + " digits, got " + digits.length()
            );
        }
        for (int i = 0; i < digits.length(); i++)
            if (!Character.isDigit(digits.charAt(i)))
                throw new IllegalArgumentException(
                        "Card number must contain only digits (found: '" + digits.charAt(i) + "')"
                );
    }

    public static void main(String[] args) {
        System.out.println(maskCreditCard("5425-6514-4225-3612"));
        System.out.println(maskCreditCard("5425 6514 4225 a612"));
        System.out.println(maskCreditCard("5425651442253612"));
    }
}