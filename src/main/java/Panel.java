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
    public static ResourceManager resourceManager;
    public static Timer theTimer;
    //default amounts; can be changed
    public static int DELAY = 50;
    public static int START_ZOMBIE_AMOUNT = 10;
    public static int PLANT_SPAWN_INTERVAL = 2;
    public static int ZOMBIE_SPAWN_INTERVAL = 16;
    public static int START_SUN_POINTS = 900;
    public static int BASIC_ZOMBIE_SPAWN_CHANCE = 70;
    public static int BUCKETHEAD_ZOMBIE_SPAWN_CHANCE = 30;
    public static int SUNFLOWER_SPAWN_CHANCE = 45;
    public static int PEASHOOTER_SPAWN_CHANCE = 25;
    public static int CHERRY_BOMB_SPAWN_CHANCE = 10;
    public static int WALNUT_SPAWN_CHANCE = 20;
    //default amounts; can NOT be changed;
    public static final int SQUARE_SIZE = 100;
    public static final int ROWS = 5;
    public static final int PLANT_COLUMNS = 5;
    public static final int ZOMBIE_COLUMNS = 4;
    public static final int COLUMNS = PLANT_COLUMNS + ZOMBIE_COLUMNS;
    //define introduction
    JLabel welcome1 = new JLabel();
    JLabel welcome2 = new JLabel();
    JLabel welcome3 = new JLabel();
    JLabel welcome4 = new JLabel();
    JLabel welcome5 = new JLabel();
    List<JLabel> welcomes = new ArrayList<>();
    JLabel welcome, label;
    JPanel panel, outsidePanel;
    //define buttons
    JButton toSpawnSelector = new JButton("To Spawn Selector");
    JButton startSimulation = new JButton("Start Simulation");
    JButton pauseSimulation = new JButton("Pause Simulation");
    JButton toSettingsChange = new JButton("To Settings Change");
    //define counters
    public static String[] strings = {"Amount of Basic Zombies", "Amount of Buckethead Zombies", "Amount of Sunflowers", "Amount of Peashooters", "Amount of Cherry Bombs",
            "Amount of Walnuts", "Amount of Peas", "Amount of Sun Points", "Time Elapsed"};//storing counters inside array for smaller loops later
    List<JLabel> labels = new ArrayList<>();
    List<JPanel> panels = new ArrayList<>();
    public Panel() {
        resourceManager = new ResourceManager();//create resource manager for everyone
        theTimer = new Timer(DELAY, this);//create timer for everyone
        //default configuration
        this.setSize(SQUARE_SIZE*COLUMNS,SQUARE_SIZE*(ROWS+1));
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(new Color(0x4f7942));
        //add buttons
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
        //add introduction
        for (int i = 0; i<5; i++) {
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
        //add counters
        outsidePanel = new JPanel();
        outsidePanel.setLayout(new FlowLayout());
        outsidePanel.setBounds(2*SQUARE_SIZE, 0, 7*SQUARE_SIZE, SQUARE_SIZE);
        outsidePanel.setBackground(new Color(0x4f7942));
        for (int i=0; i<9; i++) {
            label = new JLabel();
            panel = new JPanel();
            panel.setPreferredSize(new Dimension((int) ((Panel.SQUARE_SIZE) * 2.1f), Panel.SQUARE_SIZE / 3));
            sum = counterUpdater(i);
            label.setText(strings[i] + ": " +sum);//using counter array
            panel.setBackground(new Color(0x4f7942));
            labels.add(label);
            panel.add(label);
            panels.add(panel);
            outsidePanel.add(panel);
        }
        this.add(outsidePanel);
    }

    public void gameStart() {
        for (JLabel welcome : welcomes) {
            welcome.setVisible(false);//hide the introduction
        }
        timeElapsed = 0;//start counting time from the beginning
        pauseSimulation.setEnabled(true);
        pauseSimulation.setText("Pause Simulation");
        //removing any zombies left from previous simulation
        Zombies.clear();
        Zombies=null;
        Zombies = new ArrayList<>();
        //making sure dead plants don't create sun points
        for (Plant plant : Plants) {
            if (plant.getType() == 0) {
                Sunflower sunflower = (Sunflower) plant; //downcasting to access Sunflower-exclusive function
                sunflower.produceCycle = -9999;
                sunflower.alive=false;
            }
        }
        //removing any plants left from previous simulation
        Plants.clear();
        Plants=null;
        Plants = new ArrayList<>();
        theTimer.start();//starting the timer
        //resetting sun points to 0
        resourceManager.spendSunPoints(resourceManager.getSunPoints());
        resourceManager.addSunPoints(START_SUN_POINTS);
        //resetting spawn cycles
        plantSpawnCycle = 0;
        zombieSpawnCycle = 0;
        while (resourceManager.getSunPoints()>=Math.max(Math.max(Math.max(Sunflower.getCost(),Peashooter.getCost()),CherryBomb.getCost()),Walnut.getCost())) {
            spawnRandomPlant();//spawning plants until there isn't enough sun points left
        }
        for (int i = 0; i<START_ZOMBIE_AMOUNT; i++) {
            spawnRandomZombie();//spawning START_ZOMBIE_AMOUNT amount of zombies
        }
        toSpawnSelector.setEnabled(false);//disable spawn square changing, while simulation is running
        startSimulation.setText("Restart Simulation");
        repaint();
    }
    public int counterUpdater(int i) {
        sum = 0;
        switch (i) {
            case 0:
                //basic zombie counter
                if (!Zombies.isEmpty()) {
                    for (Zombie zombie : Zombies) {
                        if (zombie.getType() == 0) {
                            sum++;
                        }
                    }
                }
                break;
            case 1:
                //buckethead zombie counter
                if (!Zombies.isEmpty()) {
                    for (Zombie zombie : Zombies) {
                        if (zombie.getType() == 1) {
                            sum++;
                        }
                    }
                }
                break;
            case 2:
                //sunflower counter
                if (!Plants.isEmpty()) {
                    for (Plant plant : Plants) {
                        if (plant.getType() == 0) {
                            sum++;
                        }
                    }
                }
                break;
            case 3:
                //peashooter counter
                if (!Plants.isEmpty()) {
                    for (Plant plant : Plants) {
                        if (plant.getType() == 1) {
                            sum++;
                        }
                    }
                }
                break;
            case 4:
                //cherry bomb counter
                if (!Plants.isEmpty()) {
                    for (Plant plant : Plants) {
                        if (plant.getType() == 2) {
                            sum++;
                        }
                    }
                }
                break;
            case 5:
                //walnut counter
                if (!Plants.isEmpty()) {
                    for (Plant plant : Plants) {
                        if (plant.getType() == 3) {
                            sum++;
                        }
                    }
                }
                break;
            case 6:
                //peas (projectiles) counter
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
                //sun points counter
                sum = resourceManager.getSunPoints();
                break;
            case 8:
                //time counter
                if (theTimer.isRunning()) {
                    sum = timeElapsed;
                }
                break;
        }
        return sum;
    }

    public void spawnRandomPlant() {
        //randomize location and type of plant
        x = ThreadLocalRandom.current().nextInt(0,PLANT_COLUMNS*SQUARE_SIZE);
        y = ThreadLocalRandom.current().nextInt(SQUARE_SIZE,ROWS*SQUARE_SIZE);
        chance = ThreadLocalRandom.current().nextDouble(0,SUNFLOWER_SPAWN_CHANCE+PEASHOOTER_SPAWN_CHANCE+CHERRY_BOMB_SPAWN_CHANCE+WALNUT_SPAWN_CHANCE);
        int chosen;
        if (chance<SUNFLOWER_SPAWN_CHANCE) {
            chosen = 0;//sunflower chosen
        } else if (chance<SUNFLOWER_SPAWN_CHANCE+PEASHOOTER_SPAWN_CHANCE) {
            chosen = 1;//peashooter chosen
        } else if (chance<SUNFLOWER_SPAWN_CHANCE+PEASHOOTER_SPAWN_CHANCE+CHERRY_BOMB_SPAWN_CHANCE) {
            chosen = 2;//cherry bomb chosen
        } else {
            chosen = 3;//walnut chosen
        }
        List<int[]> squareType = SpawnSquares.get(chosen);
        if (squareType.isEmpty()) {
            spawnRandomPlant();//if not selected any spawn squares, then try again
        }
        for (int[] square : squareType) {
            if (x >= square[0] * SQUARE_SIZE && x <= (square[0] + 1) * SQUARE_SIZE && y >= square[1] * SQUARE_SIZE && y <= (square[1] + 1) * SQUARE_SIZE) {
                if (chosen==0) {
                    if (!resourceManager.spendSunPoints(Sunflower.getCost())) {//if you can't afford chosen plant, try again
                        spawnRandomPlant();
                    } else {
                        Plants.add(new Sunflower(x, y));
                    }
                } else if (chosen==1) {
                    if (!resourceManager.spendSunPoints(Peashooter.getCost())) {//if you can't afford chosen plant, try again
                        spawnRandomPlant();
                    } else {
                        Plants.add(new Peashooter(x, y));
                    }
                } else if (chosen==2) {
                    if (!resourceManager.spendSunPoints(CherryBomb.getCost())) {//if you can't afford chosen plant, try again
                        spawnRandomPlant();
                    } else {
                        Plants.add(new CherryBomb(x, y));
                    }
                } else {
                    if (!resourceManager.spendSunPoints(Walnut.getCost())) {//if you can't afford chosen plant, try again
                        spawnRandomPlant();
                    } else {
                        Plants.add(new Walnut(x, y));
                    }
                }
            }
        }
    }

    public void spawnRandomZombie() {
        //randomize location (only on last row) and type of zombie
        x = ThreadLocalRandom.current().nextInt((COLUMNS-1)*SQUARE_SIZE, (COLUMNS) * SQUARE_SIZE);
        y = ThreadLocalRandom.current().nextInt(SQUARE_SIZE, ROWS * SQUARE_SIZE);
        chance = ThreadLocalRandom.current().nextDouble(0,BASIC_ZOMBIE_SPAWN_CHANCE+BUCKETHEAD_ZOMBIE_SPAWN_CHANCE);
        if (chance<BASIC_ZOMBIE_SPAWN_CHANCE) {
            Zombies.add(new BasicZombie(x, y));
        } else {
            Zombies.add(new BucketheadZombie(x, y));
        }

    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==toSpawnSelector) {
            new SpawnSelector();//open spawn selector window
        }
        if (e.getSource()==toSettingsChange) {
            new SettingsChange();//open settings change window
        }
        if (e.getSource()==startSimulation) {
            //check if there are any spawn squares chosen, if not then give error
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
            //pausing and unpausing
            if (theTimer.isRunning()) {
                theTimer.stop();
                pauseSimulation.setText("Unpause Simulation");
                toSpawnSelector.setEnabled(true);
            } else {
                theTimer.start();
                pauseSimulation.setText("Pause Simulation");
                toSpawnSelector.setEnabled(false);
            }

        }
        if (theTimer.isRunning()) {
            //every PLANT_SPAWN_INTERVAL, a plant is spawned
            plantSpawnCycle++;
            if (plantSpawnCycle>=PLANT_SPAWN_INTERVAL) {
                spawnRandomPlant();
                plantSpawnCycle=0;
            }
            //every ZOMBIE_SPAWN_INTERVAL, a zombie is spawned
            zombieSpawnCycle++;
            if (zombieSpawnCycle>=ZOMBIE_SPAWN_INTERVAL) {
                spawnRandomZombie();
                zombieSpawnCycle=0;
            }
            //if any zombie touches the left side of the board, then the plants lose
            for(Zombie zombie : Zombies) {
                if (zombie.x<=0) {
                    JOptionPane.showMessageDialog(null, "Plants lost...", "Zombies for the win!!", JOptionPane.ERROR_MESSAGE);
                    theTimer.stop();
                    toSpawnSelector.setEnabled(true);
                    pauseSimulation.setEnabled(false);
                    break;
                }
            }
            //if all zombies are dead, then the zombies lose
            if(Zombies.isEmpty() && theTimer.isRunning()) {
                JOptionPane.showMessageDialog(null, "Zombies lost...", "Plants for the win!!", JOptionPane.INFORMATION_MESSAGE);
                theTimer.stop();
                startSimulation.setText("Restart Simulation");
                toSpawnSelector.setEnabled(true);
                pauseSimulation.setEnabled(false);
            }

            repaint();
            CollisionManager.checkAttacks(Plants,Zombies);//check if any zombies are eating plants
        }
        for (int i = 0; i<11; i++) {
            //update every setting changed in the SettingsChange window
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
        timeElapsed+=theTimer.getDelay();//update time counter
        for (int i=0; i<9; i++) {
            labels.get(i).setText(strings[i] + ": " + counterUpdater(i));//update the rest of the counters and put them on screen
        }
        theTimer.setDelay(DELAY);//change delay (if changed in the SettingsChange window)
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < Plants.size(); i++) {
            Plant plant = Plants.get(i);
            if (plant.getHealth()<=0) {
                if (plant.getType()==2) {//check if any cherrybombs are dead and explode them
                    CherryBomb cherryBomb = (CherryBomb) plant; //downcasting to access CherryBomb-exclusive function
                    cherryBomb.explode();
                } else if (plant.getType() == 0) {//make sure sunflowers don't produce sun points after death
                    Sunflower sunflower = (Sunflower) plant; //downcasting to access Sunflower-exclusive function
                    sunflower.produceCycle = -9999;
                    sunflower.alive=false;
                }
                Plants.remove(i);//remove plants, that are dead
                i--;
            }
            plant.paintComponent(g);//paintComponent the ones, that are alive
        }
        for (int i = 0; i < Zombies.size(); i++) {
            Zombie zombie = Zombies.get(i);
            if (zombie.getHealth()<=0) {
                Zombies.remove(i);//remove zombies, that are dead
                i--;
            }
            zombie.paintComponent(g);//paintComponent the ones, that are alive
        }
    }
}
