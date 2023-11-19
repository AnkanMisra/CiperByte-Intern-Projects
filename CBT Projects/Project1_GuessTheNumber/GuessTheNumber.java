/*
    Welcome to the Guess the Number Game! 
    We are up against the computer
    Here's the breakdown:

    The computer secretly selects a secret random number between 1 and 100.
    We will be given 10 trials to guess the secret random number
    The score will be calulated by the amount of tries that are left out of 10

    Guess the number otherwise the game ends when you run out of trials.
    If you exhaust your attempts, the game wraps up with a message.
    We then have the option to jump back in for another round"
 */


import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {
    // Define class variables
    private final int lowerBound;     // Lower bound of the secret number range
    private final int upperBound;     // Upper bound of the secret number range
    private final int maxAttempts = 10;   // Maximum number of attempts allowed
    private int secretNumber;       // The randomly generated secret number
    private int playerScore;        // Player's score based on attempts
    private int attempts;           // Number of attempts made by the player
    private int currentRound;       // Current round number

    // Constructor to initialize game settings
    public GuessTheNumber(int minRange, int maxRange) {
        this.lowerBound = minRange;
        this.upperBound = maxRange;
        this.playerScore = 0;
        this.currentRound = 1;
    }

    // Generate a random secret number within the specified range
    private void generateSecretNumber() {
        Random random = new Random();
        secretNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
    }

    // Main method to start and control the game
    public void playGame() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hey Player! Welcome to the Guess the Number game!");
        System.out.println("I'm your fellow computer and your opponent.");
        System.out.println("I've got a number in mind between " + lowerBound + " and " + upperBound);
        System.out.println("Your score will be calculated based on the number of attempts (10 attempts).");
        System.out.println("Let's see if you can guess it!");

        do {
            playerScore = 0; // Reset the playerScore at the beginning of each round
            attempts = 0;
            generateSecretNumber();

            do {
                System.out.print("Give it a shot: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess == secretNumber) {
                    System.out.println("WOW! You got it!");
                    updatePlayerScore();
                    break;
                } else if (userGuess < secretNumber) {
                    System.out.println("Not quite there! My number is higher.");
                } else {
                    System.out.println("Nice try, but my number is a bit lower.");
                }

                int attemptsLeft = maxAttempts - attempts;
                if (attemptsLeft > 0) {
                    System.out.println("You've got " + attemptsLeft + " attempt" + (attemptsLeft != 1 ? "s" : "") + " left!");
                } else {
                    System.out.println("Oops! Ran out of tries. The correct number was: " + secretNumber);
                    break;
                }

            } while (true);

            System.out.println("Your score for round " + currentRound + ": " + playerScore);
            currentRound++;

            System.out.print("Wanna go another round? (yes/no)\nAny other response will be counted as no: ");
            String playAgain = scanner.next().toLowerCase();

            if (!playAgain.equals("yes")) {
                System.out.println("Your response is no. Thanks for playing! Catch you later!");
                break;
            }

        } while (true);

        scanner.close(); // Close the scanner to prevent resource leak
    }

    // Update the player's score based on the remaining attempts
    private void updatePlayerScore() {
        int remainingTries = maxAttempts - attempts;
        playerScore += (remainingTries * 10) / maxAttempts;
    }

    // Main method to start the game
    public static void main(String[] args) {
        GuessTheNumber game = new GuessTheNumber(1, 100);
        game.playGame();
    }
}
