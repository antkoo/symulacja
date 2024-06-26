import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * A plant, that explodes in a 3x3 square radius after death or certain amount of time.
 */

public class CherryBomb extends Plant {
    Image image;
    boolean alive;
    Explosion explosion;
    /**
     * Copy of {@link Panel#Zombies} used in {@link CollisionManager#checkExplosionDeaths}.
     */
    public List<Zombie> Zombies;
    private int explodeCycle;
    private static final int EXPLODE_TIME = 20;
    private static final int HEALTH = 70;
    private static final int COST = 150;
    /**
     * Constructor of a plant
     * @param x x coordinate
     * @param y y coordinate
     */
    public CherryBomb(int x, int y) {
        super(x, y);
        //set starting values
        this.alive = true;
        this.health = HEALTH;
        this.explodeCycle = 0;
        Zombies = Panel.Zombies; //use the same list of zombies across every class
        theTimer.addActionListener(this); //make this entity listen to the timer
        image = new ImageIcon("src/CherryBomb.png").getImage();
        imageBounds = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        if (this.health==0) explode(); //when it dies, explode, no matter if explodeCycle reached EXPLODE_TIME
        g2D.drawImage(image, x, y, null);
        //if explosion exists, it exploded, so it needs to check, what the explosion killed and remove the explosion and the CherryBomb
        if (this.explosion != null) {
            this.explosion.paintComponent(g);
            CollisionManager.checkExplosionDeaths(this.explosion, Zombies);
            this.explosion = null;
            //making sure CherryBomb dies, so that it doesn't explode more than once
            this.health = 0;
            this.alive = false;
        }
    }

    @Override
    public void takeDamage(int damage) {
        this.health -= damage;
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

    /**
     * Explode by creating an {@link Explosion}.
     */
    public void explode() {
        this.explosion = new Explosion(x-Panel.SQUARE_SIZE, y-Panel.SQUARE_SIZE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if it dies, make it explode regardless of explodeCycle
        if (this.health<=0) {
            explode();
            this.explodeCycle = 0;
        }
        //make it explode after EXPLODE_TIME amount of ticks
        if (this.alive) this.explodeCycle++;
        if (this.explodeCycle >= EXPLODE_TIME) {
            this.explodeCycle = 0;
            explode();
        }
    }

    @Override
    public Rectangle getBounds() {
        return imageBounds;
    }
}