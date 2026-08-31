package me.freetime.efs.east;

import me.freetime.efs.Direction;

public class Truebsee implements Direction {
    @Override
    public double execute() {
        IO.println("You're now at \"Trübsee\" in the canton \"Nidwalden\".");
        IO.println("It took you 25 minutes to get here.");
        IO.println("You have 4 hours and 35 minutes left to escape.");
        return 1500;
    }

    @Override
    public Direction getNextLocation(String direction) {
        if (direction.equals("north") || direction.equals("n")) {
            return new Ziegelbruecke();
        }
        return null;
    }
}
