package src.edu.kit.kastel.trafficsimulation.trafficObjects;

/**
 * This class represents a car in the network
 *
 * @author unkno
 * @version 1.0
 */
public class Car extends TrafficObject {
    /**
     * Integer representing the id of the car
     */
    private final int id;
    /**
     * Integer representing the maximal speed of the car
     */
    private final int maxSpeed;
    /**
     * Integer representing the acceleration of the car
     */
    private final int acceleration;
    /**
     * Integer representing the street on which the car is currently on
     */
    private int currentStreet;
    /**
     * Integer representing the current position of the car on the street
     */
    private int currentPosition;
    /**
     * Integer representing the current speed of the car
     */
    private int currentSpeed = 0;
    /**
     * Integer representing the current direction of the car
     */
    private int currentDirection = 0;
    /**
     * Boolean representing if the car has moved
     */
    private boolean carHasMoved = false;

    /**
     * Constructor of the car object
     *
     * @param id           id of the car
     * @param startStreet  starting street of the car
     * @param maxSpeed     the maximal speed of the car
     * @param acceleration the acceleration of the car
     */

    public Car(int id, int startStreet, int maxSpeed, int acceleration) {
        this.id = id;
        this.currentStreet = startStreet;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
    }

    /**
     * Getter for the car's id
     *
     * @return car's id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter for the car's maximal speed
     *
     * @return car's maximal speed
     */

    public int getMaxSpeed() {
        return this.maxSpeed;
    }

    /**
     * Getter for the car's acceleration
     *
     * @return car's acceleration
     */
    public int getAcceleration() {
        return this.acceleration;
    }

    /**
     * Getter for the car's current street
     *
     * @return car's current street
     */

    public int getCurrentStreet() {
        return this.currentStreet;
    }

    /**
     * Setter for the car's current street
     *
     * @param currentStreet new current street of the car
     */

    public void setCurrentStreet(int currentStreet) {
        this.currentStreet = currentStreet;
    }

    /**
     * Getter for the car's current position
     *
     * @return car's current position
     */

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    /**
     * Setter for the car's current position
     *
     * @param currentPosition new current position of the car
     */

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    /**
     * Getter for the car's current speed
     *
     * @return car's current speed
     */

    public int getCurrentSpeed() {
        return this.currentSpeed;
    }

    /**
     * Setter for the car's current speed
     *
     * @param currentSpeed new current speed of the car
     */

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    /**
     * Getter for the car's current direction
     *
     * @return car's current direction
     */

    public int getCurrentDirection() {
        return this.currentDirection;
    }

    /**
     * Sets the car's current direction
     *
     * @param currentDirection new current direction of the car
     */

    public void setCurrentDirection(int currentDirection) {
        this.currentDirection = currentDirection;
    }

    /**
     * Getter for the state of the car's movement
     *
     * @return the state of the car's movement
     */

    public boolean getCarHasMoved() {
        return carHasMoved;
    }

    /**
     * Setter for the state of the car's movement
     *
     * @param carHasMoved new state of the car's movement
     */

    public void setCarHasMoved(boolean carHasMoved) {
        this.carHasMoved = carHasMoved;
    }
}
