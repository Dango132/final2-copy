package src.edu.kit.kastel.trafficsimulation.trafficUpdaters;

import src.edu.kit.kastel.trafficsimulation.trafficObjects.Car;
import src.edu.kit.kastel.trafficsimulation.trafficObjects.Crossing;
import src.edu.kit.kastel.trafficsimulation.trafficObjects.Street;

/**
 * This class represents a street updater (actualises the properties of every street in the network)
 *
 * @author unkno
 * @version 1.0
 */
public class StreetUpdater {
    /**
     * Local instance of the array of cars sorted by id
     */
    private Car[] carsArray;
    /**
     * Local instance of the array of streets sorted by id
     */
    private Street[] streetsArray;
    /**
     * Local instance of the array of crossings sorted by id
     */
    private Crossing[] crossingsArray;
    /**
     * Integer representing the id of the next car to be moved
     */
    private int carToMoveId;

    /**
     * Iterates through all streets and calls a function to update every car (beginning from the end of the street)
     */
    public void updateStreets() {
        for (Street street : streetsArray) {
            while (findNextCarToUpdate(street.getId()) != -1) {
                for (Car car : carsArray) {
                    if (car.getId() == carToMoveId) {
                        updateCar(car, street);
                    }
                }
            }
        }
        for (Car car : carsArray) {
            car.setCarHasMoved(false);
        }
    }

    /**
     * Finds the next car to be updated according to its position and if it's moved
     *
     * @param streetID the id of the current street
     * @return id of the car to be moved
     */
    private int findNextCarToUpdate(int streetID) {
        int highestPosition = 0;
        this.carToMoveId = -1;
        for (Car car : carsArray) {
            if (car.getCurrentStreet() == streetID && !car.getCarHasMoved()
                    && car.getCurrentPosition() >= highestPosition) {
                highestPosition = car.getCurrentPosition();
                this.carToMoveId = car.getId();
            }
        }
        return this.carToMoveId;
    }

    /**
     * Updates the position of the car in the following order:
     * 1- the car drives as much as possible
     * 2- the car overruns or overruns if it can
     * 3- the car drives as much as possible
     *
     * @param car    car to be updated
     * @param street street on which the car is positioned
     */
    private void updateCar(Car car, Street street) {
        car.setCurrentSpeed(calculateNewCarSpeed(car, street.getSpeedLimit()));
        int initialDistanceToMove = car.getCurrentSpeed();
        int remainingDistanceToMove = car.getCurrentSpeed();
        // Finds position of car in front
        int positionOfCarInFront = findPositionOfCarInFront(car.getCurrentPosition(), street);
        // The car moves as much as possible
        remainingDistanceToMove = adjustCarPosition(remainingDistanceToMove, positionOfCarInFront, car);
        // Finds position of second car in front
        int positionOfSecondCarInFront = findPositionOfCarInFront(positionOfCarInFront, street);
        if (remainingDistanceToMove >= 20 && street.getType() == 2
                && positionOfSecondCarInFront - positionOfCarInFront >= 20) {
            // The car overruns
            remainingDistanceToMove -= 20;
            car.setCurrentPosition(positionOfCarInFront + 10);
            // The car moves as much as possible if the car overran
            remainingDistanceToMove = adjustCarPosition(remainingDistanceToMove, positionOfSecondCarInFront, car);
        } else if (car.getCurrentPosition() == street.getLength() && remainingDistanceToMove > 0) {
            // Finds crossing at the street's end
            Crossing currentCrossing = new Crossing(0, 0);
            for (Crossing crossing : crossingsArray) {
                if (crossing.getId() == street.getEnd()) {
                    currentCrossing = crossing;
                }
            }
            if (currentCrossing.getIncomingStreetsIDs().get(currentCrossing.getCurrentGreenPhase()) == street.getId()
                    || currentCrossing.getTicksCount() == 0) {
                int outgoingStreetID;
                int positionOfClosestCarOnNewStreet;
                // Gets the id of the next street according to car's direction
                if (car.getCurrentDirection() > currentCrossing.getOutgoingStreetsIDs().size() - 1) {
                    outgoingStreetID = currentCrossing.getOutgoingStreetsIDs().get(0);
                } else {
                    outgoingStreetID = currentCrossing.getOutgoingStreetsIDs().get(car.getCurrentDirection());
                }
                // Finds position of the closest car on the new street
                positionOfClosestCarOnNewStreet = findPositionOfCarInFront(-1, streetsArray[outgoingStreetID]);
                if (positionOfClosestCarOnNewStreet >= 10) {
                    // The car turns
                    car.setCurrentStreet(outgoingStreetID);
                    car.setCurrentPosition(0);
                    if (car.getCurrentDirection() < 3) {
                        car.setCurrentDirection(car.getCurrentDirection() + 1);
                    } else {
                        car.setCurrentDirection(0);
                    }
                    // The car moves as much as possible if the car turned
                    remainingDistanceToMove
                            = adjustCarPosition(remainingDistanceToMove, positionOfClosestCarOnNewStreet, car);
                }
            }
        }
        // Resets the car's speed if it hasn't moved at all
        if (initialDistanceToMove == remainingDistanceToMove) {
            car.setCurrentSpeed(0);
        }
        car.setCarHasMoved(true);
    }

    /**
     * @param car        car which speed is calculated
     * @param speedLimit speed limit of the current street
     * @return new speed of the car
     */
    private int calculateNewCarSpeed(Car car, int speedLimit) {
        return Math.min(car.getCurrentSpeed() + car.getAcceleration(),
                Math.min(car.getMaxSpeed(), speedLimit));
    }

    /**
     * Finds the position of the car in front of the current one
     *
     * @param carPosition current car
     * @param street      street on which the car currently drives
     * @return the position of the car in front of the current one
     */
    private int findPositionOfCarInFront(int carPosition, Street street) {
        int closestPosition = street.getLength() + 10;
        for (Car car : carsArray) {
            if (car.getCurrentStreet() == street.getId()
                    && car.getCurrentPosition() > carPosition && car.getCurrentPosition() < closestPosition) {
                closestPosition = car.getCurrentPosition();
            }
        }
        if (closestPosition == street.getLength() + 1) {
            return 0;
        }
        return closestPosition;
    }

    /**
     * Adjusts a given car's position
     *
     * @param remainingDistanceToMove the distance the car can drive during this tick
     * @param positionOfCarInFront    position of the car in front of the given one
     * @param car                     car to be driven
     * @return the remaining distance which the car can still move
     */
    private int adjustCarPosition(int remainingDistanceToMove, int positionOfCarInFront, Car car) {
        int newRemainingDistanceToMove = remainingDistanceToMove;
        if (remainingDistanceToMove >= positionOfCarInFront - 10 - car.getCurrentPosition()) {
            newRemainingDistanceToMove -= (positionOfCarInFront - 10 - car.getCurrentPosition());
            car.setCurrentPosition(positionOfCarInFront - 10);
        } else {
            car.setCurrentPosition(car.getCurrentPosition() + remainingDistanceToMove);
            newRemainingDistanceToMove = 0;
        }
        return newRemainingDistanceToMove;
    }

    /**
     * Getter for the local array of cars
     *
     * @return local array of cars
     */
    public Car[] getCarsArray() {
        return carsArray;
    }

    /**
     * Setter for the array of cars
     *
     * @param carsArray current array of cars in the network
     */
    public void setCarsArray(Car[] carsArray) {
        this.carsArray = carsArray;
    }

    /**
     * Setter for the array of streets
     *
     * @param streetsArray current array of streets in the network
     */
    public void setStreetsArray(Street[] streetsArray) {
        this.streetsArray = streetsArray;
    }

    /**
     * Setter for the array of crossings
     *
     * @param crossingsArray current array of crossings in the network
     */
    public void setCrossingsArray(Crossing[] crossingsArray) {
        this.crossingsArray = crossingsArray;
    }
}
