import java.util.Scanner;

/**
 * Input - keyboard helper methods for the whole program.
 *
 * There is only ONE Scanner for the entire game, and it lives here.
 * (Two Scanners reading the keyboard at the same time can cause strange bugs.)
 *
 * Students: if your class needs to ask the player something (for example,
 * a Shopkeeper asking "Which item do you want?"), call these methods:
 *
 *      String answer = Input.ask("What would you like? ");
 *      int number    = Input.askNumber("How many? ");
 *
 * You do NOT need to create your own Scanner.
 */
public class Input
{
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Print a prompt and return what the user typed (extra spaces removed).
     */
    public static String ask(String prompt)
    {
        System.out.print(prompt);
        if (!scanner.hasNextLine())
        {
            System.out.println("\n(No more input - ending the game.)");
            System.exit(0);
        }
        return scanner.nextLine().trim();
    }

    /**
     * Ask for a whole number. If the user types something that isn't a
     * number, return -1. (The try/catch handles the error so the
     * program doesn't crash.)
     */
    public static int askNumber(String prompt)
    {
        String text = ask(prompt);
        try
        {
            return Integer.parseInt(text);
        }
        catch (NumberFormatException e)
        {
            return -1;
        }
    }
}
