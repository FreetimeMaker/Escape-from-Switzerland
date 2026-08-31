package me.freetime.efs;

public interface Direction {
    double execute();
    Direction getNextLocation(String direction);
}