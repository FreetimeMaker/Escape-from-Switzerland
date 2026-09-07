package com.freetime.efs;

import java.io.Console;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GameController {
    private Location currentLocation;
    private double startTime = 28800.0;
    private double timeElapsed = 0.0;

    public GameController() {
        setupLocations();
    }

    private void setupLocations() {
        // Location List
        Location aelggialp = new Location("Älggi-Alp", "You are at \"Älggialp\", the geographical center of Switzerland.");
        Location giswil = new Location("Giswil", "You're in \"Giswil\".");
        Location rufi = new Location("Rufi", "You're in \"Rufi\".");
        Location hasliberg = new Location("Hasliberg", "You're now in \"Hasliberg\" in the canton \"Bern\".");
        Location lungern = new Location("Lungern", "You're now in \"Lungern\".");
        Location truebsee = new Location("Trübsee", "You're now at \"Trübsee\" in the canton \"Nidwalden\".");
        Location eienwald = new Location("Alpenresort Eienwald", "You're now at \"Alpenresort Eienwald\" in the canton \"Nidwalden\".");
        Location sarnen = new Location("Sarnen", "You're now in \"Sarnen\".");
        Location melchtal_waterfall = new Location("Melchtal Waterfall", "You're now at the \"Melchtal Waterfall\".");
        Location honegg = new Location("Honegg", "You're now in \"Honegg\".");
        Location alpnach = new Location("Alpnach", "You're now in \"Alpnach\".");
        Location kerns = new Location("Kerns", "You're now in \"Kerns\".");
        Location stalden_sarnen = new Location("Stalden (Sarnen)", "You're now in \"Stalden (Sarnen)\".");
        Location aareschlucht_meiringen = new Location("Aareschlucht (Meiringen)", "You're now at the \"Aareschlucht (Meiringen)\".");
        Location maegisalp = new Location("Mägisalp", "You're now at the \"Mägisalp\".");
        Location brienzwiler = new Location("Brienzwiler", "You're now in \"Brienzwiler\".");

        // From Älggialp
        aelggialp.setTwoWayExit(Direction.NORTH, rufi, 450.0);
        aelggialp.setTwoWayExit(Direction.SOUTH, hasliberg, 1200.0);
        aelggialp.setTwoWayExit(Direction.WEST, lungern, 1050.0);
        aelggialp.setTwoWayExit(Direction.EAST, truebsee, 1500.0);

        // From Rufi
        rufi.setTwoWayExit(Direction.NORTH, giswil, 900.0);
        rufi.setTwoWayExit(Direction.SOUTH, aelggialp, 450.0);
        rufi.setTwoWayExit(Direction.WEST, lungern, 1200.0);
        rufi.setTwoWayExit(Direction.EAST, eienwald, 1950.0);

        // From Giswil
        giswil.setTwoWayExit(Direction.NORTH, sarnen, 1500.0);
        giswil.setTwoWayExit(Direction.SOUTH, rufi, 900.0);
        giswil.setTwoWayExit(Direction.EAST, melchtal_waterfall, 900.0);
        giswil.setTwoWayExit(Direction.WEST, honegg, 450.0);

        // From Sarnen
        sarnen.setTwoWayExit(Direction.SOUTH, giswil, 1500.0);
        sarnen.setTwoWayExit(Direction.NORTH, alpnach, 750.0);
        sarnen.setTwoWayExit(Direction.EAST, kerns, 450.0);
        sarnen.setTwoWayExit(Direction.WEST, stalden_sarnen, 570.0);

        // From Hasliberg
        hasliberg.setTwoWayExit(Direction.NORTH, aelggialp, 1200.0);
        hasliberg.setTwoWayExit(Direction.SOUTH, aareschlucht_meiringen, 690.0);
        hasliberg.setTwoWayExit(Direction.EAST, maegisalp, 600.0);
        hasliberg.setTwoWayExit(Direction.WEST, brienzwiler, 1050.0);

        // From Lungern
        lungern.setTwoWayExit(Direction.EAST, aelggialp, 1050.0);

        this.currentLocation = aelggialp;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        Console console = System.console();

        System.out.println("Hello and welcome to \"Escape from Switzerland\"!");

        if (console != null) {
            String eingabe = console.readLine("Enter a name to get further: ");
            System.out.println("Welcome: " + eingabe);
        }

        System.out.println("Your mission is to escape from Switzerland before the time runs out!");
        System.out.println("If the time reaches 0 seconds, you fail your escape!");
        System.out.println("You start at the \"Älggialp\" in the canton \"Obwalden\".");
        System.out.println("If you want to go in any direction type it in the console.");
        System.out.println("You've got 5 hours to get out of Switzerland.");

        List<String> validStart = Arrays.asList("start", "s");
        boolean start = false;

        while (!start) {
            System.out.print("Type \"Start\" to begin: ");
            String answer = scanner.nextLine().trim().toLowerCase();

            if (validStart.contains(answer)) {
                start = true;
            } else {
                System.out.println("Invalid input! You can answer with: " + validStart);
            }
        }

        while (startTime - timeElapsed > 0) {
            double remainingTime = startTime - timeElapsed;

            System.out.println("\n------------------------------------------------");
            System.out.println("Location: " + currentLocation.getName());
            System.out.println(currentLocation.getDescription());
            System.out.printf("Time remaining: %.0f seconds\n", remainingTime);
            System.out.print("Where would you like to go? (N/S/E/W or 'exit'): ");

            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("exit") || input.equals("q")) {
                System.out.println("Game over.");
                break;
            }

            Direction dir = Direction.parse(input);
            if (dir == null) {
                System.out.println("Invalid direction! Please enter 'N', 'S', 'E', or 'W'.");
                continue;
            }

            Location next = currentLocation.getExit(dir);
            if (next != null) {
                // Ermittelt die Zeit für genau diese Strecke
                double travelTime = currentLocation.getTravelTime(dir);
                timeElapsed += travelTime;

                System.out.printf("Traveling from %s to %s took %.0f seconds.\n", currentLocation.getName(), next.getName(), travelTime);
                currentLocation = next;
            } else {
                System.out.println("You cannot go in that direction!");
            }
        }

        if (startTime - timeElapsed <= 0) {
            System.out.println("\nTime ran out! You failed to escape Switzerland in time.");
        }

        scanner.close();
    }
}