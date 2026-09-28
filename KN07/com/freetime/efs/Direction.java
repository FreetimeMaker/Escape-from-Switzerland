package com.freetime.efs;

public enum Direction {
    NORTH, SOUTH, EAST, WEST;

    public static Direction parse(String input) {
        return switch (input.trim().toLowerCase()) {
            case "n", "north" -> NORTH;
            case "s", "south" -> SOUTH;
            case "east", "e" -> EAST;
            case "w", "west" -> WEST;
            default -> null;
        };
    }
}