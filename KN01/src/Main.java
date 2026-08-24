//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() throws InterruptedException {
    int startTime = 1000;
    int timeElapsed = 0;

    IO.println("Hello and welcome to \"Escape from Switzerland\"!");
    IO.println("Your mission is to escape from Switzerland before the time runs out!");
    IO.println();

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
