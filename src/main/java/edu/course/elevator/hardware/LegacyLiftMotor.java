package edu.course.elevator.hardware;

// Simulates a vendor library with an API that does not match our Motor interface.
public class LegacyLiftMotor {
    private int lastSelectedFloor = 1;
    public void selectDestinationAndStart(int destination) { lastSelectedFloor = destination; }
    public int getLastSelectedFloor() { return lastSelectedFloor; }
}
