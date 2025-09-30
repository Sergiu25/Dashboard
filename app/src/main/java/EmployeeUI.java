import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.Department;
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

        employeeData = FXCollections.observableArrayList(employeeManager.getAllEmployees());

        table = new TableView<>();
        table.setItems(employeeData);

        TableColumn<Employee, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Employee, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Employee, Department> deptCol = new TableColumn<>("Department");
        deptCol.setCellValueFactory(new PropertyValueFactory<>("department"));

        table.getColumns().addAll(idCol, nameCol, deptCol);

        TextField nameInput = new TextField();
        nameInput.setPromptText("Nume angajat");

        ComboBox<Department> employeeType = new ComboBox<>();
        employeeType.getItems().addAll(Department.values());
        employeeType.setPromptText("Choose The Department");

        employeeType.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? "Choose The Department" : item.toString());
            }
        });

        employeeType.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });

        // ===== Add button =====
        Button addBtn = new Button("Add");
        addBtn.setOnAction(e -> {
            String name = nameInput.getText().trim();
            Department dept = employeeType.getValue();

            if (!name.isEmpty() && dept != null) {
                Employee newEmployee = new Employee(null, name, dept);
                employeeManager.addEmployee(newEmployee);
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
                }
            }
        });

        // ===== Back button =====
        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> app.showMainMenuFromChild());

        HBox controls = new HBox(10, nameInput, employeeType, addBtn, deleteBtn, backBtn);

        BorderPane root = new BorderPane();
        root.setCenter(table);
        root.setBottom(controls);

        scene = new Scene(root, 600, 400);
    }

    public Scene getScene() {
        return scene;
    }
}
