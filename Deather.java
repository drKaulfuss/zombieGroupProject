public class Deather extends Item{
    private int damage = 0;
    public Deather(int value, int damage){
        super("Deather", "This is just an ordinary feather... right...?",value);
        this.damage = damage;
    }
    public int getAttackBonus(){
        return damage;
    }
    // IF you correctly say the name it will one hit kill any boss
    public String encantation(){
        System.out.print("You must say the Deathers name 3 times");
        String first = Input.ask("Say its name");
        String second = Input.ask("Say its name");
        String third = Input.ask("Say its name");
        if(first == "Deather" && second == "Deather" && third == "Deather"){
            this.damage = 1000000;
            return "YOU ACTIVATED DEATHER";
        }
        else{
            return " You failed to activate Deather";
        }
        
    }
}