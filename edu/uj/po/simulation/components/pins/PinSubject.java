package edu.uj.po.simulation.components.pins;

import java.util.ArrayList;
import java.util.List;

public class PinSubject {
    private final List<PinObserver> observers = new ArrayList<>();

    public void registerObserver(PinObserver observers) {
        this.observers.add(observers);
    }

    public void notifyObservers() {
        for (PinObserver observer : observers) {
            observer.update(observer);
        }
    }
}
