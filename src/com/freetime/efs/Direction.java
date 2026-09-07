package com.freetime.efs;

public enum Direction {
    NORTH, SOUTH, EAST, WEST;

    public static Direction parse(String input) {
        switch (input.trim().toLowerCase()) {
            case "n":  case "north": return NORTH;
            case "s":   case "south": return SOUTH;
            case "east": case "e": return EAST;
            case "w": case "west": return WEST;
            default: return null;
        }
    }
}