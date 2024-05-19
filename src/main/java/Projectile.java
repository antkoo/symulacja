import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Projectile implements ActionListener {
    public static Timer theTimer = Panel.theTimer;
    private int x, y;
    private int speed;
    private int damage;
    Image image;

    public Projectile(int x, int y, int speed, int damage) {
        theTimer.addActionListener(this);
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.damage = damage;
        image = new ImageIcon("src/Projectile.png").getImage();
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(image, x, y, null);
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x += speed;
    }
}