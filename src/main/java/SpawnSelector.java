import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpawnSelector extends JFrame implements ActionListener, MouseListener {

    //0 - sunflower
    //1 - peashooter
    //2 - cherry bomb
    //3 - walnut

    int selectedButton;

    JRadioButton sunflowerButton;
    JRadioButton peashooterButton;
    JRadioButton cherryBombButton;
    JRadioButton walnutButton;

    public List<int[]> SunflowerSpawnSquares = new ArrayList<>();
    public List<int[]> PeashooterSpawnSquares = new ArrayList<>();
    public List<int[]> CherryBombSpawnSquares = new ArrayList<>();
    public List<int[]> WalnutSpawnSquares = new ArrayList<>();

    public List<List<int[]>> SpawnSquares = Panel.SpawnSquares;



    public SpawnSelector() {
        SpawnSquares.add(SunflowerSpawnSquares);
        SpawnSquares.add(PeashooterSpawnSquares);
        SpawnSquares.add(CherryBombSpawnSquares);
        SpawnSquares.add(WalnutSpawnSquares);
        this.setTitle("Plants Spawn Squares Selector");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setSize(Panel.SQUARE_SIZE*Panel.COLUMNS, Panel.SQUARE_SIZE*(Panel.ROWS+1));
        this.setVisible(true);
        this.getContentPane().setBackground(new Color(0x4f7942));
        this.setLayout(new FlowLayout());

        List<JRadioButton> buttonList = new ArrayList<>();

        sunflowerButton = new JRadioButton("Sunflower");
        buttonList.add(sunflowerButton);
        peashooterButton = new JRadioButton("Peashooter");
        buttonList.add(peashooterButton);
        cherryBombButton = new JRadioButton("Cherry Bomb");
        buttonList.add(cherryBombButton);
        walnutButton = new JRadioButton("Walnut");
        buttonList.add(walnutButton);

        ButtonGroup group = new ButtonGroup();
        sunflowerButton.setSelected(true);

        for (int i = 0; i<buttonList.size(); i++) {
            group.add(buttonList.get(i));
            buttonList.get(i).addActionListener(this);
            this.add(buttonList.get(i));
            buttonList.get(i).setBackground(new Color(0x4f7942));
        }
        this.addMouseListener(this);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        for (int i = 0; i<Panel.PLANT_COLUMNS; i++) {
            for (int j = 1; j<Panel.ROWS+1; j++) {
                g2D.drawRect(Panel.SQUARE_SIZE*i, Panel.SQUARE_SIZE*j, Panel.SQUARE_SIZE, Panel.SQUARE_SIZE);
            }
        }
        g2D.setPaint(Color.BLUE);
        for (int[] square : SpawnSquares.get(selectedButton)) {
            g2D.drawRect(Panel.SQUARE_SIZE*square[0], Panel.SQUARE_SIZE*square[1], Panel.SQUARE_SIZE, Panel.SQUARE_SIZE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==sunflowerButton) {
            selectedButton = 0;
        }
        if (e.getSource()==peashooterButton) {
            selectedButton = 1;
        }
        if (e.getSource()==cherryBombButton) {
            selectedButton = 2;
        }
        if (e.getSource()==walnutButton) {
            selectedButton = 3;
        }
        repaint();
    }


    public static int[] findArrayIndex(List<List<int[]>> listOfLists, int[] targetArray) {
        for (int i = 0; i < listOfLists.size(); i++) {
            List<int[]> innerList = listOfLists.get(i);
            for (int j = 0; j < innerList.size(); j++) {
                if (Arrays.equals(innerList.get(j), targetArray)) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / Panel.SQUARE_SIZE;
        int y = e.getY() / Panel.SQUARE_SIZE;
        if(x<=Panel.PLANT_COLUMNS && x>=0 && y<=Panel.ROWS+1 && y>=1) {
            int[] coordinates = new int[]{x,y};
            int[] foundIndex = findArrayIndex(SpawnSquares,coordinates);
            if (Arrays.equals(foundIndex, new int[]{-1, -1})) {
                SpawnSquares.get(selectedButton).add(coordinates);
                repaint();
            }
            else if (foundIndex[0]==selectedButton) {
                SpawnSquares.get(selectedButton).remove(foundIndex[1]);
                repaint();
            }
            /*for (List<int[]> squareType : SpawnSquares) {
                for (int[] square : squareType) {
                    for (int i = 0; i<square.length; i++) {
                        System.out.print(square[i]+" "+ i + "  ");
                    }
                    System.out.println();
                }
                System.out.println();
            }*/
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
