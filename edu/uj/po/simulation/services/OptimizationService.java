package edu.uj.po.simulation.services;

import edu.uj.po.simulation.components.Component;
import edu.uj.po.simulation.components.chips.Chip;
import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.interfaces.ComponentPinState;
import edu.uj.po.simulation.registry.ComponentRegistry;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OptimizationService {
    private final SimulationService simulationService;
    private final List<Component> components;
    private List<Component> backupComponents;

    public OptimizationService() {
        this.simulationService = new SimulationService();
        this.components = ComponentRegistry.getInstance().getComponents();
    }

    public Set<Integer> optimize(Set<ComponentPinState> initialStates, int ticks) {
        backupComponents = new ArrayList<>(components);

        Set<ComponentPinState> startingStates = getComponentPinStates();
        resetComponentsToStates(startingStates);

        return findBestCombination(ticks, initialStates, startingStates);
    }

    private Set<Integer> findBestCombination(int ticks,
                                             Set<ComponentPinState> initialStates,
                                             Set<ComponentPinState> startingStates) {
        Map<Integer, Set<ComponentPinState>> targetResults = simulationService.simulationWithUnknownStatesAllowed(initialStates, ticks);

        List<Integer> chipIds = getChipIds();

        List<List<Integer>> allCombinations = generateAllCombinations(chipIds);

        Set<Integer> bestCombination = Collections.emptySet();
        for (List<Integer> combination : allCombinations) {
            applyCombination(combination);

            Map<Integer, Set<ComponentPinState>> simulationResults = simulationService.simulationWithUnknownStatesAllowed(initialStates, ticks);

            Set<Integer> combinationSet = new HashSet<>(combination);
            if (simulationResults.equals(targetResults) && combinationSet.size() > bestCombination.size()) {
                bestCombination = combinationSet;
            }

            restoreComponents(backupComponents, startingStates);
        }
        return bestCombination;
    }

    private List<Integer> getChipIds() {
        return components.stream()
                .filter(component -> component instanceof Chip)
                .map(components::indexOf)
                .collect(Collectors.toList());
    }

    private List<List<Integer>> generateAllCombinations(List<Integer> circuitIds) {
        List<List<Integer>> allCombinations = new ArrayList<>();
        generateCombinations(0, circuitIds, new ArrayList<>(), allCombinations);
        return allCombinations;
    }

    private void generateCombinations(int index, List<Integer> circuitIds, List<Integer> current, List<List<Integer>> result) {
        if (!current.isEmpty()) result.add(new ArrayList<>(current));
        IntStream.range(index, circuitIds.size()).forEach(i -> {
            current.add(circuitIds.get(i));
            generateCombinations(i + 1, circuitIds, current, result);
            current.remove(current.size() - 1);
        });
    }

    private void applyCombination(List<Integer> combination) {
        for (Integer id : combination) {
            Component component = components.get(id);
            if (component != null) {
                components.set(id, null);
                component.getPins().forEach(Pin::resetPin);
            }
        }
    }

    private void restoreComponents(List<Component> backupComponents, Set<ComponentPinState> startingStates) {
        for (int i = 0; i < components.size(); i++) {
            components.set(i, backupComponents.get(i));
        }
        resetComponentsToStates(startingStates);
    }

    private Set<ComponentPinState> getComponentPinStates() {
        Set<ComponentPinState> states = new HashSet<>();
        components.forEach(component ->
                component.getPins().forEach(pin ->
                        states.add(new ComponentPinState(components.indexOf(component), pin.getId(), pin.getState()))
                )
        );
        return states;
    }

    private void resetComponentsToStates(Set<ComponentPinState> states) {
        for (ComponentPinState state : states) {
            components.get(state.componentId()).getPin(state.pinId()).setState(state.state());
        }
    }
}
