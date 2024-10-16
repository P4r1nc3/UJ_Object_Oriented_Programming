package edu.uj.po.simulation.creators;

import edu.uj.po.simulation.components.chips.Chip;
import edu.uj.po.simulation.components.chips.Chip7400;

public class Chip7400Creator extends ChipCreator {
    @Override
    public Chip createChip(int id) {
        return new Chip7400(id);
    }
}
