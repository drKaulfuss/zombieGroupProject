
public class maxHealthPotion extends Item
{
   
    private String name;
    private String description;
    private int value;
    public int healingValue;

    public maxHealthPotion(String name, String description, int value, int healingValue)
    {
        super(name, description, value);
    
        this.healingValue = healingValue;
    
    }
    public void setHealing(Character foo){
        foo.heal(foo.getMaxHealth()-foo.getHealth());
    }

    public String toString()
    {
        return name + " (worth " + value + " gold)";
    }
}
