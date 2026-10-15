import java.util.Random;

public class TreyTaylor extends Character {
    private Random rand = new Random();

    public TreyTaylor() {
        super("Trey Taylor", 250, 20);
    }
   
    // Said upon encounter with the player
    public static String getOut() {
        return "Hit the road duuuude!";
    }

    public void takeTurn(Character target) {
        randomAttack(target);
    }

    // Random attack routine
    public void randomAttack(Character target) {
        int randomInt = rand.nextInt(10); // Generates a number from 0 to 9
       
        if (randomInt == 1) {
            System.out.println("Who gave you permission to fish down here boy?");
            target.takeDamage(500);
        } else if (randomInt >= 8) {
            System.out.println("Cheetah? Don Cheetah.");
        } else if (randomInt >= 5) {
            System.out.println("This is private property, this is private property, this is private property");
            target.takeDamage(10);
        } else {
            System.out.println("STATE!? THIS!? STATE!?");
            target.takeDamage(25);
        }
    }
}