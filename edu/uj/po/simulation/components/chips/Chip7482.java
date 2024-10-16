package edu.uj.po.simulation.components.chips;

import java.util.ArrayList;
import java.util.List;

import edu.uj.po.simulation.components.chips.logic.LogicPerformer;
import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.components.PinType;
import edu.uj.po.simulation.components.chips.logic.TwoBitAdderLogic;

public class Chip7482 extends Chip {
    private static final int[] INPUT_PINS = {2, 3, 14, 13, 5};  // A1, B1, A2, B2, Carry In
    private static final int[] OUTPUT_PINS = {1, 12, 10};       // Sum1, Sum2, Carry Out
    private final List<Pin> adderInputPins = new ArrayList<>();
    private final List<Pin> adderOutputPins = new ArrayList<>();

    public Chip7482(int id) {
        super(id);
    }

    @Override
    public void prepareComponent() {
        for (int pinNumber : INPUT_PINS) {
            Pin pin = new Pin(pinNumber, PinState.UNKNOWN, PinType.INPUT);
            pins.add(pin);
            adderInputPins.add(pin);
        }

        for (int pinNumber : OUTPUT_PINS) {
            Pin pin = new Pin(pinNumber, PinState.UNKNOWN, PinType.OUTPUT);
            pins.add(pin);
            adderOutputPins.add(pin);
        }
    }

    @Override
    public void beforeTickState() {
        LogicPerformer twoBitAdder = new TwoBitAdderLogic();
        newOutputPins = twoBitAdder.performLogic(adderInputPins, adderOutputPins);
    }
}
