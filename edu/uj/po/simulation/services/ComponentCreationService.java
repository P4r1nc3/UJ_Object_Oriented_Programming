package edu.uj.po.simulation.services;

import edu.uj.po.simulation.components.chips.Chip;
import edu.uj.po.simulation.components.Component;
import edu.uj.po.simulation.components.PinHeader;
import edu.uj.po.simulation.components.PinType;
import edu.uj.po.simulation.creators.*;
import edu.uj.po.simulation.interfaces.UnknownChip;
import edu.uj.po.simulation.registry.ComponentRegistry;

public class ComponentCreationService {

    private final ComponentRegistry componentRegistry;

    public ComponentCreationService() {
        this.componentRegistry = ComponentRegistry.getInstance();
    }

    public int createChip(int code) throws UnknownChip {
        ChipCreator creator;
        switch (code) {
            case 7400 -> creator = new Chip7400Creator();
            case 7402 -> creator = new Chip7402Creator();
            case 7404 -> creator = new Chip7404Creator();
            case 7408 -> creator = new Chip7408Creator();
            case 7410 -> creator = new Chip7410Creator();
            case 7411 -> creator = new Chip7411Creator();
            case 7420 -> creator = new Chip7420Creator();
            case 7431 -> creator = new Chip7431Creator();
            case 7432 -> creator = new Chip7432Creator();
            case 7434 -> creator = new Chip7434Creator();
            case 7442 -> creator = new Chip7442Creator();
            case 7444 -> creator = new Chip7444Creator();
            case 7482 -> creator = new Chip7482Creator();
            case 74138 -> creator = new Chip74138Creator();
            case 74152 -> creator = new Chip74152Creator();
            default -> throw new UnknownChip();
        }
        Chip chip = creator.prepareChip(componentRegistry.getNextComponentId());
        componentRegistry.addComponent(chip);
        return chip.getId();
    }

    public int createInputPinHeader(int size) {
        Component inputPinHeader = new PinHeader(componentRegistry.getNextComponentId(), size, PinType.INPUT);
        componentRegistry.addComponent(inputPinHeader);
        return inputPinHeader.getId();
    }

    public int createOutputPinHeader(int size) {
        Component outputPinHeader = new PinHeader(componentRegistry.getNextComponentId(), size, PinType.OUTPUT);
        componentRegistry.addComponent(outputPinHeader);
        return outputPinHeader.getId();
    }
}
