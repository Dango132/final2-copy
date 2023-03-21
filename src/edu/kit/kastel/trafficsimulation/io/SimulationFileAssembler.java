package src.edu.kit.kastel.trafficsimulation.io;


import src.edu.kit.kastel.trafficsimulation.trafficObjects.Car;
import src.edu.kit.kastel.trafficsimulation.trafficObjects.Crossing;
import src.edu.kit.kastel.trafficsimulation.trafficObjects.Street;

/**
 * This class is a frame for assembling the object arrays (cars, streets, crossings) and checking their validity
 *
 * @author unkno
 * @version 1.0
 */
public class SimulationFileAssembler {
    /**
     * Local instance of car objects array
     */
    private Car[] carsArray;
    /**
     * Local instance of the street objects array
     */
    private Street[] streetsArray;
    /**
     * Local instance of the crossing objects array
     */
    private Crossing[] crossingsArray;

    /**
     * Loads, converts and checks the simulation files for errors
     *
     * @param filePath the folder path in which the files containing the traffic objects' data are stored
     * @return true if the arrays were generated successfully
     */

    public boolean assembleSimulationFiles(String filePath) {
        SimulationFileLoader simulationFileLoader = new SimulationFileLoader(filePath);
        if (simulationFileLoader.loadCars() == null || simulationFileLoader.loadStreets() == null
                || simulationFileLoader.loadCrossings() == null) {
            return false;
        }
        SimulationFileConverter simulationFileConverter = new SimulationFileConverter();
        SimulationFileChecker simulationFileChecker = new SimulationFileChecker();
        Car[] carsArray = simulationFileConverter.generateCarsArray(simulationFileLoader.loadCars());
        Street[] streetsArray = simulationFileConverter.generateStreetsArray(simulationFileLoader.loadStreets());
        Crossing[] crossingsArray
                = simulationFileConverter.generateCrossingsArray(simulationFileLoader.loadCrossings());
        fillCrossingsWithStreets(crossingsArray, streetsArray);
        if (simulationFileChecker.checkValidity(carsArray, streetsArray, crossingsArray)) {
            this.carsArray = carsArray;
            this.streetsArray = streetsArray;
            this.crossingsArray = crossingsArray;
            placeCarsOnStreets();
            return true;
        }
        return false;
    }

    /**
     * Fills two separate arrays of every crossing with the ids of incoming and outgoing streets
     *
     * @param crossingsArray array of crossings converted from the files
     * @param streetsArray   array of streets converted from the files
     */

    private void fillCrossingsWithStreets(Crossing[] crossingsArray, Street[] streetsArray) {
        for (Street street : streetsArray) {
            for (Crossing crossing : crossingsArray) {
                if (street.getStart() == crossing.getId()) {
                    crossing.addOutgoingStreetId(street.getId());
                }
                if (street.getEnd() == crossing.getId()) {
                    crossing.addIncomingStreetId(street.getId());
                }
            }
        }
    }

    /**
     * Assigns the position of every car corresponding to its id and starting street
     */
    private void placeCarsOnStreets() {
        for (Street street : streetsArray) {
            int carNumber = 0;
            for (Car car : carsArray) {
                if (street.getId() == car.getCurrentStreet()) {
                    car.setCurrentPosition(street.getLength() - (10 * carNumber));
                    carNumber++;
                }
            }
        }
    }

    /**
     * Getter for the assembled array of car objects
     *
     * @return the assembled array of car objects
     */
    public Car[] getCarsArray() {
        return carsArray;
    }

    /**
     * Getter for the assembled array of crossings
     *
     * @return the assembled array of crossings
     */
    public Crossing[] getCrossingsArray() {
        return crossingsArray;
    }

    /**
     * Getter for the assembled array of streets
     *
     * @return the assembled array of streets
     */
    public Street[] getStreetsArray() {
        return streetsArray;
    }
}
