package edu.uj.po.simulation.components.chips;

import java.util.ArrayList;
import java.util.List;

import edu.uj.po.simulation.components.chips.logic.LogicPerformer;
import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.components.PinType;
import edu.uj.po.simulation.components.chips.logic.AndLogic;

public class Chip7408 extends Chip {
    private static final int[][] PIN_MAPPING = {
            {1, 2, 3},   // 1A, 1B, 1Y
            {4, 5, 6},   // 2A, 2B, 2Y
            {9, 10, 8},  // 3A, 3B, 3Y
            {12, 13, 11} // 4A, 4B, 4Y
    };
    private final List<Pin> andInputPins = new ArrayList<>();
    private final List<Pin> andOutputPins = new ArrayList<>();

    public Chip7408(int id) {
        super(id);
    }

    @Override
    public void prepareComponent() {
        for (int[] mapping : PIN_MAPPING) {
            pins.add(new Pin(mapping[0], PinState.UNKNOWN, PinType.INPUT));   // A
            pins.add(new Pin(mapping[1], PinState.UNKNOWN, PinType.INPUT));   // B
            pins.add(new Pin(mapping[2], PinState.UNKNOWN, PinType.OUTPUT));  // Y

            andInputPins.add(getPin(mapping[0]));
            andInputPins.add(getPin(mapping[1]));
            andOutputPins.add(getPin(mapping[2]));
        }
    }

    @Override
    public void beforeTickState() {
        LogicPerformer and = new AndLogic();
        newOutputPins = and.performLogic(andInputPins, andOutputPins);
    }
}
