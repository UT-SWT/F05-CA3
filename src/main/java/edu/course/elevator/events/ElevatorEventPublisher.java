package edu.course.elevator.events;

import java.util.ArrayList;
import java.util.List;

// Observer/Publish-Subscribe: listeners receive events without being coupled to the controller.
public class ElevatorEventPublisher {
    private final List<ElevatorEventListener> listeners = new ArrayList<>();
    public void subscribe(ElevatorEventListener listener) { listeners.add(listener); }
    public void publish(String event) {
        for (ElevatorEventListener listener : listeners) listener.onEvent(event);
    }
}
