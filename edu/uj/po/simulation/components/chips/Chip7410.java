package edu.uj.po.simulation.components.chips;

import java.util.ArrayList;
import java.util.List;

import edu.uj.po.simulation.components.chips.logic.LogicPerformer;
import edu.uj.po.simulation.components.chips.logic.NandLogic;
import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.components.PinType;

public class Chip7410 extends Chip {
    private static final int[][] PIN_MAPPING = {
            {1, 2, 13, 12},  // 1A, 1B, 1C, 1Y
            {3, 4, 5, 6},    // 2A, 2B, 2C, 2Y
            {9, 10, 11, 8}   // 3A, 3B, 3C, 3Y
    };
    private final List<Pin> nandInputPins = new ArrayList<>();
    private final List<Pin> nandOutputPins = new ArrayList<>();

    public Chip7410(int id) {
        super(id);
    }

    @Override
    public void prepareComponent() {
        for (int[] mapping : PIN_MAPPING) {
            pins.add(new Pin(mapping[0], PinState.UNKNOWN, PinType.INPUT));   // A
            pins.add(new Pin(mapping[1], PinState.UNKNOWN, PinType.INPUT));   // B
            pins.add(new Pin(mapping[2], PinState.UNKNOWN, PinType.INPUT));   // C
            pins.add(new Pin(mapping[3], PinState.UNKNOWN, PinType.OUTPUT));  // Y

            nandInputPins.add(getPin(mapping[0]));
            nandInputPins.add(getPin(mapping[1]));
            nandInputPins.add(getPin(mapping[2]));
            nandOutputPins.add(getPin(mapping[3]));
        }
    }

    @Override
    public void beforeTickState() {
        LogicPerformer nand = new NandLogic();
        newOutputPins = nand.performLogic(nandInputPins, nandOutputPins);
    }
}
