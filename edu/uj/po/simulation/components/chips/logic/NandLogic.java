package edu.uj.po.simulation.components.chips.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.interfaces.PinState;

public class NandLogic extends LogicPerformer {
    @Override
    protected Map<Integer, PinState> computeOutput(List<Pin> inputPins, List<Pin> outputPins) {
        Map<Integer, PinState> newOutputPins = new HashMap<>();

        int inputsPerOutput = inputPins.size() / outputPins.size();

        for (int i = 0; i < outputPins.size(); i++) {
            PinState result = PinState.LOW;
            for (int j = 0; j < inputsPerOutput; j++) {
                PinState state = inputPins.get(i * inputsPerOutput + j).getState();
                if (state == PinState.LOW) {
                    result = PinState.HIGH;
                    break;
                }
                if (state == PinState.UNKNOWN) {
                    result = PinState.UNKNOWN;
                }
            }
            newOutputPins.put(outputPins.get(i).getId(), result);
        }

        return newOutputPins;
    }
}
