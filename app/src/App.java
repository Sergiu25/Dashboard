import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Employee;
import service.BenefitManager;
import service.EmployeeManager;

import java.util.concurrent.ThreadLocalRandom;

public class App extends Application{

    private final EmployeeManager employeeManager = new EmployeeManager();
    private final BenefitManager benefitManager = new BenefitManager();

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Dashboard");

        //Load the information when the app starts
        employeeManager.loadFromFile("employees.json");
        benefitManager.loadFromFile("benefits.json");

        showMainMenu();

        //Save the information when the app close
        primaryStage.setOnCloseRequest(event -> {
            employeeManager.saveToFile("employees.json");
            benefitManager.saveToFile("benefits.json");
        });
    }
    private void showMainMenu(){
        Button employeeBtn= new Button("Manage Employees");
        employeeBtn.setOnAction(e -> showEmployeeScene());

        Button benefitBtn= new Button("Manage Benefits");
        benefitBtn.setOnAction(e -> {
            //Placeholder temporary
            Button backBtn= new Button("Back");
            backBtn.setOnAction(ev->showMainMenu());
            VBox box= new VBox(20,new javafx.scene.control.Label("Benefits in work..."),backBtn);
            primaryStage.setScene(new Scene(box,400,300));
        });

        Button exitBtn= new Button("Exit");
        exitBtn.setOnAction(e ->primaryStage.close());

        VBox menu=new VBox(10,employeeBtn,benefitBtn,exitBtn);
        menu.setStyle("-fx-alignment: CENTER; -fx-padding: 50;");

        primaryStage.setScene(new Scene(menu,400,300));
        primaryStage.show();
    }
    private void showEmployeeScene(){
        EmployeeUI employeeUI = new EmployeeUI(this, employeeManager);
        primaryStage.setScene(employeeUI.getScene());
    }

    public void showMainMenuFromChild(){
        showMainMenu();
    }

    public static void main(String[] args) {
        launch(args); //Start the app
    }
}
