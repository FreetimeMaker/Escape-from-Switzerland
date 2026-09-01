package com.freetime.efs;

import java.util.HashMap;
import java.util.Map;

public class Direction {
    private final String name;
    private final Map<String, Route> routes = new HashMap<>();

    public Direction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addRoute(String direction, Direction destination, double travelTimeInSeconds) {
        routes.put(direction.toLowerCase(), new Route(destination, travelTimeInSeconds));
    }

    public Route getRoute(String direction) {
        return routes.get(direction.toLowerCase());
    }

    public static class Route {
        public final Direction destination;
        public final double travelTime;

        public Route(Direction destination, double travelTime) {
            this.destination = destination;
            this.travelTime = travelTime;
        }
    }
}