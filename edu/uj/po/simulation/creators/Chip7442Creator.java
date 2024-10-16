package edu.uj.po.simulation.creators;

import edu.uj.po.simulation.components.chips.Chip;
import edu.uj.po.simulation.components.chips.Chip7442;

public class Chip7442Creator extends ChipCreator {
    @Override
    public Chip createChip(int id) {
        return new Chip7442(id);
    }
}
