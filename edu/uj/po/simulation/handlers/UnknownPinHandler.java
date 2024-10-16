package edu.uj.po.simulation.handlers;

import edu.uj.po.simulation.components.Component;
import edu.uj.po.simulation.registry.ComponentRegistry;
import edu.uj.po.simulation.interfaces.ShortCircuitException;
import edu.uj.po.simulation.interfaces.UnknownComponent;
import edu.uj.po.simulation.interfaces.UnknownPin;

import java.util.List;

public class UnknownPinHandler implements ConnectionHandler {

    private ConnectionHandler nextHandler;

    @Override
    public void setNextHandler(ConnectionHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(int component1, int pin1, int component2, int pin2)
            throws UnknownPin, ShortCircuitException, UnknownComponent {
        List<Component> components = ComponentRegistry.getInstance().getComponents();

        Component sourceComponent = components.get(component1);
        Component targetComponent = components.get(component2);

        if (sourceComponent.getPin(pin1) == null) {
            throw new UnknownPin(sourceComponent.getId(), pin1);
        }
        if (targetComponent.getPin(pin2) == null) {
            throw new UnknownPin(targetComponent.getId(), pin2);
        }

        if (nextHandler != null) {
            nextHandler.handleRequest(component1, pin1, component2, pin2);
        }
    }
}
