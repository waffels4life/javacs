package com.javacs.projects.monody;

import com.javacs.projects.RandomWords;

import java.util.Arrays;
import java.util.Random;

public class BirthdayExperiment {
    public static void main(String[] args) {

        String[] words = new RandomWords().start(100);
        int[] hash = toHashArray(words);

        Arrays.stream(hash).forEach(System.out::println);
    }



    private static int[] createArray(int size, int bound) {
        if (size <= 0 || bound <= 0)
            throw new IllegalArgumentException(
                    "Values cannot be zero or negative."
            );

        Random random = new Random();
        int randomNumber = random.nextInt(bound) + 1;
        int[] randomNumbers = new int[size];

        for (int i = 0; i < size; i++)
            randomNumbers[i] = random.nextInt(bound) + 1;

        return randomNumbers;
    }

    private static int[] toHashArray(String[] array) {
        int[] hashArray = new int[array.length];

        for (int i = 0; i < array.length; i++)
            hashArray[i] = array[i].hashCode();

        return hashArray;
    }

    private static void calculateCollision(int[] array) {
        int collision = 0;

        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {

            }
        }
    }
}
