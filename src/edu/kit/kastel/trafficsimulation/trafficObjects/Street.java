package src.edu.kit.kastel.trafficsimulation.trafficObjects;

/**
 * This class represents a street in the network
 *
 * @author unkno
 * @version 1.0
 */
public class Street extends TrafficObject {
    /**
     * Integer representing the id of the street
     */
    private final int id;
    /**
     * Integer representing the id of a crossing - start of the street
     */
    private final int start;
    /**
     * Integer representing the id of a crossing - end of the street
     */
    private final int end;
    /**
     * Integer representing the length of the street
     */
    private final int length;
    /**
     * Integer representing the type of the street (1 for single lane, 2 for fast lane)
     */
    private final int type;
    /**
     * Integer representing the speed limit of the street
     */
    private final int speedLimit;

    /**
     * Constructor of the street object
     *
     * @param id         id of the street
     * @param start      crossing's id - start of the street
     * @param end        crossing's id - end of the street
     * @param length     length of the street
     * @param type       type of the street
     * @param speedLimit speed limit of the street
     */
    public Street(int id, int start, int end, int length, int type, int speedLimit) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.length = length;
        this.type = type;
        this.speedLimit = speedLimit;
    }

    /**
     * Getter for the street's id
     *
     * @return street's id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter for the street's start crossing id
     *
     * @return street's start crossing id
     */
    public int getStart() {
        return this.start;
    }

    /**
     * Getter for the street's end crossing id
     *
     * @return street's end crossing id
     */
    public int getEnd() {
        return end;
    }

    /**
     * Getter for the street's length
     *
     * @return street's length
     */
    public int getLength() {
        return length;
    }

    /**
     * Getter for the street's type
     *
     * @return street's type
     */
    public int getType() {
        return type;
    }

    /**
     * Getter for the street's speed limit
     *
     * @return street's speed limit
     */
    public int getSpeedLimit() {
        return speedLimit;
    }
}
