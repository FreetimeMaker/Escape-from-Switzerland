package me.freetime.efs;

import java.io.Console;

public class GameController {

    private static Direction currentLocation = WorldMap.createMap();

    public static double handleDirectionInput(Console console, double startTime) {
        if (console == null) return startTime;

        IO.println("You're currently at: " + currentLocation.getName());
        Direction.Route chosenRoute = null;

        while (chosenRoute == null) {
            String input = console.readLine("Choose a direction (North/South/East/West): ").trim().toLowerCase();

            chosenRoute = currentLocation.getRoute(input);

            if (chosenRoute == null) {
                IO.println("Invalid direction or no path in that direction! Try again.");
            }
        }

        currentLocation = chosenRoute.destination;

        timeElapsed = chosenRoute.travelTime;
        startTime -= timeSpent;

        int minutes = (int) (timeSpent / 60);
        int seconds = (int) (timeSpent % 60);
        IO.println("You traveled to \"" + currentLocation.getName() + "\". It took " + minutes + " minutes and " + seconds + " seconds.");

        if (startTime <= 0) {
            IO.println("Time's up! You failed to escape and get help.");
        }

        return startTime;
    }
}