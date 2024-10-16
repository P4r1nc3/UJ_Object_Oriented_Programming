package edu.uj.po.simulation.components.pins;

public class Connection implements PinObserver {

    private final Pin sourcePin;
    private final Pin targetPin;

    public Connection(Pin sourcePin, Pin targetPin) {
        this.sourcePin = sourcePin;
        this.targetPin = targetPin;
        initializePins();
        syncTargetPinState();
        subscribeToSourcePin();
    }

    private void initializePins() {
        sourcePin.setIsInSimulation(true);
        targetPin.setIsInSimulation(true);
    }

    private void syncTargetPinState() {
        if (sourcePin.getState() != targetPin.getState()) {
            updateTargetPinState();
        }
    }

    private void subscribeToSourcePin() {
        sourcePin.getPinManager().registerObserver(this);
    }

    private void updateTargetPinState() {
        targetPin.setState(sourcePin.getState());
    }

    @Override
    public void update(PinObserver observer) {
        if (observer == this) {
            updateTargetPinState();
        }
    }
}
