import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Entity implements ActionListener {
    protected int x, y, health;

    public Entity(int x, int y, int health) {
        this.x = x;
        this.y = y;
        this.health=health;
    }

    //public abstract void update();

    public abstract void paint(Graphics g);
    public abstract void takeDamage(int damage);
}