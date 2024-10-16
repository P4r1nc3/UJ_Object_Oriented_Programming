package edu.uj.po.simulation.creators;

import edu.uj.po.simulation.components.chips.Chip;

public abstract class ChipCreator {
    public abstract Chip createChip(int id);

    public Chip prepareChip(int id) {
        Chip chip = createChip(id);
        chip.prepareComponent();
        return chip;
    }
}
