package edu.uj.po.simulation.components.chips;

import java.util.ArrayList;
import java.util.List;

import edu.uj.po.simulation.components.chips.logic.LogicPerformer;
import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.components.PinType;
import edu.uj.po.simulation.components.chips.logic.OrLogic;

public class Chip7432 extends Chip {
    private static final int[][] PIN_MAPPING = {
            {1, 2, 3},   // 1A, 1B, 1Y
            {4, 5, 6},   // 2A, 2B, 2Y
            {10, 9, 8},  // 3A, 3B, 3Y
            {13, 12, 11} // 4A, 4B, 4Y
    };
    private final List<Pin> orInputPins = new ArrayList<>();
    private final List<Pin> orOutputPins = new ArrayList<>();

    public Chip7432(int id) {
        super(id);
    }

    @Override
    public void prepareComponent() {
        for (int[] mapping : PIN_MAPPING) {
            pins.add(new Pin(mapping[0], PinState.UNKNOWN, PinType.INPUT));   // A
            pins.add(new Pin(mapping[1], PinState.UNKNOWN, PinType.INPUT));   // B
            pins.add(new Pin(mapping[2], PinState.UNKNOWN, PinType.OUTPUT));  // Y

            orInputPins.add(getPin(mapping[0]));
            orInputPins.add(getPin(mapping[1]));
            orOutputPins.add(getPin(mapping[2]));
        }
    }

    @Override
    public void beforeTickState() {
        LogicPerformer or = new OrLogic();
        newOutputPins = or.performLogic(orInputPins, orOutputPins);
    }
}
