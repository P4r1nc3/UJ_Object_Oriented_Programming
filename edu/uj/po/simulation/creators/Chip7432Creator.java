package edu.uj.po.simulation.creators;

import edu.uj.po.simulation.components.chips.Chip;
import edu.uj.po.simulation.components.chips.Chip7432;

public class Chip7432Creator extends ChipCreator {
    @Override
    public Chip createChip(int id) {
        return new Chip7432(id);
    }
}
