import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CherryBomb extends Plant {
    public static Timer theTimer = Panel.theTimer;
    Image image;
    boolean alive;
    public static Explosion explosion;
    private ResourceManager resourceManager;
    public List<Explosion> Explosions;
    public List<Zombie> Zombies;
    private int explodeCycle;
    private static final int EXPLODE_TIME = 20;
    private static final int HEALTH = 70;
    private static final int COST = 150;

    public CherryBomb(int x, int y) {
        super(x, y);
        alive = true;
        this.health = HEALTH;
        Explosions = Panel.Explosions;
        Zombies = Panel.Zombies;
        theTimer.addActionListener(this);
        resourceManager = ResourceManager.getInstance();
        this.explodeCycle = 0;
        image = new ImageIcon("src/CherryBomb.png").getImage();
        imageBounds = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));

    }
    @Override
    public void paint(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;
        if (health==0) explode();
        g2D.drawImage(image, x, y, null);
        g2D.draw(imageBounds);
        if (explosion != null) {
            explosion.paint(g);
            CollisionManager.checkExplosionDeaths(explosion, Zombies);
            explosion = null;
            this.health = 0;
            alive = false;
        }
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

    public void explode() {

        explosion = new Explosion(x-Panel.SQUARE_SIZE, y-Panel.SQUARE_SIZE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (alive) explodeCycle++;
        if (explodeCycle >= EXPLODE_TIME) {
            explodeCycle = 0;
            explode();
        }

    }

    @Override
    public Rectangle getBounds() {
        return imageBounds;
    }
}