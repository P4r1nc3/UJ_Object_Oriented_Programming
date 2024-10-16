package edu.uj.po.simulation.components;

import java.util.HashSet;
import java.util.Set;

import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.interfaces.PinState;

public class PinHeader implements Component {
    private final int id;
    private final int size;
    private final PinType pinHeaderType;
    private final Set<Pin> pins = new HashSet<>();

    public PinHeader(int id, int size, PinType pinHeaderType) {
        this.id = id;
        this.size = size;
        this.pinHeaderType = pinHeaderType;
        prepareComponent();
    }

    @Override
    public int getId() {
        return id;
    }

    public Integer getSize() {
        return size;
    }

    @Override
    public Set<Pin> getPins() {
        return pins;
    }

    @Override
    public Pin getPin(int id) {
        for (Pin pin : pins) {
            if (pin.getId() == id) {
                return pin;
            }
        }
        return null;
    }

    @Override
    public void prepareComponent() {
        for (int i = 1; i <= size; i++) {
            if (pinHeaderType.equals(PinType.INPUT)) {
                pins.add(new Pin(i, PinState.UNKNOWN, PinType.OUTPUT));
            } else if (pinHeaderType.equals(PinType.OUTPUT)) {
                pins.add(new Pin(i, PinState.UNKNOWN, PinType.INPUT));
            }
        }
    }

    public PinType getPinHeaderType() {
        return pinHeaderType;
    }
}
