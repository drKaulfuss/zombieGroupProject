import java.util.ArrayList;
import java.util.Random;

/**
 * Game - the main program. Run THIS file to play.
 *
 * The world is a 5x5 grid (a 2D array). The player walks around the grid.
 * Some squares hold characters (monsters, shopkeepers, bosses) and some
 * hold items. Walk into a hostile character to fight. Defeat every hostile
 * character to win the game!
 *
 * Students: you should NOT need to change this file to make your subclasses
 * work. Put your new classes in their own files, then "plug them in" inside
 * Setup.java. (Once your subclasses work, see PROJECT_GUIDE.md for
 * extension ideas that DO change this file.)
 *
 * Why does this work with any subclass? POLYMORPHISM! This file only talks
 * about the parent types Character and Item. Java automatically runs the
 * overridden version of each method for whichever object is really there.
 */
public class Game
{
    // The size of the world. Change these to make a bigger or smaller map.
    private static final int ROWS = 10;
    private static final int COLS = 20;

    private Character player;
    private Gamble gamble = new Gamble();   // opened from anywhere with 'G'

    // 2D arrays: one spot for each square of the map
    private Character[][] residents;   // who lives in each square (null = nobody)
    private Item[][] floorItems;       // what is lying on the floor (null = nothing)
    private boolean[][] visited;       // has the player been here yet?

    private int row;                   // the player's current position
    private int col;

    // ============================================================
    //  main
    // ============================================================

    public static void main(String[] args)
    {
        Game game = new Game();
        game.run();
    }

    public Game()
    {
        residents = new Character[ROWS][COLS];
        floorItems = new Item[ROWS][COLS];
        visited = new boolean[ROWS][COLS];
    }

    // ============================================================
    //  The MAIN GAME LOOP
    // ============================================================

    public void run()
    {
        System.out.println("==============================");
        System.out.println("      THE QUEST BEGINS");
        System.out.println("==============================");
        String name = Input.ask("What is your name, hero? ");
        String gender = Input.ask("What is your gender, fool? ");


        // Setup.java builds the player and fills the map.
        player = Setup.createPlayer(name);
        Setup.fillWorld(residents, floorItems, ROWS, COLS);

        row = (int)(Math.random() * ROWS);
        col = (int)(Math.random() * COLS);
        visited[row][col] = true;

        boolean quit = false;

        // Keep playing while the hero lives, enemies remain, and they haven't quit.
        while (player.isAlive() && countHostileLeft() > 0 && !quit)
        {
            System.out.println();
            printMap();
            System.out.println(player + "   Gold: " + player.getGold()
                               + "   Enemies left: " + countHostileLeft());
            System.out.println("(M)ove  (S)tats  (I)tems  (T)alk  (G)amble  (Q)uit");
            String choice = Input.ask("> ").toUpperCase();
try {
            switch(choice.charAt(0)) {
                case 'M' -> move();
                case 'S' -> showStats();
                case 'I' -> useItem();
                case 'T' -> talk();
                case 'G' -> gamble.open(player);
                case 'Q' -> quit = true;
                default -> System.out.println("I don't understand that choice.");
            }
        }} catch (Exception e) {
            System.out.println("You need to enter a choice.");
        }

        // The loop ended - figure out why.
        System.out.println();
        if (!player.isAlive())
        {
            System.out.println("You have fallen... GAME OVER.");
        }
        else if (countHostileLeft() == 0)
        {
            System.out.println("*** VICTORY! Every enemy has been defeated! ***");
            System.out.println("Final gold: " + player.getGold());
        }
        else
        {
            System.out.println("You abandon the quest. Goodbye!");
        }
    }

    // ============================================================
    //  Moving around the map
    // ============================================================

    private void move()
    {
        String direction = Input.ask("Which way? (N/S/E/W) ").toUpperCase();

        int newRow = row;
        int newCol = col;
        try {
            switch (direction.charAt(0)) {
                case 'N' -> newRow--;
                case 'S' -> newRow++;
                case 'E' -> newCol++;
                case 'W' -> newCol--;
                default -> System.out.println("That's not a direction.");
            }
        } catch (Exception e) {
            System.out.println("You need to enter a direction.");
        }
        

        // Make sure we stay inside the 2D array!
        if (newRow < 0 || newRow >= ROWS || newCol < 0 || newCol >= COLS)
        {
            System.out.println("A wall blocks your way.");
            return;
        }

        int oldRow = row;
        int oldCol = col;
        row = newRow;
        col = newCol;
        visited[row][col] = true;

        enterRoom(oldRow, oldCol);
    }

    /**
     * Called whenever the player steps into a new square.
     * (oldRow, oldCol) is where the player came from, in case they flee.
     */
    private void enterRoom(int oldRow, int oldCol)
    {
        Character other = residents[row][col];

        if (other != null)
        {
            if (other.isHostile())      // polymorphism: each subclass decides
            {
                fight(other);

                if (!other.isAlive())
                {
                    collectLoot(other);
                    residents[row][col] = null;     // remove the defeated enemy
                }
                else if (player.isAlive())
                {
                    // Both are alive, so the player must have run away.
                    System.out.println("You retreat to the previous room.");
                    row = oldRow;
                    col = oldCol;
                    return;
                }
            }
            else
            {
                other.interact(player);         // polymorphism again!
            }
        }

        // Pick up anything on the floor (if we're still alive).
        if (player.isAlive() && floorItems[row][col] != null)
        {
            Item item = floorItems[row][col];
            floorItems[row][col] = null;

            // The item decides whether it goes into the bag (see Item.onPickup)
            boolean keepIt = item.onPickup(player);
            if (keepIt)
            {
                player.addItem(item);
            }
        }
    }

    // ============================================================
    //  Fighting
    // ============================================================

    /**
     * Fight until someone is defeated or the player runs away.
     * When this method ends:
     *   - enemy not alive            -> the player won
     *   - player not alive           -> the player lost
     *   - both alive                 -> the player ran away
     */
    private void fight(Character enemy)
    {
        System.out.println();
        System.out.println("*** " + enemy.getName() + " blocks your path! ***");

        boolean escaped = false;

        while (player.isAlive() && enemy.isAlive() && !escaped)
        {
            System.out.println();
            System.out.println(player + "   VS   " + enemy);
            System.out.println("(A)ttack  (U)se item  (R)un");
            String choice = Input.ask("> ").toUpperCase();

            boolean turnUsed = false;

            switch(choice.charAt(0)) {
                case 'A' -> {player.attack(enemy); turnUsed = true;}
                case 'U' -> turnUsed = useItem();
                case 'R' -> {
                    if (Math.random() < 0.5)
                    {
                        System.out.println("You manage to escape!");
                        escaped = true;
                    }
                    else
                    {
                        System.out.println("You couldn't get away!");
                    }
                    turnUsed = true;
                }
                default -> System.out.println("I don't understand that choice.");
            }

            // The enemy takes a turn if it's still alive and the player didn't escape.
            if (turnUsed && enemy.isAlive() && !escaped)
            {
                enemy.takeTurn(player);     // Boss, Thief, etc. may override this!
            }
        }
    }

    /**
     * The player takes everything the defeated enemy was carrying.
     */
    private void collectLoot(Character defeated)
    {
        System.out.println();
        System.out.println("You defeated " + defeated.getName() + "!");

        int gold = defeated.getGold();
        if (gold > 0)
        {
            player.addGold(gold);
            System.out.println("You take " + gold + " gold.");
        }

        for (Item item : defeated.getInventory())
        {
            System.out.println("You take the " + item.getName() + ".");
            player.addItem(item);
        }
    }

    // ============================================================
    //  Menu actions
    // ============================================================

    /**
     * Show the bag and let the player choose an item to use.
     * Returns true if the player chose an item (so a turn was spent).
     */
    private boolean useItem()
    {
        ArrayList<Item> bag = player.getInventory();

        if (bag.isEmpty())
        {
            System.out.println("Your bag is empty.");
            return false;
        }

        for (int i = 0; i < bag.size(); i++)
        {
            System.out.println((i + 1) + ". " + bag.get(i));
        }

        int choice = Input.askNumber("Use which item? (0 to cancel) ");
        if (choice < 1 || choice > bag.size())
        {
            return false;
        }

        Item item = bag.get(choice - 1);
        boolean usedUp = item.use(player);      // polymorphism!
        if (usedUp)
        {
            player.removeItem(item);
        }
        return true;
    }

    private void showStats()
    {
        System.out.println();
        System.out.println("---- " + player.getName() + " ----");
        System.out.println("Health:   " + player.getHealth() + "/" + player.getMaxHealth());
        System.out.println("Strength: " + player.getStrength());
        System.out.println("Defense:  " + player.getDefense());
        System.out.println("Gold:     " + player.getGold());
        System.out.println("Items:    " + player.getInventory().size()
                           + " (total value " + player.getInventoryValue() + " gold)");
        for (Item item : player.getInventory())
        {
            System.out.println("   - " + item);
        }
    }

    private void talk()
    {
        Character other = residents[row][col];

        if (other == null)
        {
            System.out.println("There is no one here to talk to.");
        }
        else
        {
            other.interact(player);
        }
    }

    // ============================================================
    //  2D array helpers
    // ============================================================

    /**
     * Draw the map. P = you, . = visited, ? = unexplored
     */
    private void printMap()
    {
        for (int r = 0; r < ROWS; r++)
        {
            for (int c = 0; c < COLS; c++)
            {
                if (r == row && c == col)
                {
                    System.out.print("P ");
                }
                else if (visited[r][c])
                {
                    System.out.print(". ");
                }
                else
                {
                    System.out.print("? ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Count the hostile characters still alive on the map.
     * (Nested loops visit every square of the 2D array.)
     */
    private int countHostileLeft()
    {
        int count = 0;
        for (int r = 0; r < ROWS; r++)
        {
            for (int c = 0; c < COLS; c++)
            {
                if (residents[r][c] != null && residents[r][c].isHostile())
                {
                    count++;
                }
            }
        }
        return count;
    }

    public static int getRows() {
        return ROWS;
    }

    public static int getCols() {
        return COLS;
    }
}
