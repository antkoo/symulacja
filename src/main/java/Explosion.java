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
        //set starting values
        this.x = x;
        this.y = y;
        theTimer.addActionListener(this); //make this entity listen to the timer
        image = new ImageIcon("src/Explosion.png").getImage();
        imageBounds = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(image, x, y, null);
    }
    public int getDamage() {
        return DAMAGE;
    }

    public Rectangle getBounds() {
        return imageBounds;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}