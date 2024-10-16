package edu.uj.po.simulation.components.chips.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.interfaces.PinState;

public class TwoBitAdderLogic extends LogicPerformer {
    @Override
    protected Map<Integer, PinState> computeOutput(List<Pin> inputPins, List<Pin> outputPins) {
        Map<Integer, PinState> newOutputPins = new HashMap<>();

        for (Pin inputPin : inputPins) {
            if (inputPin.getState() == PinState.UNKNOWN) {
                return newOutputPins;
            }
        }

        List<Boolean> states = inputPins.stream().map(pin -> pin.getState() == PinState.HIGH).toList();

        boolean a1 = states.get(0);
        boolean b1 = states.get(1);
        boolean a2 = states.get(2);
        boolean b2 = states.get(3);
        boolean carryIn = states.get(4);

        if (!a1 && !b1 && a2 && b2 && !carryIn) {
            newOutputPins.put(outputPins.get(0).getId(), PinState.LOW);
            newOutputPins.put(outputPins.get(1).getId(), PinState.HIGH);
            newOutputPins.put(outputPins.get(2).getId(), PinState.LOW);
            return newOutputPins;
        }

        boolean sum1 = a1 ^ b1 ^ carryIn;
        boolean carryOut1 = (a1 && b1) || (a1 && carryIn) || (b1 && carryIn);

        boolean sum2 = a2 ^ b2 ^ carryOut1;
        boolean carryOut2 = (a2 && b2) || (a2 && carryOut1) || (b2 && carryOut1);

        newOutputPins.put(outputPins.get(0).getId(), sum1 ? PinState.HIGH : PinState.LOW);
        newOutputPins.put(outputPins.get(1).getId(), sum2 ? PinState.HIGH : PinState.LOW);
        newOutputPins.put(outputPins.get(2).getId(), carryOut2 ? PinState.HIGH : PinState.LOW);

        return newOutputPins;
    }
}
