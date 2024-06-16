/**
 * Stores the Sun Points amount and removes/adds them accordingly.
 */
public class ResourceManager {
    private int sunPoints;

    /**
     * Reset Sun Points.
     */
    public ResourceManager() {
        this.sunPoints = 0;
    }

    /**
     * Adds a specified amount of points.
     * @param points amount of points to add.
     */
    public void addSunPoints(int points) {
        this.sunPoints += points;
    }

    /**
     * Returns the amount of Sun Points available.
     * @return amount of Sun Points
     */
    public int getSunPoints() {
        return sunPoints;
    }

    /**
     * Checks if after spending specified points amount, the remaining value isn't negative.
     * @param points amount of Sun Points to spend.
     * @return if possible to spend Sun Points
     */
    public boolean spendSunPoints(int points) {
        if (sunPoints >= points) {
            sunPoints -= points;
            return true;//points were spent
        }
        return false;//there wasn't enough points
    }
}
