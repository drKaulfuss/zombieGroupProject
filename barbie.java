import java.util.*;
public class barbie extends Character {

  private int iq;
    
//constructor
  public barbie(int i){
    super("barbie", 100, 1600);
    this.iq = i;   
}

public void interact(Character Player) {
  Scanner input = new Scanner(System.in);

  System.out.println("Hi, I'm Barbie what's up?(answer good or bad)");
  String one = input.nextLine();

  if(one == "good"){
    System.out.println("YAY, you go girl.");
  } else if(one == "bad"){
    System.out.println("oh no :(. remember you're unstoppable!");
  } else{
    System.out.println("What's wrong with you, enter valid input!!"); 
  }
}



}
