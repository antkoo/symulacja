import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameTimer {
    private Timer timer;
    private static final int DELAY = 100;
    private List<ActionListener> listeners = new ArrayList<>();

    public GameTimer() {
        // Initialize the Swing Timer
        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Notify all registered listeners
                for (ActionListener listener : listeners) {
                    listener.actionPerformed(e);
                }
            }
        });
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }
}
