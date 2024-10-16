package edu.uj.po.simulation.components.chips.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.interfaces.PinState;

public class MultiplexerLogic extends LogicPerformer {
    @Override
    protected Map<Integer, PinState> computeOutput(List<Pin> inputPins, List<Pin> outputPins) {
        Map<Integer, PinState> newOutputPins = new HashMap<>();

        int decimalValue = 0;
        for (int i = 0; i < 3; i++) {
            if (inputPins.get(i).getState() == PinState.HIGH) {
                decimalValue += 1 << (2 - i);
            }
        }

        int outputPinIndex = decimalValue + 3;
        PinState inputStateToNegate = inputPins.get(outputPinIndex).getState();
        PinState outputState = (inputStateToNegate == PinState.HIGH) ? PinState.LOW :
                (inputStateToNegate == PinState.LOW) ? PinState.HIGH : PinState.UNKNOWN;

        newOutputPins.put(outputPins.get(0).getId(), outputState);

        return newOutputPins;
    }
}
