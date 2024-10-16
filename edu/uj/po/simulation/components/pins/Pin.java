package edu.uj.po.simulation.components.pins;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.components.PinType;

public class Pin implements Serializable {
    private final int id;
    private final PinType pinType;
    private final PinSubject pinSubject;
    private final List<Pin> connectedPins = new ArrayList<>();
    private PinState state;
    private PinState previousState;

    private boolean isInSimulation = false;

    public Pin(int id, PinState state, PinType pinType) {
        this.id = id;
        this.state = state;
        this.pinType = pinType;
        this.previousState = state;
        this.pinSubject = new PinSubject();
    }

    public int getId() {
        return id;
    }

    public PinState getState() {
        return state;
    }

    public void setState(PinState state) {
        if (state != previousState) {
            this.state = state;
            previousState = state;
            pinSubject.notifyObservers();
        }
    }

    public void resetPin() {
        setState(PinState.UNKNOWN);
        previousState = PinState.UNKNOWN;
        isInSimulation = false;
        connectedPins.clear();
    }

    public PinSubject getPinManager() {
        return pinSubject;
    }

    public boolean getIsInSimulation() {
        return isInSimulation;
    }

    public void setIsInSimulation(boolean isInSimulation) {
        this.isInSimulation = isInSimulation;
    }

    public List<Pin> getConnectedPins() {
        return connectedPins;
    }

    public void addConnectedPin(Pin pin) {
        connectedPins.add(pin);
    }

    public void addConnectedPins(List<Pin> pins) {
        connectedPins.addAll(pins);
    }

    public PinType getPinType() {
        return pinType;
    }
}
