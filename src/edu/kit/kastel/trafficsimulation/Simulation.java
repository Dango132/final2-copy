package src.edu.kit.kastel.trafficsimulation;


import src.edu.kit.kastel.trafficsimulation.io.SimulationFileAssembler;
import src.edu.kit.kastel.trafficsimulation.trafficObjects.Car;
import src.edu.kit.kastel.trafficsimulation.trafficObjects.Crossing;
import src.edu.kit.kastel.trafficsimulation.trafficObjects.Street;
import src.edu.kit.kastel.trafficsimulation.trafficUpdaters.CrossingUpdater;
import src.edu.kit.kastel.trafficsimulation.trafficUpdaters.StreetUpdater;

import java.util.Scanner;

/**
 * This class represents the frame of the simulation: it takes user input and calls the corresponding function
 *
 * @author unkno
 * @version 1.0
 */
public class Simulation {
    /**
     * String to match the command "load"
     */
    private static final String INPUT_LOAD = "load";
    /**
     * String to match the command "simulate"
     */
    private static final String INPUT_SIMULATE = "simulate";
    /**
     * String to match the command "load"
     */
    private static final String INPUT_POSITION = "position";
    /**
     * String to match the command "load"
     */
    private static final String INPUT_QUIT = "quit";
    /**
     * String representing the beginning of every error message
     */
    private static final String ERROR = "Error: ";
    /**
     * Dot character
     */
    private static final char DOT = '.';
    /**
     * Error in case of invalid user input
     */
    private static final String ERR_INVALID_COMMAND = ERROR + "Invalid command.";
    /**
     * Error in case the network is not loaded yet
     */
    private static final String ERR_NETWORK_NOT_LOADED = ERROR + "Street network is yet to be loaded.";
    /**
     * Error in case of negative integer input
     */
    private static final String ERR_EXPECTED_NOT_NEGATIVE_INTEGER = ERROR + "Expected not negative integer value.";
    /**
     * Error in case of seeking a car nonexistent in the simulation
     */
    private static final String ERR_NO_SUCH_CAR = ERROR + "There is no car with the identifier ";
    /**
     * String printed after the simulation has loaded
     */
    private static final String MSG_READY = "READY";
    /**
     * First part of message answering the position command
     */
    private static final String MSG_CAR = "Car ";
    /**
     * Second part of message answering the position command
     */
    private static final String MSG_ON_STREET = " on street ";
    /**
     * Third part of message answering the position command
     */
    private static final String MSG_WITH_SPEED = " with speed ";
    /**
     * Fourth part of message answering the position command
     */
    private static final String MSG_AND_POSITION = " and position ";
    /**
     * Boolean representing the state of the simulation
     */
    private boolean isRunning;
    /**
     * Boolean representing the state of the current network
     */
    private boolean networkExists;
    /**
     * Object that loads all simulation files, converts them and checks them for errors
     */
    private final SimulationFileAssembler simulationFileAssembler = new SimulationFileAssembler();
    /**
     * Object that updates the state of the streets
     */
    private final StreetUpdater streetUpdater = new StreetUpdater();
    /**
     * Object that updates the state of the crossings
     */
    private final CrossingUpdater crossingUpdater = new CrossingUpdater();
    /**
     * Array containing all cars sorted by id in the current network
     */
    private Car[] carsArray;
    /**
     * Array containing all streets sorted by id in the current network
     */
    private Street[] streetsArray;
    /**
     * Array containing all crossings sorted by id in the current network
     */
    private Crossing[] crossingsArray;

    /**
     * The body of the simulation
     */
    public void beginSimulation() {
        this.isRunning = true;
        this.networkExists = false;
        Scanner scanner = new Scanner(System.in);
        while (this.isRunning) {
            String nextLine = scanner.nextLine();
            handleLine(nextLine);
        }
        scanner.close();
    }

    /**
     * Handles commands by calling the corresponding function
     *
     * @param userInput the line passed in by the user
     */

    private void handleLine(String userInput) {
        String[] splitInput = userInput.split(" ");
        if (splitInput.length == 2) {
            switch (splitInput[0]) {
                case INPUT_LOAD -> loadSimulationFiles(splitInput[1]);
                case INPUT_SIMULATE -> simulateTicks(splitInput[1]);
                case INPUT_POSITION -> printCarPosition(splitInput[1]);
                default -> System.out.println(ERR_INVALID_COMMAND);
            }
        } else if (splitInput.length == 1) {
            if (splitInput[0].equals(INPUT_QUIT)) {
                this.isRunning = false;
            } else {
                System.out.println(ERR_INVALID_COMMAND);
            }
        } else {
            System.out.println(ERR_INVALID_COMMAND);
        }
    }

    /**
     * Loads simulation files in the specified folder
     *
     * @param filePath the path to the files' folder
     */
    private void loadSimulationFiles(String filePath) {
        if (simulationFileAssembler.assembleSimulationFiles(filePath)) {
            this.carsArray = simulationFileAssembler.getCarsArray();
            this.streetsArray = simulationFileAssembler.getStreetsArray();
            this.crossingsArray = simulationFileAssembler.getCrossingsArray();
            this.networkExists = true;
        }
    }

    /**
     * Simulates the specified number of ticks if the network is loaded
     *
     * @param stringTicksCount number of ticks passed in by the user
     */
    private void simulateTicks(String stringTicksCount) {
        if (networkCheck() || integerCheck(stringTicksCount)) {
            return;
        }
        int integerTicksCount = Integer.parseInt(stringTicksCount);
        for (int i = 0; i < integerTicksCount; i++) {
            streetUpdater.setCarsArray(carsArray);
            streetUpdater.setStreetsArray(streetsArray);
            streetUpdater.setCrossingsArray(crossingsArray);
            streetUpdater.updateStreets();
            this.carsArray = streetUpdater.getCarsArray();
            crossingUpdater.setCrossingsArray(crossingsArray);
            crossingUpdater.updateCrossings();
            this.crossingsArray = crossingUpdater.getCrossingsArray();
        }
        System.out.println(MSG_READY);
    }

    /**
     * Prints out the id, position, street and speed of a car with specified id
     *
     * @param stringCarID the id of the car to print
     */
    private void printCarPosition(String stringCarID) {
        if (networkCheck() || integerCheck(stringCarID)) {
            return;
        }
        int integerCarID = Integer.parseInt(stringCarID);
        for (Car car : carsArray) {
            if (car.getId() == integerCarID) {
                System.out.println(MSG_CAR + car.getId() + MSG_ON_STREET + car.getCurrentStreet() + MSG_WITH_SPEED
                        + car.getCurrentSpeed() + MSG_AND_POSITION + car.getCurrentPosition());
                return;
            }
        }
        System.out.println(ERR_NO_SUCH_CAR + integerCarID + DOT);
    }

    /**
     * Checks if the network is loaded
     *
     * @return false if the network is loaded
     */

    private boolean networkCheck() {
        if (!networkExists) {
            System.out.println(ERR_NETWORK_NOT_LOADED);
            return true;
        }
        return false;
    }

    /**
     * Checks if an integer can be parsed from a string
     *
     * @param stringInteger the string from which the integer is parsed
     * @return false if the integer can be parsed
     */
    private boolean integerCheck(String stringInteger) {
        try {
            if (Integer.parseInt(stringInteger) < 0) {
                System.out.println(ERR_EXPECTED_NOT_NEGATIVE_INTEGER);
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println(ERR_EXPECTED_NOT_NEGATIVE_INTEGER);
            return true;
        }
        return false;
    }
}
