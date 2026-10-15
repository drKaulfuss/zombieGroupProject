
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
   
   public boolean use(Character user) {
      user.heal(100);
      return true;
  }
    public String toString()
    {
        return name + " (worth " + value + " gold)";
    }
}
