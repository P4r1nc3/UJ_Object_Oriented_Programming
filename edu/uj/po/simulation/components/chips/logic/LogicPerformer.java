package edu.uj.po.simulation.components.chips.logic;

import java.util.List;
import java.util.Map;

import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.interfaces.PinState;

public abstract class LogicPerformer {

    public final Map<Integer, PinState> performLogic(List<Pin> inputPins, List<Pin> outputPins) {
        setMissingInputs(inputPins, outputPins);
        return computeOutput(inputPins, outputPins);
    }

    private void setMissingInputs(List<Pin> inputPins, List<Pin> outputPins) {
        int inputsPerOutput = inputPins.size() / outputPins.size();

        for (int i = 0; i < outputPins.size(); i++) {
            if (outputPins.get(i).getIsInSimulation()) {
                for (int j = 0; j < inputsPerOutput; j++) {
                    inputPins.get(i * inputsPerOutput + j).setIsInSimulation(true);
                }
            }
        }
    }

    protected abstract Map<Integer, PinState> computeOutput(List<Pin> inputPins, List<Pin> outputPins);
}
