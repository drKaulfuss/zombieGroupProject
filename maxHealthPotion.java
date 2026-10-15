
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
    
    public boolean onPickup(Character finder) {
        System.out.println("You found: " + name + " - " + description);
        return true;

    }
    public void setHealing(Character foo){
        foo.heal(foo.getMaxHealth()-foo.getHealth());
    }

    public boolean use(Character user)
    {
        System.out.println("You can't use the " + name + " right now.");
        return false;
    }

    public String toString()
    {
        return name + " (worth " + value + " gold)";
    }
}
