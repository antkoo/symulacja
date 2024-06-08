import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Projectile implements ActionListener {
    public static Timer theTimer = Panel.theTimer;
    public int x, y;
    private boolean alive;
    private static final int SPEED = 10;
    private static final int DAMAGE = 20;
    Image image;
    private Rectangle imageBounds;

    public Projectile(int x, int y) {
        theTimer.addActionListener(this);
        this.image = new ImageIcon("src/Projectile.png").getImage();
        this.x = x;
        this.y = y;
        this.imageBounds = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(image, x, y, null);
    }

    public int getDamage() {
        return DAMAGE;
    }

    public int getWidth() {
        return image.getWidth(null);
    }

    public Rectangle getBounds() {
        return imageBounds;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.x += SPEED;
        this.imageBounds.x=x;
    }
}