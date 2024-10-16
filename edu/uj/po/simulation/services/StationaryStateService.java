package edu.uj.po.simulation.services;

import edu.uj.po.simulation.components.chips.Chip;
import edu.uj.po.simulation.components.Component;
import edu.uj.po.simulation.components.PinHeader;
import edu.uj.po.simulation.components.PinType;
import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.interfaces.ComponentPinState;
import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.interfaces.UnknownStateException;
import edu.uj.po.simulation.registry.ComponentRegistry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StationaryStateService {
    private final List<Component> components;

    public StationaryStateService() {
        this.components = ComponentRegistry.getInstance().getComponents();
    }

    public void stationaryState(Set<ComponentPinState> states) throws UnknownStateException {
        updatePinStates(states);
        validateInitialStates();
        runSimulationUntilStable();
        validateFinalStates();
    }

    private void updatePinStates(Set<ComponentPinState> states) {
        for (ComponentPinState componentPinState : states) {
            Component component = components.get(componentPinState.componentId());
            if (component != null) {
                Pin pin = component.getPin(componentPinState.pinId());
                if (pin != null) {
                    pin.setState(componentPinState.state());
                }
            }
        }
    }

    private void validateInitialStates() throws UnknownStateException {
        for (Component component : components) {
            if (component instanceof PinHeader && ((PinHeader) component).getPinHeaderType() == PinType.INPUT) {
                checkPinsForUnknownState(component);
            }
        }
    }

    private void validateFinalStates() throws UnknownStateException {
        for (Component component : components) {
            checkPinsForUnknownState(component);
        }
    }

    private void checkPinsForUnknownState(Component component) throws UnknownStateException {
        for (Pin pin : component.getPins()) {
            if (pin.getState() == PinState.UNKNOWN && pin.getIsInSimulation()) {
                throw new UnknownStateException(
                        new ComponentPinState(components.indexOf(component), pin.getId(), pin.getState()));
            }
        }
    }

    private void runSimulationUntilStable() {
        SimulationService simulationService = new SimulationService();

        while (true) {
            Set<ComponentPinState> previousState = getChipPinStates();
            simulationService.tick();
            Set<ComponentPinState> currentState = getChipPinStates();
            if (previousState.equals(currentState)) break;
        }
    }

    private Set<ComponentPinState> getChipPinStates() {
        Set<ComponentPinState> states = new HashSet<>();
        components.stream()
                .filter(component -> component instanceof Chip)
                .forEach(component ->
                        component.getPins().forEach(pin ->
                                states.add(new ComponentPinState(components.indexOf(component), pin.getId(), pin.getState()))
                        )
                );
        return states;
    }
}
