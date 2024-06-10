import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Panel extends JPanel implements ActionListener {

    public static List<Plant> Plants = new ArrayList<>();
    public static List<Zombie> Zombies = new ArrayList<>();
    int x, y, plantSpawnCycle, zombieSpawnCycle, sum, timeElapsed;
    double chance;
    public static List<List<int[]>> SpawnSquares = new ArrayList<>();
    private final ResourceManager resourceManager;
    public static Timer theTimer;
    public static int DELAY = 50;
    public static int START_ZOMBIE_AMOUNT = 10;
    public static int PLANT_SPAWN_INTERVAL = 3;
    public static int ZOMBIE_SPAWN_INTERVAL = 6;
    public static int START_SUN_POINTS = 500;
    public static int BASIC_ZOMBIE_SPAWN_CHANCE = 70;
    public static int BUCKETHEAD_ZOMBIE_SPAWN_CHANCE = 30;
    public static int SUNFLOWER_SPAWN_CHANCE = 45;
    public static int PEASHOOTER_SPAWN_CHANCE = 25;
    public static int CHERRY_BOMB_SPAWN_CHANCE = 10;
    public static int WALNUT_SPAWN_CHANCE = 20;

    public static final int SQUARE_SIZE = 100;
    public static final int ROWS = 5;
    public static final int PLANT_COLUMNS = 5;
    public static final int ZOMBIE_COLUMNS = 4;
    public static final int COLUMNS = PLANT_COLUMNS + ZOMBIE_COLUMNS;
    JLabel label;
    JLabel welcome1 = new JLabel();
    JLabel welcome2 = new JLabel();
    JLabel welcome3 = new JLabel();
    JLabel welcome4 = new JLabel();
    JLabel welcome5 = new JLabel();
    List<JLabel> welcomes = new ArrayList<>();


    JPanel panel, outsidePanel;
    JButton toSpawnSelector = new JButton("To Spawn Selector");
    JButton startSimulation = new JButton("Start Simulation");
    JButton pauseSimulation = new JButton("Pause Simulation");
    JButton toSettingsChange = new JButton("To Settings Change");
    public static String[] strings = {"Amount of Basic Zombies", "Amount of Buckethead Zombies", "Amount of Sunflowers", "Amount of Peashooters", "Amount of Cherry Bombs",
            "Amount of Walnuts", "Amount of Peas", "Amount of Sun Points", "Time Elapsed"};
    List<JLabel> labels = new ArrayList<>();
    List<JPanel> panels = new ArrayList<>();
    public Panel() {
        resourceManager = ResourceManager.getInstance();
        theTimer = new Timer(DELAY, this);
        this.setSize(SQUARE_SIZE*COLUMNS,SQUARE_SIZE*(ROWS+1));
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(new Color(0x4f7942));

        toSpawnSelector.setBounds(0, 0, 2*SQUARE_SIZE, SQUARE_SIZE/4);
        toSpawnSelector.setFocusable(false);
        toSpawnSelector.addActionListener(this);
        startSimulation.setBounds(0, SQUARE_SIZE/4, 2*SQUARE_SIZE, SQUARE_SIZE/4);
        startSimulation.setFocusable(false);
        startSimulation.addActionListener(this);
        pauseSimulation.setBounds(0, SQUARE_SIZE*2/4, 2*SQUARE_SIZE, SQUARE_SIZE/4);
        pauseSimulation.setFocusable(false);
        pauseSimulation.addActionListener(this);
        pauseSimulation.setEnabled(false);
        toSettingsChange.setBounds(0, SQUARE_SIZE*3/4, 2*SQUARE_SIZE, SQUARE_SIZE/4);
        toSettingsChange.setFocusable(false);
        toSettingsChange.addActionListener(this);
        this.add(toSpawnSelector);
        this.add(startSimulation);
        this.add(pauseSimulation);
        this.add(toSettingsChange);
        for (int i = 0; i<5; i++) {
            JLabel welcome;
            switch (i) {
                case 0:
                    welcome = welcome1;
                    welcome.setText("Welcome to the Plants vs Zombies-themed simulation. ");
                    break;
                case 1:
                    welcome = welcome2;
                    welcome.setText("In order to begin the simulation, you need to choose on which squares ");
                    break;
                case 2:
                    welcome = welcome3;
                    welcome.setText("can the different types of plants spawn on by using the \"To Spawn Selector\" button. ");
                    break;
                case 3:
                    welcome = welcome4;
                    welcome.setText("To change different settings within the simulation, use the \"To Settings Change\" button. ");
                    break;
                default:
                    welcome = welcome5;
                    welcome.setText("To start the simulation, click the \"Start Simulation\" button.");
                    break;
            }
            welcome.setBounds(0,(int) (Panel.SQUARE_SIZE+ (i * 0.5f * Panel.SQUARE_SIZE)), 9*Panel.SQUARE_SIZE, (int) (0.75f*Panel.SQUARE_SIZE));
            welcome.setHorizontalAlignment(JLabel.CENTER);
            welcome.setFont(new Font("Serif", Font.ITALIC, 24));
            welcomes.add(welcome);
            this.add(welcome);
        }
        outsidePanel = new JPanel();
        outsidePanel.setLayout(new FlowLayout());
        outsidePanel.setBounds(2*SQUARE_SIZE, 0, 7*SQUARE_SIZE, SQUARE_SIZE);
        outsidePanel.setBackground(new Color(0x4f7942));
        for (int i=0; i<9; i++) {
            label = new JLabel();
            panel = new JPanel();
            panel.setPreferredSize(new Dimension((int) ((Panel.SQUARE_SIZE) * 2.1f), Panel.SQUARE_SIZE / 3));
            sum = counterUpdater(i);
            label.setText(strings[i] + ": " +sum);
            panel.setBackground(new Color(0x4f7942));
            labels.add(label);
            panel.add(label);
            panels.add(panel);
            outsidePanel.add(panel);
        }
        this.add(outsidePanel);


    }

    public int counterUpdater(int i) {
        sum = 0;
        switch (i) {
            case 0:
                if (!Zombies.isEmpty()) {
                    for (Zombie zombie : Zombies) {
                        if (zombie.getType() == 0) {
                            sum++;
                        }
                    }
                }
                break;
            case 1:
                if (!Zombies.isEmpty()) {
                    for (Zombie zombie : Zombies) {
                        if (zombie.getType() == 1) {
                            sum++;
                        }
                    }
                }
                break;
            case 2:
                if (!Plants.isEmpty()) {
                    for (Plant plant : Plants) {
                        if (plant.getType() == 0) {
                            sum++;
                        }
                    }
                }
                break;
            case 3:
                if (!Plants.isEmpty()) {
                    for (Plant plant : Plants) {
                        if (plant.getType() == 1) {
                            sum++;
                        }
                    }
                }
                break;
            case 4:
                if (!Plants.isEmpty()) {
                    for (Plant plant : Plants) {
                        if (plant.getType() == 2) {
                            sum++;
                        }
                    }
                }
                break;
            case 5:
                if (!Plants.isEmpty()) {
                    for (Plant plant : Plants) {
                        if (plant.getType() == 3) {
                            sum++;
                        }
                    }
                }
                break;
            case 6:
                if (!Plants.isEmpty()) {
                    for (Plant plant : Plants) {
                        if (plant.getType() == 1) {
                            Peashooter peashooter = (Peashooter) plant; //downcasting to access Peashooter-exclusive function
                            if (!peashooter.Projectiles.isEmpty()) {
                                sum += peashooter.Projectiles.size();
                            }
                        }
                    }
                }
                break;
            case 7:
                sum = resourceManager.getSunPoints();
                break;
            case 8:
                if (theTimer.isRunning()) {
                    sum = timeElapsed;
                }
                break;
        }
        return sum;
    }

    public void spawnRandomPlant() {
        x = ThreadLocalRandom.current().nextInt(0,PLANT_COLUMNS*SQUARE_SIZE);
        y = ThreadLocalRandom.current().nextInt(SQUARE_SIZE,ROWS*SQUARE_SIZE);
        chance = ThreadLocalRandom.current().nextDouble(0,SUNFLOWER_SPAWN_CHANCE+PEASHOOTER_SPAWN_CHANCE+CHERRY_BOMB_SPAWN_CHANCE+WALNUT_SPAWN_CHANCE);
        int chosen;
        if (chance<SUNFLOWER_SPAWN_CHANCE) {
            chosen = 0;
        } else if (chance<SUNFLOWER_SPAWN_CHANCE+PEASHOOTER_SPAWN_CHANCE) {
            chosen = 1;
        } else if (chance<SUNFLOWER_SPAWN_CHANCE+PEASHOOTER_SPAWN_CHANCE+CHERRY_BOMB_SPAWN_CHANCE) {
            chosen = 2;
        } else {
            chosen = 3;
        }
        List<int[]> squareType = SpawnSquares.get(chosen);
        if (squareType.isEmpty()) {
            spawnRandomPlant();
        }
        for (int[] square : squareType) {
            if (x >= square[0] * SQUARE_SIZE && x <= (square[0] + 1) * SQUARE_SIZE && y >= square[1] * SQUARE_SIZE && y <= (square[1] + 1) * SQUARE_SIZE) {
                if (chosen==0) {
                    Plants.add(new Sunflower(x, y));
                    if (!resourceManager.spendSunPoints(Plants.get(Plants.size()-1).getCost())) {
                        Plants.remove(Plants.size()-1);
                        spawnRandomPlant();
                    }
                } else if (chosen==1) {
                    Plants.add(new Peashooter(x, y));
                    if (!resourceManager.spendSunPoints(Plants.get(Plants.size()-1).getCost())) {
                        Plants.remove(Plants.size()-1);
                        spawnRandomPlant();
                    }
                } else if (chosen==2) {
                    Plants.add(new CherryBomb(x, y));
                    if (!resourceManager.spendSunPoints(Plants.get(Plants.size()-1).getCost())) {
                        Plants.remove(Plants.size()-1);
                        spawnRandomPlant();
                    }
                } else {
                    Plants.add(new Walnut(x, y));
                    if (!resourceManager.spendSunPoints(Plants.get(Plants.size()-1).getCost())) {
                        Plants.remove(Plants.size()-1);
                        spawnRandomPlant();
                    }
                }
            }
        }
    }

    public void spawnRandomZombie() {
        x = ThreadLocalRandom.current().nextInt((COLUMNS-1)*SQUARE_SIZE, (COLUMNS) * SQUARE_SIZE);
        y = ThreadLocalRandom.current().nextInt(SQUARE_SIZE, ROWS * SQUARE_SIZE);
        chance = ThreadLocalRandom.current().nextDouble(0,BASIC_ZOMBIE_SPAWN_CHANCE+BUCKETHEAD_ZOMBIE_SPAWN_CHANCE);
        if (chance<BASIC_ZOMBIE_SPAWN_CHANCE) {
            Zombies.add(new BasicZombie(x, y));
        } else {
            Zombies.add(new BucketheadZombie(x, y));
        }

    }

    public void gameStart() {
        for (JLabel welcome : welcomes) {
            welcome.setVisible(false);
        }
        timeElapsed = 0;
        pauseSimulation.setEnabled(true);
        Zombies.clear();
        Plants.clear();
        resourceManager.spendSunPoints(resourceManager.getSunPoints());
        theTimer.start();
        resourceManager.addSunPoints(START_SUN_POINTS);
        plantSpawnCycle = 0;
        zombieSpawnCycle = 0;
        while (resourceManager.getSunPoints()>0) {
            spawnRandomPlant();
        }
        for (int i = 0; i<START_ZOMBIE_AMOUNT; i++) {
            spawnRandomZombie();
        }
        toSpawnSelector.setEnabled(false);
        startSimulation.setText("Restart Simulation");
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==toSpawnSelector) {
            new SpawnSelector();
        }
        if (e.getSource()==toSettingsChange) {
            new SettingsChange();
        }
        if (e.getSource()==startSimulation) {
            boolean allEmpty = true;
            for (List<int[]> spawnSquare : SpawnSquares) {
                if (!spawnSquare.isEmpty()) {
                    allEmpty = false;
                    break;
                }
            }
            if (allEmpty) {
                JOptionPane.showMessageDialog(null, "ALL SPAWN SQUARES EMPTY", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                gameStart();
            }

        }
        if (e.getSource()==pauseSimulation) {
            if (theTimer.isRunning()) {
                theTimer.stop();
                pauseSimulation.setText("Unpause Simulation");
            } else {
                theTimer.start();
                pauseSimulation.setText("Pause Simulation");
            }

        }
        if (theTimer.isRunning()) {
            plantSpawnCycle++;
            if (plantSpawnCycle>=PLANT_SPAWN_INTERVAL) {
                spawnRandomPlant();
                plantSpawnCycle=0;
            }

            zombieSpawnCycle++;
            if (zombieSpawnCycle>=ZOMBIE_SPAWN_INTERVAL) {
                spawnRandomZombie();
                zombieSpawnCycle=0;
            }
            for(Zombie zombie : Zombies) {
                if (zombie.x<=0) {
                    JOptionPane.showMessageDialog(null, "GAME OVER", "YOU LOSE", JOptionPane.ERROR_MESSAGE);
                    theTimer.stop();
                    toSpawnSelector.setEnabled(true);
                    break;
                }
            }

            if(Zombies.isEmpty() && theTimer.isRunning()) {
                JOptionPane.showMessageDialog(null, "YOU WIN", "YOU WIN", JOptionPane.INFORMATION_MESSAGE);
                theTimer.stop();
                startSimulation.setText("Restart Simulation");
                toSpawnSelector.setEnabled(true);
            }

            repaint();
            CollisionManager.checkAttacks(Plants,Zombies);
        }
        for (int i = 0; i<11; i++) {
            if (!SettingsChange.labels.isEmpty()) {
                SettingsChange.labels.get(i).setText(SettingsChange.strings[i] + " = " + SettingsChange.values[i]);
                try {
                    Field field = this.getClass().getDeclaredField(SettingsChange.strings[i]);
                    field.set(this, SettingsChange.values[i]);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

        }
        timeElapsed+=theTimer.getDelay();
        for (int i=0; i<9; i++) {
            labels.get(i).setText(strings[i] + ": " + counterUpdater(i));
        }
        theTimer.setDelay(DELAY);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < Plants.size(); i++) {
            Plant plant = Plants.get(i);
            if (plant.getHealth()<=0) {
                if (plant.getType()==2) {
                    CherryBomb cherryBomb = (CherryBomb) plant; //downcasting to access CherryBomb-exclusive function
                    cherryBomb.explode();
                }
                Plants.remove(i);
                i--;
            }
            plant.paint(g);
        }
        for (int i = 0; i < Zombies.size(); i++) {
            Zombie zombie = Zombies.get(i);
            if (zombie.getHealth()<=0) {
                Zombies.remove(i);
                i--;
            }
            zombie.paint(g);
        }
    }
}
