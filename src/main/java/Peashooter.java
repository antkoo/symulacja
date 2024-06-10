import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Peashooter extends Plant {
    public List<Projectile> Projectiles;
    public static Timer theTimer = Panel.theTimer;
    public List<Zombie> Zombies;
    Image image;
    private int shootCycle;
    private static final int SHOOT_INTERVAL = 15;
    private static final int HEALTH = 200;
    private static final int COST = 100;

    public Peashooter(int x, int y) {
        super(x, y);
        //set starting values
        this.health = HEALTH;
        this.shootCycle = 0;
        theTimer.addActionListener(this); //make this entity listen to the timer
        Zombies = Panel.Zombies;//use the same list of zombies across every class
        this.Projectiles = new ArrayList<>(); //create a separate list of projectiles for each Peashooter
        image = new ImageIcon("src/Peashooter.png").getImage();
        imageBounds = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(image, x, y, null);
        //for each projectile of this entity, check if it hit a zombie and if so, remove it
        if (this.Projectiles!=null)
            for (int i = 0; i < this.Projectiles.size(); i++) {
                Projectile projectile = this.Projectiles.get(i);
                if (projectile!=null) {
                    if (CollisionManager.checkProjectileHit(projectile, Zombies) || projectile.x>=(Panel.COLUMNS*Panel.SQUARE_SIZE-projectile.getWidth())){
                        this.Projectiles.remove(i);
                        i--;
                    }
                    //if it didn't hit, paint it
                    projectile.paint(g);
                }
            }
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //make it shoot each SHOOT_INTERVAL amount of ticks
        this.shootCycle++;
        if (this.shootCycle >= SHOOT_INTERVAL) {
            this.shootCycle = 0;
            shoot();
        }
    }

    public void shoot() {
        this.Projectiles.add(new Projectile(x+image.getWidth(null), y));
    }

    public int getHealth() {
        return health;
    }

    public int getCost() {
        return COST;
    }


    @Override
    public Rectangle getBounds() {
        return imageBounds;
    }

}