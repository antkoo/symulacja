import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;

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
