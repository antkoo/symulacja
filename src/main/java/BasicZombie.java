import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BasicZombie extends Zombie {
    public static Timer theTimer = Panel.theTimer;

    Image image;
    private static final int HEALTH = 200;
    private static final int SPEED = 2;
    private static final int ATTACK_DAMAGE = 40;

    public BasicZombie(int x, int y) {
        super(x, y);
        
        this.health = HEALTH;
        theTimer.addActionListener(this);
        image = new ImageIcon("src/BasicZombie.png").getImage();
        imageBounds = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }

    @Override
    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return SPEED;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(image, x, y, null);
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public int getAttackDamage() {
        return ATTACK_DAMAGE;
    }

    @Override
    public Rectangle getBounds() {
        return imageBounds;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        x-=SPEED;
        imageBounds.x=x;
    }
}