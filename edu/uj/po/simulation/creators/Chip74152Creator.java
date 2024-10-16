package edu.uj.po.simulation.creators;

import edu.uj.po.simulation.components.chips.Chip;
import edu.uj.po.simulation.components.chips.Chip74152;

public class Chip74152Creator extends ChipCreator {
    @Override
    public Chip createChip(int id) {
        return new Chip74152(id);
    }
}
