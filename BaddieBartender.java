import java.util.ArrayList;

// Only spawn at the bar
public class BaddieBartender extends Character {

    public BaddieBartender() {
        super("Baddiella", 100, 25);
    }

    @Override
    public boolean isHostile() {
        return false; // She is an interactive NPC, not a hostile monster
    }

    @Override
    public void interact(Character player) {
        String gender = Input.ask("What is your gender, fool? ");

        if (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("m")) {
            System.out.println("You're exhausted from battling all day, parched asf");
            System.out.println("Suddenly there appears a pub in the middle of the woods with the sign -Bar-");
            System.out.println("You sigh from relief and head inside");
            System.out.println("As soon as you set foot into the pub, you see a:\n" +
                               "beautiful,\n" +
                               "stunning,\n" +
                               "lovely,\n" +
                               "pretty,\n" +
                               "wildly attractive woman");
            System.out.println("You sit down at the bar, mesmerized by her resplendence, sublimity, and exquisiteness");

            int countdrink = 0;
            boolean drinking = true;

            while (drinking && player.isAlive()) {
                String drink = Input.ask("She asks you in a voice like honey:\n'Would you like to drink with me?' (y/n) ");

                if (drink.equalsIgnoreCase("yes") || drink.equalsIgnoreCase("y")) {
                    countdrink++;
                    System.out.println("She looks bashfully into your eyes as she fills up a shot and slides it to you.");
                    System.out.println("You gulp drink #" + countdrink + "!");

                    if (countdrink >= 3) {
                        System.out.println("\nYou take the last sip and suddenly everything goes black...");
                        System.out.println("You hear a honey-like voice in your ear: 'Thank you for stopping by!'\n");

                        // Rob the player completely
                        player.getInventory().clear();
                        player.addGold(-player.getGold()); // Sets gold to 0

                        System.out.println("You wake up outside, completely robbed of your items and gold!\n");
                        drinking = false;
                    }
                } else {
                    System.out.println("Aww well, come again next time!");
                    System.out.println("You escape in one piece, safe and sound.");
                    drinking = false;
                }
            }

        } else {
            // Female / Non-male path
            System.out.println("You're exhausted from battling all day, parched asf");
            System.out.println("Suddenly there appears a pub in the middle of the woods with the sign -Bar-");
            System.out.println("You sigh from relief and head inside");
            System.out.println("As soon as you set foot into the pub, you see a:\n" +
                               "beautiful,\n" +
                               "stunning,\n" +
                               "lovely,\n" +
                               "pretty,\n" +
                               "wildly attractive woman");
            System.out.println("You sit down at the bar, mesmerized by her resplendence, sublimity, and exquisiteness");

            String drink = Input.ask("She asks you in a voice like honey:\n'Would you like a drink?' (y/n) ");

            if (drink.equalsIgnoreCase("yes") || drink.equalsIgnoreCase("y")) {
                System.out.println("Here's a drink for you, baddie!");
                System.out.println("Have a nice journey!");
            } else {
                System.out.println("Goodbye, gorgeous queen!");
            }
        }
    }
}