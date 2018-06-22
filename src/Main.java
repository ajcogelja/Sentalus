
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.geometry.*;
import javafx.scene.canvas.Canvas;

public class Main extends Application{
  //  public static void main(String[] args) {
  //      launch(args);
  //  }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(800, 800);
        Dimension2D dim = new Dimension2D(500, 500);
        Camera cam = new ParallelCamera();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        primaryStage.setTitle("Test");
        primaryStage.setResizable(false);
        Button btn = new Button();
        //Rectangle2D rect = new Rectangle2D(100, 100, 100, 100);
        btn.setPrefSize(300, 300);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
        gc.setFill(Color.MAGENTA);
        gc.fillRect(0, 0, 300, 300);

        StackPane root = new StackPane();
        Scene scene = new Scene(root, 500, 500);
        scene.setCamera(cam);
        root.getChildren().add(canvas);
        root.getChildren().add(btn);
        primaryStage.setScene(scene);
        primaryStage.show();

        

    }
}
