package dice;

/**
 * Represents the Player class with its associated thread
 */
public class Player {
    private int points;
    private int numberOfVictories;
    private boolean didThrowInCurrentRound;
    private Thread threadOfPlayer;
    private String name;

    private Game game;

    public Player(Game game, String name) {
        super();
        this.game = game;
        this.name = name;
        threadOfPlayer = new Thread(() -> DoMove());
    }

    /**
     * Player's thread interruption
     */
    public void stop() {
        threadOfPlayer.interrupt();
    }

    /**
     * Player's thread start
     */
    public void move() {
        threadOfPlayer.start();
    }

    /**
     * Main logic of program: player throws dice there
     */
    private void DoMove() {
        while(!Thread.currentThread().isInterrupted()) {
            synchronized (game.getDice()) {
                //It mean, player can throw dice if he already don't throw dice and previous player ended him move
                if (!didThrowInCurrentRound && !game.doMoveHappen()) {
                    this.points = game.getDice().throwDice();
                    didThrowInCurrentRound = true;
                    game.setLastPlayerInRound(this);
                    game.incrementNumberOfMove();
                    game.setDoMoveHappen(true);
                }
            }
        }
    }

    /**
     * Initialization for new round
     */
    public void startNewRound() {
        didThrowInCurrentRound = false;
        points = 0;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public int getNumberOfVictories() {
        return numberOfVictories;
    }

    public void addWin() {
        this.numberOfVictories++;
    }

    @Override
    public String toString() {
        return name + " throws " + points + " points.";
    }

    public String getName() {
        return name;
    }

}
