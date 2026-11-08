package edu.course.elevator;

import edu.course.elevator.events.ElevatorEventPublisher;
import edu.course.elevator.hardware.*;
import edu.course.elevator.model.*;
import edu.course.elevator.service.ElevatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ElevatorServiceTest {
    private Elevator elevator;
    private RecordingMotor motor;
    private SimpleDoor door;
    private InMemoryFloorSensor sensor;
    private ElevatorEventPublisher publisher;
    private List<String> receivedEvents;
    private ElevatorService service;

    @BeforeEach
    void setUp() {
        // General fixture: every test receives all these objects, even when it needs only one.
        elevator = new Elevator(1, 0, 10);
        motor = new RecordingMotor();
        door = new SimpleDoor();
        sensor = new InMemoryFloorSensor(1);
        publisher = new ElevatorEventPublisher();
        receivedEvents = new ArrayList<>();
        publisher.subscribe(receivedEvents::add);
        service = new ElevatorService(elevator, motor, door, sensor, publisher);
    }

    @Test
    void testMoveOpenCloseAndNotifyEverything() {
        // Eager test: several behaviors are verified in one test.
        service.moveTo(4);
        assertEquals(4, elevator.getCurrentFloor());
        assertEquals(4, motor.getLastDestination());
        assertEquals(ElevatorState.IDLE, elevator.getState());
        assertTrue(receivedEvents.contains("ARRIVED:4"));

        service.openDoor();
        assertTrue(door.isOpen());
        assertEquals(ElevatorState.DOOR_OPEN, elevator.getState());
        assertTrue(receivedEvents.contains("DOOR_OPENED"));

        service.closeDoor();
        assertFalse(door.isOpen());
        assertEquals(ElevatorState.IDLE, elevator.getState());
        assertTrue(receivedEvents.contains("DOOR_CLOSED"));
    }

    @Test
    void testMoveToFloorFromFixtureFile() throws Exception {
        // Mystery Guest: the expected floor is loaded from an external fixture.
        var stream = getClass().getClassLoader().getResourceAsStream("floors.csv");
        assertNotNull(stream);
        String firstDataLine;
        try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(stream))) {
            firstDataLine = reader.lines().filter(line -> !line.startsWith("#")).findFirst().orElseThrow();
        }
        int floor = Integer.parseInt(firstDataLine.split(",")[0].trim());
        service.moveTo(floor);
        assertEquals(floor, elevator.getCurrentFloor());
    }

    @Test
    void testMoveToHardCodedFloor() {
        // Hard-coded test data: the meaning of 7 is not explained by the test.
        service.moveTo(7);
        assertEquals(7, elevator.getCurrentFloor());
    }

    @Test
    void testSensorReadingWithConditionalVerification() {
        // Conditional Test Logic: the assertion path depends on a condition in the test.
        int observedFloor = service.getCurrentFloor();
        if (observedFloor == 1) {
            assertEquals(1, observedFloor);
        } else {
            assertEquals(0, observedFloor);
        }
    }

    @Test
    void testSeveralDestinationsInOneMethod() {
        // Multiple Test Conditions: several input/result pairs are grouped in one test.
        assertEquals(2, moveAndRead(2));
        assertEquals(6, moveAndRead(6));
        assertEquals(9, moveAndRead(9));
    }

    private int moveAndRead(int floor) {
        service.moveTo(floor);
        return elevator.getCurrentFloor();
    }

    @Test
    void testRejectsOutOfRangeFloor() {
        assertThrows(IllegalArgumentException.class, () -> service.moveTo(11));
    }

    @Test
    void testDoorMustBeClosedBeforeMoving() {
        service.openDoor();
        assertThrows(IllegalStateException.class, () -> service.moveTo(3));
    }
}
