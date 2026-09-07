package com.freetime.efs;

import com.freetime.efs.north.Rufi;

public class Aelggialp implements Direction {
    @Override
    public double execute() {
        IO.println("You are at Älggialp, the geographical center of Switzerland.");
        return 0;
    }

    @Override
    public Direction getNextLocation(String direction) {
        if (direction.equals("north") || direction.equals("n")) {
            return new Rufi(); // Führt zum nächsten Ort im Norden
        }
        return null; // Ungültige Richtung für diesen Ort
    }
}