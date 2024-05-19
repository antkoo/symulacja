import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Peashooter extends Plant {
    Image image;
    private int shootCycle;
    private static final int SHOOT_INTERVAL = 30;

    public Peashooter(int x, int y, int health) {
        super(x, y, health);
        this.shootCycle = 0;
        image = new ImageIcon("src/Peashooter.png").getImage();
    }

    /*
    @Override
    public void update() {
        produceCycle++;
        if (produceCycle >= CYCLE_DURATION) {
            produceCycle = 0;
            resourceManager.addSunPoints(25);
        }
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
        shootCycle++;
        if (shootCycle >= SHOOT_INTERVAL) {
            shootCycle = 0;
            shoot();
        }
    }

    public void shoot() {

    }
}