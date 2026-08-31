package me.freetime.efs.west;

import me.freetime.efs.Direction;

public class Lungern implements Direction {
    @Override
    public double execute() {
        IO.println("You're now in \"Lungern\".");
        IO.println("It took you 17 minutes and 30 seconds to get here.");
        IO.println("You have 4 hours, 42 minutes and 30 seconds left to escape.");
        return 1050;
    }

    @Override
    public Direction getNextLocation(String direction) {
        if (direction.equals("north") || direction.equals("n")) {
            return new Ziegelbruecke();
        }
        return null;
    }
}
