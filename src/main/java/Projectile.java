import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Projectile implements ActionListener {
    public static Timer theTimer = Panel.theTimer;
    public int x, y;
    private static final int SPEED = 10;
    private static final int DAMAGE = 20;
    Image image;
    private final Rectangle imageBounds;

    public Projectile(int x, int y) {
        //set starting values
        this.x = x;
        this.y = y;
        theTimer.addActionListener(this); //make this entity listen to the timer
        this.image = new ImageIcon("src/Projectile.png").getImage();
        this.imageBounds = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }

    public void paintComponent(Graphics g) {
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
        this.x += SPEED;//move right towards zombies
        this.imageBounds.x=x;//update imageBounds for correct collision register
    }
}