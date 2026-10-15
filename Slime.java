import java.util.*;


public class Slime extends Character {

    private boolean poison;

    public Slime() {
        super("Slime", 1, 0);
        Random rand = new Random();
        poison = rand.nextInt(5) < 2;
        if (poison) {
            System.out.println("Unlucky! " + getName() + " is a poisonous slime!");
        } else {
            System.out.println(getName() + " is a harmless slime.");
        }
    }

    public void takeTurn(Character target) {
        if (poison) {
            System.out.println(getName() + " stares at you in anger.");
            System.out.println("You squish "+ getName() + ". As it perishes, you are sprayed with its poison.");
            takeDamage(5);
            target.takeDamage(10);

        } else {
            System.out.println(getName() + " stares at you helplessly.");
            System.out.println("ԅ(°ㅂ°ԅ)");
            System.out.println(getName() + " has been squashed under your foot.");
            takeDamage(20000000);
            System.out.println(getName() + " is slowly disintegrating in front of you.");
            target.heal(10);
        }
    }
}