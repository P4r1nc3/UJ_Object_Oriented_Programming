package edu.uj.po.simulation.components.chips.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.interfaces.PinState;

public class DemultiplexerLogic extends LogicPerformer {
    @Override
    protected Map<Integer, PinState> computeOutput(List<Pin> inputPins, List<Pin> outputPins) {
        Map<Integer, PinState> newOutputPins = new HashMap<>();

        PinState controlPin1State = inputPins.get(0).getState();
        PinState invertedControlPin2State = inputPins.get(1).getState();
        PinState invertedControlPin3State = inputPins.get(2).getState();

        boolean controlPin1 = controlPin1State == PinState.HIGH;
        boolean invertedControlPin2 = invertedControlPin2State == PinState.HIGH;
        boolean invertedControlPin3 = invertedControlPin3State == PinState.HIGH;
        boolean combinedInvertedControlPins = invertedControlPin2 && invertedControlPin3;

        if ((invertedControlPin2State == PinState.UNKNOWN && invertedControlPin3State == PinState.UNKNOWN && controlPin1) ||
                (controlPin1State == PinState.UNKNOWN && !combinedInvertedControlPins)) {
            return newOutputPins;
        }

        boolean isEnabled = controlPin1 && !combinedInvertedControlPins;

        if (!isEnabled) {
            outputPins.forEach(outputPin -> newOutputPins.put(outputPin.getId(), PinState.HIGH));
            return newOutputPins;
        }

        int selectedOutput = 0;
        for (int i = 0; i < 3; i++) {
            if (inputPins.get(i + 3).getState() == PinState.HIGH) {
                selectedOutput += 1 << (2 - i);
            }
        }

        for (int i = 0; i < outputPins.size(); i++) {
            newOutputPins.put(outputPins.get(i).getId(), i == selectedOutput ? PinState.LOW : PinState.HIGH);
        }

        return newOutputPins;
    }
}
