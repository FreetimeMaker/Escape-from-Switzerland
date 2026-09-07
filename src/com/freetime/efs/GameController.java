package com.freetime.efs;

import java.io.Console;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GameController {
    private Location currentLocation;
    private double startTime = 216000.0;
    private double timeElapsed = 0.0;

    public GameController() {
        setupLocations();
    }

    private void setupLocations() {
        // Location List
        Location aelggialp = new Location("Älggi-Alp", "Du bist auf der \"Älggialp\" im Kanton \"Obwalden\", dem geografischen Mittelpunkt der Schweiz.");
        Location giswil = new Location("Giswil", "Du bist in \"Giswil\" im Kanton \"Obwalden\".");
        Location rufi = new Location("Rufi", "Du bist in \"Rufi\" im Kanton \"Obwalden\".");
        Location hasliberg = new Location("Hasliberg", "Du bist jetzt in \"Hasliberg\" im Kanton \"Bern\".");
        Location lungern = new Location("Lungern", "Du bist jetzt in \"Lungern\" im Kanton \"Obwalden\".");
        Location truebsee = new Location("Trübsee", "Du bist jetzt am \"Trübsee\" im Kanton \"Nidwalden\".");
        Location alpenresort_eienwaeldli = new Location("Alpenresort Eienwäldli", "Du bist jetzt im \"Alpenresort Eienwäldli\" im Kanton \"Obwalden\".");
        Location sarnen = new Location("Sarnen", "Du bist jetzt in \"Sarnen\" im Kanton \"Obwalden\".");
        Location melchtal_waterfall = new Location("Melchtal Waterfall", "Du bist jetzt am \"Melchtal Waterfall\" im Kanton \"Obwalden\".");
        Location honegg = new Location("Honegg", "Du bist jetzt in \"Honegg\" im Kanton \"Obwalden\".");
        Location alpnach = new Location("Alpnach", "Du bist jetzt in \"Alpnach\" im Kanton \"Obwalden\".");
        Location kerns = new Location("Kerns", "Du bist jetzt in \"Kerns\" im Kanton \"Obwalden\".");
        Location stalden_sarnen = new Location("Stalden (Sarnen)", "Du bist jetzt in \"Stalden (Sarnen)\" im Kanton \"Obwalden\".");
        Location aareschlucht_meiringen = new Location("Aareschlucht (Meiringen)", "Du bist jetzt an der \"Aareschlucht (Meiringen)\" im Kanton \"Bern\".");
        Location maegisalp = new Location("Mägisalp", "Du bist jetzt auf der \"Mägisalp\" im Kanton \"Bern\".");
        Location brienzwiler = new Location("Brienzwiler", "Du bist jetzt in \"Brienzwiler\" im Kanton \"Bern\".");
        Location balm_bei_meiringen = new Location("Balm bei Meiringen", "Du bist jetzt in \"Balm bei Meiringen\" im Kanton \"Bern\".");
        Location berghaus_schoenbueel = new Location("Berghaus Schönbüel", "Du bist jetzt im \"Berghaus Schönbüel\" im Kanton \"Obwalden\".");
        Location giglistock = new Location("Giglistock", "Du bist jetzt am \"Giglistock\" im Kanton \"Bern\".");
        Location engelberg = new Location("Engelberg", "Du bist jetzt in \"Engelberg\" im Kanton \"Obwalden\".");
        Location silenen = new Location("Silenen", "Du bist jetzt in \"Silenen\" im Kanton \"Uri\".");
        Location susten_passhoehe = new Location("Susten Passhöhe", "Du bist jetzt auf der \"Susten Passhöhe\" im Kanton \"Bern\".");
        Location bannalpsee = new Location("Bannalpsee", "Du bist jetzt am \"Bannalpsee\" im Kanton \"Nidwalden\".");
        Location erstfelder_tal = new Location("Erstfelder Tal", "Du bist jetzt im \"Erstfelder Tal\" im Kanton \"Uri\".");
        Location sportcamp_melchtal = new Location("Sportcamp Melchtal", "Du bist jetzt im \"Sportcamp Melchtal\" im Kanton \"Obwalden\".");
        Location teufibach = new Location("Teufibach", "Du bist jetzt am \"Teufibach\" im Kanton \"Obwalden\".");
        Location eugenisee = new Location("Eugenisee", "Du bist jetzt am \"Eugenisee\" im Kanton \"Obwalden\".");
        Location berghotel_langis = new Location("Berghotel Langis", "Du bist jetzt im \"Berghotel Langis\" im Kanton \"Obwalden\".");
        Location gipfelkreuz_giswilerstock = new Location("Gipfelkreuz Giswilerstock", "Du bist jetzt am \"Gipfelkreuz Giswilerstock\" im Kanton \"Obwalden\".");
        Location chessiloch_fluehli = new Location("Chessiloch Flühli", "Du bist jetzt im \"Chessiloch Flühli\" im Kanton \"Luzern\".");
        Location hergiswil = new Location("Hergiswil", "Du bist jetzt in \"Hergiswil\" im Kanton \"Nidwalden\".");
        Location oberdorf = new Location("Oberdorf", "Du bist jetzt in \"Oberdorf\" im Kanton \"Nidwalden\".");
        Location regionalmuseum_chueechlihus = new Location("Regionalmuseum Chüechlihus", "Du bist jetzt im \"Regionalmuseum Chüechlihus\" im Kanton \"Bern\".");
        Location luzern = new Location("Luzern", "Du bist jetzt in \"Luzern\" im Kanton \"Luzern\".");

        // From Älggialp
        aelggialp.setTwoWayExit(Direction.NORTH, rufi, 450.0);
        aelggialp.setTwoWayExit(Direction.SOUTH, hasliberg, 1200.0);
        aelggialp.setTwoWayExit(Direction.WEST, lungern, 1050.0);
        aelggialp.setTwoWayExit(Direction.EAST, truebsee, 1500.0);

        // From Rufi
        rufi.setTwoWayExit(Direction.NORTH, giswil, 900.0);
        rufi.setTwoWayExit(Direction.SOUTH, aelggialp, 450.0);
        rufi.setTwoWayExit(Direction.WEST, lungern, 1200.0);
        rufi.setTwoWayExit(Direction.EAST, alpenresort_eienwaeldli, 1950.0);

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
        lungern.setTwoWayExit(Direction.NORTH, giswil, 900.0);
        lungern.setTwoWayExit(Direction.SOUTH, balm_bei_meiringen, 900.0);
        lungern.setTwoWayExit(Direction.WEST, berghaus_schoenbueel, 750.0);

        // From Trübsee
        truebsee.setTwoWayExit(Direction.WEST, aelggialp, 1500.0);
        truebsee.setTwoWayExit(Direction.SOUTH, giglistock, 1500.0);
        truebsee.setTwoWayExit(Direction.NORTH, engelberg, 600.0);
        truebsee.setTwoWayExit(Direction.EAST, silenen, 2850.0);

        // From Alpenresort Eienwäldli
        alpenresort_eienwaeldli.setTwoWayExit(Direction.WEST, rufi, 1950.0);
        alpenresort_eienwaeldli.setTwoWayExit(Direction.SOUTH, susten_passhoehe, 2850.0);
        alpenresort_eienwaeldli.setTwoWayExit(Direction.NORTH, bannalpsee, 900.0);
        alpenresort_eienwaeldli.setTwoWayExit(Direction.EAST, erstfelder_tal, 1500.0);

        // From Melchtal Waterfall
        melchtal_waterfall.setTwoWayExit(Direction.WEST, giswil, 900.0);
        melchtal_waterfall.setTwoWayExit(Direction.SOUTH, sportcamp_melchtal, 240.0);
        melchtal_waterfall.setTwoWayExit(Direction.NORTH, teufibach, 390.0);
        melchtal_waterfall.setTwoWayExit(Direction.EAST, eugenisee, 1290.0);

        // From Honegg
        honegg.setTwoWayExit(Direction.EAST, giswil, 450.0);
        honegg.setTwoWayExit(Direction.NORTH, berghotel_langis, 3150.0);
        honegg.setTwoWayExit(Direction.SOUTH, gipfelkreuz_giswilerstock, 3900.0);
        honegg.setTwoWayExit(Direction.WEST, chessiloch_fluehli, 4050.0);

        // From Alpnach
        alpnach.setTwoWayExit(Direction.SOUTH, sarnen, 750.0);
        alpnach.setTwoWayExit(Direction.NORTH, hergiswil, 810.0);
        alpnach.setTwoWayExit(Direction.EAST, oberdorf, 1200.0);
        alpnach.setTwoWayExit(Direction.WEST, regionalmuseum_chueechlihus, 4950.0);

        // From Kerns
        kerns.setTwoWayExit(Direction.WEST, sarnen, 450.0);
        kerns.setTwoWayExit(Direction.NORTH, luzern, 2100.0);


        this.currentLocation = aelggialp;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        Console console = System.console();

        System.out.println("Hallo und willkommen bei \"Escape from Switzerland\"!");

        if (console != null) {
            String eingabe = console.readLine("Gib einen Namen ein, um fortzufahren: ");
            System.out.println("Willkommen: " + eingabe);
        }

        System.out.println("Deine Mission ist es, aus der Schweiz zu fliehen, bevor die Zeit abläuft!");
        System.out.println("Wenn die Zeit 0 Sekunden erreicht, scheiterst du bei deiner Flucht!");
        System.out.println("Du startest auf der \"Älggialp\" im Kanton \"Obwalden\".");
        System.out.println("Wenn du in eine bestimmte Richtung gehen möchtest, tippe sie in die Konsole ein.");
        System.out.println("Du hast 2 Tage und 12 Stunden Zeit, um aus der Schweiz herauszukommen.");

        List<String> validStart = Arrays.asList("start", "start");
        boolean start = false;

        while (!start) {
            System.out.print("Tippe \"Start\" oder \"start\" ein, um zu beginnen: ");
            String answer = scanner.nextLine().trim().toLowerCase();

            if (validStart.contains(answer)) {
                start = true;
            } else {
                System.out.println("Ungültige Eingabe! Du kannst antworten mit: " + validStart);
            }
        }

        while (startTime - timeElapsed > 0) {
            double remainingTime = startTime - timeElapsed;

            System.out.println("\n------------------------------------------------");
            System.out.println("Ort: " + currentLocation.getName());
            System.out.println(currentLocation.getDescription());
            System.out.printf("Verbleibende Zeit: %.0f Sekunden\n", remainingTime);
            System.out.print("Wohin möchtest du gehen? (N/S/O/W oder 'exit'): ");

            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("exit") || input.equals("q")) {
                System.out.println("Spiel vorbei.");
                break;
            }

            Direction dir = Direction.parse(input);
            if (dir == null) {
                System.out.println("Ungültige Richtung! Bitte gib 'N', 'S', 'O' oder 'W' ein.");
                continue;
            }

            Location next = currentLocation.getExit(dir);
            if (next != null) {
                // Ermittelt die Zeit für genau diese Strecke
                double travelTime = currentLocation.getTravelTime(dir);
                timeElapsed += travelTime;

                System.out.printf("Die Reise von %s nach %s hat %.0f Sekunden gedauert.\n", currentLocation.getName(), next.getName(), travelTime);
                currentLocation = next;
            } else {
                System.out.println("Du kannst nicht in diese Richtung gehen!");
            }
        }

        if (startTime - timeElapsed <= 0) {
            System.out.println("\nDie Zeit ist abgelaufen! Du hast es nicht rechtzeitig geschafft, aus der Schweiz zu fliehen.");
        }

        scanner.close();
    }
}