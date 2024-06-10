public abstract class Plant extends Entity {
    public Plant(int x, int y) {
        super(x, y);
    }
    public abstract int getType();
    public abstract int getHealth();

}
