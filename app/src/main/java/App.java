import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
import service.BenefitManager;
import service.DBManager;
import service.EmployeeManager;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import javafx.animation.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
public class App extends Application{

    private final EmployeeManager employeeManager = new EmployeeManager();
    private final BenefitManager benefitManager = new BenefitManager();

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Dashboard");

       DBManager.initDatabase();
       DBManager.testInsertAndSelect();
        //Load the information when the app starts
        employeeManager.loadFromFile("employees.json");
        benefitManager.loadFromFile("benefits.json");

        showWelcomeScreen();

        //Save the information when the app close
        primaryStage.setOnCloseRequest(event -> {
            employeeManager.saveToFile("employees.json");
            benefitManager.saveToFile("benefits.json");
        });
    }
    public static void applyAnimatedBackground(Region root){
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,new KeyValue(root.styleProperty(),
                        "-fx-background-color:linear-gradient(to bottom,#0b0c10,#1f2833);")),
                new KeyFrame(Duration.seconds(5),
                        new KeyValue(root.styleProperty(),
                                "-fx-background-color:linear-gradient(to bottom,#1f2833,#0b0c10);"))
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    //==================Welcome Screen=========
    private void showWelcomeScreen() {
        Label title = new Label("Dashboard");
        title.getStyleClass().add("welcome-title");
        //Admin Button
        FontIcon adminIcon=new FontIcon(FontAwesomeSolid.LOCK);
        adminIcon.setIconSize(40);
        adminIcon.setIconColor(javafx.scene.paint.Color.valueOf("#66fcf1"));

        Button adminBtn=new Button("Admin",adminIcon);
        adminBtn.getStyleClass().addAll("big-button","admin-button");
        adminBtn.setContentDisplay(ContentDisplay.TOP);
        adminBtn.setOnAction(e -> showAdminLogin());
        //Client Button
        FontIcon clientIcon=new FontIcon(FontAwesomeSolid.USER);
        clientIcon.setIconSize(24);
        clientIcon.setIconColor(javafx.scene.paint.Color.valueOf("#66fcf1"));

        Button clientBtn=new Button("Client",clientIcon);
        clientBtn.getStyleClass().addAll("big-button","client-button");
        clientBtn.setContentDisplay(ContentDisplay.TOP);
        clientBtn.setOnAction(e -> showClientLogin());

        HBox hbox=new HBox(80,clientBtn,adminBtn);
        hbox.setStyle("-fx-alignment:center; -fx-padding:50;");

        HBox titleBox=new HBox(title);
        titleBox.setStyle("-fx-alignment:center;");

        VBox root=new VBox(50,title,hbox);
        root.setStyle("-fx-alignment:center; -fx-padding:60;");

        title.setMaxWidth(Double.MAX_VALUE);
        title.getStyleClass().add("welcome-title");

        applyAnimatedBackground(root);

        Scene scene=new Scene(root,600,400);

        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
//================Admin login==============
    private void showAdminLogin() {
        PasswordField passwordField=new PasswordField();
        passwordField.setPromptText("Enter Admin Password");

        Button loginBtn=new Button("Login");
        Label errorLabel=new Label();

        loginBtn.setOnAction(e -> {
            String password=passwordField.getText();
            if("admin1".equals(password)){
                showAdminMenu();
            }else {
                errorLabel.setText("Incorrect Admin Password");
            }
        });
        Button backBtn=new Button("Back");
        backBtn.setOnAction(e -> showWelcomeScreen());

        VBox vbox=new VBox(20,passwordField,loginBtn,errorLabel,backBtn);
        vbox.setStyle("-fx-alignment:center; -fx-padding:20;");
        applyAnimatedBackground(vbox);
        primaryStage.setScene(new Scene(vbox,600,400));
    }
//====================Client Login===============
    private void showClientLogin() {
        TextField usernameField=new TextField();
        usernameField.setPromptText("Enter Username or Email");

        PasswordField passwordField=new PasswordField();
        passwordField.setPromptText("Enter Password");

        Button loginBtn=new Button("Login");
        Label errorLabel=new Label();

        loginBtn.setOnAction(e -> {
            String username=usernameField.getText();
            String password=passwordField.getText();
            if("client".equalsIgnoreCase(username)&&"1234".equals(password)){
                showClientMenu();
            }else{
                errorLabel.setText("Incorrect Username or Password");
            }
        });
        Button backBtn=new Button("Back");
        backBtn.setOnAction(e -> showWelcomeScreen());

        VBox vbox=new VBox(10,usernameField,passwordField,loginBtn,errorLabel,backBtn);
        vbox.setStyle("-fx-alignment:center; -fx-padding:20;");
        applyAnimatedBackground(vbox);
        primaryStage.setScene(new Scene(vbox,600,400));
    }
//=================Admin Menu==================
private void showAdminMenu(){
    Button employeeBtn= new Button("Manage Employees");
    employeeBtn.setOnAction(e -> showEmployeeScene());

    Button benefitBtn= new Button("Manage Benefits");
    benefitBtn.setOnAction(e -> showBenefitScene());

    Button logoutBtn=new Button("Logout");
    logoutBtn.setOnAction(e -> showWelcomeScreen());

    VBox menu=new VBox(10,employeeBtn,benefitBtn,logoutBtn);
    menu.setStyle("-fx-alignment:center; -fx-padding:50;");
    applyAnimatedBackground(menu);
    primaryStage.setScene(new Scene(menu,600,400));
    primaryStage.show();

}

//==============Client Menu=================

    private void showClientMenu(){
        Label label =new Label("Welcome client!");

        Button logoutBtn=new Button("Logout");
        logoutBtn.setOnAction(e -> showWelcomeScreen());

        VBox vbox=new VBox(20,label,logoutBtn);
        vbox.setStyle("-fx-alignment:center; -fx-padding:50;");
        applyAnimatedBackground(vbox);
        primaryStage.setScene(new Scene(vbox,400,300));
    }

    //================Show Employee Scene=============
    private void showEmployeeScene(){
        EmployeeUI employeeUI = new EmployeeUI(this, employeeManager);
        primaryStage.setScene(employeeUI.getScene());
    }

    //==============Show Benefit Scene=================
    private void showBenefitScene(){
        BenefitUI benefitUI = new BenefitUI(this, benefitManager);
        primaryStage.setScene(benefitUI.getScene());
    }
//======================UTIL=========================
    public void showMainMenuFromChild(){
        showAdminMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
