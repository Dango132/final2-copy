package src.edu.kit.kastel.trafficsimulation.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * File loader for simulation files.
 *
 * @author Lucas Alber
 * @version 1.0
 */
public final class SimulationFileLoader {

    /**
     * The filename for the simulation data representing streets.
     */
    public static final String FILENAME_STREETS = "streets.sim";
    /**
     * The filename for the simulation data representing crossings.
     */
    public static final String FILENAME_CROSSINGS = "crossings.sim";
    /**
     * The filename for the simulation data representing cars.
     */
    public static final String FILENAME_CARS = "cars.sim";


    private final Path folderPath;


    /**
     * Creates a new {@link SimulationFileLoader}.
     *
     * @param folderPath a path to a folder containing the three simulation files.
     */
    public SimulationFileLoader(final String folderPath) {
        this.folderPath = Path.of(folderPath).normalize().toAbsolutePath();
    }


    /**
     * Loads the simulation file {@value FILENAME_STREETS} and returns the lines as list of String.
     * <p>
     * An empty list is returned, if the file is empty.
     *
     * @return the lines of the file as list of String.
     */
    public List<String> loadStreets() {
        return loadSimulationFile(FILENAME_STREETS);
    }

    /**
     * Loads the simulation file {@value FILENAME_CROSSINGS} and returns the lines as list of String.
     * <p>
     * An empty list is returned, if the file is empty.
     *
     * @return the lines of the file as list of String.
     */
    public List<String> loadCrossings() {
        return loadSimulationFile(FILENAME_CROSSINGS);
    }

    /**
     * Loads the simulation file {@value FILENAME_CARS} and returns the lines as list of String.
     * <p>
     * An empty list is returned, if the file is empty.
     *
     * @return the lines of the file as list of String.
     */
    public List<String> loadCars() {
        return loadSimulationFile(FILENAME_CARS);
    }

    /**
     * Loads the simulation file with the specified file name
     *
     * @param fileName the name of the file
     * @return null if the file doesn't exist, the lines as list of String otherwise
     */
    private List<String> loadSimulationFile(String fileName) {
        final Path filePath = this.folderPath.resolve(Path.of(fileName));
        final File file = filePath.toFile();

        if (!file.exists()) {
            System.out.printf("folder %s does not exist.%n", this.folderPath);
            return null;
        }
        if (!file.isFile()) {
            System.out.printf("file %s is not a normal file.%n", filePath);
            return null;
        }
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            System.out.println("Error: Invalid file behaviour.");
            return null;
        }
    }

}
