package edu.uj.po.simulation.components.chips;

import java.util.ArrayList;
import java.util.List;

import edu.uj.po.simulation.components.chips.logic.LogicPerformer;
import edu.uj.po.simulation.components.chips.logic.NandLogic;
import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.components.PinType;

public class Chip7420 extends Chip {
    private static final int[][] PIN_MAPPING = {
            {1, 2, 4, 5, 6},    // 1A, 1B, 1C, 1D, 1Y
            {9, 10, 12, 13, 8}  // 2A, 2B, 2C, 2D, 2Y
    };
    private final List<Pin> nandInputPins = new ArrayList<>();
    private final List<Pin> nandOutputPins = new ArrayList<>();

    public Chip7420(int id) {
        super(id);
    }

    @Override
    public void prepareComponent() {
        for (int[] mapping : PIN_MAPPING) {
            pins.add(new Pin(mapping[0], PinState.UNKNOWN, PinType.INPUT));   // A
            pins.add(new Pin(mapping[1], PinState.UNKNOWN, PinType.INPUT));   // B
            pins.add(new Pin(mapping[2], PinState.UNKNOWN, PinType.INPUT));   // C
            pins.add(new Pin(mapping[3], PinState.UNKNOWN, PinType.INPUT));   // D
            pins.add(new Pin(mapping[4], PinState.UNKNOWN, PinType.OUTPUT));  // Y

            nandInputPins.add(getPin(mapping[0]));
            nandInputPins.add(getPin(mapping[1]));
            nandInputPins.add(getPin(mapping[2]));
            nandInputPins.add(getPin(mapping[3]));
            nandOutputPins.add(getPin(mapping[4]));
        }
    }

    @Override
    public void beforeTickState() {
        LogicPerformer nand = new NandLogic();
        newOutputPins = nand.performLogic(nandInputPins, nandOutputPins);
    }
}
