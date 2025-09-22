import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application{

    @Override
    public void start(Stage primaryStage){
        //Create the button
        Button btn = new Button("Click Me");

        //Add action on click
        btn.setOnAction(event->{System.out.println("The button was pressed!");});

        //We position the button to a simple layout

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        //Create the scene
        Scene scene = new Scene(root,300,200);
        primaryStage.setTitle("Dashbord");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args); //Start the app
    }
}
