package edu.uj.po.simulation.creators;

import edu.uj.po.simulation.components.chips.Chip;
import edu.uj.po.simulation.components.chips.Chip7411;

public class Chip7411Creator extends ChipCreator {
    @Override
    public Chip createChip(int id) {
        return new Chip7411(id);
    }
}
