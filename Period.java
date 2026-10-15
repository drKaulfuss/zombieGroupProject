public class Period
{
    private int strengthLoss;

    public Period()
    {
        strengthLoss = 0;
    }

    public void onBattleStart(Character player, String gender)
    {
        strengthLoss = 0;

        boolean female = gender != null && gender.trim().toUpperCase().startsWith("F");

        if (female && Math.random() < 0.15)
        {
            System.out.println(player.getName() + " is weakened by the Period!");

            int healthLoss = (int)(player.getMaxHealth() * 0.20);
            healthLoss = Math.min(healthLoss, player.getHealth() - 1);
            if (healthLoss > 0)
            {
                player.takeDamage(healthLoss + player.getDefense());
            }

            int oldStrength = player.getStrength();
            int newStrength = (int)Math.round(oldStrength * 0.85);
            strengthLoss = oldStrength - newStrength;
            player.setStrength(newStrength);
        }
    }

    public void onBattleEnd(Character player)
    {
        player.setStrength(player.getStrength() + strengthLoss);
        strengthLoss = 0;
    }
}

/*
 * INSTRUCTIONS 
 *
 * Period is not an inventory item. It is a random event that can happen
 * at the start of any battle, but only if the player's gender answer
 * starts with the letter F (female). It has a 15% chance per battle. When it
 * happens, the player loses 20% of max health (never below 1 HP) and
 * their strength is lowered by 15% until the battle ends.
 *
 * To use it, add these three things to Game.java:
 *
 * 1. Add two fields next to the other fields at the top of the class:
 *
 *        private Period period = new Period();
 *        private String gender;
 *
 * 2. In run(), change the gender question so it fills the field instead
 *    of a local variable (remove the word "String" in front of it):
 *
 *        gender = Input.ask("What is your gender, fool? ");
 *
 * 3. In fight(), call onBattleStart right after the "blocks your path"
 *    message and BEFORE the while loop:
 *
 *        period.onBattleStart(player, gender);
 *
 *    Then call onBattleEnd right AFTER the closing brace of the while loop,
 *    still inside fight():
 *
 *        period.onBattleEnd(player);
 *
 * Both calls are required. onBattleEnd gives back the strength that
 * onBattleStart took away. Lost health is not restored.
 */
