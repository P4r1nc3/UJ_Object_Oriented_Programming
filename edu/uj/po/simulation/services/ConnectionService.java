package edu.uj.po.simulation.services;

import edu.uj.po.simulation.components.Component;
import edu.uj.po.simulation.components.PinType;
import edu.uj.po.simulation.components.pins.Connection;
import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.handlers.UnknownComponentHandler;
import edu.uj.po.simulation.handlers.ConnectionHandler;
import edu.uj.po.simulation.handlers.ShortCircuitHandler;
import edu.uj.po.simulation.handlers.UnknownPinHandler;
import edu.uj.po.simulation.interfaces.ShortCircuitException;
import edu.uj.po.simulation.interfaces.UnknownComponent;
import edu.uj.po.simulation.interfaces.UnknownPin;
import edu.uj.po.simulation.registry.ComponentRegistry;

import java.util.ArrayList;
import java.util.List;

public class ConnectionService {
    private final List<Component> components;
    private final List<Connection> connections;

    public ConnectionService() {
        this.components = ComponentRegistry.getInstance().getComponents();
        this.connections = new ArrayList<>();
    }

    public void connect(int component1, int pin1, int component2, int pin2)
            throws ShortCircuitException, UnknownPin, UnknownComponent {
        ConnectionHandler chain = createConnectionHandlerChain();
        chain.handleRequest(component1, pin1, component2, pin2);
        addConnectionToList(component1, pin1, component2, pin2);
    }

    private ConnectionHandler createConnectionHandlerChain() {
        ConnectionHandler componentExistenceHandler = new UnknownComponentHandler();
        ConnectionHandler unknownPinHandler = new UnknownPinHandler();
        ConnectionHandler shortCircuitHandler = new ShortCircuitHandler();

        componentExistenceHandler.setNextHandler(unknownPinHandler);
        unknownPinHandler.setNextHandler(shortCircuitHandler);

        return componentExistenceHandler;
    }

    private void addConnectionToList(int component1, int pin1, int component2, int pin2) {
        Component sourceComponent = components.get(component1);
        Component targetComponent = components.get(component2);

        Pin sourcePin = sourceComponent.getPin(pin1);
        Pin targetPin = targetComponent.getPin(pin2);

        Connection connection = new Connection(sourcePin, targetPin);

        if (sourcePin.getPinType().equals(PinType.INPUT) && targetPin.getPinType().equals(PinType.OUTPUT)) {
            connection = new Connection(targetPin, sourcePin);
        }
        if (sourcePin.getPinType().equals(PinType.INPUT) && targetPin.getPinType().equals(PinType.INPUT)) {
            connection = new Connection(targetPin, sourcePin);
        }

        connections.add(connection);
    }
}
