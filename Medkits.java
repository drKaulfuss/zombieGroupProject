class Medkits extends Item {
public Medkits() {
 super("Medpack", "Sets health to max", 50);
}

public boolean use(Character user) {
  user.heal(user.getMaxHealth());
  return true;
}
