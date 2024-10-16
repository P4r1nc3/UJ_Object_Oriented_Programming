package edu.uj.po.simulation.components.chips;

import java.util.ArrayList;
import java.util.List;

import edu.uj.po.simulation.components.chips.logic.LogicPerformer;
import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.components.PinType;
import edu.uj.po.simulation.components.chips.logic.BcdToDecimalLogic;

public class Chip7442 extends Chip {
    private static final int[] BCD_INPUT_PINS = {12, 13, 14, 15};
    private static final int[] DECIMAL_OUTPUT_PINS = {1, 2, 3, 4, 5, 6, 7, 9, 10, 11};
    private final List<Pin> bcdInputPins = new ArrayList<>();
    private final List<Pin> decimalOutputPins = new ArrayList<>();

    public Chip7442(int id) {
        super(id);
    }

    @Override
    public void prepareComponent() {
        for (int pinNumber : BCD_INPUT_PINS) {
            Pin pin = new Pin(pinNumber, PinState.UNKNOWN, PinType.INPUT);
            pins.add(pin);
            bcdInputPins.add(pin);
        }

        for (int pinNumber : DECIMAL_OUTPUT_PINS) {
            Pin pin = new Pin(pinNumber, PinState.UNKNOWN, PinType.OUTPUT);
            pins.add(pin);
            decimalOutputPins.add(pin);
        }
    }

    @Override
    public void beforeTickState() {
        LogicPerformer bcdToDecimal = new BcdToDecimalLogic();
        newOutputPins = bcdToDecimal.performLogic(bcdInputPins, decimalOutputPins);
    }
}
