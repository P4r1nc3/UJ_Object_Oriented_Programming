package edu.uj.po.simulation.components.chips;

import java.util.ArrayList;
import java.util.List;

import edu.uj.po.simulation.components.chips.logic.LogicPerformer;
import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.components.PinType;
import edu.uj.po.simulation.components.chips.logic.DemultiplexerLogic;

public class Chip74138 extends Chip {
    private static final int[] INPUT_PINS = {6, 4, 5, 3, 2, 1};  // C, B, A, G2A, G2B, G1
    private static final int[] OUTPUT_PINS = {15, 14, 13, 12, 11, 10, 9, 7}; // Y7, Y6, Y5, Y4, Y3, Y2, Y1, Y0
    private final List<Pin> demultiplexerInputPins = new ArrayList<>();
    private final List<Pin> demultiplexerOutputPins = new ArrayList<>();

    public Chip74138(int id) {
        super(id);
    }

    @Override
    public void prepareComponent() {
        for (int pinNumber : INPUT_PINS) {
            Pin pin = new Pin(pinNumber, PinState.UNKNOWN, PinType.INPUT);
            pins.add(pin);
            demultiplexerInputPins.add(pin);
        }

        for (int pinNumber : OUTPUT_PINS) {
            Pin pin = new Pin(pinNumber, PinState.UNKNOWN, PinType.OUTPUT);
            pins.add(pin);
            demultiplexerOutputPins.add(pin);
        }
    }

    @Override
    public void beforeTickState() {
        LogicPerformer demultiplexer = new DemultiplexerLogic();
        newOutputPins = demultiplexer.performLogic(demultiplexerInputPins, demultiplexerOutputPins);
    }
}
