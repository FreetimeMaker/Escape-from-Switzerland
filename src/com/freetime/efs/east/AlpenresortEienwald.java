package com.freetime.efs.east;

import com.freetime.efs.Direction;

public class AlpenresortEienwald implements Direction {
    @Override
    public double execute() {
        IO.println("You're now at \"Alpenresort Eienwald\" in the canton \"Nidwalden\".");
        IO.println("It took you 32 minutes and 30 seconds to get here.");
        IO.println("You have 4 hours and 27 minutes and 30 seconds left to escape.");
        return 1950.0;
    }

    @Override
    public Direction getNextLocation(String direction) {
        if (direction.equals("north") || direction.equals("n")) {
            return new Ziegelbruecke();
        }
        return null;
    }
}