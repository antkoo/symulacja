import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Sunflower extends Plant {
    public static Timer theTimer = Panel.theTimer;
    public boolean alive;
    Image image;
    public static ResourceManager resourceManager;
    protected int produceCycle;
    private static final int CYCLE_DURATION = 24;
    private static final int HEALTH = 300;
    private static final int COST = 50;
    private static final int PRODUCE_AMOUNT = 25;

    public Sunflower(int x, int y) {
        super(x, y);
        theTimer.addActionListener(this); //make this entity listen to the timer
        resourceManager = Panel.resourceManager; //get a copy of the same ResourceManager, as other entities
        //set starting values
        this.health = HEALTH;
        this.produceCycle = 0;
        this.alive=true;
        image = new ImageIcon("src/Sunflower.png").getImage();
        imageBounds = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }

    @Override
    public int getType() {
        return 0;
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

    public int getHealth() {
        return health;
    }

    public static int getCost() {
        return COST;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //make it produce PRODUCE_AMOUNT of sun points each CYCLE_DURATION amount of ticks
        if (produceCycle>=0) {
            this.produceCycle++;
        }
        if (this.produceCycle >= CYCLE_DURATION) {
            this.produceCycle = 0;
            resourceManager.addSunPoints(PRODUCE_AMOUNT);
        }
    }

    @Override
    public Rectangle getBounds() {
        return imageBounds;
    }
}