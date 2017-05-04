import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.animation.PathTransition;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

/**
 * GamePane.java
 * @author Nathan Smith
 * @version 1.0
 *
 * Pupose - Contains the Nodes used in the game and
 * is responsible for game actions: shot animation, win message, reset
 */
public class GamePane extends Pane {

    private Button shoot1 = new Button("Draw (a)"); //player 1 shoot button
    private Button shoot2 = new Button("Draw (l)"); //player 2 shoot button
    private Button playAgain = new Button("Play Again?");
    private ImageView p1 = new ImageView("Player 1.png"); //player 1
    private ImageView p2 = new ImageView("Player 2.png"); //player 2
    private Circle shot = new Circle(0, 20, 2); //bullet that will act as the Node for PathTransition
    private Line shotPath1 = new Line(10, 35, 285, 35); //used as path
    private Line shotPath2 = new Line(280, 35, 10, 35); //used as path
    private PathTransition pt = new PathTransition(new Duration(500), shotPath1, shot); //moves the shot across the GamePane
    private GameTimer gt = new GameTimer(); //is supposed to countdown to the game start, but when has "supposed to" ever meant anything?
    private Label winMessage = new Label();
    private double reactionTime;
    private boolean roundEnd = false;

    public GamePane() {
        
        this.getChildren().addAll(p1, p2, shoot1, shoot2, gt.getLabel()); //add Nodes to screen
        //placing Nodes
        shoot1.setLayoutX(0);
        shoot1.setLayoutY(75);
        shoot2.setLayoutX(254);
        shoot2.setLayoutY(75);
        playAgain.setLayoutY(75);
        playAgain.layoutXProperty().bind((this.widthProperty().subtract(playAgain.widthProperty())).divide(2));
        p1.setLayoutX(0);
        p1.setLayoutY(20);
        p2.setLayoutX(280);
        p2.setLayoutY(20);
        gt.getLabel().layoutXProperty().bind((this.widthProperty().subtract(gt.getLabel().widthProperty())).divide(2));
        gt.getLabel().setLayoutY(20);
        winMessage.layoutXProperty().bind((this.widthProperty().subtract(winMessage.widthProperty())).divide(2));
        winMessage.setLayoutY(20);
        winMessage.setTextAlignment(TextAlignment.CENTER);

        //actions for shoot button presses
        shoot1.setOnAction(e -> { 
            if(gt.isDone() && !roundEnd) {
                reactionTime = (double) ((System.nanoTime() - gt.getTimerEnd()) / Math.pow(10, 9));
                this.getChildren().remove(gt.getLabel());
                this.getChildren().add(shot);
                pt.setPath(shotPath1);
                pt.play();
                gameOver(shoot1);}});
        shoot2.setOnAction(e -> {
            if(gt.isDone() && !roundEnd) {
                reactionTime = (double) ((System.nanoTime() - gt.getTimerEnd()) / Math.pow(10, 9));
                this.getChildren().remove(gt.getLabel());
                this.getChildren().add(shot);
                pt.setPath(shotPath2);
                pt.play();
                gameOver(shoot2);}});
        playAgain.setOnAction(e -> reset());

        this.setOnKeyPressed(e -> {
            switch(e.getCode()) {
                case A: shoot1.fire(); break;
                case L: shoot2.fire(); break;
            }
        });

    }

    public void countdown() {

        gt.start();

    }
 
    public void gameOver(Button button) {

        roundEnd = true;
        this.getChildren().add(playAgain);

        if(button == shoot1) {
            
            winMessage.setText("Player 1 wins with a reaction time of\n" + String.valueOf((int) (reactionTime * 1000) / 1000.0) + " seconds");
            this.getChildren().add(winMessage);

        } else {

            winMessage.setText("Player 2 wins with a reaction time of\n" + String.valueOf((int) (reactionTime * 1000) / 1000.0) + " seconds");
            this.getChildren().add(winMessage);

        } 

    }

    public void reset() {

        roundEnd = false;
        this.getChildren().clear();
        gt.getLabel().setText("3");
        this.getChildren().addAll(p1, p2, shoot1, shoot2, gt.getLabel());
        gt.resetLastUpdate();
        gt.start();

    }

}