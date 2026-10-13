import java.util.ArrayList;
//only spawn at the bar
public class BaddieBartender extends Character{

    public BaddieBartender() {
        super("Baddiella", 100, 25);
    }
    public boolean isHostile() {
        return false; // She is an interactive NPC, not a hostile monster
    }
    public void gender(Character player) {
        String gender = Input.ask("What is your gender, fool? ");

    
    if (gender == "m" || gender == "M"){
        System.out.println("You're exhuasted from battling all day, partched asf");
        System.out.println("Suddenly there appears a pub in the middle of the woods with the sign -Bar-");
        System.out.println("You sigh from relief and head inside");
        System.out.println("As soon as you set foot into the pub and see a\n" +
        "beautiful,\n" +
        "stunning,\n" +
        "lovely,\n" +
        "pretty,\n" +
        "wildly attractive, woman");
        System.out.println("You sit down at the bar, mesmerized by her resplendence, sublimity, and exquisiteness");
        System.out.println("she s");
    } else {
        System.out.println("hi");
    }
    }

}
