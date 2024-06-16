import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
/**
 * A window with sliders to change settings specified in SettingsChange#strings.
 */
public class SettingsChange extends JFrame implements ChangeListener {
    //take default values from main Panel
    /**
     * Tick value
     */
    public static int DELAY = Panel.DELAY;
    /**
     * Amount of Zombies in beginning of simulation.
     */
    public static int START_ZOMBIE_AMOUNT = Panel.START_ZOMBIE_AMOUNT;
    /**
     * Amount of ticks till {@link Plant} spawn.
     */
    public static int PLANT_SPAWN_INTERVAL = Panel.PLANT_SPAWN_INTERVAL;
    /**
     * Amount of ticks till {@link Zombie} spawn.
     */
    public static int ZOMBIE_SPAWN_INTERVAL = Panel.ZOMBIE_SPAWN_INTERVAL;
    /**
     * Starting amount of Sun Points in {@link ResourceManager}.
     */
    public static int START_SUN_POINTS = Panel.START_SUN_POINTS;
    /**
     * Percentage of chance to spawn a {@link BasicZombie}.
     */
    public static int BASIC_ZOMBIE_SPAWN_CHANCE = Panel.BASIC_ZOMBIE_SPAWN_CHANCE;
    /**
     * Percentage of chance to spawn a {@link BucketheadZombie}.
     */
    public static int BUCKETHEAD_ZOMBIE_SPAWN_CHANCE = Panel.BUCKETHEAD_ZOMBIE_SPAWN_CHANCE;
    /**
     * Percentage of chance to spawn a {@link Sunflower}.
     */
    public static int SUNFLOWER_SPAWN_CHANCE = Panel.SUNFLOWER_SPAWN_CHANCE;
    /**
     * Percentage of chance to spawn a {@link Peashooter}.
     */
    public static int PEASHOOTER_SPAWN_CHANCE = Panel.PEASHOOTER_SPAWN_CHANCE;
    /**
     * Percentage of chance to spawn a {@link CherryBomb}.
     */
    public static int CHERRY_BOMB_SPAWN_CHANCE = Panel.CHERRY_BOMB_SPAWN_CHANCE;
    /**
     * Percentage of chance to spawn a {@link Walnut}.
     */
    public static int WALNUT_SPAWN_CHANCE = Panel.WALNUT_SPAWN_CHANCE;
    /**
     * A list of all the settings' values.
     */
    public static int[] values = {DELAY, START_ZOMBIE_AMOUNT, PLANT_SPAWN_INTERVAL, ZOMBIE_SPAWN_INTERVAL, START_SUN_POINTS, BASIC_ZOMBIE_SPAWN_CHANCE, BUCKETHEAD_ZOMBIE_SPAWN_CHANCE,
            SUNFLOWER_SPAWN_CHANCE, PEASHOOTER_SPAWN_CHANCE, CHERRY_BOMB_SPAWN_CHANCE, WALNUT_SPAWN_CHANCE};//store values in array for smaller loops
    /**
     * A list of all the settings' String names.
     */
    public static String[] strings = {"DELAY", "START_ZOMBIE_AMOUNT", "PLANT_SPAWN_INTERVAL", "ZOMBIE_SPAWN_INTERVAL", "START_SUN_POINTS", "BASIC_ZOMBIE_SPAWN_CHANCE",
            "BUCKETHEAD_ZOMBIE_SPAWN_CHANCE", "SUNFLOWER_SPAWN_CHANCE", "PEASHOOTER_SPAWN_CHANCE", "CHERRY_BOMB_SPAWN_CHANCE", "WALNUT_SPAWN_CHANCE"};//store strings in array for smaller loops
    //define sliders
    List<JSlider> sliders = new ArrayList<>();
    static List<JLabel> labels = new ArrayList<>();

    /**
     * Creates the window and sliders.
     */
    public SettingsChange() {
        //default configuration
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
            //create sliders based on values
            int value = values[i];
            JPanel panel = new JPanel();
            JSlider slider = new JSlider(0, value*10, value);
            JLabel label = new JLabel(strings[i] + " = " + values[i], JLabel.CENTER);//description of slider
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

    /**
     * If a slider is moved, this function is called to change the value of the field based on the slider changed.
     * @param e slider was moved
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        for (int i = 0; i<11; i++) {
            if (e.getSource() == sliders.get(i)) {
                values[i] = sliders.get(i).getValue();//if slider changed value, change it in the array
                labels.get(i).setText(strings[i] + " = " + values[i]);//change the slider title
                try {
                    Field field = this.getClass().getDeclaredField(strings[i]);//choose a field with a name from the strings array
                    field.set(this, values[i]);//set the value of field to the corresponding value in values array
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        }
    }
}
