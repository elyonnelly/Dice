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
                //System.out.println(name);
                //It mean, player can throw dice if he already don't throw dice and previous player ended him move
                while(!Thread.currentThread().isInterrupted() && didThrowInCurrentRound || game.doMoveHappen()) {
                    //System.out.println(name + " " + didThrowInCurrentRound + " " + game.doMoveHappen());
                    try {
                        game.getDice().wait();
                    } catch (InterruptedException e) {
                        //это нормально, если в процессе ожидания своего хода игра завершается и поток прерывается
                        //e.printStackTrace();
                        return;
                    }
                }
                this.points = game.getDice().throwDice();
                //System.out.println(name + " " + points);
                didThrowInCurrentRound = true;
                game.setLastPlayerInRound(this);
                game.incrementNumberOfMove();
                game.setDoMoveHappen(true);

                //Этот блок необходим, чтобы в это время другие потоки не могли перехватить кубики,
                // до тех пор, пока комментатор не сделал комментарий
                synchronized (Game.move) {
                    Game.move.notify();
                    if (game.doMoveHappen()) {
                        try {
                            Game.move.wait(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }

                game.getDice().notifyAll();
                //блен адай лок другому потоку пожожта!!!!
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
