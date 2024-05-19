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

    public Sunflower(int x, int y, int health) {
        super(x, y, health);
        theTimer.addActionListener(this);
        resourceManager = ResourceManager.getInstance();
        this.produceCycle = 0;
        image = new ImageIcon("src/Sunflower.png").getImage();

    }

    /*
    @Override
    public void update() {
    }
    */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(image, x, y, null);
    }

    @Override
    public void takeDamage(int damage) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        produceCycle++;
        if (produceCycle >= CYCLE_DURATION) {
            produceCycle = 0;
            resourceManager.addSunPoints(25);
        }
    }
}