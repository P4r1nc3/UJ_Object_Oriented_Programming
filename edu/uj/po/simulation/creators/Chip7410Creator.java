package edu.uj.po.simulation.creators;

import edu.uj.po.simulation.components.chips.Chip;
import edu.uj.po.simulation.components.chips.Chip7410;

public class Chip7410Creator extends ChipCreator {
    @Override
    public Chip createChip(int id) {
        return new Chip7410(id);
    }
}
