import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CherryBomb extends Plant {
    public static Timer theTimer = Panel.theTimer;
    Image image;
    boolean alive;
    public Explosion explosion;
    private ResourceManager resourceManager;
    public List<Explosion> Explosions;
    public List<Zombie> Zombies;
    private int explodeCycle;
    private static final int EXPLODE_TIME = 20;
    private static final int HEALTH = 70;
    private static final int COST = 150;

    public CherryBomb(int x, int y) {
        super(x, y);
        this.alive = true;
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
    public int getType() {
        return 2;
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;
        if (this.health==0) explode();
        g2D.drawImage(image, x, y, null);
        g2D.draw(imageBounds);
        if (this.explosion != null) {
            this.explosion.paint(g);
            CollisionManager.checkExplosionDeaths(this.explosion, Zombies);
            this.explosion = null;
            this.health = 0;
            this.alive = false;
        }
    }


    @Override
    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public int getHealth() {
        return health;
    }

    public int getCost() {
        return COST;
    }

    public void explode() {

        this.explosion = new Explosion(x-Panel.SQUARE_SIZE, y-Panel.SQUARE_SIZE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.health==0) {
            explode();
            this.explodeCycle = 0;
        }
        if (this.alive) explodeCycle++;
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