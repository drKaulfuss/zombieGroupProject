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

        int [] pos = randomPos(residents, ROWS, COLS);
        System.out.println(pos[0] + "   " + pos[1]);

        residents[pos[0]][pos[1]] = new Character("Goblin", 15, 2);

        pos = randomPos(residents, ROWS, COLS);
        System.out.println(pos[0] + "   " + pos[1]);
        residents[pos[0]][pos[1]] = new Character("Skeleton", 20, 3);

        pos = randomPos(residents, ROWS, COLS);
        System.out.println(pos[0] + "   " + pos[1]);
        residents[pos[0]][pos[1]] = new Character("Wolf", 18, 3);

        // TODO (Shopkeeper team): add a Shopkeeper somewhere, for example:
        //     residents[3][1] = new Shopkeeper();

        // TODO (Boss team): replace the Dragon with your Boss subclass
        pos = randomPos(residents, ROWS, COLS);
        System.out.println(pos[0] + "   " + pos[1]);
        residents[pos[0]][pos[1]] = new Character("Dragon", 35, 4);

        pos = randomPos(residents, ROWS, COLS);
        residents[pos[0]][pos[1]] = new BaddieBartender();

        // ---------------------- ITEMS ----------------------
        // TODO (Items team): add Weapons, Coins, Potions, Armor, MagicItems...
        //     floorItems[0][2] = new Coin(10);
        //     floorItems[3][3] = new Weapon("Iron Sword", 4);
        for (int i = 0; i < Game.getRows(); i++) {
            for (int j = 0; j < Game.getCols(); j++) {
                if (Math.random() < 0.1) {
                    floorItems[i][j] = new Item("Shiny Rock", "It looks pretty but does nothing.", 5);
                }
            }
        }
    }



}
