package edu.course.elevator.hardware;

// Adapter: translates the application's Motor API to the vendor library API.
public class LegacyMotorAdapter implements Motor {
    private final LegacyLiftMotor legacyMotor;
    public LegacyMotorAdapter(LegacyLiftMotor legacyMotor) { this.legacyMotor = legacyMotor; }
    @Override public void moveTo(int floor) { legacyMotor.selectDestinationAndStart(floor); }
}
