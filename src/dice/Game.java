package dice;

public class Game {
    private int numberOfPlayers;
    private int numberOfDice;
    private int numberOfRequiredVictories;
    private Dice dice;
    private Player bestPlayerInRound;

    private Player lastPlayerInRound;
    private int numberOfMoveInRound ;
    private boolean doMoveHappen;
    private boolean isEnd;

    private Player[] players;
    private Commentator commentator;

    public static final Object move = new Object();

    public Game(int numberOfPlayers, int numberOfDice, int numberOfRequiredVictories) {
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfDice = numberOfDice;
        this.numberOfRequiredVictories = numberOfRequiredVictories;
        dice = new Dice(this.numberOfDice);
        players = new Player[this.numberOfPlayers];
        bestPlayerInRound = new Player(this, "nobody");

        for (int i = 0; i < this.numberOfPlayers; i++) {
            players[i] = new Player(this, "Player" + i);
        }

        commentator = new Commentator(this);
    }

    /**
     * The start of the game, which is accompanied by the launch of threads corresponding to all players,
     * and the commentator's thread.
     */
    public  void playGame() {
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i].move();
        }

        commentator.startCommenting();
    }

    /**
     * The stop of the game with interrupting all threads.
     */
    public void stopGame() {
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i].stop();
        }
    }

    /**
     * Initialization of all players before the new round.
     */
    public void startNewRound() {
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i].startNewRound();
        }
        numberOfMoveInRound = 0;
        bestPlayerInRound = new Player(this, "nobody");
    }

    /**
     * Displaying information about all players and the number of their victories.
     */
    public void showStatistic() {
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println(players[i].getName() + ". total win: " + players[i].getNumberOfVictories());
        }
    }

    public int getNumberOfRequiredVictories() {
        return numberOfRequiredVictories;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public Dice getDice() {
        return dice;
    }

    public Player getBestPlayerInRound() {
        return bestPlayerInRound;
    }

    public void setBestPlayerInRound(Player bestPlayerInRound) {
        this.bestPlayerInRound = bestPlayerInRound;
    }

    public Player getLastPlayerInRound() {
        return lastPlayerInRound;
    }

    public void setLastPlayerInRound(Player lastPlayerInRound) {
        this.lastPlayerInRound = lastPlayerInRound;
    }

    public int getNumberOfMoveInRound() {
        return numberOfMoveInRound;
    }

    /**
     *  Increment the move counter in the round
     */
    public void incrementNumberOfMove() {
        numberOfMoveInRound++;
    }

    /**
     * @return Did any of the players make a move
     * that they have not yet commented on (consider that the move has not yet completed)
     */
    public boolean doMoveHappen() {
        return doMoveHappen;
    }

    public void setDoMoveHappen(boolean doMoveHappen) {
        this.doMoveHappen = doMoveHappen;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}
