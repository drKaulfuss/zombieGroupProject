import java.util.Random;


public class Gamble {

    // Exchange rates
    private static final int COINS_PER_CHIP = 10;   // deposit: 10 coins = 1 chip
    private static final int COINS_PER_CASHOUT = 9; // cashout: 1 chip = 9 coins (casino tax)

    private int chips;
    private Character player; // whoever is visiting right now
    private Random random;

    public Gamble() {
        this.chips = 0;
        this.random = new Random();
    }

    // Called by Game.java when the player presses G.
    public void open(Character visitor) {
        this.player = visitor;
        System.out.println("You step into a smoky casino tent.");
        gambleMenu();
    }

    // ------------------------------
    // Menu
    // ------------------------------

    public void gambleMenu() {
        boolean open = true;
        while (open && player.isAlive()) {
            System.out.println();
            System.out.println("Welcome to the gamble menu!");
            System.out.println("Gold: " + player.getGold() + "   Chips: " + chips);
            System.out.println("1. Coin Flip");
            System.out.println("2. Slots");
            System.out.println("3. Scratch Offs");
            System.out.println("4. Three Cups");
            System.out.println("5. Black Jack");
            System.out.println("6. Roulette");
            System.out.println("7. High Low");
            System.out.println("8. Russian Roulette");
            System.out.println("9. Exit");
            System.out.println("10. Deposit Coins");
            System.out.println("11. Payout Coins");

            int choice = Input.askNumber("> ");

            switch (choice) {
                case 1 -> coinFlip();
                case 2 -> slots();
                case 3 -> lottery();
                case 4 -> threeCups();
                case 5 -> blackjack();
                case 6 -> roulette();
                case 7 -> highLow();
                case 8 -> russianRoulette();
                case 9 -> {
                    System.out.println("Exiting...");
                    open = false;
                }
                case 10 -> depositCoins();
                case 11 -> payout();
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // ------------------------------
    // Chip methods
    // ------------------------------

    // Turns coins into chips (10 coins = 1 chip).
    public void depositCoins() {
        System.out.println("You have " + player.getGold() + " gold.");
        int coins = Input.askNumber("How many coins would you like to deposit? (10 coins per chip, 0 to cancel) ");
        int newChips = coins / COINS_PER_CHIP;

        if (coins <= 0 || newChips < 1) {
            System.out.println("Nothing deposited.");
        } else if (player.spendGold(newChips * COINS_PER_CHIP)) {
            chips += newChips;
            System.out.println("You get " + newChips + " chips.");
        } else {
            System.out.println("You do not have enough coins.");
        }
    }

    // Turns chips into coins (1 chip = 9 coins).
    public void payout() {
        System.out.println("You currently have " + chips + " chips.");
        int cashOut = Input.askNumber("How many chips would you like to cash out? (0 to cancel) ");

        if (cashOut > 0 && hasEnoughChips(cashOut)) {
            player.addGold(cashOut * COINS_PER_CASHOUT);
            chips -= cashOut;
            System.out.println("You get " + (cashOut * COINS_PER_CASHOUT) + " gold.");
        } else {
            System.out.println("Nothing cashed out.");
        }
    }

    public int getChips() {
        return chips;
    }

    public void addChips(int amount) {
        chips += amount;
    }

    public void removeChips(int amount) {
        chips -= amount;
    }

    public boolean hasEnoughChips(int bet) {
        return chips >= bet;
    }

    // ------------------------------
    // Game methods
    // ------------------------------

    public void threeCups() {
        do {
            int bet = startGame("Three Cups", "You will be given 3 cups, one of which has a ball under it. You will have to guess which cup the ball is under. If you guess correctly, you will win 2x your bet.");
            if (bet == 0) {
                return;
            }
            int userChoice = Input.askNumber("Pick a cup (1, 2, or 3): ");
            int ball = randomInt(1, 3);
            while (ball == userChoice) {
                ball = randomInt(1, 3);
            }
            System.out.println("The ball was under cup " + ball);
            System.out.println("You have lost " + bet + " chips");
            loseBet(bet);
        } while (askPlayAgain());
        System.out.println("You have " + chips + " chips.");
    }

    public void slots() {
        int bet = startGame("Slots", "You will place a bet and spin the machine. If one 7 lands you win 1x your bet, if two 7s land you win 2x, and if three 7s land you win 3x.");
        if (bet == 0) {
            return;
        }
        System.out.println("Spinning the slots...");
        for (int i = 0; i < 3; i++) {
            pause(500);
            System.out.println(randomInt(1, 9) + "  " + randomInt(1, 9) + "  " + randomInt(1, 9));
        }
        pause(500);
        int[] reels = {randomInt(1, 9), randomInt(1, 9), randomInt(1, 9)};
        System.out.println("Final result: " + reels[0] + "  " + reels[1] + "  " + reels[2]);
        settleSevens(bet, reels);
    }

    public void lottery() {
        int bet = startGame("Scratch Offs", "Choose your ticket amount. One 7 is 1x, Two 7s are 2x, and three 7s are 3x");
        if (bet == 0) {
            return;
        }
        int[] spots = new int[3];
        for (int i = 0; i < 3; i++) {
            System.out.println("Scratching...");
            pause(500);
            spots[i] = randomInt(1, 14);
            System.out.println(spots[i]);
        }
        System.out.println("Final result: " + spots[0] + "  " + spots[1] + "  " + spots[2]);
        settleSevens(bet, spots);
    }

    public void blackjack() {
        int bet = startGame("Blackjack", "You will be given a hand of 2 to 21. The dealer gets one too. Get as close to 21 as possible without going over.");
        if (bet == 0) {
            return;
        }
        int hand = randomInt(2, 21);
        int dealer = randomInt(2, 21);
        System.out.println("Your hand is " + hand + ".");

        boolean hit = true;
        while (hit && hand < 21) {
            System.out.println("Hit or stand?");
            if (readChoice("hit", "stand").equals("hit")) {
                hand += randomInt(1, 11);
                System.out.println("Your hand is " + hand + ".");
            } else {
                hit = false;
            }
        }

        System.out.println("The dealer has " + dealer + ".");

        if (hand <= 21 && hand > dealer) {
            System.out.println("You win!");
            winBet(bet, 1);
        } else {
            System.out.println("You lose.");
            loseBet(bet);
        }
    }

    public void roulette() {
        int bet = startGame("Roulette", "Bet on red, black, or green. Red and black pay 2x. Green pays 14x.");
        if (bet == 0) {
            return;
        }
        int userPred = Input.askNumber("1 = red, 2 = black, 3 = green: ");
        if (userPred < 1 || userPred > 3) {
            System.out.println("That is not a color. Your bet is forfeited.");
            loseBet(bet);
            return;
        }

        int table = random.nextInt(101); // 0 to 100
        int result;                      // 1 red, 2 black, 3 green
        if (table == 0) {
            result = 3;
        } else if (table % 2 == 0) {
            result = 1;
        } else {
            result = 2;
        }
        String[] names = {"", "red", "black", "green"};
        System.out.println("The ball lands on " + table + " (" + names[result] + ").");

        if (result == userPred) {
            System.out.println("Congratulations you guessed correctly");
            winBet(bet, result == 3 ? 14 : 2);
        } else {
            System.out.println("You did not guess correctly");
            loseBet(bet);
        }
    }

    public void russianRoulette() {
        do {
            int bet = startGame("Russian Roulette", "A revolver with 6 chambers and 1 bullet. Survive the pull and win 6x your bet.");
            if (bet == 0) {
                return;
            }
            if (random.nextInt(6) == 0) {
                int clicks = randomInt(0, 4);
                for (int i = 0; i < clicks; i++) {
                    System.out.println("Click");
                    pause(1000);
                }
                System.out.println("BANG!");
                loseBet(bet);
                if (random.nextBoolean()) {
                    System.out.println("You have been shot and are now in the hospital");
                    player.takeDamage(10);
                } else {
                    System.out.println("You have been shot and have died");
                    player.takeDamage(player.getHealth() + player.getDefense());
                }
                return;
            }
            System.out.println("Click. You survived and won " + (bet * 6) + " chips");
            winBet(bet, 2);
        } while (askPlayAgain());
        System.out.println("You have " + chips + " chips.");
    }

    public void highLow() {
        int bet = startGame("High Low", "You will see a card, then guess if the next card is Higher or Lower. If you win, you win 2x your bet. You lose your bet if you are wrong or tie.");
        if (bet == 0) {
            return;
        }

        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        int[] values = {14, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13}; // Ace is highest
        String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};

        int first = randomInt(0, ranks.length - 1);
        int firstSuit = randomInt(0, suits.length - 1);
        System.out.println("\nYour current card is: " + ranks[first] + " of " + suits[firstSuit]);

        System.out.println("Will the next card be Higher or Lower? (high/low)");
        String guess = readChoice("high", "low");

        int second = randomInt(0, ranks.length - 1);
        int secondSuit = randomInt(0, suits.length - 1);
        System.out.println("The next card is: " + ranks[second] + " of " + suits[secondSuit]);

        boolean won = false;
        if (values[second] > values[first] && guess.equals("high")) {
            won = true;
        } else if (values[second] < values[first] && guess.equals("low")) {
            won = true;
        } else if (values[second] == values[first]) {
            System.out.println("It's a tie! You lost.");
        }

        if (won) {
            System.out.println("Congratulations! You guessed correctly.");
            winBet(bet, 2);
            System.out.println("You won " + (bet * 2) + " chips! Total chips: " + chips);
        } else {
            System.out.println("Incorrect guess! You lost your bet.");
            loseBet(bet);
            System.out.println("Total chips remaining: " + chips);
        }
    }

    public void coinFlip() {
        int bet = startGame("Coin Flip", "You will pick Heads or Tails. If you are correct you win 2x your bet.");
        if (bet == 0) {
            return;
        }
        String guess = Input.ask("Heads or Tails? ").toLowerCase();
        int pick;
        if (guess.startsWith("h")) {
            pick = 0;
        } else if (guess.startsWith("t")) {
            pick = 1;
        } else {
            System.out.println("Invalid input. As a consequence, your bet is forfeited.");
            loseBet(bet);
            return;
        }

        int flip = random.nextInt(2);
        System.out.println("It lands on " + (flip == 0 ? "Heads" : "Tails") + ".");
        if (pick == flip) {
            System.out.println("You win!");
            winBet(bet, 2);
        } else {
            System.out.println("You lose!!!");
            loseBet(bet);
        }
        System.out.println("Thanks for playing!");
    }

    // ------------------------------
    // Helper methods
    // ------------------------------

    // Checks chips, explains the game, and asks for a bet. Returns 0 if the player cannot or will not play.
    private int startGame(String name, String rules) {
        if (chips < 1) {
            System.out.println("You have no chips. Deposit some coins first.");
            return 0;
        }
        System.out.println("Welcome to " + name + ". The rules are as follows: " + rules);
        return askForBet();
    }

    // Prompts for a bet until it is valid. Returns 0 if the player cancels.
    private int askForBet() {
        while (true) {
            int bet = Input.askNumber("How many chips would you like to bet? You have " + chips + " (0 to cancel) ");
            if (bet == 0) {
                return 0;
            }
            if (bet > 0 && hasEnoughChips(bet)) {
                return bet;
            }
            System.out.println("That is not a valid bet.");
        }
    }

    // Returns true if the player wants to play again.
    private boolean askPlayAgain() {
        String answer = readChoice("y", "n");
        return answer.equals("y");
    }

    // Shared payout for slots and scratch offs: count the 7s.
    private void settleSevens(int bet, int[] spots) {
        int sevens = 0;
        for (int spot : spots) {
            if (spot == 7) {
                sevens++;
            }
        }
        if (sevens > 0) {
            System.out.println("You win " + sevens + "x your bet!");
            winBet(bet, sevens);
        } else {
            System.out.println("You lose!!!");
            loseBet(bet);
        }
    }

    // Returns a random int from min to max, inclusive.
    private int randomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    // Adds winnings to chips (bet * multiplier).
    private void winBet(int bet, int multiplier) {
        chips += bet * multiplier;
    }

    // Removes the bet from chips.
    private void loseBet(int bet) {
        chips -= bet;
    }

    // Reads a valid text choice from the given options.
    private String readChoice(String... options) {
        while (true) {
            String input = Input.ask("> ").toLowerCase();
            for (String option : options) {
                if (input.equals(option.toLowerCase())) {
                    return option.toLowerCase();
                }
            }
            System.out.println("Please type one of these: " + String.join(", ", options));
        }
    }

    private void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}