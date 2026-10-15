import java.util.Random;
class Medpill extends Item {
 private Random random = new Random();
 public Medpill() {
 super("Medpill", "May heal or kill you", 5);
}

public boolean use(Character user) {
  if (random.nextDouble() > 0.4) {
   user.heal(-100);
    return true;
  }
  int missingHealth = user.getMaxHealth()-user.getHealth();
  if (missingHealth > 0) {
   int healAmount = random.nextInt(missingHealth + 1);
   user.heal(healAmount);
  return true;
  }
 return false;
}
}
