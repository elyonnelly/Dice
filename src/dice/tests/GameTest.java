package dice.tests;

import dice.Game;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(5, 4, 10);
    }

    @Test
    void playGame() {
        var rnd = new Random();

        boolean error = false;

        for (int i = 0; i < 100; i++) {
            game = new Game(2 + rnd.nextInt(6), 2 + rnd.nextInt(5),
                    1 + rnd.nextInt(100));
            try {
                game.playGame();
            } catch (Exception ex) {
                System.out.println(ex);
                error = true;
            }
        }

        Assert.assertFalse(error);

    }

    @Test
    void isGameOver() {
        for (int i = 0; i < game.getNumberOfRequiredVictories(); i++) {
            game.getPlayers()[0].addWin();
        }

        Assert.assertTrue(game.isGameOver());
    }

    @Test
    void startNewRound() {
        getRandomState();
        game.startNewRound();
        for (int i = 0; i < game.getNumberOfPlayers(); i++) {
            Assert.assertEquals(0, game.getPlayers()[i].getPoints());
        }

        Assert.assertEquals("nobody", game.getBestPlayerInRound().getName());
        Assert.assertEquals(0, game.getBestPlayerInRound().getPoints());
        Assert.assertEquals(0, game.getNumberOfMoveInRound());
    }

    @Test
    void incrementNumberOfMove() {
        int oldValue = game.getNumberOfMoveInRound();
        game.incrementNumberOfMove();
        Assert.assertEquals(oldValue + 1, game.getNumberOfMoveInRound());
    }

    private void getRandomState() {
        var players = game.getPlayers();
        var rnd = new Random();
        for (dice.Player player : players) {
            player.setPoints(rnd.nextInt(30));
        }
        players[game.getNumberOfPlayers()/2].addWin();
        players[game.getNumberOfPlayers()/2].addWin();
    }
}