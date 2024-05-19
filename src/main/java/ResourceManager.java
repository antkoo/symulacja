public class ResourceManager {
    private static ResourceManager instance;
    private int sunPoints;

    public ResourceManager() {
        this.sunPoints = 0;
    }
    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
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
            return true;
        }
        return false;
    }
}
