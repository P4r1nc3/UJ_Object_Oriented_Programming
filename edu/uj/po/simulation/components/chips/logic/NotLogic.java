package edu.uj.po.simulation.components.chips.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.interfaces.PinState;

public class NotLogic extends LogicPerformer {
    @Override
    protected Map<Integer, PinState> computeOutput(List<Pin> inputPins, List<Pin> outputPins) {
        Map<Integer, PinState> newOutputPins = new HashMap<>();

        for (int i = 0; i < inputPins.size(); i++) {
            PinState inputState = inputPins.get(i).getState();
            PinState outputState = inputState == PinState.HIGH ? PinState.LOW :
                    inputState == PinState.LOW ? PinState.HIGH :
                            PinState.UNKNOWN;

            newOutputPins.put(outputPins.get(i).getId(), outputState);
        }

        return newOutputPins;
    }
}
