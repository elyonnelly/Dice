package dice;

public class Main {

    public static void main(String[] args) {

        int numberOfPlayers = 5, numberOfDice = 4, numberOfRequiredVictories = 100;
        /*if (args.length < 3) {
            System.out.printf("Too few argument for start!");
            System.exit(1);
        }
        try {
            numberOfPlayers = Integer.parseInt(args[0]);
            numberOfDice = Integer.parseInt(args[1]);
            numberOfRequiredVictories = Integer.parseInt(args[2]);
        }
        catch (NumberFormatException ex) {
            System.out.printf("Wrong input format");
            System.exit(1);
        }
        if (!(2 <= numberOfPlayers && numberOfPlayers <= 6
                && 2 <= numberOfDice && numberOfDice <= 5
                && 1 <= numberOfRequiredVictories && numberOfRequiredVictories <= 100)) {
            System.out.printf("Incorrect parameters for start: " +
                    "number of players must be between 2 and 6, " +
                    "number of dices must be between 2 and 5, " +
                    "number of required victories must be between 1 and 100");
            System.exit(1);
        }*/

        var game = new Game(numberOfPlayers, numberOfDice, numberOfRequiredVictories);

        game.playGame();
    }
}
