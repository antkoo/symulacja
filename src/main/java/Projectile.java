import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An object created by the {@link Peashooter}, moves towards zombies and deals damage when it hits one.
 */
public class Projectile implements ActionListener {
    /**
     * Copy of the base timer, located in {@link Panel#theTimer}.
     */
    public static Timer theTimer = Panel.theTimer;
    /**
     * coordinates
     */
    public int x, y;
    private static final int SPEED = 10;
    private static final int DAMAGE = 20;
    Image image;
    private final Rectangle imageBounds;

    /**
     * Constructor
     * @param x x coordinate
     * @param y y coordinate
     */
    public Projectile(int x, int y) {
        //set starting values
        this.x = x;
        this.y = y;
        theTimer.addActionListener(this); //make this entity listen to the timer
        this.image = new ImageIcon("src/Projectile.png").getImage();
        this.imageBounds = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
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
     * Returns the damage of a projectile.
     * @return damage of a projectile
     */
    public int getDamage() {
        return DAMAGE;
    }
    /**
     * Returns a projectile's width
     * @return projectile's width
     */
    public int getWidth() {
        return image.getWidth(null);
    }
    /**
     * Returns a projectile's hitbox
     * @return projectile's hitbox
     */
    public Rectangle getBounds() {
        return imageBounds;
    }
    /**
     * Every {@link Panel#theTimer} tick, this function is called. Used for updating position of the projectile.
     * @param e tick of {@link Panel#theTimer}
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.x += SPEED;//move right towards zombies
        this.imageBounds.x=x;//update imageBounds for correct collision register
    }
}