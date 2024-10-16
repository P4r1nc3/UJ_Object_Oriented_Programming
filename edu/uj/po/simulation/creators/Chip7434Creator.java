package edu.uj.po.simulation.creators;

import edu.uj.po.simulation.components.chips.Chip;
import edu.uj.po.simulation.components.chips.Chip7434;

public class Chip7434Creator extends ChipCreator {
    @Override
    public Chip createChip(int id) {
        return new Chip7434(id);
    }
}
