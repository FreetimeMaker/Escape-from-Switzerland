//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
final void main() throws InterruptedException {
    int startTime = 1000;
    int timeElapsed = 0;
    boolean start = false;

    IO.println("Hello and welcome to \"Escape from Switzerland\"!");

    Console console = System.console();
    if (console != null) {
        String eingabe = console.readLine("Enter a name to get further: ");
        IO.println("Welcome: " + eingabe);
    }

    IO.println("Your mission is to escape from Switzerland before the time runs out!");
    IO.println("You start at the \"Älggi-Alp\" that's exactly the middle of Switzerland.");
    IO.println("If you want to go in any Direction type it in the Console and you go in that Direction.");
    IO.println("Per Room you have 1000 Seconds to get out");
    IO.println("You can now start the game with Start");

    if (console != null) {
        Scanner in = new Scanner(System.in);
        List<String> answers = Arrays.asList(new String[]{"Start", "start"});

        boolean again = false;
        do {
            String answer = in.next();

            if (answers.contains(answer.toLowerCase())) {
                start = true;
                break;
            } else {
                System.out.println("You can answer with these words: " + answers);
                again = true;
            }
        } while (again);
    }

    if (start == true) {
        while (startTime > 0) {
            timeElapsed++;
            double remTime = (startTime - timeElapsed);
            IO.println("Time left: " + remTime);
            Thread.sleep(1000);

            if (remTime <= 0) {
                IO.println("Time's up! You failed to escape.");
                break;
            }
        }
    }
}