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
        while(!game.isEnd()) {
            synchronized (game.getDice()) {
                System.out.println(name);
                //It mean, player can throw dice if he already don't throw dice and previous player ended him move
                //хочется не выходить из синхронайзд блока,пока мы не получим от комментатора ответ
                while(didThrowInCurrentRound || game.doMoveHappen()) {
                    System.out.println(name + " " + didThrowInCurrentRound + " " + game.doMoveHappen());
                    try {
                        game.getDice().wait();
                    } catch (InterruptedException e) {
                        //unfortunately, thread of player can be interrupted, and it is normal :)
                        e.printStackTrace();
                        return;
                    }
                }
                this.points = game.getDice().throwDice();
                System.out.println(name + " " + points);
                didThrowInCurrentRound = true;
                game.setLastPlayerInRound(this);
                game.incrementNumberOfMove();
                game.setDoMoveHappen(true);
                game.getDice().notifyAll();
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
