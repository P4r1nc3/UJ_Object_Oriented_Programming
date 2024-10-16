package edu.uj.po.simulation.handlers;

import edu.uj.po.simulation.components.Component;
import edu.uj.po.simulation.registry.ComponentRegistry;
import edu.uj.po.simulation.interfaces.ShortCircuitException;
import edu.uj.po.simulation.interfaces.UnknownComponent;
import edu.uj.po.simulation.interfaces.UnknownPin;

import java.util.List;

public class UnknownComponentHandler implements ConnectionHandler {

    private ConnectionHandler nextHandler;

    @Override
    public void setNextHandler(ConnectionHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(int component1, int pin1, int component2, int pin2)
            throws UnknownComponent, UnknownPin, ShortCircuitException {
        List<Component> components = ComponentRegistry.getInstance().getComponents();

        if (component1 >= components.size() || component1 < 0) {
            throw new UnknownComponent(component1);
        }
        if (component2 >= components.size() || component2 < 0) {
            throw new UnknownComponent(component2);
        }

        if (nextHandler != null) {
            nextHandler.handleRequest(component1, pin1, component2, pin2);
        }
    }
}
