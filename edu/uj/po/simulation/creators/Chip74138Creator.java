package edu.uj.po.simulation.creators;

import edu.uj.po.simulation.components.chips.Chip;
import edu.uj.po.simulation.components.chips.Chip74138;

public class Chip74138Creator extends ChipCreator{
    @Override
    public Chip createChip(int id) {
        return new Chip74138(id);
    }
}
