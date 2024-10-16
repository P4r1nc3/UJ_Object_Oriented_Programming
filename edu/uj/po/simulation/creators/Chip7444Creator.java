package edu.uj.po.simulation.creators;

import edu.uj.po.simulation.components.chips.Chip;
import edu.uj.po.simulation.components.chips.Chip7444;

public class Chip7444Creator extends ChipCreator{
    @Override
    public Chip createChip(int id) {
        return new Chip7444(id);
    }
}
