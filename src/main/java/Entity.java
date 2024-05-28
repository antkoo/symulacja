import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Entity implements ActionListener {
    protected int x, y, health;
    protected Rectangle imageBounds;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //public abstract void update();


    public abstract int getHealth();
    public abstract void paint(Graphics g);
    public abstract void takeDamage(int damage);
    public abstract Rectangle getBounds();
}