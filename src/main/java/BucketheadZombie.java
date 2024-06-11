import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BucketheadZombie extends Zombie {
    public static Timer theTimer = Panel.theTimer;
    Image image;
    private static final int HEALTH = 500;
    private static final int SPEED = 1;
    private static final int ATTACK_DAMAGE = 40;

    public BucketheadZombie(int x, int y) {
        super(x, y);
        //set starting values
        this.health = HEALTH;
        theTimer.addActionListener(this);//make this entity listen to the timer
        image = new ImageIcon("src/BucketheadZombie.png").getImage();
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
        return 1;
    }

    @Override
    public void paintComponent(Graphics g) {
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
        x-=SPEED;//every tick, this zombie moves left SPEED amount of pixels
        imageBounds.x=x;//update imageBounds, so that the collisions register properly
    }
}