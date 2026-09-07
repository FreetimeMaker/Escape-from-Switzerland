package com.freetime.efs.south;

import com.freetime.efs.Direction;

public class Hasliberg implements Direction {
    @Override
    public double execute() {
        IO.println("You're now in \"Hasliberg\" in the canton \"Bern\".");
        IO.println("It took you 20 minutes to get here.");
        IO.println("You have 4 hours and 40 minutes left.");
        return 1200.0;
    }

    @Override
    public Direction getNextLocation(String direction) {
        if (direction.equals("north") || direction.equals("n")) {
            return new Ziegelbruecke();
        }
        return null;
    }
}