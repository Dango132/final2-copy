package src.edu.kit.kastel.trafficsimulation.io;

import src.edu.kit.kastel.trafficsimulation.trafficObjects.Car;
import src.edu.kit.kastel.trafficsimulation.trafficObjects.Crossing;
import src.edu.kit.kastel.trafficsimulation.trafficObjects.Street;
import src.edu.kit.kastel.trafficsimulation.trafficObjects.TrafficObject;

/**
 * This class contains multiple functions that check the validity of the traffic objects
 *
 * @author unkno
 * @version 1.0
 */
public class SimulationFileChecker {
    /**
     * String representing the beginning of every error message
     */
    private static final String ERROR = "Error: ";
    /**
     * Dot character
     */
    private static final char DOT = '.';
    /**
     * Error in case of two objects having the same id
     */
    private static final String ERR_MATCHING_ID = ERROR + "Multiple elements can't have the same ID: ";
    /**
     * Error in case of too many cars on a single street
     */
    private static final String ERR_TOO_MANY_CARS = ERROR + "Too many cars on street ";
    /**
     * Error in case of too much or too few streets connected to a crossing
     */
    private static final String ERR_INVALID_NUMBER_OF_STREETS = ERROR + "Invalid number of streets on crossing ";
    /**
     * Error in case of an undefined crossing which is an end or a start of a street
     */
    private static final String ERR_CROSSING_MISSING = ERROR + "Missing crossing with ID: ";
    /**
     * String representing the beginning of messages for invalid input
     */
    private static final String ERR_INVALID = ERROR + "Invalid ";
    /**
     * String representing the second part of a message for invalid street start
     */
    private static final String MSG_STREET_START = "street start ";
    /**
     * String representing the second part of a message for invalid street end
     */
    private static final String MSG_STREET_END = "street end ";
    /**
     * String representing the second part of a message for invalid street length
     */
    private static final String MSG_STREET_LENGTH = "street length ";
    /**
     * String representing the second part of a message for invalid street type
     */
    private static final String MSG_STREET_TYPE = "street type ";
    /**
     * String representing the second part of a message for invalid street speed limit
     */
    private static final String MSG_STREET_SPEED_LIMIT = "street speed limit ";
    /**
     * String representing the third part of a message for invalid street input
     */
    private static final String MSG_AT_STREET_ID = " at street with ID ";
    /**
     * String representing the second part of a message for invalid crossing ticks count
     */
    private static final String MSG_CROSSING_TICKS_COUNT = "crossing ticks count ";
    /**
     * String representing the third part of a message for invalid crossing input
     */
    private static final String MSG_AT_CROSSING_ID = " at crossing with ID ";
    /**
     * String representing the second part of a message for invalid car speed limit
     */
    private static final String MSG_CAR_MAX_SPEED = "car speed limit ";
    /**
     * String representing the second part of a message for invalid car acceleration
     */
    private static final String MSG_CAR_ACCELERATION = "car acceleration ";
    /**
     * String representing the second part of a message for invalid car starting street
     */
    private static final String MSG_CAR_START_STREET = "car start street ";
    /**
     * String representing the third part of a message for invalid car input
     */
    private static final String MSG_AT_CAR_ID = " at car with ID ";

    /**
     * Checks the validity of all three object arrays (cars, streets, crossings)
     *
     * @param carsArray      the array of cars converted from the files
     * @param streetsArray   the array of streets converted from the files
     * @param crossingsArray the array of crossings converted from the files
     * @return true if the files are valid
     */
    public boolean checkValidity(Car[] carsArray, Street[] streetsArray, Crossing[] crossingsArray) {
        return !invalidStreetProperties(streetsArray) && !invalidCrossingProperties(crossingsArray)
                && !invalidCarProperties(carsArray, streetsArray.length) && existsMatchingID(carsArray)
                && existsMatchingID(streetsArray) && !tooManyCars(streetsArray, carsArray)
                && !invalidStreetsOnCrossing(crossingsArray) && !endsOfStreetMissing(streetsArray, crossingsArray);
    }

    /**
     * Checks if two elements have the same id
     *
     * @param array array of traffic objects
     * @param <T>   template for car and street objects
     * @return false if two ids match
     */
    private <T extends TrafficObject> boolean existsMatchingID(T[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i].getId() == array[i + 1].getId()) {
                System.out.println(ERR_MATCHING_ID + array[i].getId() + DOT);
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if there are too many cars on a single street
     *
     * @param streetsArray array of streets converted from the files
     * @param carsArray    array of cats converted from the files
     * @return false if there aren't too many cars on a single street
     */
    private boolean tooManyCars(Street[] streetsArray, Car[] carsArray) {
        for (Street street : streetsArray) {
            int carsOnCurrentStreet = 0;
            for (Car car : carsArray) {
                if (car.getCurrentStreet() == street.getId()) {
                    carsOnCurrentStreet++;
                }
            }
            if (carsOnCurrentStreet > (street.getLength() / 10) + 1) {
                System.out.println(ERR_TOO_MANY_CARS + street.getId() + DOT);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the number of incoming and outgoing streets on every crossing is valid
     *
     * @param crossingsArray array of crossings converted from the files
     * @return false if the number of incoming and outgoing streets on every crossing is valid
     */

    private boolean invalidStreetsOnCrossing(Crossing[] crossingsArray) {
        for (Crossing crossing : crossingsArray) {
            if (crossing.getIncomingStreetsIDs().size() > 4 || crossing.getIncomingStreetsIDs().size() < 1
                    || crossing.getOutgoingStreetsIDs().size() > 4 || crossing.getOutgoingStreetsIDs().size() < 1) {
                System.out.println(ERR_INVALID_NUMBER_OF_STREETS + crossing.getId() + DOT);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the crossings at the end of every street exist
     *
     * @param streetsArray   array of streets converted from the files
     * @param crossingsArray array of crossings converted from the files
     * @return false if every needed crossing exists
     */
    private boolean endsOfStreetMissing(Street[] streetsArray, Crossing[] crossingsArray) {
        for (Street street : streetsArray) {
            boolean startExists = false;
            boolean endExists = false;
            for (Crossing crossing : crossingsArray) {
                if (crossing.getId() == street.getStart()) {
                    startExists = true;
                }
                if (crossing.getId() == street.getEnd()) {
                    endExists = true;
                }
            }
            if (!endExists) {
                System.out.println(ERR_CROSSING_MISSING + street.getEnd() + DOT);
                return true;
            }
            if (!startExists) {
                System.out.println(ERR_CROSSING_MISSING + street.getStart() + DOT);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if every street has valid properties: length, speed limit, start, end, type
     *
     * @param streetsArray array of streets converted from the files
     * @return false if the properties of all streets are valid
     */
    private boolean invalidStreetProperties(Street[] streetsArray) {
        for (Street street : streetsArray) {
            if (street.getLength() < 10 || street.getLength() > 1000) {
                System.out.println(ERR_INVALID + MSG_STREET_LENGTH
                        + street.getLength() + MSG_AT_STREET_ID + street.getId() + DOT);
                return true;
            }
            if (street.getSpeedLimit() < 5 || street.getSpeedLimit() > 40) {
                System.out.println(ERR_INVALID + MSG_STREET_SPEED_LIMIT
                        + street.getLength() + MSG_AT_STREET_ID + street.getId() + DOT);
                return true;
            }
            if (street.getStart() < 0) {
                System.out.println(ERR_INVALID + MSG_STREET_START
                        + street.getLength() + MSG_AT_STREET_ID + street.getId() + DOT);
                return true;
            }
            if (street.getEnd() < 0) {
                System.out.println(ERR_INVALID + MSG_STREET_END
                        + street.getLength() + MSG_AT_STREET_ID + street.getId() + DOT);
                return true;
            }
            if (street.getType() != 1 && street.getType() != 2) {
                System.out.println(ERR_INVALID + MSG_STREET_TYPE
                        + street.getLength() + MSG_AT_STREET_ID + street.getId() + DOT);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if every crossing had valid properties: ticks count
     *
     * @param crossingsArray array of crossings converted from the files
     * @return false if the properties of all crossings are valid
     */
    private boolean invalidCrossingProperties(Crossing[] crossingsArray) {
        for (Crossing crossing : crossingsArray) {
            if (crossing.getTicksCount() != 0 && (crossing.getTicksCount() < 3 || crossing.getTicksCount() > 10)) {
                System.out.println(ERR_INVALID + MSG_CROSSING_TICKS_COUNT
                        + crossing.getTicksCount() + MSG_AT_CROSSING_ID + crossing.getId() + DOT);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the properties of every car are valid: starting street, maximal speed, acceleration
     *
     * @param carsArray    array of cars converted from the files
     * @param streetsCount the count of streets converted from the files
     * @return false if the properties of all cars are valid
     */
    private boolean invalidCarProperties(Car[] carsArray, int streetsCount) {
        for (Car car : carsArray) {
            if (car.getCurrentStreet() >= streetsCount) {
                System.out.println(ERR_INVALID + MSG_CAR_START_STREET
                        + car.getCurrentStreet() + MSG_AT_CAR_ID + car.getId() + DOT);
                return true;
            }
            if (car.getMaxSpeed() < 20 || car.getMaxSpeed() > 40) {
                System.out.println(ERR_INVALID + MSG_CAR_MAX_SPEED
                        + car.getMaxSpeed() + MSG_AT_CAR_ID + car.getId() + DOT);
                return true;
            }
            if (car.getAcceleration() < 1 || car.getAcceleration() > 10) {
                System.out.println(ERR_INVALID + MSG_CAR_ACCELERATION
                        + car.getAcceleration() + MSG_AT_CAR_ID + car.getId() + DOT);
                return true;
            }
        }
        return false;
    }
}
