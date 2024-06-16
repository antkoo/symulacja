import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
/**
 * A plant, that has a very high health.
 */
public class Walnut extends Plant {
    Image image;
    private static final int HEALTH = 3500;
    private static final int COST = 50;
    /**
     * Constructor of a plant
     * @param x x coordinate
     * @param y y coordinate
     */
    public Walnut(int x, int y) {
        super(x, y);
        //set starting values
        this.health = HEALTH;
        theTimer.addActionListener(this);//make this entity listen to the timer
        image = new ImageIcon("src/Walnut.png").getImage();
        imageBounds = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }

    @Override
    public int getType() {
        return 3;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(image, x, y, null);
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public int getHealth() {
        return health;
    }
    /**
     * Returns cost of the plant in Sun Points.
     * @return cost of the plant in Sun Points
     */
    public static int getCost() {
        return COST;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public Rectangle getBounds() {
        return imageBounds;
    }
}