package com.freetime.efs.north;

import com.freetime.efs.Aelggialp;
import com.freetime.efs.Direction;
import com.freetime.efs.east.AlpenresortEienwald;
import com.freetime.efs.west.Lungern;

public class Rufi implements Direction {
    @Override
    public double execute() {
        IO.println("You're in \"Rufi\".");
        IO.println("It took you 7 minutes and 30 seconds to get here.");
        return 450.0;
    }

    @Override
    public Direction getNextLocation(String direction) {
        return switch (direction) {
            case "north", "n" -> new Giswil();
            case "west", "w" -> new Lungern();
            case "south", "s" -> new Aelggialp();
            case "east", "e" -> new AlpenresortEienwald();
            default -> null;
        };
    }
}