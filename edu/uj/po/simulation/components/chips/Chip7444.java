package edu.uj.po.simulation.components.chips;

import java.util.ArrayList;
import java.util.List;

import edu.uj.po.simulation.components.chips.logic.LogicPerformer;
import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.components.PinType;
import edu.uj.po.simulation.components.chips.logic.GrayToDecimalLogic;

public class Chip7444 extends Chip {
    private static final int[] GRAY_INPUT_PINS = {15, 14, 13, 12};
    private static final int[] DECIMAL_OUTPUT_PINS = {1, 2, 3, 4, 5, 6, 7, 9, 10, 11};
    private final List<Pin> grayInputPins = new ArrayList<>();
    private final List<Pin> decimalOutputPins = new ArrayList<>();

    public Chip7444(int id) {
        super(id);
    }

    @Override
    public void prepareComponent() {
        for (int pinNumber : GRAY_INPUT_PINS) {
            Pin pin = new Pin(pinNumber, PinState.UNKNOWN, PinType.INPUT);
            pins.add(pin);
            grayInputPins.add(pin);
        }

        for (int pinNumber : DECIMAL_OUTPUT_PINS) {
            Pin pin = new Pin(pinNumber, PinState.UNKNOWN, PinType.OUTPUT);
            pins.add(pin);
            decimalOutputPins.add(pin);
        }
    }

    @Override
    public void beforeTickState() {
        LogicPerformer grayToDecimal = new GrayToDecimalLogic();
        newOutputPins = grayToDecimal.performLogic(grayInputPins, decimalOutputPins);
    }
}
