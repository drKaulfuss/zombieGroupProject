/**
 * Setup - THIS is the file where students "plug in" their subclasses.
 *
 * Right now it uses only the plain parent classes (Character and Item),
 * so the game runs, but it's boring: every enemy behaves the same and
 * no item does anything. As you write subclasses, replace the plain
 * objects below with YOUR objects.
 *
 * Example: once someone writes a Goblin class, change
 *      new Character("Goblin", 15, 2)
 * to
 *      new Goblin()
 *
 * Because Goblin extends Character, it fits in a Character variable
 * or a Character[][] array with no problem. (This is polymorphism!)
 *
 * TEAMS: to avoid editing the same lines, each team should only change
 * the lines in its own section (marked below).
 */
public class Setup
{
    /**
     * Create the hero the person is playing.
     * The name comes from the keyboard in Game.java.
     *
     * TODO (Player team): replace this with  return new Player(name);
     */
    public static Character createPlayer(String name)
    {
        Character hero = new Character(name, 50, 4);

        // TODO (Items team): give the hero a starting item, for example:
        //     hero.addItem(new Potion());

        return hero;
    }

    /**
     * Fill the map. residents[r][c] holds the character in row r, column c;
     * floorItems[r][c] holds the item lying there. Square [0][0] is where
     * the hero starts, so leave it empty!
     */
        public static int[] randomPos(Character[][] residents, int ROWS, int COLS) {
        int[] randomPosition = {((int)(Math.random()*ROWS)), ((int)(Math.random()*COLS))};

        while (residents[randomPosition[0]][randomPosition[1]] != null) {
            randomPosition[0] = (int)(Math.random()*ROWS);
            randomPosition[1] = (int)(Math.random()*COLS);
                
        }

        return randomPosition;
    }

    public static void fillWorld(Character[][] residents, Item[][] floorItems, int ROWS, int COLS)
    {
        // ------------------- CHARACTERS -------------------
        // TODO (Monster team):  replace these plain Characters with your
        //                       own subclasses (Goblin, Skeleton, ...)
        if (ROWS > 6 && COLS > 7) {
            System.out.println("Trey Taylor at (6, 7)");
            residents[6][7] = new TreyTaylor();
        }
        int bartenderRow = (int)(Math.random() * ROWS);
        int bartenderCol = (int)(Math.random() * COLS);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (i == bartenderRow && j == bartenderCol) {
                    System.out.printf("%s at (%d, %d)\n", "Baddiella", bartenderRow + 1, bartenderCol + 1);
                    residents[i][j] = new BaddieBartender();
                } else if (Math.random() < 0.05) { // Spawn a random mob
                    double spawnSeed = Math.random();
                    if (spawnSeed < 0.35) { // Spawn a Slime
                        System.out.printf("%s at (%d, %d)\n", "Slime", i + 1, j + 1);
                        residents[i][j] = new Slime();
                    } else { // Spawn a random generic monster
                        String tempName = switch ((int)(Math.random() * 3)) {
                            case 0 -> "Goblin";
                            case 1 -> "Wolf";
                            case 2 -> "Skeleton";
                            default -> "Zombie";
                        };
                        System.out.printf("%s at (%d, %d)\n", tempName, i + 1, j + 1);
                        // Randomizes the mob's stats just a little, based on the range for the original mobs
                        residents[i][j] = new Character(tempName, (int)(Math.random() * 6) + 15, (int)(Math.random() * 2) + 1);
                    }
                }
            }
        }
        // TODO (Shopkeeper team): add a Shopkeeper somewhere, for example:
        //     residents[3][1] = new Shopkeeper();

        // TODO (Boss team): replace the Dragon with your Boss subclass
        int[] pos = randomPos(residents, ROWS, COLS);
        System.out.println("Dragon at" + (pos[0] + 1) + " " + (pos[1] + 1));
        residents[pos[0]][pos[1]] = new Character("Dragon", 35, 4);




        // ---------------------- ITEMS ----------------------
        // TODO (Items team): add Weapons, Coins, Potions, Armor, MagicItems...
        //     floorItems[0][2] = new Coin(10);
        //     floorItems[3][3] = new Weapon("Iron Sword", 4);
        int[] deatherRows = {(int)(Math.random()*ROWS), (int)(Math.random()*ROWS)}; // First is the deather, second is the note
        int[] deatherCols = {(int)(Math.random()*COLS), (int)(Math.random()*COLS)};
        for (int i = 0; i < Game.getRows(); i++) {
            for (int j = 0; j < Game.getCols(); j++) {
                if (i == deatherRows[0] && j == deatherCols[0]) {
                    floorItems[i][j] = new Deather(1, 1);
                } else if (i == deatherRows[1] && j == deatherCols[1]) {
                    floorItems[i][j] = new noteForDeather("Mysterious Note", "Says \"Speak Deather Three Time to use the Deather\"", 1);
                } else if (Math.random() < 0.1) {
                    floorItems[i][j] = new Medpill();
                } else if (Math.random() < 0.1) {
                    floorItems[i][j] = new maxHealthPotion("Max Health Potion", "Heals you to max hp", 5, 5);
                } else if (Math.random() < 0.05) {
                    floorItems[i][j] = new Item("Shiny Rock", "It looks pretty but does nothing.", 5);
                }
            }
        }
    }



}
