import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Base of almost all painted objects.
 */
public abstract class Entity implements ActionListener {
    /**
     * identifying values of an entity
     */
    protected int x, y, health;
    /**
     * a standardized size of entity's hitbox
     */
    protected Rectangle imageBounds;
    /**
     * Copy of the base timer, located in {@link Panel#theTimer}.
     */
    protected static Timer theTimer = Panel.theTimer;
    /**
     * Constructor
     * @param x x coordinate
     * @param y y coordinate
     */
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the amount of health of an entity.
     * @return health amount
     */
    public abstract int getHealth();

    /**
     * Paints the entity on screen.
     * @param g required to paint on screen
     */
    public abstract void paintComponent(Graphics g);

    /**
     * Remove a specified amount of health from an entity.
     * @param damage amount of damage dealt
     */
    public abstract void takeDamage(int damage);

    /**
     * Returns the entity's hitbox
     * @return entity's hitbox
     */
    public abstract Rectangle getBounds();

    /**
     * Returns the type value defined by each entity.
     * @return type of entity
     */
    public abstract int getType();

    /**
     * Every {@link Panel#theTimer} tick, this function is called. Used for updating position, health, image on screen etc.
     * @param e tick of {@link Panel#theTimer}
     */
    public abstract void actionPerformed(ActionEvent e);
}