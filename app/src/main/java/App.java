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
        //Load the information when the app starts

        benefitManager.loadFromFile("benefits.json");

        showWelcomeScreen();

        //Save the information when the app close
        primaryStage.setOnCloseRequest(event -> {

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

        // ================= Admin Button =================
        FontIcon adminIcon = new FontIcon(FontAwesomeSolid.LOCK);
        adminIcon.setIconSize(40);
        adminIcon.setIconColor(javafx.scene.paint.Color.valueOf("#66fcf1"));

        StackPane adminIconWrapper = new StackPane(adminIcon);
        adminIconWrapper.getStyleClass().add("icon-highlight");

        Button adminBtn = new Button("Admin", adminIconWrapper);
        adminBtn.getStyleClass().addAll("big-button", "admin-button");
        adminBtn.setContentDisplay(ContentDisplay.TOP);
        adminBtn.setMaxWidth(Double.MAX_VALUE);
        adminBtn.setOnAction(e -> showAdminLogin());

        // ================= Client Button =================
        FontIcon clientIcon = new FontIcon(FontAwesomeSolid.USER);
        clientIcon.setIconSize(40);
        clientIcon.setIconColor(javafx.scene.paint.Color.valueOf("#66fcf1"));

        StackPane clientIconWrapper = new StackPane(clientIcon);
        clientIconWrapper.getStyleClass().add("icon-highlight");

        Button clientBtn = new Button("Client", clientIconWrapper);
        clientBtn.getStyleClass().addAll("big-button", "client-button");
        clientBtn.setContentDisplay(ContentDisplay.TOP);
        clientBtn.setMaxWidth(Double.MAX_VALUE);
        clientBtn.setOnAction(e -> showClientLogin());

        // ================= Exit Button =================
        Button exitBtn = new Button("Exit");
        exitBtn.getStyleClass().add("exit-button");
        exitBtn.setOnAction(e -> primaryStage.close());

        // ================= Layout =================
        HBox buttonBox = new HBox(40, clientBtn, adminBtn);
        buttonBox.setStyle("-fx-alignment:center; -fx-padding:20;");
        HBox.setHgrow(clientBtn, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(adminBtn, javafx.scene.layout.Priority.ALWAYS);

        VBox centerBox = new VBox(40, title, buttonBox);
        centerBox.setStyle("-fx-alignment:center;");

        BorderPane root = new BorderPane();
        root.setCenter(centerBox);
        root.setBottom(exitBtn);
        BorderPane.setAlignment(exitBtn, javafx.geometry.Pos.CENTER);
        BorderPane.setMargin(exitBtn, new javafx.geometry.Insets(20));

        applyAnimatedBackground(root);

        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        //  Responsive scaling
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double w = newVal.doubleValue();


            double titleSize = Math.max(18, Math.min(w / 15, 48));
            title.setStyle("-fx-font-size: " + titleSize + "px; -fx-text-fill: #66fcf1; -fx-font-weight: bold;");


            double buttonSize = Math.max(12, Math.min(w / 40, 22));
            adminBtn.setStyle("-fx-font-size: " + buttonSize + "px;");
            clientBtn.setStyle("-fx-font-size: " + buttonSize + "px;");


            double exitSize = Math.max(10, Math.min(w / 60, 16));
            exitBtn.setStyle("-fx-font-size: " + exitSize + "px;");

            // the icons gets bigger proportional
            adminIcon.setIconSize((int)(w / 20));
            clientIcon.setIconSize((int)(w / 25));
        });

       //Responsive only for title
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            double h = newVal.doubleValue();
            double titleSize = Math.max(18, Math.min(h / 10, 48));
            title.setStyle("-fx-font-size: " + titleSize + "px; -fx-text-fill: #66fcf1; -fx-font-weight: bold;");
        });

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
