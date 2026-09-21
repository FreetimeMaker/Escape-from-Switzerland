package com.freetime.efs;

import java.io.Console;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameController {

    // 10 Tage Spielzeit
    private static final double GAME_DURATION = 10 * 24 * 60 * 60;

    // Spielstart um 06:00 Uhr
    private static final int START_HOUR = 6;

    // Nachts dauern Reisen 20 % länger
    private static final double NIGHT_TRAVEL_MULTIPLIER = 1.20;

    // "warten" = 30 Minuten
    private static final double WAIT_TIME = 30 * 60;

    private Location currentLocation;

    private double startTime = GAME_DURATION;
    private double timeElapsed = 0.0;

    private final Random random = new Random();

    private Weather currentWeather = Weather.CLEAR;

    // Erster Wetterwechsel nach 3 Stunden
    private double nextWeatherChange = 3 * 60 * 60;


    /*
     * ============================================================
     * WETTER
     * ============================================================
     */

    private enum Weather {

        CLEAR(
                "☀️ Klar",
                1.00,
                true
        ),

        CLOUDY(
                "☁️ Bewölkt",
                1.00,
                true
        ),

        RAIN(
                "🌧️ Regen",
                1.15,
                true
        ),

        FOG(
                "🌫️ Nebel",
                1.25,
                true
        ),

        STRONG_WIND(
                "💨 Starker Wind",
                1.40,
                true
        ),

        THUNDERSTORM(
                "⛈️ Gewitter",
                1.00,
                false
        );

        private final String displayName;
        private final double travelMultiplier;
        private final boolean travelAllowed;

        Weather(
                String displayName,
                double travelMultiplier,
                boolean travelAllowed
        ) {
            this.displayName = displayName;
            this.travelMultiplier = travelMultiplier;
            this.travelAllowed = travelAllowed;
        }

        public String getDisplayName() {
            return displayName;
        }

        public double getTravelMultiplier() {
            return travelMultiplier;
        }

        public boolean isTravelAllowed() {
            return travelAllowed;
        }
    }


    /*
     * ============================================================
     * KONSTRUKTOR
     * ============================================================
     */

    public GameController() {
        setupLocations();
    }


    /*
     * ============================================================
     * ORTE
     * ============================================================
     */

    private void setupLocations() {

        Location aelggialp = new Location(
                "Älggi-Alp",
                "Du bist auf der \"Älggialp\" im Kanton \"Obwalden\" oder kurz \"OW\", dem geografischen Mittelpunkt der Schweiz."
        );

        Location giswil = new Location(
                "Giswil",
                "Du bist in \"Giswil\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location rufi = new Location(
                "Rufi",
                "Du bist in \"Rufi\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location hasliberg = new Location(
                "Hasliberg",
                "Du bist jetzt in \"Hasliberg\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location lungern = new Location(
                "Lungern",
                "Du bist jetzt in \"Lungern\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location truebsee = new Location(
                "Trübsee",
                "Du bist jetzt am \"Trübsee\" im Kanton \"Nidwalden\" oder kurz \"NW\"."
        );

        Location alpenresort_eienwaeldli = new Location(
                "Alpenresort Eienwäldli",
                "Du bist jetzt im \"Alpenresort Eienwäldli\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location sarnen = new Location(
                "Sarnen",
                "Du bist jetzt in \"Sarnen\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location melchtal_waterfall = new Location(
                "Melchtal Waterfall",
                "Du bist jetzt am \"Melchtal Waterfall\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location honegg = new Location(
                "Honegg",
                "Du bist jetzt in \"Honegg\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location alpnach = new Location(
                "Alpnach",
                "Du bist jetzt in \"Alpnach\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location kerns = new Location(
                "Kerns",
                "Du bist jetzt in \"Kerns\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location stalden_sarnen = new Location(
                "Stalden (Sarnen)",
                "Du bist jetzt in \"Stalden (Sarnen)\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location aareschlucht_meiringen = new Location(
                "Aareschlucht (Meiringen)",
                "Du bist jetzt an der \"Aareschlucht (Meiringen)\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location maegisalp = new Location(
                "Mägisalp",
                "Du bist jetzt auf der \"Mägisalp\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location brienzwiler = new Location(
                "Brienzwiler",
                "Du bist jetzt in \"Brienzwiler\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location balm_bei_meiringen = new Location(
                "Balm bei Meiringen",
                "Du bist jetzt in \"Balm bei Meiringen\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location berghaus_schoenbueel = new Location(
                "Berghaus Schönbüel",
                "Du bist jetzt im \"Berghaus Schönbüel\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location giglistock = new Location(
                "Giglistock",
                "Du bist jetzt am \"Giglistock\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location engelberg = new Location(
                "Engelberg",
                "Du bist jetzt in \"Engelberg\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location silenen = new Location(
                "Silenen",
                "Du bist jetzt in \"Silenen\" im Kanton \"Uri\" oder kurz \"UR\"."
        );

        Location susten_passhoehe = new Location(
                "Susten Passhöhe",
                "Du bist jetzt auf der \"Susten Passhöhe\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location bannalpsee = new Location(
                "Bannalpsee",
                "Du bist jetzt am \"Bannalpsee\" im Kanton \"Nidwalden\" oder kurz \"NW\"."
        );

        Location erstfelder_tal = new Location(
                "Erstfelder Tal",
                "Du bist jetzt im \"Erstfelder Tal\" im Kanton \"Uri\" oder kurz \"UR\"."
        );

        Location sportcamp_melchtal = new Location(
                "Sportcamp Melchtal",
                "Du bist jetzt im \"Sportcamp Melchtal\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location teufibach = new Location(
                "Teufibach",
                "Du bist jetzt am \"Teufibach\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location eugenisee = new Location(
                "Eugenisee",
                "Du bist jetzt am \"Eugenisee\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location berghotel_langis = new Location(
                "Berghotel Langis",
                "Du bist jetzt im \"Berghotel Langis\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location gipfelkreuz_giswilerstock = new Location(
                "Gipfelkreuz Giswilerstock",
                "Du bist jetzt am \"Gipfelkreuz Giswilerstock\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location chessiloch_fluehli = new Location(
                "Chessiloch Flühli",
                "Du bist jetzt im \"Chessiloch Flühli\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location hergiswil = new Location(
                "Hergiswil",
                "Du bist jetzt in \"Hergiswil\" im Kanton \"Nidwalden\" oder kurz \"NW\"."
        );

        Location oberdorf = new Location(
                "Oberdorf",
                "Du bist jetzt in \"Oberdorf\" im Kanton \"Nidwalden\" oder kurz \"NW\"."
        );

        Location regionalmuseum_chueechlihus = new Location(
                "Regionalmuseum Chüechlihus",
                "Du bist jetzt im \"Regionalmuseum Chüechlihus\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location luzern = new Location(
                "Luzern",
                "Du bist jetzt in \"Luzern\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location glogghues = new Location(
                "Glogghüs",
                "Du bist jetzt im \"Glogghüs\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location altdorf = new Location(
                "Altdorf",
                "Du bist jetzt in \"Altdorf\" im Kanton \"Uri\" oder kurz \"UR\"."
        );

        Location sempachersee = new Location(
                "Sempachersee",
                "Du bist jetzt am \"Sempachersee\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location rickhubel = new Location(
                "Rickhubel",
                "Du bist jetzt in \"Rickhubel\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location wilen_sarnen = new Location(
                "Wilen (Sarnen)",
                "Du bist jetzt in \"Wilen (Sarnen)\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location reichenbachfall = new Location(
                "Reichenbachfall",
                "Du bist jetzt am \"Reichenbachfall\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location hochstollen = new Location(
                "Hochstollen",
                "Du bist jetzt auf dem \"Hochstollen\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location taelli_klettersteig = new Location(
                "Tälli Klettersteig",
                "Du bist jetzt am \"Tälli Klettersteig\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location innertkirchen = new Location(
                "Innertkirchen",
                "Du bist jetzt in \"Innertkirchen\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location oltschiburg = new Location(
                "Oltschiburg",
                "Du bist jetzt auf der \"Oltschiburg\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location brienz = new Location(
                "Brienz",
                "Du bist jetzt in \"Brienz\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location gletscherschlucht_rosenlaui = new Location(
                "Gletscherschlucht Rosenlaui",
                "Du bist jetzt in der \"Gletscherschlucht Rosenlaui\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location brienzersee = new Location(
                "Brienzersee",
                "Du bist jetzt beim \"Brienzersee\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location panoramastrasse_glaubenbielen = new Location(
                "Panoramastrasse Glaubenbielen",
                "Du bist jetzt bei der \"Panoramastrasse Glaubenbielen\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location brienzer_rothorn = new Location(
                "Brienzer Rothorn",
                "Du bist jetzt beim \"Brienzer Rothorn\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location rhonegletscher = new Location(
                "Rhonegletscher",
                "Du bist jetzt beim \"Rhonegletscher\" im Kanton \"Wallis\" oder kurz \"VS\"."
        );

        Location wassen = new Location(
                "Wassen",
                "Du bist jetzt in \"Wassen\" im Kanton \"Uri\" oder kurz \"UR\"."
        );

        Location dallenwil = new Location(
                "Dallenwil",
                "Du bist jetzt in \"Dallenwil\" im Kanton \"Nidwalden\" oder kurz \"NW\"."
        );

        Location erstfeld = new Location(
                "Erstfeld",
                "Du bist jetzt in \"Erstfeld\" im Kanton \"Uri\" oder kurz \"UR\"."
        );

        Location laax = new Location(
                "Laax",
                "Du bist jetzt in \"Laax\" im Kanton \"Graubünden\" oder kurz \"GR\"."
        );

        Location realp = new Location(
                "Realp",
                "Du bist jetzt in \"Realp\" im Kanton \"Uri\" oder kurz \"UR\"."
        );

        Location gschletterkurve = new Location(
                "Gletterkurve",
                "Du bist jetzt bei der Gschletterkurve im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location gurtnellen = new Location(
                "Gurtnellen",
                "Du bist jetzt in \"Gurtnellen\" im Kanton \"Uri\" oder kurz \"UR\"."
        );

        Location rhonestock = new Location(
                "Rhonestock",
                "Du bist jetzt beim \"Rhonestock\" im Kanton \"Wallis\" oder kurz \"VS\"."
        );


        /*
         * ========================================================
         * VERBINDUNGEN
         * ========================================================
         */

        // Von Älggialp
        aelggialp.setTwoWayExit(Direction.NORTH, rufi, 450.0);
        aelggialp.setTwoWayExit(Direction.SOUTH, hasliberg, 1200.0);
        aelggialp.setTwoWayExit(Direction.WEST, lungern, 1050.0);
        aelggialp.setTwoWayExit(Direction.EAST, truebsee, 1500.0);

        // Von Rufi
        rufi.setTwoWayExit(Direction.NORTH, giswil, 900.0);
        rufi.setTwoWayExit(Direction.SOUTH, aelggialp, 450.0);
        rufi.setTwoWayExit(Direction.WEST, lungern, 1200.0);
        rufi.setTwoWayExit(Direction.EAST, alpenresort_eienwaeldli, 1950.0);

        // Von Giswil
        giswil.setTwoWayExit(Direction.NORTH, sarnen, 1500.0);
        giswil.setTwoWayExit(Direction.SOUTH, rufi, 900.0);
        giswil.setTwoWayExit(Direction.EAST, melchtal_waterfall, 900.0);
        giswil.setTwoWayExit(Direction.WEST, honegg, 450.0);

        // Von Sarnen
        sarnen.setTwoWayExit(Direction.SOUTH, giswil, 1500.0);
        sarnen.setTwoWayExit(Direction.NORTH, alpnach, 750.0);
        sarnen.setTwoWayExit(Direction.EAST, kerns, 450.0);
        sarnen.setTwoWayExit(Direction.WEST, stalden_sarnen, 570.0);

        // Von Hasliberg
        hasliberg.setTwoWayExit(Direction.NORTH, aelggialp, 1200.0);
        hasliberg.setTwoWayExit(Direction.SOUTH, aareschlucht_meiringen, 690.0);
        hasliberg.setTwoWayExit(Direction.EAST, maegisalp, 600.0);
        hasliberg.setTwoWayExit(Direction.WEST, brienzwiler, 1050.0);

        // Von Lungern
        lungern.setTwoWayExit(Direction.EAST, aelggialp, 1050.0);
        lungern.setTwoWayExit(Direction.NORTH, giswil, 900.0);
        lungern.setTwoWayExit(Direction.SOUTH, balm_bei_meiringen, 900.0);
        lungern.setTwoWayExit(Direction.WEST, berghaus_schoenbueel, 750.0);

        // Von Trübsee
        truebsee.setTwoWayExit(Direction.WEST, aelggialp, 1500.0);
        truebsee.setTwoWayExit(Direction.SOUTH, giglistock, 1500.0);
        truebsee.setTwoWayExit(Direction.NORTH, engelberg, 600.0);
        truebsee.setTwoWayExit(Direction.EAST, silenen, 2850.0);

        // Von Alpenresort Eienwäldli
        alpenresort_eienwaeldli.setTwoWayExit(Direction.WEST, rufi, 1950.0);
        alpenresort_eienwaeldli.setTwoWayExit(Direction.SOUTH, susten_passhoehe, 2850.0);
        alpenresort_eienwaeldli.setTwoWayExit(Direction.NORTH, bannalpsee, 900.0);
        alpenresort_eienwaeldli.setTwoWayExit(Direction.EAST, erstfelder_tal, 1500.0);

        // Vom Melchtal Waterfall
        melchtal_waterfall.setTwoWayExit(Direction.WEST, giswil, 900.0);
        melchtal_waterfall.setTwoWayExit(Direction.SOUTH, sportcamp_melchtal, 240.0);
        melchtal_waterfall.setTwoWayExit(Direction.NORTH, teufibach, 390.0);
        melchtal_waterfall.setTwoWayExit(Direction.EAST, eugenisee, 1290.0);

        // Von Honegg
        honegg.setTwoWayExit(Direction.EAST, giswil, 450.0);
        honegg.setTwoWayExit(Direction.NORTH, berghotel_langis, 3150.0);
        honegg.setTwoWayExit(Direction.SOUTH, gipfelkreuz_giswilerstock, 3900.0);
        honegg.setTwoWayExit(Direction.WEST, chessiloch_fluehli, 4050.0);

        // Von Alpnach
        alpnach.setTwoWayExit(Direction.SOUTH, sarnen, 750.0);
        alpnach.setTwoWayExit(Direction.NORTH, hergiswil, 810.0);
        alpnach.setTwoWayExit(Direction.EAST, oberdorf, 1200.0);
        alpnach.setTwoWayExit(Direction.WEST, regionalmuseum_chueechlihus, 4950.0);

        // Von Kerns
        kerns.setTwoWayExit(Direction.WEST, sarnen, 450.0);
        kerns.setTwoWayExit(Direction.NORTH, luzern, 2100.0);
        kerns.setTwoWayExit(Direction.SOUTH, glogghues, 1800.0);
        kerns.setTwoWayExit(Direction.EAST, altdorf, 2550.0);

        // Von Stalden
        stalden_sarnen.setTwoWayExit(Direction.EAST, sarnen, 570.0);
        stalden_sarnen.setTwoWayExit(Direction.NORTH, sempachersee, 4050.0);
        stalden_sarnen.setTwoWayExit(Direction.WEST, rickhubel, 1200.0);
        stalden_sarnen.setTwoWayExit(Direction.SOUTH, wilen_sarnen, 240.0);

        // Von Aareschlucht
        aareschlucht_meiringen.setTwoWayExit(Direction.NORTH, hasliberg, 690.0);
        aareschlucht_meiringen.setTwoWayExit(Direction.SOUTH, reichenbachfall, 180.0);
        aareschlucht_meiringen.setTwoWayExit(Direction.WEST, balm_bei_meiringen, 390.0);
        aareschlucht_meiringen.setTwoWayExit(Direction.EAST, giglistock, 1800.0);

        // Von Mägisalp
        maegisalp.setTwoWayExit(Direction.WEST, hasliberg, 600.0);
        maegisalp.setTwoWayExit(Direction.NORTH, hochstollen, 390.0);
        maegisalp.setTwoWayExit(Direction.EAST, taelli_klettersteig, 900.0);
        maegisalp.setTwoWayExit(Direction.SOUTH, innertkirchen, 600.0);

        // Von Brienzwiler
        brienzwiler.setTwoWayExit(Direction.EAST, hasliberg, 1050.0);
        brienzwiler.setTwoWayExit(Direction.NORTH, berghaus_schoenbueel, 600.0);
        brienzwiler.setTwoWayExit(Direction.SOUTH, oltschiburg, 480.0);
        brienzwiler.setTwoWayExit(Direction.WEST, brienz, 600.0);

        // Von Balm bei Meiringen
        balm_bei_meiringen.setTwoWayExit(Direction.NORTH, lungern, 900.0);
        balm_bei_meiringen.setTwoWayExit(Direction.SOUTH, gletscherschlucht_rosenlaui, 840.0);
        balm_bei_meiringen.setTwoWayExit(Direction.EAST, aareschlucht_meiringen, 390.0);
        balm_bei_meiringen.setTwoWayExit(Direction.WEST, brienzersee, 540.0);

        // Vom Berghaus Schönbüel
        berghaus_schoenbueel.setTwoWayExit(Direction.EAST, lungern, 750.0);
        berghaus_schoenbueel.setTwoWayExit(Direction.NORTH, panoramastrasse_glaubenbielen, 600.0);
        berghaus_schoenbueel.setTwoWayExit(Direction.WEST, brienzer_rothorn, 510.0);
        berghaus_schoenbueel.setTwoWayExit(Direction.SOUTH, brienzwiler, 600.0);

        // Vom Giglistock
        giglistock.setTwoWayExit(Direction.NORTH, truebsee, 1500.0);
        giglistock.setTwoWayExit(Direction.WEST, aareschlucht_meiringen, 1800.0);
        giglistock.setTwoWayExit(Direction.SOUTH, rhonegletscher, 1290.0);
        giglistock.setTwoWayExit(Direction.EAST, wassen, 1500.0);

        // Von Engelberg
        engelberg.setTwoWayExit(Direction.SOUTH, truebsee, 600.0);
        engelberg.setTwoWayExit(Direction.NORTH, dallenwil, 1020.0);
        engelberg.setTwoWayExit(Direction.WEST, gipfelkreuz_giswilerstock, 1800.0);
        engelberg.setTwoWayExit(Direction.EAST, erstfeld, 1500.0);

        // Von Silenen
        silenen.setTwoWayExit(Direction.WEST, truebsee, 2850.0);
        silenen.setTwoWayExit(Direction.EAST, laax, 3600.0);
        silenen.setTwoWayExit(Direction.NORTH, erstfeld, 360.0);
        silenen.setTwoWayExit(Direction.SOUTH, wassen, 900.0);

        // Von Susten Passhöhe
        susten_passhoehe.setTwoWayExit(Direction.NORTH, alpenresort_eienwaeldli, 2850.0);
        susten_passhoehe.setTwoWayExit(Direction.SOUTH, realp, 1500.0);
        susten_passhoehe.setTwoWayExit(Direction.WEST, gschletterkurve, 600.0);
        susten_passhoehe.setTwoWayExit(Direction.EAST, gurtnellen, 1290.0);

        // Vom Bannalpsee
        bannalpsee.setTwoWayExit(Direction.NORTH, alpenresort_eienwaeldli, 900.0);
        bannalpsee.setTwoWayExit(Direction.SOUTH, rhonestock, 2250.0);

        this.currentLocation = aelggialp;
    }


    /*
     * ============================================================
     * ZEITSYSTEM
     * ============================================================
     */

    private long getAbsoluteGameSeconds() {
        return START_HOUR * 3600L + (long) timeElapsed;
    }

    private int getCurrentDay() {
        return (int) (getAbsoluteGameSeconds() / 86400L) + 1;
    }

    private int getCurrentHour() {
        return (int) ((getAbsoluteGameSeconds() / 3600L) % 24);
    }

    private int getCurrentMinute() {
        return (int) ((getAbsoluteGameSeconds() / 60L) % 60);
    }

    private int getCurrentSecond() {
        return (int) (getAbsoluteGameSeconds() % 60);
    }

    private String getDayPhase() {
        int hour = getCurrentHour();

        if (hour >= 5 && hour < 8) {
            return "🌅 Morgen";
        }

        if (hour >= 8 && hour < 18) {
            return "☀️ Tag";
        }

        if (hour >= 18 && hour < 21) {
            return "🌆 Abend";
        }

        return "🌙 Nacht";
    }

    private boolean isNight() {
        int hour = getCurrentHour();
        return hour >= 21 || hour < 5;
    }

    private void printGameTime() {
        System.out.printf(
                "Zeit: Tag %d | %02d:%02d:%02d | %s%n",
                getCurrentDay(),
                getCurrentHour(),
                getCurrentMinute(),
                getCurrentSecond(),
                getDayPhase()
        );
    }

    private String formatRemainingTime(double seconds) {
        long totalSeconds = Math.max(0, (long) seconds);

        long days = totalSeconds / 86400;
        totalSeconds %= 86400;

        long hours = totalSeconds / 3600;
        totalSeconds %= 3600;

        long minutes = totalSeconds / 60;
        long secs = totalSeconds % 60;

        return String.format(
                "%d Tage, %02d:%02d:%02d",
                days,
                hours,
                minutes,
                secs
        );
    }

    private String formatDuration(double seconds) {
        long totalSeconds = Math.max(0, Math.round(seconds));

        long hours = totalSeconds / 3600;
        totalSeconds %= 3600;

        long minutes = totalSeconds / 60;
        long secs = totalSeconds % 60;

        if (hours > 0) {
            return String.format(
                    "%d Stunden, %d Minuten und %d Sekunden",
                    hours,
                    minutes,
                    secs
            );
        }

        if (minutes > 0) {
            return String.format(
                    "%d Minuten und %d Sekunden",
                    minutes,
                    secs
            );
        }

        return secs + " Sekunden";
    }


    /*
     * ============================================================
     * WETTERSYSTEM
     * ============================================================
     */

    private void updateWeather() {

        /*
         * Falls durch Schlafen oder eine lange Reise mehrere
         * Wetterwechsel übersprungen wurden, holen wir sie hier nach.
         */
        while (timeElapsed >= nextWeatherChange) {

            Weather oldWeather = currentWeather;

            currentWeather = getNextWeather(currentWeather);

            // Nächster Wechsel nach 2 bis 6 Stunden
            int hoursUntilNextChange =
                    2 + random.nextInt(5);

            nextWeatherChange +=
                    hoursUntilNextChange * 3600.0;

            if (oldWeather != currentWeather) {
                System.out.println();
                System.out.println(
                        "🌤️ Das Wetter hat sich geändert:"
                );

                System.out.println(
                        oldWeather.getDisplayName()
                                + " → "
                                + currentWeather.getDisplayName()
                );
            }
        }
    }

    /**
     * Das Wetter springt nicht komplett zufällig herum.
     * Stattdessen hängt das nächste Wetter vom aktuellen Wetter ab.
     */
    private Weather getNextWeather(Weather weather) {

        int roll = random.nextInt(100);

        switch (weather) {

            case CLEAR:
                if (roll < 55) {
                    return Weather.CLEAR;
                }

                if (roll < 85) {
                    return Weather.CLOUDY;
                }

                if (roll < 95) {
                    return Weather.FOG;
                }

                return Weather.RAIN;


            case CLOUDY:
                if (roll < 25) {
                    return Weather.CLEAR;
                }

                if (roll < 60) {
                    return Weather.CLOUDY;
                }

                if (roll < 80) {
                    return Weather.RAIN;
                }

                if (roll < 93) {
                    return Weather.FOG;
                }

                return Weather.STRONG_WIND;


            case RAIN:
                if (roll < 15) {
                    return Weather.CLEAR;
                }

                if (roll < 45) {
                    return Weather.CLOUDY;
                }

                if (roll < 75) {
                    return Weather.RAIN;
                }

                if (roll < 90) {
                    return Weather.STRONG_WIND;
                }

                return Weather.THUNDERSTORM;


            case FOG:
                if (roll < 20) {
                    return Weather.CLEAR;
                }

                if (roll < 55) {
                    return Weather.CLOUDY;
                }

                if (roll < 85) {
                    return Weather.FOG;
                }

                return Weather.RAIN;


            case STRONG_WIND:
                if (roll < 15) {
                    return Weather.CLEAR;
                }

                if (roll < 45) {
                    return Weather.CLOUDY;
                }

                if (roll < 65) {
                    return Weather.RAIN;
                }

                if (roll < 90) {
                    return Weather.STRONG_WIND;
                }

                return Weather.THUNDERSTORM;


            case THUNDERSTORM:
                if (roll < 45) {
                    return Weather.RAIN;
                }

                if (roll < 70) {
                    return Weather.CLOUDY;
                }

                if (roll < 90) {
                    return Weather.STRONG_WIND;
                }

                return Weather.THUNDERSTORM;


            default:
                return Weather.CLEAR;
        }
    }

    private void printWeather() {
        System.out.println(
                "Wetter: "
                        + currentWeather.getDisplayName()
        );

        if (currentWeather == Weather.RAIN) {
            System.out.println(
                    "🌧️ Durch den Regen dauern Reisen 15 % länger."
            );
        } else if (currentWeather == Weather.FOG) {
            System.out.println(
                    "🌫️ Durch den Nebel dauern Reisen 25 % länger."
            );
        } else if (currentWeather == Weather.STRONG_WIND) {
            System.out.println(
                    "💨 Durch den starken Wind dauern Reisen 40 % länger."
            );
        } else if (currentWeather == Weather.THUNDERSTORM) {
            System.out.println(
                    "⛈️ Bei diesem Gewitter kannst du nicht mit dem Heissluftballon fliegen."
            );
        }
    }


    /*
     * ============================================================
     * SCHLAFEN / WARTEN
     * ============================================================
     */

    private void sleepUntilMorning() {

        long currentSecondsOfDay =
                getCurrentHour() * 3600L
                        + getCurrentMinute() * 60L
                        + getCurrentSecond();

        long targetSeconds =
                6 * 3600L;

        long sleepSeconds;

        if (currentSecondsOfDay < targetSeconds) {

            sleepSeconds =
                    targetSeconds
                            - currentSecondsOfDay;

        } else {

            sleepSeconds =
                    (24 * 3600L - currentSecondsOfDay)
                            + targetSeconds;
        }

        double remainingTime =
                startTime - timeElapsed;

        if (sleepSeconds >= remainingTime) {

            timeElapsed = startTime;

            System.out.println();
            System.out.println(
                    "🛏️ Du legst dich schlafen..."
            );

            System.out.println(
                    "Während du schläfst, läuft deine verbleibende Zeit ab."
            );

            return;
        }

        System.out.println();
        System.out.println(
                "🛏️ Du legst dich schlafen..."
        );

        timeElapsed += sleepSeconds;

        System.out.println(
                "Du hast "
                        + formatDuration(sleepSeconds)
                        + " geschlafen."
        );

        updateWeather();

        System.out.printf(
                "🌅 Du wachst an Tag %d um %02d:%02d Uhr auf.%n",
                getCurrentDay(),
                getCurrentHour(),
                getCurrentMinute()
        );

        System.out.println(
                "Wetter: "
                        + currentWeather.getDisplayName()
        );
    }

    private void waitThirtyMinutes() {

        double remainingTime =
                startTime - timeElapsed;

        if (WAIT_TIME >= remainingTime) {

            timeElapsed = startTime;

            System.out.println();
            System.out.println(
                    "⏳ Du wartest..."
            );

            System.out.println(
                    "Während du wartest, läuft deine verbleibende Zeit ab."
            );

            return;
        }

        String oldDayPhase =
                getDayPhase();

        int oldDay =
                getCurrentDay();

        System.out.println();
        System.out.println(
                "⏳ Du wartest 30 Minuten."
        );

        timeElapsed += WAIT_TIME;

        updateWeather();

        if (getCurrentDay() > oldDay) {
            System.out.printf(
                    "🌅 Tag %d hat begonnen!%n",
                    getCurrentDay()
            );
        }

        if (!oldDayPhase.equals(getDayPhase())) {
            System.out.println(
                    "Die Tageszeit hat sich geändert: "
                            + oldDayPhase
                            + " → "
                            + getDayPhase()
            );
        }

        System.out.printf(
                "Es ist jetzt %02d:%02d Uhr.%n",
                getCurrentHour(),
                getCurrentMinute()
        );

        System.out.println(
                "Wetter: "
                        + currentWeather.getDisplayName()
        );
    }


    /*
     * ============================================================
     * SPIEL
     * ============================================================
     */

    public void start() {

        Scanner scanner =
                new Scanner(System.in);

        Console console =
                System.console();

        System.out.println(
                "Hallo und willkommen bei \"Escape from Switzerland\"!"
        );

        if (console != null) {

            String eingabe =
                    console.readLine(
                            "Gib einen Namen ein, um fortzufahren: "
                    );

            System.out.println(
                    "Willkommen: " + eingabe
            );
        }

        System.out.println();

        System.out.println(
                "Deine Mission ist es, aus der Schweiz zu fliehen, bevor die Zeit abläuft!"
        );

        System.out.println(
                "Du nimmst extra einen Heissluftballon, damit du schneller unterwegs bist."
        );

        System.out.println(
                "Du startest auf der \"Älggialp\" im Kanton \"Obwalden\"."
        );

        System.out.println(
                "Du hast 10 Tage Zeit, um aus der Schweiz herauszukommen."
        );

        System.out.println();

        System.out.println(
                "Das Spiel beginnt an Tag 1 um 06:00 Uhr."
        );

        System.out.println(
                "Während deiner Reisen vergeht die Zeit."
        );

        System.out.println(
                "Nachts dauern Reisen 20 % länger."
        );

        System.out.println(
                "Das Wetter kann deine Reise zusätzlich verlangsamen."
        );

        System.out.println(
                "Bei Gewitter kannst du nicht fliegen."
        );

        System.out.println(
                "Mit \"schlafen\" schläfst du bis 06:00 Uhr."
        );

        System.out.println(
                "Mit \"warten\" wartest du 30 Minuten."
        );


        List<String> validStart =
                Arrays.asList("start");

        boolean start = false;

        while (!start) {

            System.out.print(
                    "\nTippe \"Start\" oder \"start\" ein, um zu beginnen: "
            );

            String answer =
                    scanner.nextLine()
                            .trim()
                            .toLowerCase();

            if (validStart.contains(answer)) {

                start = true;

            } else {

                System.out.println(
                        "Ungültige Eingabe!"
                );
            }
        }


        /*
         * ========================================================
         * HAUPTSCHLEIFE
         * ========================================================
         */

        while (startTime - timeElapsed > 0) {

            updateWeather();

            double remainingTime =
                    startTime - timeElapsed;

            System.out.println();
            System.out.println(
                    "================================================"
            );

            System.out.println(
                    "📍 Ort: "
                            + currentLocation.getName()
            );

            System.out.println(
                    currentLocation.getDescription()
            );

            System.out.println();

            printGameTime();

            printWeather();

            System.out.println(
                    "⏳ Verbleibende Zeit: "
                            + formatRemainingTime(remainingTime)
            );

            if (isNight()) {
                System.out.println(
                        "🌙 Reisen dauern wegen der Dunkelheit 20 % länger."
                );
            }

            System.out.println();

            System.out.print(
                    "Aktion (N/S/O/W, schlafen, warten oder exit): "
            );

            String input =
                    scanner.nextLine()
                            .trim()
                            .toLowerCase();


            /*
             * ====================================================
             * BEENDEN
             * ====================================================
             */

            if (input.equals("exit")
                    || input.equals("q")) {

                System.out.println(
                        "Spiel vorbei."
                );

                break;
            }


            /*
             * ====================================================
             * SCHLAFEN
             * ====================================================
             */

            if (input.equals("schlafen")
                    || input.equals("sleep")) {

                sleepUntilMorning();

                continue;
            }


            /*
             * ====================================================
             * WARTEN
             * ====================================================
             */

            if (input.equals("warten")
                    || input.equals("wait")) {

                waitThirtyMinutes();

                continue;
            }


            /*
             * ====================================================
             * RICHTUNG
             * ====================================================
             */

            Direction dir =
                    Direction.parse(input);

            if (dir == null) {

                System.out.println(
                        "Ungültige Eingabe!"
                );

                System.out.println(
                        "Benutze N, S, O, W, schlafen, warten oder exit."
                );

                continue;
            }


            Location next =
                    currentLocation.getExit(dir);

            if (next == null) {

                System.out.println(
                        "Du kannst nicht in diese Richtung gehen!"
                );

                continue;
            }


            /*
             * ====================================================
             * GEWITTER
             * ====================================================
             */

            if (!currentWeather.isTravelAllowed()) {

                System.out.println();
                System.out.println(
                        "⛈️ Das Gewitter ist zu gefährlich!"
                );

                System.out.println(
                        "Du kannst mit dem Heissluftballon momentan nicht starten."
                );

                System.out.println(
                        "Benutze \"warten\" oder \"schlafen\"."
                );

                continue;
            }


            /*
             * ====================================================
             * REISE BERECHNEN
             * ====================================================
             */

            String oldDayPhase =
                    getDayPhase();

            int oldDay =
                    getCurrentDay();

            int oldHour =
                    getCurrentHour();

            int oldMinute =
                    getCurrentMinute();


            double baseTravelTime =
                    currentLocation.getTravelTime(dir);

            double travelTime =
                    baseTravelTime;


            /*
             * Wetter-Multiplikator
             */
            double weatherMultiplier =
                    currentWeather.getTravelMultiplier();

            travelTime *=
                    weatherMultiplier;


            /*
             * Nacht-Multiplikator
             */
            boolean travellingAtNight =
                    isNight();

            if (travellingAtNight) {

                travelTime *=
                        NIGHT_TRAVEL_MULTIPLIER;
            }


            /*
             * ====================================================
             * REISEINFORMATIONEN
             * ====================================================
             */

            System.out.println();

            System.out.printf(
                    "🎈 Du bereitest den Flug von %s nach %s vor.%n",
                    currentLocation.getName(),
                    next.getName()
            );

            System.out.println(
                    "Wetter: "
                            + currentWeather.getDisplayName()
            );

            System.out.println(
                    "Normale Reisezeit: "
                            + formatDuration(baseTravelTime)
            );


            if (weatherMultiplier > 1.0) {

                int weatherPercent =
                        (int) Math.round(
                                (weatherMultiplier - 1.0)
                                        * 100
                        );

                System.out.println(
                        "Wetter-Malus: +"
                                + weatherPercent
                                + " %"
                );
            }


            if (travellingAtNight) {

                System.out.println(
                        "Nacht-Malus: +20 %"
                );
            }


            System.out.println(
                    "Tatsächliche Reisezeit: "
                            + formatDuration(travelTime)
            );


            /*
             * ====================================================
             * GENUG ZEIT?
             * ====================================================
             */

            remainingTime =
                    startTime - timeElapsed;

            if (travelTime >= remainingTime) {

                System.out.println();

                System.out.println(
                        "🎈 Du startest deine Reise..."
                );

                timeElapsed =
                        startTime;

                System.out.println(
                        "Doch während des Fluges läuft deine verbleibende Zeit ab."
                );

                break;
            }


            /*
             * ====================================================
             * REISE DURCHFÜHREN
             * ====================================================
             */

            System.out.println();

            System.out.println(
                    "🎈 Du startest..."
            );

            timeElapsed +=
                    travelTime;

            currentLocation =
                    next;


            /*
             * Wetter nach vergangener Zeit aktualisieren
             */
            updateWeather();


            System.out.println();

            System.out.printf(
                    "📍 Du bist in %s angekommen.%n",
                    currentLocation.getName()
            );

            System.out.println(
                    "Die Reise hat "
                            + formatDuration(travelTime)
                            + " gedauert."
            );


            /*
             * ====================================================
             * TAG GEWECHSELT?
             * ====================================================
             */

            if (getCurrentDay() > oldDay) {

                System.out.println();

                System.out.printf(
                        "🌅 Tag %d hat begonnen!%n",
                        getCurrentDay()
                );
            }


            /*
             * ====================================================
             * TAGESZEIT GEWECHSELT?
             * ====================================================
             */

            String newDayPhase =
                    getDayPhase();

            if (!oldDayPhase.equals(newDayPhase)) {

                System.out.println();

                System.out.println(
                        "Die Tageszeit hat sich während deiner Reise geändert:"
                );

                System.out.println(
                        oldDayPhase
                                + " → "
                                + newDayPhase
                );
            }


            /*
             * ====================================================
             * ANKUNFTSZEIT
             * ====================================================
             */

            System.out.println();

            System.out.printf(
                    "Abfahrt: %02d:%02d Uhr%n",
                    oldHour,
                    oldMinute
            );

            System.out.printf(
                    "Ankunft: %02d:%02d Uhr%n",
                    getCurrentHour(),
                    getCurrentMinute()
            );

            System.out.println(
                    "Wetter bei Ankunft: "
                            + currentWeather.getDisplayName()
            );
        }


        /*
         * ========================================================
         * GAME OVER
         * ========================================================
         */

        if (startTime - timeElapsed <= 0) {

            System.out.println();
            System.out.println(
                    "================================================"
            );

            System.out.println(
                    "⏰ Die Zeit ist abgelaufen!"
            );

            System.out.println(
                    "Du hast es nicht rechtzeitig geschafft, aus der Schweiz zu fliehen."
            );

            System.out.println();

            System.out.println(
                    "GAME OVER"
            );

            System.out.println(
                    "================================================"
            );
        }

        scanner.close();
    }
}