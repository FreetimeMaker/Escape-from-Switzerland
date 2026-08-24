//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() throws InterruptedException {
    int startTime = 1000;
    int timeElapsed = 0;
    boolean start = false;

    IO.println("Hello and welcome to \"Escape from Switzerland\"!");
    IO.println("Your mission is to escape from Switzerland before the time runs out!");
    IO.println("You start at the \"Älggi-Alp\" that's exactly the middle of Switzerland.");
    IO.println("If you want to go in any Direction type it in the Console and you go in that Direction.");
    IO.println("You understand everything good now you can type Start");

    Console console = System.console();

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
            int remTime = startTime - timeElapsed;
            IO.println("Time left: " + remTime);
            Thread.sleep(1000);

            if (remTime <= 0) {
                IO.println("Time's up! You failed to escape.");
                break;
            }
        }
    }
}
