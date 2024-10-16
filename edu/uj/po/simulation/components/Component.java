package edu.uj.po.simulation.components;

import java.util.Set;

import edu.uj.po.simulation.components.pins.Pin;

public interface Component {

    int getId();

    Pin getPin(int id);

    Set<Pin> getPins();

    void prepareComponent();
}
