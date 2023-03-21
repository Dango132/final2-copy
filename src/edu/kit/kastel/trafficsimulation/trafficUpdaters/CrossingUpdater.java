package src.edu.kit.kastel.trafficsimulation.trafficUpdaters;


import src.edu.kit.kastel.trafficsimulation.trafficObjects.Crossing;

/**
 * This class represents a crossing updater (actualises the properties of every crossing in the network)
 *
 * @author unkno
 * @version 1.0
 */
public class CrossingUpdater {
    /**
     * Local instance of the array of crossings sorted by id
     */
    Crossing[] crossingsArray;

    /**
     * Iterates through all crossings and calls the green phase updater function
     */
    public void updateCrossings() {
        for (int i = 0; i < crossingsArray.length; i++) {
            updateGreenPhase(i);
        }
    }

    /**
     * Updates the green phase of a crossing
     *
     * @param crossingIndex the index of the crossing in the crossings array
     */
    private void updateGreenPhase(int crossingIndex) {
        Crossing currentCrossing = crossingsArray[crossingIndex];
        if (currentCrossing.getGreenPhaseDuration() > 0) {
            currentCrossing.setGreenPhaseDuration(currentCrossing.getGreenPhaseDuration() - 1);
        } else if (currentCrossing.getTicksCount() != 0) {
            currentCrossing.setGreenPhaseDuration(currentCrossing.getTicksCount() - 1);
            if (currentCrossing.getCurrentGreenPhase() < currentCrossing.getIncomingStreetsIDs().size() - 1) {
                currentCrossing.setCurrentGreenPhase(currentCrossing.getCurrentGreenPhase() + 1);
            } else {
                currentCrossing.setCurrentGreenPhase(0);
            }
        }
        crossingsArray[crossingIndex] = currentCrossing;
    }

    /**
     * Getter for the local crossings array
     *
     * @return local crossings array
     */
    public Crossing[] getCrossingsArray() {
        return crossingsArray;
    }

    /**
     * Setter for the crossings array
     *
     * @param crossingsArray current crossings array in the network
     */
    public void setCrossingsArray(Crossing[] crossingsArray) {
        this.crossingsArray = crossingsArray;
    }
}
