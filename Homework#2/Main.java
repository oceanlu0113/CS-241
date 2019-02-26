
public class Main {

    public static void main(String[] args) {
        GuessingGame game = new GuessingGame();
        do {
            game.playGame();
        } while (game.check("Enter (yes) to continue: "));
        System.out.println("Thank you for playing.");
        System.exit(0);
    }
}
