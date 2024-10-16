package edu.uj.po.simulation.creators;

import edu.uj.po.simulation.components.chips.Chip;
import edu.uj.po.simulation.components.chips.Chip7482;

public class Chip7482Creator extends ChipCreator {
    @Override
    public Chip createChip(int id) {
        return new Chip7482(id);
    }
}
