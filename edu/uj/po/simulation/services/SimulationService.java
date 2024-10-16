package edu.uj.po.simulation.services;

import edu.uj.po.simulation.components.Component;
import edu.uj.po.simulation.components.PinHeader;
import edu.uj.po.simulation.components.PinType;
import edu.uj.po.simulation.components.chips.Chip;
import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.interfaces.ComponentPinState;
import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.interfaces.UnknownStateException;
import edu.uj.po.simulation.registry.ComponentRegistry;

import java.util.*;

public class SimulationService {
    private final List<Component> components;

    public SimulationService() {
        this.components = ComponentRegistry.getInstance().getComponents();
    }

    public Map<Integer, Set<ComponentPinState>> simulationWithoutUnknownStatesAllowed(Set<ComponentPinState> initialStates, int ticks)
            throws UnknownStateException {
        validateNoUnknownStates(initialStates);
        validateComponentsHaveNoUnknownStates();
        return runSimulation(initialStates, ticks);
    }

    public Map<Integer, Set<ComponentPinState>> simulationWithUnknownStatesAllowed(Set<ComponentPinState> initialStates, int ticks) {
        return runSimulation(initialStates, ticks);
    }

    public Set<ComponentPinState> tick() {
        List<Chip> chips = getChips();
        chips.forEach(Chip::beforeTickState);
        chips.forEach(Chip::afterTickState);
        return getOutputPinHeaderStates();
    }

    private void validateNoUnknownStates(Set<ComponentPinState> states) throws UnknownStateException {
        for (ComponentPinState state : states) {
            if (state.state() == PinState.UNKNOWN) {
                throw new UnknownStateException(state);
            }
        }
    }

    private void validateComponentsHaveNoUnknownStates() throws UnknownStateException {
        for (Component component : components) {
            for (Pin pin : component.getPins()) {
                if (pin.getState() == PinState.UNKNOWN && pin.getIsInSimulation()) {
                    throw new UnknownStateException(
                            new ComponentPinState(components.indexOf(component), pin.getId(), pin.getState()));
                }
            }
        }
    }

    private Map<Integer, Set<ComponentPinState>> runSimulation(Set<ComponentPinState> initialStates, int ticks) {
        Map<Integer, Set<ComponentPinState>> simulationResults = new HashMap<>();
        simulationResults.put(0, getOutputPinHeaderStates());
        applyInitialStates(initialStates);

        for (int tick = 1; tick <= ticks; tick++) {
            simulationResults.put(tick, tick());
        }

        return simulationResults;
    }

    private void applyInitialStates(Set<ComponentPinState> states) {
        for (ComponentPinState state : states) {
            Component component = components.get(state.componentId());
            if (component != null) {
                Pin pin = component.getPin(state.pinId());
                if (pin != null) {
                    pin.setState(state.state());
                }
            }
        }
    }

    private List<Chip> getChips() {
        return components.stream()
                .filter(component -> component instanceof Chip)
                .map(component -> (Chip) component)
                .toList();
    }

    private Set<ComponentPinState> getOutputPinHeaderStates() {
        Set<ComponentPinState> states = new HashSet<>();
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            if (component instanceof PinHeader && ((PinHeader) component).getPinHeaderType() == PinType.OUTPUT) {
                for (Pin pin : component.getPins()) {
                    states.add(new ComponentPinState(i, pin.getId(), pin.getState()));
                }
            }
        }
        return states;
    }
}
