package src.edu.kit.kastel.trafficsimulation.trafficObjects;

import java.util.ArrayList;

/**
 * This object represents a crossing in the network
 *
 * @author unkno
 * @version 1.0
 */
public class Crossing extends TrafficObject {
    /**
     * Integer representing the id of the crossing
     */
    private final int id;
    /**
     * Integer representing the ticks count of the crossing
     */
    private final int ticksCount;
    /**
     * List of integers representing the ids of the incoming streets to the crossing
     */
    private final ArrayList<Integer> incomingStreetsIDs = new ArrayList<>();
    /**
     * List of integers representing the ids of the outgoing streets from the crossing
     */
    private final ArrayList<Integer> outgoingStreetsIDs = new ArrayList<>();
    /**
     * Integer representing the remaining duration of the current green phase of the crossing
     */
    private int greenPhaseDuration;
    /**
     * Integer representing the current green phase of the crossing
     */
    private int currentGreenPhase = 0;

    /**
     * Constructor of the crossing object
     *
     * @param id         id of the crossing
     * @param ticksCount ticks count of the crossing
     */

    public Crossing(int id, int ticksCount) {
        this.id = id;
        this.ticksCount = ticksCount;
        if (ticksCount == 0) {
            this.greenPhaseDuration = 0;
        } else if (ticksCount > 0) {
            this.greenPhaseDuration = ticksCount - 1;
        }
    }

    /**
     * Getter for the crossing's id
     *
     * @return crossing's id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter for the crossing's ticks count
     *
     * @return crossing's ticks count
     */
    public int getTicksCount() {
        return this.ticksCount;
    }

    /**
     * Getter for the crossing's green phase duration
     *
     * @return crossing's green phase duration
     */
    public int getGreenPhaseDuration() {
        return this.greenPhaseDuration;
    }

    /**
     * Setter for the crossing's green phase duration
     *
     * @param greenPhaseDuration new green phase duration of the crossing
     */
    public void setGreenPhaseDuration(int greenPhaseDuration) {
        this.greenPhaseDuration = greenPhaseDuration;
    }

    /**
     * Getter for the crossing's current green phase
     *
     * @return crossing's current green phase
     */
    public int getCurrentGreenPhase() {
        return this.currentGreenPhase;
    }

    /**
     * Setter for the crossing's current green phase
     *
     * @param currentGreenPhase new current green phase of the crossing
     */
    public void setCurrentGreenPhase(int currentGreenPhase) {
        this.currentGreenPhase = currentGreenPhase;
    }

    /**
     * Getter for the crossing's list of incoming streets' ids
     *
     * @return crossing's list of incoming streets' ids
     */

    public ArrayList<Integer> getIncomingStreetsIDs() {
        return this.incomingStreetsIDs;
    }

    /**
     * Adds the id of a street to the incoming streets' ids
     *
     * @param streetID id of the street to be added
     */
    public void addIncomingStreetId(int streetID) {
        incomingStreetsIDs.add(streetID);
    }

    /**
     * Getter for the crossing's list of outgoing streets' ids
     *
     * @return crossing's list of outgoing streets' ids
     */
    public ArrayList<Integer> getOutgoingStreetsIDs() {
        return this.outgoingStreetsIDs;
    }

    /**
     * Adds the id of a street to the outgoing street's ids
     *
     * @param streetID id of the street to be added
     */

    public void addOutgoingStreetId(int streetID) {
        outgoingStreetsIDs.add(streetID);
    }
}
