package dice;

import java.util.Random;

/**
 * Represents dice that only one player can throw at a time
 */
public class Dice {
    private int number;
    private int MAX_VALUE;

    public Dice(int number) {
        this.number = number;
        MAX_VALUE = number * 6;
    }

    public int getMAX_VALUE() {
        return MAX_VALUE;
    }

    /**
     * Throws random points on dice
     * @return Sum of points
     */
    public int throwDice() {
        int points = 0;
        var rnd = new Random();
        for (int i = 0; i < number; i++) {
            points += 1 + rnd.nextInt(6);
        }

        return points;
    }
}
