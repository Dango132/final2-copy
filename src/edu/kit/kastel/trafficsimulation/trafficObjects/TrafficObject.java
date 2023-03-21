package src.edu.kit.kastel.trafficsimulation.trafficObjects;

/**
 * This abstract class represents a traffic object template
 *
 * @author unkno
 * @version 1.0
 */
public abstract class TrafficObject {
    /**
     * id of the object
     */
    int id;

    /**
     * Getter for the object's id
     *
     * @return the object's id
     */
    public int getId() {
        return id;
    }
}
