import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class Sunflower extends Plant {
    public static Timer theTimer = Panel.theTimer;
    Image image;
    private ResourceManager resourceManager;
    private int produceCycle;
    private static final int CYCLE_DURATION = 24;
    private static final int HEALTH = 300;
    private static final int COST = 50;

    public Sunflower(int x, int y) {
        super(x, y);
        this.health = HEALTH;
        theTimer.addActionListener(this);
        resourceManager = ResourceManager.getInstance();
        this.produceCycle = 0;
        image = new ImageIcon("src/Sunflower.png").getImage();
        imageBounds = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));

    }

    @Override
    public int getType() {
        return 0;
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

    public int getCost() {
        return COST;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        produceCycle++;
        if (produceCycle >= CYCLE_DURATION) {
            produceCycle = 0;
            resourceManager.addSunPoints(25);
        }
    }

    @Override
    public Rectangle getBounds() {
        return imageBounds;
    }
}