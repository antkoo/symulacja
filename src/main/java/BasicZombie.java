import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BasicZombie extends Zombie {
    public static Timer theTimer = Panel.theTimer;
    Image image;
    private static final int HEALTH = 200;
    private static final int SPEED = 2;

    public BasicZombie(int x, int y) {
        super(x, y);
        this.health = HEALTH;
        theTimer.addActionListener(this);
        image = new ImageIcon("src/BasicZombie.png").getImage();
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(image, x, y, null);
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x-=SPEED;
    }
}