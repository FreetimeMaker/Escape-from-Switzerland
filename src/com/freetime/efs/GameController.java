package com.freetime.efs;

import com.freetime.efs.east.Truebsee;
import com.freetime.efs.north.Rufi;
import com.freetime.efs.south.Hasliberg;
import com.freetime.efs.west.Lungern;

import java.io.Console;

public class GameController {
    public static double handleDirectionInput(Console console, double startTime, double timeElapsed) {
        IO.println("You're at \"Älggialp\", in which Direction do you want to go?");

        Direction direction = null;

        if (console != null) {
            boolean validChoice = false;

            do {
                String directionAnswer = console.readLine("Choose a direction (North/South/East/West): ").trim().toLowerCase();

                switch (directionAnswer) {
                    case "north":
                    case "n":
                        direction = new Rufi();
                        validChoice = true;
                        break;
                    case "south":
                    case "s":
                        direction = new Hasliberg();
                        validChoice = true;
                        break;
                    case "west":
                    case "w":
                        direction = new Lungern();
                        validChoice = true;
                        break;
                    case "east":
                    case "e":
                        direction = new Truebsee();
                        validChoice = true;
                        break;
                    default:
                        System.out.println("Invalid direction! Type North (N), South (S), East (E), or West (W).");
                        validChoice = false;
                        break;
                }
            } while (!validChoice);
        }

        if (direction != null) {
            double timeSpent = direction.execute();
            startTime -= timeSpent;
        }

        if (startTime <= 0) {
            IO.println("Time's up! You failed to escape and get help.");
        }

        return startTime;
    }
}