package edu.uj.po.simulation.creators;

import edu.uj.po.simulation.components.chips.Chip;
import edu.uj.po.simulation.components.chips.Chip7402;

public class Chip7402Creator extends ChipCreator {
    @Override
    public Chip createChip(int id) {
        return new Chip7402(id);
    }
}
