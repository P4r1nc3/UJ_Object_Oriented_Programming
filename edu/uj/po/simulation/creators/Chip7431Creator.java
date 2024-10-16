package edu.uj.po.simulation.creators;

import edu.uj.po.simulation.components.chips.Chip;
import edu.uj.po.simulation.components.chips.Chip7431;

public class Chip7431Creator extends ChipCreator {
    @Override
    public Chip createChip(int id) {
        return new Chip7431(id);
    }
}
