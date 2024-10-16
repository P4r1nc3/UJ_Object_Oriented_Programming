package edu.uj.po.simulation.handlers;

import edu.uj.po.simulation.components.Component;
import edu.uj.po.simulation.components.PinType;
import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.interfaces.ShortCircuitException;
import edu.uj.po.simulation.interfaces.UnknownComponent;
import edu.uj.po.simulation.interfaces.UnknownPin;
import edu.uj.po.simulation.registry.ComponentRegistry;

import java.util.List;

public class ShortCircuitHandler implements ConnectionHandler {

    private ConnectionHandler nextHandler;

    @Override
    public void setNextHandler(ConnectionHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(int component1, int pin1, int component2, int pin2)
            throws ShortCircuitException, UnknownPin, UnknownComponent {
        List<Component> components = ComponentRegistry.getInstance().getComponents();

        Component sourceComponent = components.get(component1);
        Component targetComponent = components.get(component2);

        Pin sourcePin = sourceComponent.getPin(pin1);
        Pin targetPin = targetComponent.getPin(pin2);

        if (sourcePin.getPinType() == PinType.OUTPUT && targetPin.getPinType() == PinType.OUTPUT) {
            throw new ShortCircuitException();
        }

        sourcePin.addConnectedPin(targetPin);
        targetPin.addConnectedPin(sourcePin);
        sourcePin.addConnectedPins(targetPin.getConnectedPins());
        targetPin.addConnectedPins(sourcePin.getConnectedPins());

        checkForMultipleOutputPins(sourcePin.getConnectedPins());
        checkForMultipleOutputPins(targetPin.getConnectedPins());

        if (nextHandler != null) {
            nextHandler.handleRequest(component1, pin1, component2, pin2);
        }
    }

    private void checkForMultipleOutputPins(List<Pin> pins) throws ShortCircuitException {
        long outputPinCount = pins.stream()
                .distinct()
                .filter(pin -> pin.getPinType().equals(PinType.OUTPUT))
                .count();

        if (outputPinCount >= 2) throw new ShortCircuitException();
    }
}
