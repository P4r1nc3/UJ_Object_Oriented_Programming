package edu.uj.po.simulation.components.chips.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.interfaces.PinState;

public class GrayToDecimalLogic extends LogicPerformer {
    @Override
    protected Map<Integer, PinState> computeOutput(List<Pin> inputPins, List<Pin> outputPins) {
        Map<Integer, PinState> newOutputPins = new HashMap<>();

        for (Pin inputPin : inputPins) {
            if (inputPin.getState() == PinState.UNKNOWN) {
                return newOutputPins;
            }
        }

        int grayCode = 0;
        for (int i = 0; i < 4; i++) {
            if (inputPins.get(i).getState() == PinState.HIGH) {
                grayCode |= 1 << (3 - i);
            }
        }

        int decimalValue = 0;
        int tempGray = grayCode;
        while (tempGray > 0) {
            decimalValue ^= tempGray;
            tempGray >>= 1;
        }
        decimalValue -= 3;

        for (int i = 0; i < outputPins.size(); i++) {
            newOutputPins.put(outputPins.get(i).getId(), i == decimalValue ? PinState.LOW : PinState.HIGH);
        }

        return newOutputPins;
    }
}
