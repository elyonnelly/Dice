package dice;

import java.util.Arrays;

public class Commentator {

    private Thread commentatorsThread;
    private Game game;

    public Commentator(Game _game) {
        game = _game;
        commentatorsThread = new Thread(() -> doComment());
    }

    public void startCommenting() {
        commentatorsThread.start();
    }

    /**
     * Makes comments about the progress of the game until game is over
     */
    public void doComment() {
        while(!game.isEnd()) {
            synchronized (game.getDice()) {
                System.out.printf("comment");
                //It means, we do comment only after some player's move
                while (!game.doMoveHappen()) {
                    try {
                        game.getDice().wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(game.getLastPlayerInRound().getName() + " throws " + game.getLastPlayerInRound().getPoints() + " points.");

                if (game.getLastPlayerInRound().getPoints() > game.getBestPlayerInRound().getPoints()) {
                    game.setBestPlayerInRound(game.getLastPlayerInRound());
                }

                System.out.println("The best player now is " + game.getBestPlayerInRound().getName() +
                        " with " + game.getBestPlayerInRound().getPoints() + " points ");

                if (game.getLastPlayerInRound().getPoints() == game.getDice().getMAX_VALUE()
                        || game.getNumberOfMoveInRound() == game.getNumberOfPlayers()) {
                    game.getBestPlayerInRound().addWin();
                    System.out.println("\nRound is over!\nWinner is " + game.getBestPlayerInRound().getName()
                            + ". Total wins: " + game.getBestPlayerInRound().getNumberOfVictories());
                    System.out.println("----------------------------------------------------------------");

                    game.startNewRound();
                }

                if (game.getBestPlayerInRound().getNumberOfVictories() == game.getNumberOfRequiredVictories()) {
                    game.stopGame();
                    game.setEnd(true);

                    Arrays.sort(game.getPlayers(), (o1, o2) -> o1.getNumberOfVictories() > o2.getNumberOfVictories() ? 1 : 0);
                    System.out.println("Congratulation " + game.getPlayers()[0].getName() + "!\n\nFull statistic:");

                    game.showStatistic();
                }
                game.setDoMoveHappen(false);
                game.getDice().notifyAll();

            }
        }
    }


}
