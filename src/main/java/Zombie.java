import java.awt.*;

public abstract class Zombie extends Entity {
    public Zombie(int x, int y) {
        super(x, y);
    }


    public abstract int getAttackDamage();
    public abstract int getSpeed();
    public abstract int getType();
}