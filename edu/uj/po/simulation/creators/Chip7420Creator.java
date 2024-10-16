package edu.uj.po.simulation.creators;

import edu.uj.po.simulation.components.chips.Chip;
import edu.uj.po.simulation.components.chips.Chip7420;

public class Chip7420Creator extends ChipCreator {
    @Override
    public Chip createChip(int id) {
        return new Chip7420(id);
    }
}
