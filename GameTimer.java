import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import java.util.concurrent.ThreadLocalRandom;

/**
 * GameTimer.java 
 * @author Nathan Smith
 * @version 1.0
 * 
 * Purpose - Holds a label that counts down to the game's start
 * using the handle method
 */
public class GameTimer extends AnimationTimer {

    private Label countdown;
    private long lastUpdate;
    private long timerEnd;

    public GameTimer() {

        countdown = new Label("3");
        countdown.setTextAlignment(TextAlignment.CENTER);
        lastUpdate = System.nanoTime();

    }

    public void handle(long now) {

        long delay = ThreadLocalRandom.current().nextLong(1, 5);

        if((now - lastUpdate) / Math.pow(10, 9) <= delay) 
            return;

        if(countdown.getText().equals("1")) {

            countdown.setText("Shoot!");
            stop();
            timerEnd = System.nanoTime();

        }
        else {

            countdown.setText(String.valueOf(Integer.parseInt(countdown.getText()) - 1));
            lastUpdate = now;

        }

    }

    public Label getLabel() {

        return this.countdown;

    }

    public boolean isDone() {

        return countdown.getText().equals("Shoot!");

    }

    public long getTimerEnd() {

        return timerEnd;

    }

    public void resetLastUpdate() {

        lastUpdate = System.nanoTime();

    }

}