package src.edu.kit.kastel.trafficsimulation.io;

import src.edu.kit.kastel.trafficsimulation.trafficObjects.Car;
import src.edu.kit.kastel.trafficsimulation.trafficObjects.Crossing;
import src.edu.kit.kastel.trafficsimulation.trafficObjects.Street;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * This class contains convertors from raw string arrays to specified traffic object arrays
 *
 * @author unkno
 * @version 1.0
 */
public class SimulationFileConverter {
    /**
     * Converts a list of raw car properties to array of car objects sorted by id
     *
     * @param carsRaw string array of cars properties
     * @return array of car objects with specified properties
     */
    public Car[] generateCarsArray(List<String> carsRaw) {
        Car[] carsArray = new Car[carsRaw.size()];
        for (int i = 0; i < carsArray.length; i++) {
            int[] parsedIntegers = parseIntegers(carsRaw.get(i));
            carsArray[i] = new Car(parsedIntegers[0], parsedIntegers[1], parsedIntegers[2], parsedIntegers[3]);
        }
        Arrays.sort(carsArray, Comparator.comparingInt(Car::getId));
        return carsArray;
    }

    /**
     * Converts a list of raw street properties to array of street objects sorted by id
     *
     * @param streetsRaw string list of streets properties
     * @return array of street objects with specified properties
     */

    public Street[] generateStreetsArray(List<String> streetsRaw) {
        Street[] streetsArray = new Street[streetsRaw.size()];
        for (int i = 0; i < streetsArray.length; i++) {
            int[] parsedIntegers = parseIntegers(streetsRaw.get(i));
            streetsArray[i] = new Street(i, parsedIntegers[0], parsedIntegers[1],
                    parsedIntegers[2], parsedIntegers[3], parsedIntegers[4]);
        }
        return streetsArray;
    }

    /**
     * Converts a list of raw crossings properties to array of crossing objects sorted by id
     *
     * @param crossingsRaw string list of crossings properties
     * @return array of crossing objects with specified properties
     */
    public Crossing[] generateCrossingsArray(List<String> crossingsRaw) {
        Crossing[] crossingsArray = new Crossing[crossingsRaw.size()];
        for (int i = 0; i < crossingsArray.length; i++) {
            int[] parsedIntegers = parseIntegers(crossingsRaw.get(i));
            crossingsArray[i] = new Crossing(parsedIntegers[0], parsedIntegers[1]);
        }
        Arrays.sort(crossingsArray, Comparator.comparingInt(Crossing::getId));
        return crossingsArray;
    }

    /**
     * Parses all separated integers from a string
     *
     * @param rawString string to be parsed from
     * @return array of parsed integers
     */
    private int[] parseIntegers(String rawString) {
        String[] splitString = rawString.replaceAll("[^0-9]", " ").trim().replaceAll(" +", " ").split(" ");
        int[] splitIntegers = new int[splitString.length];
        for (int i = 0; i < splitString.length; i++) {
            splitIntegers[i] = Integer.parseInt(splitString[i]);
        }
        return splitIntegers;
    }
}
