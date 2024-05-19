import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Projectile implements ActionListener {
    public static Timer theTimer = Panel.theTimer;
    public int x, y;
    private int speed;
    private int damage;
    private static final int SPEED = 20;
    private static final int DAMAGE = 20;
    Image image;

    public Projectile(int x, int y) {
        theTimer.addActionListener(this);
        image = new ImageIcon("src/Projectile.png").getImage();
        this.x = x;
        this.y = y;
        this.speed = SPEED;
        this.damage = DAMAGE;
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(image, x, y, null);
    }

    public int getDamage() {
        return damage;
    }

    public int getWidth() {
        return image.getWidth(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x += speed;

    }
}