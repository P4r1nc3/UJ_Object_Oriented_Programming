package edu.uj.po.simulation.components.chips;

import edu.uj.po.simulation.components.PinType;
import edu.uj.po.simulation.components.chips.logic.LogicPerformer;
import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.components.chips.logic.MultiplexerLogic;

import java.util.List;
import java.util.ArrayList;

public class Chip74152 extends Chip {
    private static final int[] DATA_INPUT_PINS = {8, 9, 10, 5, 4, 3, 2, 1};  // D7 to D0 Inputs
    private static final int[] SELECTOR_PINS = {13, 12, 11};                 // S2, S1, S0 Selectors
    private static final int OUTPUT_PIN = 6;                                 // Y Output
    private final List<Pin> inputPins = new ArrayList<>();
    private final List<Pin> outputPins = new ArrayList<>();

    public Chip74152(int id) {
        super(id);
    }

    @Override
    public void prepareComponent() {
        for (int pinNumber : DATA_INPUT_PINS) {
            inputPins.add(new Pin(pinNumber, PinState.UNKNOWN, PinType.INPUT));
        }

        for (int pinNumber : SELECTOR_PINS) {
            inputPins.add(new Pin(pinNumber, PinState.UNKNOWN, PinType.INPUT));
        }

        Pin outputPin = new Pin(OUTPUT_PIN, PinState.UNKNOWN, PinType.OUTPUT);
        outputPins.add(outputPin);
        pins.addAll(inputPins);
        pins.addAll(outputPins);
    }

    @Override
    public void beforeTickState() {
        LogicPerformer multiplexer = new MultiplexerLogic();
        newOutputPins = multiplexer.performLogic(inputPins, outputPins);
    }
}
