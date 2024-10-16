package edu.uj.po.simulation.creators;

import edu.uj.po.simulation.components.chips.Chip;
import edu.uj.po.simulation.components.chips.Chip7404;

public class Chip7404Creator extends ChipCreator {
    @Override
    public Chip createChip(int id) {
        return new Chip7404(id);
    }
}
