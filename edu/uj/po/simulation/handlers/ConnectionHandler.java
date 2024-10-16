package edu.uj.po.simulation.handlers;

import edu.uj.po.simulation.interfaces.ShortCircuitException;
import edu.uj.po.simulation.interfaces.UnknownComponent;
import edu.uj.po.simulation.interfaces.UnknownPin;

public interface ConnectionHandler {
    void setNextHandler(ConnectionHandler nextHandler);
    void handleRequest(int component1, int pin1, int component2, int pin2)
            throws ShortCircuitException, UnknownComponent, UnknownPin;
}
