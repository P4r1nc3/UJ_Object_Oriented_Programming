package edu.uj.po.simulation.components.chips;

import java.util.ArrayList;
import java.util.List;

import edu.uj.po.simulation.components.chips.logic.LogicPerformer;
import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.components.PinType;
import edu.uj.po.simulation.components.chips.logic.IdentityLogic;

public class Chip7434 extends Chip {
    private static final int[][] PIN_MAPPING = {
            {1, 2},    // 1A, 1Y
            {3, 4},    // 2A, 2Y
            {5, 6},    // 3A, 3Y
            {9, 8},    // 4A, 4Y
            {11, 10},  // 5A, 5Y
            {13, 12}   // 6A, 6Y
    };
    private final List<Pin> identityInputPins = new ArrayList<>();
    private final List<Pin> identityOutputPins = new ArrayList<>();

    public Chip7434(int id) {
        super(id);
    }

    @Override
    public void prepareComponent() {
        for (int[] mapping : PIN_MAPPING) {
            pins.add(new Pin(mapping[0], PinState.UNKNOWN, PinType.INPUT));   // A
            pins.add(new Pin(mapping[1], PinState.UNKNOWN, PinType.OUTPUT));  // Y

            identityInputPins.add(getPin(mapping[0]));
            identityOutputPins.add(getPin(mapping[1]));
        }
    }

    @Override
    public void beforeTickState() {
        LogicPerformer identity = new IdentityLogic();
        newOutputPins = identity.performLogic(identityInputPins, identityOutputPins);
    }
}
