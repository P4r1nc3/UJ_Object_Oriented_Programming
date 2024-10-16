package edu.uj.po.simulation.components.chips;

import java.util.ArrayList;
import java.util.List;

import edu.uj.po.simulation.components.chips.logic.LogicPerformer;
import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.components.PinType;
import edu.uj.po.simulation.components.chips.logic.NorLogic;

public class Chip7402 extends Chip {
    private static final int[][] PIN_MAPPING = {
            {2, 3, 1},   // 1A, 1B, 1Y
            {5, 6, 4},   // 2A, 2B, 2Y
            {8, 9, 10},  // 3A, 3B, 3Y
            {11, 12, 13} // 4A, 4B, 4Y
    };
    private final List<Pin> norInputPins = new ArrayList<>();
    private final List<Pin> norOutputPins = new ArrayList<>();

    public Chip7402(int id) {
        super(id);
    }

    @Override
    public void prepareComponent() {
        for (int[] mapping : PIN_MAPPING) {
            pins.add(new Pin(mapping[0], PinState.UNKNOWN, PinType.INPUT));   // A
            pins.add(new Pin(mapping[1], PinState.UNKNOWN, PinType.INPUT));   // B
            pins.add(new Pin(mapping[2], PinState.UNKNOWN, PinType.OUTPUT));  // Y

            norInputPins.add(getPin(mapping[0]));
            norInputPins.add(getPin(mapping[1]));
            norOutputPins.add(getPin(mapping[2]));
        }
    }

    @Override
    public void beforeTickState() {
        LogicPerformer nor = new NorLogic();
        newOutputPins = nor.performLogic(norInputPins, norOutputPins);
    }
}
