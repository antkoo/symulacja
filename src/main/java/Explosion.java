import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Explosion implements ActionListener {
    public static Timer theTimer = Panel.theTimer;
    public int x, y;
    private static final int DAMAGE = 9999;
    Image image;
    private final Rectangle imageBounds;

    public Explosion(int x, int y) {
        theTimer.addActionListener(this);
        image = new ImageIcon("src/Explosion.png").getImage();
        this.x = x;
        this.y = y;
        imageBounds = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
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
    }
}