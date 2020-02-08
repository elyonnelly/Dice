package dice.tests;

import dice.Game;
import dice.Player;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(5, 4, 10);
    }

    @Test
    void startNewRound() {
        var player = new Player(game, "who");
        player.setPoints(42);

        player.startNewRound();
        Assert.assertEquals(0, player.getPoints());
    }

    @Test
    void addWin() {
        var player = new Player(game, "who");
        int oldValue = player.getNumberOfVictories();

        player.addWin();
        Assert.assertEquals(oldValue + 1, player.getNumberOfVictories());
    }
}