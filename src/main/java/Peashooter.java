import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Peashooter extends Plant {
    public List<Projectile> Projectiles = new ArrayList<>();
    public static Timer theTimer = Panel.theTimer;

    Image image;
    private int shootCycle;
    private static final int SHOOT_INTERVAL = 30;
    private static final int HEALTH = 200;
    private static final int COST = 100;

    public Peashooter(int x, int y) {
        super(x, y);
        this.health = HEALTH;
        theTimer.addActionListener(this);
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
        for (int i = 0; i < Projectiles.size(); i++) {
            Projectile projectile = Projectiles.get(i);
            if (projectile.x>=(Panel.COLUMNS*Panel.SQUARE_SIZE-projectile.getWidth())) {
                Projectiles.remove(i);
                i--;
            }
            projectile.paint(g);



        }
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
        Projectiles.add(new Projectile(x+ image.getWidth(null), y));
    }

    public int getHealth() {
        return health;
    }

    public int getCost() {
        return COST;
    }

}