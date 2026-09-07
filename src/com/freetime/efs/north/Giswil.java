package com.freetime.efs.north;

import com.freetime.efs.Direction;

public class Giswil implements Direction {
    @Override
    public double execute() {
        IO.println("You're in \"Giswil\".");
        IO.println("It took you 15 minutes to get here.");
        IO.println("You have 4 hours and 45 minutes left to escape.");
        return 900.0;
    }

    @Override
    public Direction getNextLocation(String direction) {
        if (direction.equals("north") || direction.equals("n")) {
            return new Sarnen();
        }
        return null;
    }
