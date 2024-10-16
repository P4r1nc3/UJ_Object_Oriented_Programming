import edu.uj.po.simulation.interfaces.*;
import edu.uj.po.simulation.registry.ComponentRegistry;
import edu.uj.po.simulation.services.*;

import java.util.Map;
import java.util.Set;

public class Simulation implements UserInterface {
    private final ComponentCreationService componentCreationService;
    private final StationaryStateService stationaryStateService;
    private final SimulationService simulationService;
    private final ConnectionService connectionService;
    private final OptimizationService optimizationService;

    public Simulation() {
        ComponentRegistry.getInstance().reset();
        componentCreationService = new ComponentCreationService();
        stationaryStateService = new StationaryStateService();
        simulationService = new SimulationService();
        connectionService = new ConnectionService();
        optimizationService = new OptimizationService();
    }

    @Override
    public int createChip(int code) throws UnknownChip {
        return componentCreationService.createChip(code);
    }

    @Override
    public int createInputPinHeader(int size) {
        return componentCreationService.createInputPinHeader(size);
    }

    @Override
    public int createOutputPinHeader(int size) {
        return componentCreationService.createOutputPinHeader(size);
    }

    @Override
    public void connect(int component1, int pin1, int component2, int pin2)
            throws ShortCircuitException, UnknownPin, UnknownComponent {
        connectionService.connect(component1, pin1, component2, pin2);
    }

    @Override
    public void stationaryState(Set<ComponentPinState> states) throws UnknownStateException {
        stationaryStateService.stationaryState(states);
    }

    @Override
    public Map<Integer, Set<ComponentPinState>> simulation(Set<ComponentPinState> states0, int ticks)
            throws UnknownStateException {
        return simulationService.simulationWithoutUnknownStatesAllowed(states0, ticks);
    }

    @Override
    public Set<Integer> optimize(Set<ComponentPinState> states0, int ticks) throws UnknownStateException {
        return optimizationService.optimize(states0, ticks);
    }
}
