package edu.uj.po.simulation.registry;

import edu.uj.po.simulation.components.Component;

import java.util.ArrayList;
import java.util.List;

public class ComponentRegistry {
    private static ComponentRegistry instance;
    private final List<Component> components;

    private ComponentRegistry() {
        components = new ArrayList<>();
    }

    public static ComponentRegistry getInstance() {
        if (instance == null) {
            instance = new ComponentRegistry();
        }
        return instance;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    public int getNextComponentId() {
        return components.size();
    }

    public void reset() {
        components.clear();
    }
}
