import javax.swing.*;
import java.awt.*;

/**
 * The window, where the simulation is created. Simple JFrame, that adds the {@link Panel} to the window.
 */
public class Board extends JFrame {
    public Board() {
        this.setTitle("Plants vs Zombies simulation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(Panel.SQUARE_SIZE*Panel.COLUMNS,Panel.SQUARE_SIZE*(Panel.ROWS+1));
        this.setLayout(null);
        this.setVisible(true);
        this.getContentPane().setBackground(new Color(0x4f7942));
        this.add(new Panel());
    }
}
