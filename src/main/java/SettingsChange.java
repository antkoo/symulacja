import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SettingsChange extends JFrame implements ChangeListener {
    public static int DELAY = Panel.DELAY;
    public static int START_ZOMBIE_AMOUNT = Panel.START_ZOMBIE_AMOUNT;
    public static int PLANT_SPAWN_INTERVAL = Panel.PLANT_SPAWN_INTERVAL;
    public static int ZOMBIE_SPAWN_INTERVAL = Panel.ZOMBIE_SPAWN_INTERVAL;
    public static int START_SUN_POINTS = Panel.START_SUN_POINTS;
    public static int BASIC_ZOMBIE_SPAWN_CHANCE = Panel.BASIC_ZOMBIE_SPAWN_CHANCE;
    public static int BUCKETHEAD_ZOMBIE_SPAWN_CHANCE = Panel.BUCKETHEAD_ZOMBIE_SPAWN_CHANCE;
    public static int SUNFLOWER_SPAWN_CHANCE = Panel.SUNFLOWER_SPAWN_CHANCE;
    public static int PEASHOOTER_SPAWN_CHANCE = Panel.PEASHOOTER_SPAWN_CHANCE;
    public static int CHERRY_BOMB_SPAWN_CHANCE = Panel.CHERRY_BOMB_SPAWN_CHANCE;
    public static int WALNUT_SPAWN_CHANCE = Panel.WALNUT_SPAWN_CHANCE;
    public static int[] values = {DELAY, START_ZOMBIE_AMOUNT, PLANT_SPAWN_INTERVAL, ZOMBIE_SPAWN_INTERVAL, START_SUN_POINTS, BASIC_ZOMBIE_SPAWN_CHANCE, BUCKETHEAD_ZOMBIE_SPAWN_CHANCE,
            SUNFLOWER_SPAWN_CHANCE, PEASHOOTER_SPAWN_CHANCE, CHERRY_BOMB_SPAWN_CHANCE, WALNUT_SPAWN_CHANCE};
    public static String[] strings = {"DELAY", "START_ZOMBIE_AMOUNT", "PLANT_SPAWN_INTERVAL", "ZOMBIE_SPAWN_INTERVAL", "START_SUN_POINTS", "BASIC_ZOMBIE_SPAWN_CHANCE",
            "BUCKETHEAD_ZOMBIE_SPAWN_CHANCE", "SUNFLOWER_SPAWN_CHANCE", "PEASHOOTER_SPAWN_CHANCE", "CHERRY_BOMB_SPAWN_CHANCE", "WALNUT_SPAWN_CHANCE"};

    List<JSlider> sliders = new ArrayList<>();
    static List<JLabel> labels = new ArrayList<>();


    public SettingsChange() {
        this.setTitle("Settings Changer");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setSize(Panel.SQUARE_SIZE*Panel.COLUMNS, Panel.SQUARE_SIZE*(Panel.ROWS+1));
        this.setVisible(true);
        this.getContentPane().setBackground(new Color(0x4f7942));
        this.setLayout(new FlowLayout());
        JLabel Title = new JLabel("Here you can change certain parameters.", JLabel.CENTER);
        Title.setFont(new Font("Serif", Font.PLAIN, 50));
        this.add(Title);
        for (int i=0; i<11; i++) {
            int value = values[i];
            JPanel panel = new JPanel();
            JSlider slider = new JSlider(0, value*10, value);
            JLabel label = new JLabel(strings[i] + " = " + values[i], JLabel.CENTER);
            panel.setLayout(new FlowLayout());
            panel.setPreferredSize(new Dimension((int) (Panel.SQUARE_SIZE*4.3f), Panel.SQUARE_SIZE/4*3));
            panel.add(label);
            slider.setPaintTicks(true);
            slider.setMinorTickSpacing(value/10);
            slider.setPaintTrack(true);
            slider.setMajorTickSpacing(value);
            slider.setPreferredSize(new Dimension((int) (Panel.SQUARE_SIZE*4.3f), Panel.SQUARE_SIZE/2));
            slider.setPaintLabels(true);
            slider.addChangeListener(this);
            panel.add(slider);
            this.add(panel);
            sliders.add(slider);
            labels.add(label);
        }
    }
    @Override
    public void stateChanged(ChangeEvent e) {
        for (int i = 0; i<11; i++) {
            if (e.getSource() == sliders.get(i)) {
                values[i] = sliders.get(i).getValue();
                labels.get(i).setText(strings[i] + " = " + values[i]);
                try {
                    Field field = this.getClass().getDeclaredField(strings[i]);
                    field.set(this, values[i]);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        }
    }
}
