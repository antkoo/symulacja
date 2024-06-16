import javax.swing.*;
import java.awt.*;
/**
 * An object created by the {@link CherryBomb}, explodes on creation, dealing damage in 3x3 radius.
 */
public class Explosion {
    /**
     * Coordinates
     */
    public int x, y;
    private static final int DAMAGE = 9999;
    Image image;
    private final Rectangle imageBounds;

    /**
     * Constructor
     * @param x x coordinate
     * @param y y coordinate
     */
    public Explosion(int x, int y) {
        //set starting values
        this.x = x;
        this.y = y;
        image = new ImageIcon("src/Explosion.png").getImage();
        imageBounds = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }
    /**
     * Paints the entity on screen.
     * @param g required to paint on screen
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(image, x, y, null);
    }
    /**
     * Returns the damage of the explosion.
     * @return damage of the explosion
     */
    public int getDamage() {
        return DAMAGE;
    }
    /**
     * Returns the explosion's hitbox
     * @return explosion's hitbox
     */
    public Rectangle getBounds() {
        return imageBounds;
    }
}