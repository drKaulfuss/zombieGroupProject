public class noteForDeather extends Item{
    public noteForDeather(String name, String description, int value){
        super(name,description,value);
    }
    public String activateDeather(){
        return "Be careful! If you say the Deathers name 3 times its true power will be unleashed upon your enemy";
    }

}