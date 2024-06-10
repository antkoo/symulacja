import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Walnut extends Plant {
    public static Timer theTimer = Panel.theTimer;
    Image image;
    private static final int HEALTH = 1500;
    private static final int COST = 50;

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
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(image, x, y, null);
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    public int getHealth() {
        return health;
    }

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