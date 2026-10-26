package com.freetime.efs;

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

    private final double startTime = GAME_DURATION;
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
        CLEAR("Klar", 1.00, true),

        CLOUDY("Bewölkt", 1.00, true),

        RAIN("Regen", 1.15, true),

        FOG("Nebel", 1.25, true),

        STRONG_WIND("Starker Wind", 1.40, true),

        THUNDERSTORM("Gewitter", 1.00, false);

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

    private static double minutes(double minutes) {
        return minutes * 60;
    }

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

        Location langnau_im_emmental = new Location(
            "Langnau im Emmental",
            "Du bist jetzt in \"Langnau im Emmental\" im Kanton \"Luzern\" oder kurz \"LU\"."
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

        Location attinghausen = new Location(
            "Attinghausen",
            "Du bist jetzt in \"Attinghausen\" im Kanton \"Uri\" oder kurz \"UR\"."
        );

        Location altzellen = new Location(
            "Altzellen",
            "Du bist jetzt in \"Altzellen\" im Kanton \"Nidwalden\" oder kurz \"NW\"."
        );

        Location seewlialp = new Location(
            "Seewlialp",
            "Du bist jetzt bei der \"Seewlialp\" im Kanton \"Uri\" oder kurz \"UR\"."
        );

        Location seelisberg = new Location(
            "Seelisberg",
            "Du bist jetzt in \"Seelisberg\" im Kanton \"Uri\" oder kurz \"UR\"."
        );

        Location hospental = new Location(
            "Hospental",
            "Du bist jetzt in \"Hospental\" im Kanton \"Uri\" oder kurz \"UR\"."
        );

        Location melchsee = new Location(
            "Melchsee",
            "Du bist jetzt beim \"Melchsee\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location limmerensee = new Location(
            "Limmerensee",
            "Du bist jetzt beim \"Limmerensee\" im Kanton \"Glasrus\" oder kurz \"GL\"."
        );

        Location unesco_biospaehre_entlebuch = new Location(
            "UNESCO Biospähre Entlebuch",
            "Du bist jetzt bei der \"UNESCO Biosphäre Entlebuch\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location kriens = new Location(
            "Kriens",
            "Du bist jetzt in \"Kriens\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location heftihuette = new Location(
            "Heftihütte",
            "Du bist jetzt bei der \"Hefthütte\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location berghaus_klondike = new Location(
            "Berghaus Klondike",
            "Du bist jetzt beim \"Berghaus Klondike\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location panixersee = new Location(
            "Panixersee",
            "Du bist jetzt beim \"Panixersee\" im Kanton \"Graubünden\" oder kurz \"GR\"."
        );

        Location truebseebach_wasserfall = new Location(
            "Trübseebach Wasserfall",
            "Du bist jetzt beim \"Trübseebach Wasserfall\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location eugenibach = new Location(
            "Eugenibach",
            "Du bist jetzt beim \"Eugenibach\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location schachen = new Location(
            "Schachen",
            "Du bist jetzt in \"Schachen\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location fluehli = new Location(
            "Flühli",
            "Du bist jetzt in \"Flühli\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location wolfenschiessen = new Location(
            "Wolfenschiessen",
            "Du bist jetzt in \"Wolfenschiessen\" im Kanton \"Nidwalden\" oder kurz \"NW\"."
        );

        Location soerenberg = new Location(
            "Sörenberg",
            "Du bist jetzt in \"Sörenberg\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location wetterhorn = new Location(
            "Wetterhorn",
            "Du bist jetzt beim \"Wetterhorn\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location hohwald = new Location(
            "Hohwald",
            "Du bist jetzt in \"Hohwald\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location menznau = new Location(
            "Menznau",
            "Du bist jetzt in \"Menznau\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location horw = new Location(
            "Horw",
            "Du bist jetzt in \"Horw\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location entlebuch = new Location(
            "Entlebuch",
            "Du bist jetzt in \"Entlebuch\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location gersau = new Location(
            "Gersau",
            "Du bist jetzt in \"Gersau\" im Kanton \"Schwyz\" oder kurz \"SZ\"."
        );

        Location meggen = new Location(
            "Meggen",
            "Du bist jetzt in \"Meggen\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location emmetten = new Location(
            "Emmetten",
            "Du bist jetzt in \"Emmetten\" im Kanton \"Nidwalden\" oder kurz \"NW\"."
        );

        Location madiswil = new Location(
            "Madiswil",
            "Du bist jetzt in \"Madiswil\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location eggiwil = new Location(
            "Eggiwil",
            "Du bist jetzt in \"Eggiwil\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location worb = new Location(
            "Worb",
            "Du bist jetzt in \"Worb\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location hochdorf = new Location(
            "Hochdorf",
            "Du bist jetzt in \"Hochdorf\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location emmen = new Location(
            "Emmen",
            "Du bist jetzt in \"Emmen\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location burgdorf = new Location(
            "Burgdorf",
            "Du bist jetzt in \"Burgdorf\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location bruenig = new Location(
            "Brünig",
            "Du bist jetzt in \"Brünig\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location meiringen = new Location(
            "Meiringen",
            "Du bist jetzt in \"Meiringen\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location sustlihuette = new Location(
            "Sustlihütte",
            "Du bist jetzt in der \"Sustlihütte\" im Kanton \"Uri\" oder kurz \"UR\"."
        );

        Location laaxer_stock = new Location(
            "Laaxer Stock",
            "Du bist jetzt beim \"Laaxer Stock\" im Kanton \"Graubünden\" oder kurz \"GR\"."
        );

        Location flueelen = new Location(
            "Flüelen",
            "Du bist jetzt in \"Flüelen\" im Kanton \"Uri\" oder kurz \"UR\"."
        );

        Location schattdorf = new Location(
            "Schattdorf",
            "Du bist jetzt in \"Schattdorf\" im Kanton \"Uri\" oder kurz \"UR\"."
        );

        Location sursee = new Location(
            "Sursee",
            "Du bist jetzt in \"Sursee\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location willisau = new Location(
            "Willisau",
            "Du bist jetzt in \"Willisau\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location finsterwald_bei_entlebuch = new Location(
            "Finsterwald bei Entlebuch",
            "Du bist jetzt in \"Finsterwald bei Entlebuch\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location mettlen = new Location(
            "Mettlen",
            "Du bist jetzt in \"Mettlen\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location kugelweg_hasliberg = new Location(
            "Kugelweg Hasliberg",
            "Du bist jetzt beim \"Kugelweg Hasliberg\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location laetzengraben = new Location(
            "Lätzengrabben",
            "Du bist jetzt beim \"Lätzengrabben\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location st_niklausen = new Location(
            "St. Niklausen",
            "Du bist jetzt in \"St. Niklausen\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location rychenbach = new Location(
            "Rychenbach",
            "Du bist jetzt beim \"Rychenbach\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location kraftwerk_hopflauenen = new Location(
            "Kraftwerk Hopflauenen",
            "Du bist jetzt beim \"Kraftwerk Hopflauenen\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location axalphorn = new Location(
            "Axalphorn",
            "Du bist jetzt beim \"Axalphorn\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location sachseln = new Location(
            "Sachseln",
            "Du bist jetzt in \"Sachseln\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location gibel = new Location(
            "Gibel",
            "Du bist jetzt beim \"Gibel\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location triftbruecke = new Location(
            "Triftbrücke",
            "Du bist jetzt bei der \"Triftbrücke\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location engstlensee = new Location(
            "Engstlensee",
            "Du bist jetzt beim \"Engstlensee\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location bristensee = new Location(
            "Bristensee",
            "Du bist jetzt beim \"Bristensee\" im Kanton \"Uri\" oder kurz \"UR\"."
        );

        Location schattenhalb = new Location(
            "Schattenhalb",
            "Du bist jetzt in \"Schattenhalb\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location lueterenseewli = new Location(
            "Lüterenseewli",
            "Du bist jetzt beim \"Lüterenseewli\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location unterstock = new Location(
            "Unterstock",
            "Du bist jetzt in \"Unterstock\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location giessbachfaelle = new Location(
            "Giessbachfälle",
            "Du bist jetzt bei den \"Giessbachfällen\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location tschingel = new Location(
            "Tschingel",
            "Du bist jetzt in \"Tschingel\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location schwarzhorn = new Location(
            "Schwarzhorn",
            "Du bist jetzt beim \"Schwarzhorn\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location augstmatthorn = new Location(
            "Augstmatthorn",
            "Du bist jetzt beim \"Augstmatthorn\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location klein_wellhorn = new Location(
            "Klein Wellhorn",
            "Du bist jetzt beim \"Klein Wellhorn\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location hoch_horefellistock = new Location(
            "Hoch Horefellistock",
            "Du bist jetzt beim \"Hoch Horefellistock\" im Kanton \"Uri\" oder kurz \"UR\"."
        );

        Location schrattenfluh = new Location(
            "Schrattenfluh",
            "Du bist jetzt bei der \"Schrattenfluh\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location luetschental = new Location(
            "Lütschental",
            "Du bist jetzt im \"Lütschental\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location interlaken = new Location(
            "Interlaken",
            "Du bist jetzt in \"Interlaken\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location glaubenberg = new Location(
            "Glaubenberg",
            "Du bist jetzt beim \"Glaubenberg\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location fels_am_sturz = new Location(
            "Fels am Sturz",
            "Du bist jetzt beim \"Fels am Sturz\" im Kanton \"Obwalden\" oder kurz \"OW\"."
        );

        Location ochsenmatt = new Location(
            "Ochsenmatt",
            "Du bist jetzt bei der \"Ochsenmatt\" im Kanton \"Nidwalden\" oder kurz \"NW\"."
        );

        Location gitziloch = new Location(
            "Gitziloch",
            "Du bist jetzt beim \"Gitziloch\" im Kanton \"Luzern\" oder kurz \"LU\"."
        );

        Location laettgaessli = new Location(
            "Lättgässl",
            "Du bist jetzt bei der \"Lättgässl\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location pizzo_gallina = new Location(
            "Pizzo Gallina",
            "Du bist jetzt beim \"Pizzo Gallina\" im Kanton \"Tessin\" oder kurz \"TI\"."
        );

        Location gelmersee = new Location(
            "Gelmersee",
            "Du bist jetzt beim \"Gelmersee\" im Kanton \"Bern\" oder kurz \"BE\"."
        );

        Location goeschenernalpsee = new Location(
            "Göscheneralpsee",
            "Du bist jetzt beim \"Göscheneralpsee\" im Kanton \"Uri\" oder kurz \"UR\"."
        );

        Location disentis_muster = new Location(
            "Disentis/Muster",
            "Du bist jetzt in \"Disentis/Muster\" im Kanton \"Graubünden\" oder kurz \"GR\"."
        );

        Location goeschenen = new Location(
            "Göschenen",
            "Du bist jetzt in \"Göschenen\" im Kanton \"Uri\" oder kurz \"UR\"."
        );

        Location stanserhorn = new Location(
            "Stanserhorn",
            "Du bist jetzt beim \"Stanserhorn\" im Kanton \"Nidwalden\" oder kurz \"NW\"."
        );

        Location chaiserstock = new Location(
            "Chaiserstock",
            "Du bist jetzt beim \"Chaiserstock\" im Kanton \"Schwyz\" oder kurz \"SZ\"."
        );

        /*
         * ========================================================
         * VERBINDUNGEN
         * ========================================================
         */

        // Von Älggialp
        aelggialp.setTwoWayExit(Direction.NORTH, rufi, minutes(7.5));
        aelggialp.setTwoWayExit(Direction.SOUTH, hasliberg, minutes(20));
        aelggialp.setTwoWayExit(Direction.WEST, lungern, minutes(17.5));
        aelggialp.setTwoWayExit(Direction.EAST, truebsee, minutes(25));

        // Von Rufi
        rufi.setTwoWayExit(Direction.NORTH, giswil, minutes(15));
        rufi.setTwoWayExit(Direction.SOUTH, aelggialp, minutes(7.5));
        rufi.setTwoWayExit(Direction.WEST, lungern, minutes(20));
        rufi.setTwoWayExit(
            Direction.EAST,
            alpenresort_eienwaeldli,
            minutes(32.5)
        );

        // Von Giswil
        giswil.setTwoWayExit(Direction.NORTH, sarnen, minutes(25));
        giswil.setTwoWayExit(Direction.SOUTH, rufi, minutes(15));
        giswil.setTwoWayExit(Direction.EAST, melchtal_waterfall, minutes(15));
        giswil.setTwoWayExit(Direction.WEST, honegg, minutes(7.5));

        // Von Sarnen
        sarnen.setTwoWayExit(Direction.SOUTH, giswil, minutes(25));
        sarnen.setTwoWayExit(Direction.NORTH, alpnach, minutes(12.5));
        sarnen.setTwoWayExit(Direction.EAST, kerns, minutes(7.5));
        sarnen.setTwoWayExit(Direction.WEST, stalden_sarnen, minutes(9.5));

        // Von Hasliberg
        hasliberg.setTwoWayExit(Direction.NORTH, aelggialp, minutes(20));
        hasliberg.setTwoWayExit(
            Direction.SOUTH,
            aareschlucht_meiringen,
            minutes(11.5)
        );
        hasliberg.setTwoWayExit(Direction.EAST, maegisalp, minutes(10));
        hasliberg.setTwoWayExit(Direction.WEST, brienzwiler, minutes(17.5));

        // Von Lungern
        lungern.setTwoWayExit(Direction.EAST, aelggialp, minutes(17.5));
        lungern.setTwoWayExit(Direction.NORTH, giswil, minutes(15));
        lungern.setTwoWayExit(Direction.SOUTH, balm_bei_meiringen, minutes(15));
        lungern.setTwoWayExit(
            Direction.WEST,
            berghaus_schoenbueel,
            minutes(12.5)
        );

        // Von Trübsee
        truebsee.setTwoWayExit(Direction.WEST, aelggialp, minutes(25));
        truebsee.setTwoWayExit(Direction.SOUTH, giglistock, minutes(25));
        truebsee.setTwoWayExit(Direction.NORTH, engelberg, minutes(10));
        truebsee.setTwoWayExit(Direction.EAST, silenen, minutes(47.5));

        // Von Alpenresort Eienwäldli
        alpenresort_eienwaeldli.setTwoWayExit(
            Direction.WEST,
            rufi,
            minutes(32.5)
        );
        alpenresort_eienwaeldli.setTwoWayExit(
            Direction.SOUTH,
            susten_passhoehe,
            minutes(47.5)
        );
        alpenresort_eienwaeldli.setTwoWayExit(
            Direction.NORTH,
            bannalpsee,
            minutes(15)
        );
        alpenresort_eienwaeldli.setTwoWayExit(
            Direction.EAST,
            erstfelder_tal,
            minutes(25)
        );

        // Vom Melchtal Waterfall
        melchtal_waterfall.setTwoWayExit(Direction.WEST, giswil, minutes(15));
        melchtal_waterfall.setTwoWayExit(
            Direction.SOUTH,
            sportcamp_melchtal,
            minutes(4)
        );
        melchtal_waterfall.setTwoWayExit(
            Direction.NORTH,
            teufibach,
            minutes(6.5)
        );
        melchtal_waterfall.setTwoWayExit(
            Direction.EAST,
            eugenisee,
            minutes(21.5)
        );

        // Von Honegg
        honegg.setTwoWayExit(Direction.EAST, giswil, minutes(7.5));
        honegg.setTwoWayExit(Direction.NORTH, berghotel_langis, minutes(52.5));
        honegg.setTwoWayExit(
            Direction.SOUTH,
            gipfelkreuz_giswilerstock,
            minutes(65)
        );
        honegg.setTwoWayExit(Direction.WEST, chessiloch_fluehli, minutes(67.5));

        // Von Alpnach
        alpnach.setTwoWayExit(Direction.SOUTH, sarnen, minutes(12.5));
        alpnach.setTwoWayExit(Direction.NORTH, hergiswil, minutes(13.5));
        alpnach.setTwoWayExit(Direction.EAST, oberdorf, minutes(20));
        alpnach.setTwoWayExit(
            Direction.WEST,
            langnau_im_emmental,
            minutes(82.5)
        );

        // Von Kerns
        kerns.setTwoWayExit(Direction.WEST, sarnen, minutes(7.5));
        kerns.setTwoWayExit(Direction.NORTH, luzern, minutes(35));
        kerns.setTwoWayExit(Direction.SOUTH, glogghues, minutes(30));
        kerns.setTwoWayExit(Direction.EAST, altdorf, minutes(42.5));

        // Von Stalden
        stalden_sarnen.setTwoWayExit(Direction.EAST, sarnen, minutes(9.5));
        stalden_sarnen.setTwoWayExit(
            Direction.NORTH,
            sempachersee,
            minutes(67.5)
        );
        stalden_sarnen.setTwoWayExit(Direction.WEST, rickhubel, minutes(20));
        stalden_sarnen.setTwoWayExit(Direction.SOUTH, wilen_sarnen, minutes(4));

        // Von Aareschlucht
        aareschlucht_meiringen.setTwoWayExit(
            Direction.NORTH,
            hasliberg,
            minutes(11.5)
        );
        aareschlucht_meiringen.setTwoWayExit(
            Direction.SOUTH,
            reichenbachfall,
            minutes(3)
        );
        aareschlucht_meiringen.setTwoWayExit(
            Direction.WEST,
            balm_bei_meiringen,
            minutes(6.5)
        );
        aareschlucht_meiringen.setTwoWayExit(
            Direction.EAST,
            giglistock,
            minutes(30)
        );

        // Von Mägisalp
        maegisalp.setTwoWayExit(Direction.WEST, hasliberg, minutes(10));
        maegisalp.setTwoWayExit(Direction.NORTH, hochstollen, minutes(6.5));
        maegisalp.setTwoWayExit(
            Direction.EAST,
            taelli_klettersteig,
            minutes(15)
        );
        maegisalp.setTwoWayExit(Direction.SOUTH, innertkirchen, minutes(10));

        // Von Brienzwiler
        brienzwiler.setTwoWayExit(Direction.EAST, hasliberg, minutes(17.5));
        brienzwiler.setTwoWayExit(
            Direction.NORTH,
            berghaus_schoenbueel,
            minutes(10)
        );
        brienzwiler.setTwoWayExit(Direction.SOUTH, oltschiburg, minutes(8));
        brienzwiler.setTwoWayExit(Direction.WEST, brienz, minutes(10));

        // Von Balm bei Meiringen
        balm_bei_meiringen.setTwoWayExit(Direction.NORTH, lungern, minutes(15));
        balm_bei_meiringen.setTwoWayExit(
            Direction.SOUTH,
            gletscherschlucht_rosenlaui,
            minutes(14)
        );
        balm_bei_meiringen.setTwoWayExit(
            Direction.EAST,
            aareschlucht_meiringen,
            minutes(6.5)
        );
        balm_bei_meiringen.setTwoWayExit(
            Direction.WEST,
            brienzersee,
            minutes(9)
        );

        // Vom Berghaus Schönbüel
        berghaus_schoenbueel.setTwoWayExit(
            Direction.EAST,
            lungern,
            minutes(12.5)
        );
        berghaus_schoenbueel.setTwoWayExit(
            Direction.NORTH,
            panoramastrasse_glaubenbielen,
            minutes(10)
        );
        berghaus_schoenbueel.setTwoWayExit(
            Direction.WEST,
            brienzer_rothorn,
            minutes(8.5)
        );
        berghaus_schoenbueel.setTwoWayExit(
            Direction.SOUTH,
            brienzwiler,
            minutes(10)
        );

        // Vom Giglistock
        giglistock.setTwoWayExit(Direction.NORTH, truebsee, minutes(25));
        giglistock.setTwoWayExit(
            Direction.WEST,
            aareschlucht_meiringen,
            minutes(30)
        );
        giglistock.setTwoWayExit(
            Direction.SOUTH,
            rhonegletscher,
            minutes(21.5)
        );
        giglistock.setTwoWayExit(Direction.EAST, wassen, minutes(25));

        // Von Engelberg
        engelberg.setTwoWayExit(Direction.SOUTH, truebsee, minutes(10));
        engelberg.setTwoWayExit(Direction.NORTH, dallenwil, minutes(17));
        engelberg.setTwoWayExit(
            Direction.WEST,
            gipfelkreuz_giswilerstock,
            minutes(30)
        );
        engelberg.setTwoWayExit(Direction.EAST, erstfeld, minutes(25));

        // Von Silenen
        silenen.setTwoWayExit(Direction.WEST, truebsee, minutes(47.5));
        silenen.setTwoWayExit(Direction.EAST, laax, minutes(60));
        silenen.setTwoWayExit(Direction.NORTH, erstfeld, minutes(6));
        silenen.setTwoWayExit(Direction.SOUTH, wassen, minutes(15));

        // Von Susten Passhöhe
        susten_passhoehe.setTwoWayExit(
            Direction.NORTH,
            alpenresort_eienwaeldli,
            minutes(47.5)
        );
        susten_passhoehe.setTwoWayExit(Direction.SOUTH, realp, minutes(25));
        susten_passhoehe.setTwoWayExit(
            Direction.WEST,
            gschletterkurve,
            minutes(10)
        );
        susten_passhoehe.setTwoWayExit(
            Direction.EAST,
            gurtnellen,
            minutes(21.5)
        );

        // Vom Bannalpsee
        bannalpsee.setTwoWayExit(
            Direction.NORTH,
            alpenresort_eienwaeldli,
            minutes(15)
        );
        bannalpsee.setTwoWayExit(Direction.SOUTH, rhonestock, minutes(37.5));
        bannalpsee.setTwoWayExit(Direction.EAST, attinghausen, minutes(18.5));
        bannalpsee.setTwoWayExit(Direction.WEST, altzellen, minutes(7.5));

        // Vom Erstfelder Tal
        erstfelder_tal.setTwoWayExit(
            Direction.WEST,
            alpenresort_eienwaeldli,
            minutes(25)
        );
        erstfelder_tal.setTwoWayExit(Direction.EAST, seewlialp, minutes(12.5));
        erstfelder_tal.setTwoWayExit(Direction.NORTH, seelisberg, minutes(30));
        erstfelder_tal.setTwoWayExit(Direction.SOUTH, hospental, minutes(35));

        // Vom Sportcamp Melchtal
        sportcamp_melchtal.setTwoWayExit(
            Direction.NORTH,
            melchtal_waterfall,
            minutes(4)
        );
        sportcamp_melchtal.setTwoWayExit(
            Direction.SOUTH,
            melchsee,
            minutes(10)
        );
        sportcamp_melchtal.setTwoWayExit(
            Direction.EAST,
            limmerensee,
            minutes(65)
        );
        sportcamp_melchtal.setTwoWayExit(
            Direction.WEST,
            unesco_biospaehre_entlebuch,
            minutes(30)
        );

        // Vom Teufibach
        teufibach.setTwoWayExit(
            Direction.SOUTH,
            melchtal_waterfall,
            minutes(6.5)
        );
        teufibach.setTwoWayExit(Direction.NORTH, kriens, minutes(21.5));
        teufibach.setTwoWayExit(Direction.WEST, heftihuette, minutes(35));
        teufibach.setTwoWayExit(
            Direction.EAST,
            berghaus_klondike,
            minutes(17.5)
        );

        // Vom Eugenisee
        eugenisee.setTwoWayExit(
            Direction.WEST,
            melchtal_waterfall,
            minutes(21.5)
        );
        eugenisee.setTwoWayExit(Direction.EAST, panixersee, minutes(75));
        eugenisee.setTwoWayExit(
            Direction.SOUTH,
            truebseebach_wasserfall,
            minutes(7.5)
        );
        eugenisee.setTwoWayExit(Direction.NORTH, eugenibach, minutes(10));

        // Vom Berghotel Langis
        berghotel_langis.setTwoWayExit(Direction.SOUTH, honegg, minutes(52.5));
        berghotel_langis.setTwoWayExit(Direction.NORTH, schachen, minutes(24));
        berghotel_langis.setTwoWayExit(Direction.WEST, fluehli, minutes(15));
        berghotel_langis.setTwoWayExit(
            Direction.EAST,
            wolfenschiessen,
            minutes(30)
        );

        // Vom Gipfelkreuz Giswilerstock
        gipfelkreuz_giswilerstock.setTwoWayExit(
            Direction.NORTH,
            honegg,
            minutes(65)
        );
        gipfelkreuz_giswilerstock.setTwoWayExit(
            Direction.EAST,
            engelberg,
            minutes(30)
        );
        gipfelkreuz_giswilerstock.setTwoWayExit(
            Direction.WEST,
            soerenberg,
            minutes(12.5)
        );
        gipfelkreuz_giswilerstock.setTwoWayExit(
            Direction.SOUTH,
            wetterhorn,
            minutes(52.5)
        );

        // Vom Chessiloch in Flühli
        chessiloch_fluehli.setTwoWayExit(Direction.EAST, honegg, minutes(67.5));
        chessiloch_fluehli.setTwoWayExit(
            Direction.SOUTH,
            soerenberg,
            minutes(7.5)
        );
        chessiloch_fluehli.setTwoWayExit(Direction.WEST, hohwald, minutes(3));
        chessiloch_fluehli.setTwoWayExit(Direction.NORTH, menznau, minutes(35));

        // Von Hergiswil
        hergiswil.setTwoWayExit(Direction.SOUTH, alpnach, minutes(13.5));
        hergiswil.setTwoWayExit(Direction.NORTH, horw, minutes(5.5));
        hergiswil.setTwoWayExit(Direction.WEST, entlebuch, minutes(30));
        hergiswil.setTwoWayExit(Direction.EAST, gersau, minutes(25));

        // Von Oberdorf
        oberdorf.setTwoWayExit(Direction.WEST, alpnach, minutes(20));
        oberdorf.setTwoWayExit(Direction.NORTH, meggen, minutes(17.5));
        oberdorf.setTwoWayExit(Direction.SOUTH, dallenwil, minutes(3));
        oberdorf.setTwoWayExit(Direction.EAST, emmetten, minutes(15));

        // Von Langnau im Emmental
        langnau_im_emmental.setTwoWayExit(
            Direction.EAST,
            alpnach,
            minutes(82) + 38
        );
        langnau_im_emmental.setTwoWayExit(
            Direction.NORTH,
            madiswil,
            minutes(37.5)
        );
        langnau_im_emmental.setTwoWayExit(
            Direction.SOUTH,
            eggiwil,
            minutes(10)
        );
        langnau_im_emmental.setTwoWayExit(Direction.WEST, worb, minutes(25));

        // Von Luzern
        luzern.setTwoWayExit(Direction.SOUTH, kerns, minutes(35));
        luzern.setTwoWayExit(Direction.EAST, meggen, minutes(8));
        luzern.setTwoWayExit(Direction.NORTH, hochdorf, minutes(20));
        luzern.setTwoWayExit(Direction.WEST, emmen, minutes(7.5));

        // Von Glogghüs
        glogghues.setTwoWayExit(Direction.NORTH, kerns, minutes(30));
        glogghues.setTwoWayExit(Direction.WEST, bruenig, minutes(15));
        glogghues.setTwoWayExit(Direction.SOUTH, meiringen, minutes(12.5));
        glogghues.setTwoWayExit(Direction.EAST, sustlihuette, minutes(17.5));

        // Von Altdorf
        altdorf.setTwoWayExit(Direction.WEST, kerns, minutes(42.5));
        altdorf.setTwoWayExit(Direction.EAST, laaxer_stock, minutes(37.5));
        altdorf.setTwoWayExit(Direction.NORTH, flueelen, minutes(4));
        altdorf.setTwoWayExit(Direction.SOUTH, schattdorf, minutes(3));

        // Vom Sempachersee
        sempachersee.setTwoWayExit(
            Direction.SOUTH,
            stalden_sarnen,
            minutes(67.5)
        );
        sempachersee.setTwoWayExit(Direction.NORTH, sursee, minutes(7.5));
        sempachersee.setTwoWayExit(Direction.WEST, willisau, minutes(17.5));
        sempachersee.setTwoWayExit(Direction.EAST, hochdorf, minutes(15));

        // Von Rickhubel
        rickhubel.setTwoWayExit(Direction.EAST, stalden_sarnen, minutes(20));
        rickhubel.setTwoWayExit(
            Direction.NORTH,
            finsterwald_bei_entlebuch,
            minutes(12.5)
        );
        rickhubel.setTwoWayExit(
            Direction.SOUTH,
            unesco_biospaehre_entlebuch,
            minutes(15)
        );
        rickhubel.setTwoWayExit(Direction.WEST, mettlen, minutes(30));

        // Von Wilen (Sarnen)
        wilen_sarnen.setTwoWayExit(Direction.NORTH, stalden_sarnen, minutes(4));
        wilen_sarnen.setTwoWayExit(
            Direction.SOUTH,
            kugelweg_hasliberg,
            minutes(20)
        );
        wilen_sarnen.setTwoWayExit(Direction.WEST, laetzengraben, minutes(2.5));
        wilen_sarnen.setTwoWayExit(Direction.EAST, st_niklausen, minutes(1.5));

        // Vom Reichenbachfall
        reichenbachfall.setTwoWayExit(
            Direction.NORTH,
            aareschlucht_meiringen,
            minutes(3)
        );
        reichenbachfall.setTwoWayExit(
            Direction.SOUTH,
            rychenbach,
            minutes(3.5)
        );
        reichenbachfall.setTwoWayExit(
            Direction.EAST,
            kraftwerk_hopflauenen,
            minutes(4)
        );
        reichenbachfall.setTwoWayExit(Direction.WEST, axalphorn, minutes(15));

        // Von Hochstollen
        hochstollen.setTwoWayExit(Direction.SOUTH, maegisalp, minutes(6.5));
        hochstollen.setTwoWayExit(Direction.NORTH, sachseln, minutes(15));
        hochstollen.setTwoWayExit(Direction.WEST, gibel, minutes(8));
        hochstollen.setTwoWayExit(Direction.EAST, melchsee, minutes(4));

        // Vom Tälli Klettersteig
        taelli_klettersteig.setTwoWayExit(
            Direction.WEST,
            maegisalp,
            minutes(15)
        );
        taelli_klettersteig.setTwoWayExit(
            Direction.SOUTH,
            triftbruecke,
            minutes(7.5)
        );
        taelli_klettersteig.setTwoWayExit(
            Direction.NORTH,
            engstlensee,
            minutes(6.5)
        );
        taelli_klettersteig.setTwoWayExit(
            Direction.EAST,
            bristensee,
            minutes(18.5)
        );

        // Von Innertkirchen
        innertkirchen.setTwoWayExit(Direction.NORTH, maegisalp, minutes(10));
        innertkirchen.setTwoWayExit(Direction.WEST, schattenhalb, minutes(5));
        innertkirchen.setTwoWayExit(
            Direction.EAST,
            lueterenseewli,
            minutes(10)
        );
        innertkirchen.setTwoWayExit(Direction.SOUTH, unterstock, minutes(2.5));

        // Von der Oltschiburg
        oltschiburg.setTwoWayExit(Direction.NORTH, brienzwiler, minutes(8));
        oltschiburg.setTwoWayExit(Direction.WEST, giessbachfaelle, minutes(8));
        oltschiburg.setTwoWayExit(Direction.EAST, tschingel, minutes(6.5));
        oltschiburg.setTwoWayExit(Direction.SOUTH, schwarzhorn, minutes(14));

        // Von Brienz
        brienz.setTwoWayExit(Direction.EAST, brienzwiler, minutes(10));
        brienz.setTwoWayExit(Direction.SOUTH, giessbachfaelle, minutes(5));
        brienz.setTwoWayExit(Direction.NORTH, brienzer_rothorn, minutes(9));
        brienz.setTwoWayExit(Direction.WEST, augstmatthorn, minutes(8));

        // Von der Gletscherschlucht Rosenlaui
        gletscherschlucht_rosenlaui.setTwoWayExit(
            Direction.NORTH,
            balm_bei_meiringen,
            minutes(14)
        );
        gletscherschlucht_rosenlaui.setTwoWayExit(
            Direction.SOUTH,
            klein_wellhorn,
            minutes(4)
        );
        gletscherschlucht_rosenlaui.setTwoWayExit(
            Direction.WEST,
            schwarzhorn,
            minutes(8.5)
        );
        gletscherschlucht_rosenlaui.setTwoWayExit(
            Direction.EAST,
            hoch_horefellistock,
            minutes(15)
        );

        // Vom Brienzersee
        brienzersee.setTwoWayExit(
            Direction.EAST,
            balm_bei_meiringen,
            minutes(9)
        );
        brienzersee.setTwoWayExit(Direction.NORTH, schrattenfluh, minutes(15));
        brienzersee.setTwoWayExit(Direction.SOUTH, luetschental, minutes(8.5));
        brienzersee.setTwoWayExit(Direction.WEST, interlaken, minutes(7.5));

        // Von der Panoramastrasse Glaubenbielen
        panoramastrasse_glaubenbielen.setTwoWayExit(
            Direction.SOUTH,
            berghaus_schoenbueel,
            minutes(10)
        );
        panoramastrasse_glaubenbielen.setTwoWayExit(
            Direction.NORTH,
            glaubenberg,
            minutes(10)
        );
        panoramastrasse_glaubenbielen.setTwoWayExit(
            Direction.WEST,
            fels_am_sturz,
            minutes(3)
        );
        panoramastrasse_glaubenbielen.setTwoWayExit(
            Direction.EAST,
            ochsenmatt,
            minutes(8.5)
        );

        // Vom Brienzer Rothorn
        brienzer_rothorn.setTwoWayExit(Direction.SOUTH, brienz, minutes(9));
        brienzer_rothorn.setTwoWayExit(
            Direction.EAST,
            berghaus_schoenbueel,
            minutes(8.5)
        );
        brienzer_rothorn.setTwoWayExit(Direction.NORTH, gitziloch, minutes(10));
        brienzer_rothorn.setTwoWayExit(
            Direction.WEST,
            laettgaessli,
            minutes(1)
        );

        // Vom Rhonegletscher
        rhonegletscher.setTwoWayExit(
            Direction.NORTH,
            giglistock,
            minutes(21.5)
        );
        rhonegletscher.setTwoWayExit(
            Direction.SOUTH,
            pizzo_gallina,
            minutes(14)
        );
        rhonegletscher.setTwoWayExit(Direction.WEST, gelmersee, minutes(10));
        rhonegletscher.setTwoWayExit(
            Direction.EAST,
            goeschenernalpsee,
            minutes(12)
        );

        // Von Wassen
        wassen.setTwoWayExit(Direction.WEST, giglistock, minutes(25));
        wassen.setTwoWayExit(Direction.NORTH, silenen, minutes(15));
        wassen.setTwoWayExit(Direction.EAST, disentis_muster, minutes(25));
        wassen.setTwoWayExit(Direction.SOUTH, goeschenen, minutes(5));

        // Von Dallenwil
        dallenwil.setTwoWayExit(Direction.SOUTH, engelberg, minutes(17));
        dallenwil.setTwoWayExit(Direction.NORTH, oberdorf, minutes(3));
        dallenwil.setTwoWayExit(Direction.WEST, stanserhorn, minutes(6.5));
        dallenwil.setTwoWayExit(Direction.EAST, chaiserstock, minutes(18.5));

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
            return "Morgen";
        }

        if (hour >= 8 && hour < 18) {
            return "Tag";
        }

        if (hour >= 18 && hour < 21) {
            return "Abend";
        }

        return "Nacht";
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
            return String.format("%d Minuten und %d Sekunden", minutes, secs);
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
            int hoursUntilNextChange = 2 + random.nextInt(5);

            nextWeatherChange += hoursUntilNextChange * 3600.0;

            if (oldWeather != currentWeather) {
                System.out.println();
                System.out.println("Das Wetter hat sich geändert:");

                System.out.println(
                    oldWeather.getDisplayName() +
                        " → " +
                        currentWeather.getDisplayName()
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
        System.out.println("Wetter: " + currentWeather.getDisplayName());

        if (currentWeather == Weather.RAIN) {
            System.out.println("Durch den Regen dauern Reisen 15 % länger.");
        } else if (currentWeather == Weather.FOG) {
            System.out.println("Durch den Nebel dauern Reisen 25 % länger.");
        } else if (currentWeather == Weather.STRONG_WIND) {
            System.out.println(
                "Durch den starken Wind dauern Reisen 40 % länger."
            );
        } else if (currentWeather == Weather.THUNDERSTORM) {
            System.out.println(
                "Bei diesem Gewitter kannst du nicht mit dem Heissluftballon fliegen."
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
            getCurrentHour() * 3600L +
            getCurrentMinute() * 60L +
            getCurrentSecond();

        long targetSeconds = 6 * 3600L;

        long sleepSeconds;

        if (currentSecondsOfDay < targetSeconds) {
            sleepSeconds = targetSeconds - currentSecondsOfDay;
        } else {
            sleepSeconds = 24 * 3600L - currentSecondsOfDay + targetSeconds;
        }

        double remainingTime = startTime - timeElapsed;

        if (sleepSeconds >= remainingTime) {
            timeElapsed = startTime;

            System.out.println();
            System.out.println("Du legst dich schlafen...");

            System.out.println(
                "Während du schläfst, läuft deine verbleibende Zeit ab."
            );

            return;
        }

        System.out.println();
        System.out.println("Du legst dich schlafen...");

        timeElapsed += sleepSeconds;

        System.out.println(
            "Du hast " + formatDuration(sleepSeconds) + " geschlafen."
        );

        updateWeather();

        System.out.printf(
            "Du wachst an Tag %d um %02d:%02d Uhr auf.%n",
            getCurrentDay(),
            getCurrentHour(),
            getCurrentMinute()
        );

        System.out.println("Wetter: " + currentWeather.getDisplayName());
    }

    private void waitThirtyMinutes() {
        double remainingTime = startTime - timeElapsed;

        if (WAIT_TIME >= remainingTime) {
            timeElapsed = startTime;

            System.out.println();
            System.out.println("Du wartest...");

            System.out.println(
                "Während du wartest, läuft deine verbleibende Zeit ab."
            );

            return;
        }

        String oldDayPhase = getDayPhase();

        int oldDay = getCurrentDay();

        System.out.println();
        System.out.println("Du wartest 30 Minuten.");

        timeElapsed += WAIT_TIME;

        updateWeather();

        if (getCurrentDay() > oldDay) {
            System.out.printf("Tag %d hat begonnen!%n", getCurrentDay());
        }

        if (!oldDayPhase.equals(getDayPhase())) {
            System.out.println(
                "Die Tageszeit hat sich geändert: " +
                    oldDayPhase +
                    " → " +
                    getDayPhase()
            );
        }

        System.out.printf(
            "Es ist jetzt %02d:%02d Uhr.%n",
            getCurrentHour(),
            getCurrentMinute()
        );

        System.out.println("Wetter: " + currentWeather.getDisplayName());
    }

    /*
     * ============================================================
     * SPIEL
     * ============================================================
     */

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println(
            "Hallo und willkommen bei \"Escape from Switzerland\"!"
        );

        String name;

        do {
            System.out.print("Gib einen Namen ein, um fortzufahren: ");
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Der Name darf nicht leer sein.");
            }
        } while (name.isEmpty());

        System.out.println("Willkommen: " + name);

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

        System.out.println("Das Spiel beginnt an Tag 1 um 06:00 Uhr.");

        System.out.println("Während deiner Reisen vergeht die Zeit.");

        System.out.println("Nachts dauern Reisen 20 % länger.");

        System.out.println(
            "Das Wetter kann deine Reise zusätzlich verlangsamen."
        );

        System.out.println("Bei Gewitter kannst du nicht fliegen.");

        System.out.println("Mit \"schlafen\" schläfst du bis 06:00 Uhr.");

        System.out.println("Mit \"warten\" wartest du 30 Minuten.");

        List<String> validStart = List.of("start");

        boolean start = false;

        while (!start) {
            System.out.print(
                "\nTippe \"Start\" oder \"start\" ein, um zu beginnen: "
            );

            String answer = scanner.nextLine().trim().toLowerCase();

            if (validStart.contains(answer)) {
                start = true;
            } else {
                System.out.println("Ungültige Eingabe!");
            }
        }

        /*
         * ========================================================
         * HAUPTSCHLEIFE
         * ========================================================
         */

        label: while (startTime - timeElapsed > 0) {
            updateWeather();

            double remainingTime = startTime - timeElapsed;

            System.out.println();
            System.out.println(
                "================================================"
            );

            System.out.println("Ort: " + currentLocation.getName());

            System.out.println(currentLocation.getDescription());

            System.out.println();

            printGameTime();

            printWeather();

            System.out.println(
                "Verbleibende Zeit: " + formatRemainingTime(remainingTime)
            );

            if (isNight()) {
                System.out.println(
                    "Reisen dauern wegen der Dunkelheit 20 % länger."
                );
            }

            System.out.println();

            System.out.print("Aktion (N/S/O/W, schlafen, warten oder exit): ");

            String input = scanner.nextLine().trim().toLowerCase();

            /*
             * ====================================================
             * BEENDEN
             * ====================================================
             */

            switch (input) {
                case "exit":
                case "q":
                    System.out.println("Spiel vorbei.");

                    break label;
                /*
                 * ====================================================
                 * SCHLAFEN
                 * ====================================================
                 */
                case "schlafen":
                case "sleep":
                    sleepUntilMorning();

                    continue;
                /*
                 * ====================================================
                 * WARTEN
                 * ====================================================
                 */
                case "warten":
                case "wait":
                    waitThirtyMinutes();

                    continue;
            }

            /*
             * ====================================================
             * RICHTUNG
             * ====================================================
             */

            Direction dir = Direction.parse(input);

            if (dir == null) {
                System.out.println("Ungültige Eingabe!");

                System.out.println(
                    "Benutze N, S, O, W, schlafen, warten oder exit."
                );

                continue;
            }

            Location next = currentLocation.getExit(dir);

            if (next == null) {
                System.out.println("Du kannst nicht in diese Richtung gehen!");

                continue;
            }

            /*
             * ====================================================
             * GEWITTER
             * ====================================================
             */

            if (!currentWeather.isTravelAllowed()) {
                System.out.println();
                System.out.println("Das Gewitter ist zu gefährlich!");

                System.out.println(
                    "Du kannst mit dem Heissluftballon momentan nicht starten."
                );

                System.out.println("Benutze \"warten\" oder \"schlafen\".");

                continue;
            }

            /*
             * ====================================================
             * REISE BERECHNEN
             * ====================================================
             */

            String oldDayPhase = getDayPhase();

            int oldDay = getCurrentDay();

            int oldHour = getCurrentHour();

            int oldMinute = getCurrentMinute();

            double baseTravelTime = currentLocation.getTravelTime(dir);

            double travelTime = baseTravelTime;

            /*
             * Wetter-Multiplikator
             */
            double weatherMultiplier = currentWeather.getTravelMultiplier();

            travelTime *= weatherMultiplier;

            /*
             * Nacht-Multiplikator
             */
            boolean travellingAtNight = isNight();

            if (travellingAtNight) {
                travelTime *= NIGHT_TRAVEL_MULTIPLIER;
            }

            /*
             * ====================================================
             * REISEINFORMATIONEN
             * ====================================================
             */

            System.out.println();

            System.out.printf(
                "Du bereitest den Flug von %s nach %s vor.%n",
                currentLocation.getName(),
                next.getName()
            );

            System.out.println("Wetter: " + currentWeather.getDisplayName());

            System.out.println(
                "Normale Reisezeit: " + formatDuration(baseTravelTime)
            );

            if (weatherMultiplier > 1.0) {
                int weatherPercent = (int) Math.round(
                    (weatherMultiplier - 1.0) * 100
                );

                System.out.println("Wetter-Malus: +" + weatherPercent + " %");
            }

            if (travellingAtNight) {
                System.out.println("Nacht-Malus: +20 %");
            }

            System.out.println(
                "Tatsächliche Reisezeit: " + formatDuration(travelTime)
            );

            /*
             * ====================================================
             * GENUG ZEIT?
             * ====================================================
             */

            remainingTime = startTime - timeElapsed;

            if (travelTime >= remainingTime) {
                System.out.println();

                System.out.println("Du startest deine Reise...");

                timeElapsed = startTime;

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

            System.out.println("Du startest...");

            timeElapsed += travelTime;

            currentLocation = next;

            /*
             * Wetter nach vergangener Zeit aktualisieren
             */
            updateWeather();

            System.out.println();

            System.out.printf(
                "Du bist in %s angekommen.%n",
                currentLocation.getName()
            );

            System.out.println(
                "Die Reise hat " + formatDuration(travelTime) + " gedauert."
            );

            /*
             * ====================================================
             * TAG GEWECHSELT?
             * ====================================================
             */

            if (getCurrentDay() > oldDay) {
                System.out.println();

                System.out.printf("Tag %d hat begonnen!%n", getCurrentDay());
            }

            /*
             * ====================================================
             * TAGESZEIT GEWECHSELT?
             * ====================================================
             */

            String newDayPhase = getDayPhase();

            if (!oldDayPhase.equals(newDayPhase)) {
                System.out.println();

                System.out.println(
                    "Die Tageszeit hat sich während deiner Reise geändert:"
                );

                System.out.println(oldDayPhase + " → " + newDayPhase);
            }

            /*
             * ====================================================
             * ANKUNFTSZEIT
             * ====================================================
             */

            System.out.println();

            System.out.printf("Abfahrt: %02d:%02d Uhr%n", oldHour, oldMinute);

            System.out.printf(
                "Ankunft: %02d:%02d Uhr%n",
                getCurrentHour(),
                getCurrentMinute()
            );

            System.out.println(
                "Wetter bei Ankunft: " + currentWeather.getDisplayName()
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

            System.out.println("Die Zeit ist abgelaufen!");

            System.out.println(
                "Du hast es nicht rechtzeitig geschafft, aus der Schweiz zu fliehen."
            );

            System.out.println();

            System.out.println("GAME OVER");

            System.out.println(
                "================================================"
            );
        }

        scanner.close();
    }
}
