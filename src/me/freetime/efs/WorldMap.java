package me.freetime.efs;

public class WorldMap {
    public static Direction createMap() {
        Direction aelggialp = new Direction("Älggialp");
        Direction rufi = new Direction("Rufi");
        Direction hasliberg = new Direction("Hasliberg");
        Direction lungern = new Direction("Lungern");
        Direction truebsee = new Direction("Trübsee");
        Direction giswil = new Direction("Giswil");

        // Von Älggialp aus:
        aelggialp.addRoute("north", rufi, 450.0);
        aelggialp.addRoute("n", rufi, 450.0);

        aelggialp.addRoute("south", hasliberg, 1200.0);
        aelggialp.addRoute("s", hasliberg, 1200.0);

        aelggialp.addRoute("west", lungern, 1050.0);
        aelggialp.addRoute("w", lungern, 1050.0);

        aelggialp.addRoute("east", truebsee, 1500.0);
        aelggialp.addRoute("e", truebsee, 1500.0);

        // Von Rufi weiter nach Norden:
        rufi.addRoute("north", giswil, 900.0);
        rufi.addRoute("n", giswil, 900.0);
        rufi.addRoute("west", lungern, 1200.0);
        rufi.addRoute("w", lungern, 1200.0);

        // Rückweg von Rufi nach Süden zurück zur Älggialp:
        rufi.addRoute("south", aelggialp, 450.0);
        rufi.addRoute("s", aelggialp, 450.0);

        // Startpunkt zurückgeben
        return aelggialp;
    }
}