import java.util.*;


public class Slime extends Character {
    public Slime() {
        super("Dr Slime", 1, 0);
    }


    public void takeTurn(Character target) {
        System.out.println(getName() + " Stares at you helplessly.");
        System.out.println("ԅ(°ㅂ°ԅ)");
        System.out.println(getName() + " Has been squashed under your foot.");
        takeDamage(20000000);
        System.out.println(getName() + " is slowly disintegrating in front of you.");
        target.heal(10);

    }
}