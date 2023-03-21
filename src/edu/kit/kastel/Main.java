package src.edu.kit.kastel;

import src.edu.kit.kastel.trafficsimulation.Simulation;

/**
 * The main class for the second final assignment
 *
 * @author unkno
 * @version 1.0
 */
public final class Main {
    /**
     * Error in case the main class is tried to be instantiated
     */
    public static final String UTILITY_CLASS_INSTANTIATION = "Utility class cannot be instantiated.";

    /**
     * Private constructor to avoid object generation.
     */
    private Main() {
        throw new IllegalStateException(UTILITY_CLASS_INSTANTIATION);
    }

    /**
     * Entry point to the simulation
     *
     * @param args arguments passed in by the user (not expected)
     */
    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        simulation.beginSimulation();
    }
}