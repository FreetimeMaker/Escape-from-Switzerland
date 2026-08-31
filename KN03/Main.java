import me.freetime.efs.GameController;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
final void main() throws InterruptedException {
    double startTime = 28800.0;
    double timeElapsed = 0.0;
    boolean start = false;

    IO.println("Hello and welcome to \"Escape from Switzerland\"!");

    Console console = System.console();
    if (console != null) {
        String eingabe = console.readLine("Enter a name to get further: ");
        IO.println("Welcome: " + eingabe);
    }

    IO.println("Your mission is to escape from Switzerland before the time runs out and stop Germany to take over the World!");
    IO.println("if the time gets to 0 seconds Germany will take over the World successfuly!");
    IO.println("You start at the \"Älggialp\" in the canton \"Obwalden\".");
    IO.println("If you want to go in any Direction type it in the Console and you go in that Direction.");
    IO.println("You've got 5 hours to get out of Switzerland");
    IO.println("You can now start the game with Start");

    if (console != null) {
        List<String> validStart = Arrays.asList("start", "s");

        boolean again = false;
        do {
            String answer = console.readLine("Type \"Start\" to begin: ").trim().toLowerCase();

            if (validStart.contains(answer)) {
                start = true;
                again = false;
            } else {
                System.out.println("Invalid input! You can answer with: " + validStart);
                again = true;
            }
        } while (again);
    }

    if (start) {
        startTime = GameController.handleDirectionInput(console, startTime);
    }
}