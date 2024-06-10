public class ResourceManager {
    private int sunPoints;

    public ResourceManager() {
        this.sunPoints = 0;
    }
    public void addSunPoints(int points) {
        this.sunPoints += points;
    }

    public int getSunPoints() {
        return sunPoints;
    }

    public boolean spendSunPoints(int points) {
        if (sunPoints >= points) {
            sunPoints -= points;
            return true;//points were spent
        }
        return false;//there wasn't enough points
    }
}
