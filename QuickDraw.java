import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * QuickDraw.java
 * @author Nathan Smith
 * @version 1.0
 *
 * Purpose - Launches the application
 */
public class QuickDraw extends Application {

    @Override //start method in Application class
    public void start(Stage primaryStage) {

        GamePane screen = new GamePane();
        Scene scene = new Scene(screen, 300, 100);

        primaryStage.setScene(scene);
        primaryStage.setTitle("QuickDraw");
        primaryStage.setResizable(false);
        primaryStage.show();

        screen.countdown();

    }

}
