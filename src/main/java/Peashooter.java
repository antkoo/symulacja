import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class Peashooter extends Plant {
    public List<Projectile> Projectiles;
    public static Timer theTimer = Panel.theTimer;

    Image image;
    private int shootCycle;
    private static final int SHOOT_INTERVAL = 15;
    private static final int HEALTH = 200;
    private static final int COST = 100;

    public Peashooter(int x, int y) {
        super(x, y);
        Projectiles = Panel.Projectiles;
        this.health = HEALTH;
        theTimer.addActionListener(this);
        this.shootCycle = 0;
        image = new ImageIcon("src/Peashooter.png").getImage();
        imageBounds = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(image, x, y, null);
        g2D.draw(imageBounds);
        for (int i = 0; i < Projectiles.size(); i++) {
            Projectile projectile = Projectiles.get(i);
            //System.out.println(Projectiles.size());
            if (projectile.x>=(Panel.COLUMNS*Panel.SQUARE_SIZE-projectile.getWidth())) {
                Projectiles.remove(i);
                i--;
            }
            projectile.paint(g);
        }
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
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
        Projectiles.add(new Projectile(x+ image.getWidth(null), y));
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