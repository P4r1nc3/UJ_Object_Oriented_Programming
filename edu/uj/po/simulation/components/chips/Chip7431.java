package edu.uj.po.simulation.components.chips;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.uj.po.simulation.components.chips.logic.*;
import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.components.PinType;

public class Chip7431 extends Chip {
    private static final int[][] NOT_PIN_MAPPING = {
            {1, 2},  // NOT 1A, 1Y
            {15, 14} // NOT 2A, 2Y
    };

    private static final int[][] IDENTITY_PIN_MAPPING = {
            {3, 4},   // Identity 1A, 1Y
            {13, 12}  // Identity 2A, 2Y
    };

    private static final int[][] NAND_PIN_MAPPING = {
            {5, 6, 7},   // NAND 1A, 1B, 1Y
            {10, 11, 9}  // NAND 2A, 2B, 2Y
    };
    private final List<Pin> notInputPins = new ArrayList<>();
    private final List<Pin> notOutputPins = new ArrayList<>();
    private final List<Pin> identityInputPins = new ArrayList<>();
    private final List<Pin> identityOutputPins = new ArrayList<>();
    private final List<Pin> nandInputPins = new ArrayList<>();
    private final List<Pin> nandOutputPins = new ArrayList<>();

    public Chip7431(int id) {
        super(id);
    }

    @Override
    public void prepareComponent() {
        // Prepare NOT pins
        for (int[] mapping : NOT_PIN_MAPPING) {
            pins.add(new Pin(mapping[0], PinState.UNKNOWN, PinType.INPUT));   // A
            pins.add(new Pin(mapping[1], PinState.UNKNOWN, PinType.OUTPUT));  // Y

            notInputPins.add(getPin(mapping[0]));
            notOutputPins.add(getPin(mapping[1]));
        }

        // Prepare Identity pins
        for (int[] mapping : IDENTITY_PIN_MAPPING) {
            pins.add(new Pin(mapping[0], PinState.UNKNOWN, PinType.INPUT));   // A
            pins.add(new Pin(mapping[1], PinState.UNKNOWN, PinType.OUTPUT));  // Y

            identityInputPins.add(getPin(mapping[0]));
            identityOutputPins.add(getPin(mapping[1]));
        }

        // Prepare NAND pins
        for (int[] mapping : NAND_PIN_MAPPING) {
            pins.add(new Pin(mapping[0], PinState.UNKNOWN, PinType.INPUT));   // A
            pins.add(new Pin(mapping[1], PinState.UNKNOWN, PinType.INPUT));   // B
            pins.add(new Pin(mapping[2], PinState.UNKNOWN, PinType.OUTPUT));  // Y

            nandInputPins.add(getPin(mapping[0]));
            nandInputPins.add(getPin(mapping[1]));
            nandOutputPins.add(getPin(mapping[2]));
        }
    }

    @Override
    public void beforeTickState() {
        LogicPerformer not = new NotLogic();
        LogicPerformer identity = new IdentityLogic();
        LogicPerformer nand = new NandLogic();

        Map<Integer, PinState> notOutputPinsToChange = not.performLogic(notInputPins, notOutputPins);
        Map<Integer, PinState> identityOutputPinsToChange = identity.performLogic(identityInputPins, identityOutputPins);
        Map<Integer, PinState> nandOutputPinsToChange = nand.performLogic(nandInputPins, nandOutputPins);

        newOutputPins.putAll(notOutputPinsToChange);
        newOutputPins.putAll(identityOutputPinsToChange);
        newOutputPins.putAll(nandOutputPinsToChange);
    }
}
