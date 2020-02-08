package dice.tests;

import dice.Dice;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

    @Test
    void getMAX_VALUE() {
        var rnd = new Random();
        int number = 2 + rnd.nextInt(6);
        var dice = new Dice(number);
        Assert.assertEquals(number * 6, dice.getMAX_VALUE());
    }

    @Test
    void throwDice() {
        for (int i = 0; i < 1000; i++) {
            var rnd = new Random();
            int number = 2 + rnd.nextInt(6);
            var dice = new Dice(number);
            Assert.assertTrue(dice.throwDice() <= number * 6);
        }
    }
}