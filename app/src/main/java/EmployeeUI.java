import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.Employee;
import service.EmployeeManager;

public class EmployeeUI {
    private App app;
    private EmployeeManager employeeManager;

    private TableView<Employee> table;
    private ObservableList<Employee> employeeData;
    private Scene scene;

    public EmployeeUI(App app, EmployeeManager employeeManager) {
        this.app = app;
        this.employeeManager = employeeManager;

        // Încărcăm din DB
        employeeData = FXCollections.observableArrayList(employeeManager.getAllEmployees());

        table = new TableView<>();
        table.setItems(employeeData);

        TableColumn<Employee, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Employee, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Employee, String> deptCol = new TableColumn<>("Department");
        deptCol.setCellValueFactory(new PropertyValueFactory<>("department"));

        table.getColumns().addAll(idCol, nameCol, deptCol);

        TextField nameInput = new TextField();
        nameInput.setPromptText("Nume angajat");

        ComboBox<String> employeeType = new ComboBox<>();
        employeeType.getItems().addAll("Manager", "HR", "IT", "Finance", "Marketing", "Purchasing", "Logistics");
        employeeType.setPromptText("Choose The Department");

        // Păstrăm "Choose The Department" după clear
        employeeType.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? "Choose The Department" : item);
            }
        });
        employeeType.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
            }
        });

        // ===== Add button =====
        Button addBtn = new Button("Add");
        addBtn.setOnAction(e -> {
            String name = nameInput.getText().trim();
            String dept = employeeType.getValue();

            if (!name.isEmpty() && dept != null) {
                Employee newEmployee = new Employee(null, name, dept);
                employeeManager.addEmployee(newEmployee);

                // Reîncărcăm din DB (acum are ID real)
                employeeData.setAll(employeeManager.getAllEmployees());

                nameInput.clear();
                employeeType.getSelectionModel().clearSelection();
            }
        });

        // ===== Delete button =====
        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(e -> {
            Employee selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                boolean deleted = employeeManager.deleteEmployee(selected.getId());
                if (deleted) {
                    employeeData.setAll(employeeManager.getAllEmployees());
                } else {
                    System.out.println("⚠️ Nothing deleted for ID: " + selected.getId());
                }
            }
        });

        // ===== Back button =====
        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> app.showMainMenuFromChild());

        HBox controls = new HBox(10, nameInput, addBtn, employeeType, deleteBtn, backBtn);

        BorderPane root = new BorderPane();
        root.setCenter(table);
        root.setBottom(controls);

        scene = new Scene(root, 600, 400);
    }

    public Scene getScene() {
        return scene;
    }
}
