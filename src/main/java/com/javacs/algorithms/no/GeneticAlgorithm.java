package com.javacs.algorithms.no;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public final class GeneticAlgorithm {

    @Contract(pure = true)
    private GeneticAlgorithm() {}

    // The length of each potential solution
    private static final int CHROMOSOME_LENGTH = 8;

    // The number of solutions we keep in each generation
    private static final int POPULATION_SIZE = 10;

    // The rate that each number could mutate
    private static final double MUTATION_RATE = 0.01;

    // for creating random bits
    private static final Random RANDOM = new Random();

    private static int @NotNull [] createIndividual() {
        int[] individual = new int[CHROMOSOME_LENGTH];

        /*
         * Output   => [1, 0, 1, 1, 0, 0, 1, 0]
         * bound(2) => range: [0,1]
         */
        for (int i = 0; i < individual.length; i++)
            individual[i] = RANDOM.nextInt(2);

        return individual;
    }

    /*
     * Each row is an individual.
     *
     * Each individual has 8 bits.
     *
     * The entire population consists of 10 individuals.
     */
    private static int @NotNull [][] createPopulation() {
        int[][] population = new int[POPULATION_SIZE][];

        /*
         * population[0] = [1, 0, 1, 1, 0, 0, 1, 0]
         * population[1] = [0, 1, 1, 0, 1, 0, 0, 1]
         * population[2] = [1, 1, 1, 1, 0, 0, 0, 0]
         * population[3] = [0, 0, 1, 0, 1, 1, 0, 1]
         * population[4] = [1, 0, 0, 1, 1, 1, 0, 0]
         * population[5] = [0, 1, 0, 0, 0, 1, 1, 1]
         * population[6] = [1, 1, 0, 1, 0, 1, 0, 0]
         * population[7] = [0, 0, 0, 1, 1, 0, 1, 0]
         * population[8] = [1, 0, 1, 0, 1, 0, 1, 1]
         * population[9] = [0, 1, 1, 1, 0, 1, 0, 0]
         */
        for (int i = 0; i < population.length; i++)
            population[i] = createIndividual();

        return population;
    }

    @Contract(pure = true)
    private static int calculateFitness(int @NotNull [] individual) {
        int fitness = 0;

        /*
         * Individual: [1, 0, 1, 1, 0, 0, 1, 0] | Fitness: 4
         * Individual: [1, 1, 1, 0, 0, 1, 0, 1] | Fitness: 5
         * ...
         */
        for (int gene : individual)
            if (gene == 1)
                fitness++;

        return fitness;
    }

    private static final int[] chart = new int[CHROMOSOME_LENGTH + 1];
    private static void chartFitness(int fitness) {
        if (fitness > CHROMOSOME_LENGTH)
            throw new ArrayIndexOutOfBoundsException();

        chart[fitness]++;
    }

    @Contract(pure = true)
    public static @NotNull String getChart() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < chart.length; i++)
            stringBuilder.append("[")
                    .append(i)
                    .append(":")
                    .append(chart[i])
                    .append("]");

        return stringBuilder.toString();
    }

    private static void chartReset() {
        Arrays.fill(chart, 0);
    }


    /*
     * Two individuals are selected at random from the population.
     *
     * The fitness of both is calculated.
     *
     * The individual with the higher score is returned as a parent.
     */
    private static int[] selectParent(int @NotNull [][] population) {
        int firstIndex = RANDOM.nextInt(population.length);
        int secondIndex = RANDOM.nextInt(population.length);

        int[] first = population[firstIndex];
        int[] second = population[secondIndex];

        if (calculateFitness(first) >= calculateFitness(second))
            return first;

        return second;
    }

    /*
     * [Crossover]
     * Individual A: 1111 -> Parent A: 11 | 11
     * Individual B: 1101 -> Parent B: 11 | 01
     *
     * Child A: 11 | 01  -> 1101
     * Child B: 11 | 11  -> 1111
     */
    @Contract(pure = true)
    private static int @NotNull [] crossover(int[] parentA, int[] parentB) {
        Objects.requireNonNull(parentA);
        Objects.requireNonNull(parentB);

        // initialize child array set
        int[] child = new int[CHROMOSOME_LENGTH];

        /*
         * Since the range in this method includes the start but excludes the end,
         * the cut point can be any number from 1 to 7.
         *
         * Why don't we choose 0 or 8? Because in that case, one of the parents might
         * have no contribution to the offspring.
         */
        int crossoverPoint = RANDOM.nextInt(1, CHROMOSOME_LENGTH);

        /*
         * Parent A: 1 1 1 1 | 0 0 0 0
         * Parent B: 0 0 0 0 | 1 1 1 1
         *                   ^
         *              Crossover Point
         *
         * Child:    1 1 1 1 | 1 1 1 1
         */
        for (int i = 0; i < CHROMOSOME_LENGTH; i++) {
            if (i < crossoverPoint) child[i] = parentA[i];
            else child[i] = parentB[i];
        }

        return child;
    }

    /*
     * [Mutation in genes]
     *
     * Before: 10110101
     * After:  10111101
     *             ^
     *             Mutation (0 changed to 1)
     *             Fitness 5 -> 6
     */
    private static void mutate(int @NotNull [] individual) {
        for (int i = 0; i < individual.length; i++)

            // generated a random number between 0 and 1
            // if the number is below 0.01 -> mutate number
            if (RANDOM.nextDouble() < MUTATION_RATE)
                individual[i] = 1 - individual[i];
    }

    public static void main(String[] args) {
        int[][] population = createPopulation();

        for (int[] individual : population) {
            int fitness = calculateFitness(individual);
            chartFitness(fitness);

            /*
             * Individual: [1, 1, 0, 0, 1, 1, 0, 0] | Fitness: 4
             * Individual: [0, 1, 0, 0, 1, 0, 1, 1] | Fitness: 4
             * Individual: [1, 1, 0, 1, 1, 1, 0, 0] | Fitness: 5
             * Individual: [1, 1, 1, 1, 1, 0, 0, 0] | Fitness: 5
             * Individual: [0, 1, 0, 1, 1, 1, 1, 1] | Fitness: 6
             * Individual: [0, 1, 0, 0, 0, 1, 0, 1] | Fitness: 3
             * Individual: [1, 1, 0, 0, 1, 0, 0, 0] | Fitness: 3
             * Individual: [0, 1, 0, 0, 0, 0, 1, 1] | Fitness: 3
             * Individual: [1, 1, 1, 1, 0, 1, 1, 1] | Fitness: 7
             * Individual: [1, 0, 0, 0, 0, 1, 0, 1] | Fitness: 3
             */
            System.out.println(
                    "Individual: " + Arrays.toString(individual)
                            + " | Fitness: " + fitness
            );
        }
        System.out.println(getChart());

        /*
         * Parent A: [1, 1, 1, 1, 0, 0, 0, 0]
         * Parent B: [1, 1, 0, 1, 1, 1, 0, 0]
         * Child:    [1, 1, 1, 1, 1, 1, 0, 0]
         * Fitness:  6
         */
        int[] parentA = selectParent(population);
        int[] parentB = selectParent(population);

        int[] child = crossover(parentA, parentB);

        System.out.println("Parent A: " + Arrays.toString(parentA));
        System.out.println("Parent B: " + Arrays.toString(parentB));
        System.out.println("Child:    " + Arrays.toString(child));
        System.out.println("Fitness:  " + calculateFitness(child));

        int i = 0;
        do {
            mutate(child);
            i++;
        } while (i < 100);

        System.out.println("Child (after mutation):    " + Arrays.toString(child));
        System.out.println("Fitness (after mutation):  "
                + calculateFitness(child)
        );
        /*
         * Individual: [1, 0, 1, 0, 1, 1, 1, 0] | Fitness: 5 | index: 1
         * [0:0][1:1][2:2][3:1][4:2][5:4][6:0][7:0][8:0]
         * Parent A: [1, 0, 1, 0, 1, 1, 1, 0]
         * Parent B: [1, 0, 1, 0, 1, 1, 1, 0]
         * Child:    [1, 0, 1, 0, 1, 1, 1, 0]
         * Fitness:  5
         * Child(after mutation):     [1, 0, 1, 1, 0, 0, 1, 1]
         * Fitness (after mutation):  5
         */
    }
}
