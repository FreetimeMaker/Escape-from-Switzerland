package me.freetime.efs.north;

import me.freetime.efs.Direction;

public class Rufi implements Direction {
    @Override
    public double execute() {
        IO.println("You're in \"Rufi\".");
        IO.println("It took you 7 minutes and 30 seconds to get here.");
        return 450.0;
    }

    @Override
    public Direction getNextLocation(String direction) {
        if (direction.equals("north") || direction.equals("n")) {
            return new Ziegelbruecke();
        }
        return null;
    }
}
