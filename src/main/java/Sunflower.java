import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class Sunflower extends Plant {
    private GameTimer timer;
    Image image;
    public ResourceManager resourceManager;
    private int produceCycle;
    private static final int CYCLE_DURATION = 24;

    public Sunflower(int x, int y, int health) {
        super(x, y, health);
        timer = new GameTimer();
        timer.addActionListener(this);
        resourceManager = ResourceManager.getInstance();
        this.produceCycle = 0;
        BufferedImage bf = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
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
        System.out.println("aha");
        produceCycle++;
        if (produceCycle >= CYCLE_DURATION) {
            System.out.println(resourceManager.getSunPoints());
            produceCycle = 0;
            resourceManager.addSunPoints(25);
        }
    }
}