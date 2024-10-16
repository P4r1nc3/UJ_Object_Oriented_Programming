package edu.uj.po.simulation.components.chips;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

import edu.uj.po.simulation.components.Component;
import edu.uj.po.simulation.components.pins.Pin;
import edu.uj.po.simulation.interfaces.PinState;

public abstract class Chip implements Component {

    protected int id;
    protected Set<Pin> pins = new HashSet<>();
    protected Map<Integer, PinState> newOutputPins = new HashMap<>();

    public Chip(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Pin getPin(int id) {
        return pins.stream()
                .filter(pin -> pin.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Set<Pin> getPins() {
        return pins;
    }

    public abstract void beforeTickState();

    public void afterTickState() {
        newOutputPins.forEach((pinId, state) -> {
            Optional<Pin> optionalPin = pins.stream()
                    .filter(pin -> pin.getId() == pinId)
                    .findFirst();
            optionalPin.ifPresent(pin -> pin.setState(state));
        });
    }
}
