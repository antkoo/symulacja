/**
 * A base of all zombie-type entities.
 */
public abstract class Zombie extends Entity {
    /**
     * Constructor
     * @param x x coordinate
     * @param y y coordinate
     */
    public Zombie(int x, int y) {
        super(x, y);
    }
    /**
     * Returns the attack damage of a zombie entity.
     * @return attack damage of a zombie entity
     */
    public abstract int getAttackDamage();
    /**
     * Returns the speed of a zombie entity.
     * @return speed of a zombie entity
     */
    public abstract int getSpeed();
    /**
     * Returns the type value of a zombie entity.
     * @return type value of a zombie entity
     */
    public abstract int getType();
}