package com.freetime.efs;

import java.util.HashMap;
import java.util.Map;

public class Location {
    private final String name;
    private final String description;
    private final Map<Direction, Location> exits = new HashMap<>();
    private final Map<Direction, Double> travelTimes = new HashMap<>();

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setTwoWayExit(Direction direction, Location neighbor, double travelTime) {
        this.exits.put(direction, neighbor);
        this.travelTimes.put(direction, travelTime);

        Direction opposite = getOppositeDirection(direction);
        neighbor.exits.put(opposite, this);
        neighbor.travelTimes.put(opposite, travelTime);
    }

    private Direction getOppositeDirection(Direction dir) {
        switch (dir) {
            case NORTH: return Direction.SOUTH;
            case SOUTH: return Direction.NORTH;
            case EAST: return Direction.WEST;
            case WEST: return Direction.EAST;
            default: return null;
        }
    }

    public Location getExit(Direction direction) {
        return exits.get(direction);
    }

    public double getTravelTime(Direction direction) {
        return travelTimes.getOrDefault(direction, 0.0);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}