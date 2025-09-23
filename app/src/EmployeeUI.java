import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.Employee;
import java.util.concurrent.ThreadLocalRandom;

public class EmployeeUI {
    private TableView<Employee> table;
    private ObservableList<Employee> employeeData;
    private Scene scene;

    //Map all the departament with an unique cod
    private int getDepartmentCode(String departament)
    {
        return switch(departament){
            case "Manager"->1;
            case "HR"->2;
            case "IT"->3;
            case "Finance"->4;
            case "Marketing"->5;
            case "Purchasing"->6;
            case "Logistics"->7;
            default ->9;

        };
    }
    //=================Unique ID Alghoritm===================
    private String generateUniqueId(int departamentCode) {
        String id;
        boolean exists;
        do {
            int randomPart = ThreadLocalRandom.current().nextInt(1000, 10000);
            id=departamentCode + String.valueOf(randomPart);
            String finalId = id;
            exists = employeeData.stream().anyMatch(e -> e.getId() == finalId);
        } while (exists);
        return id;
    }

    public EmployeeUI (App app){
        employeeData = FXCollections.observableArrayList();

        table = new TableView<>();
        table.setItems(employeeData);

        TableColumn<Employee, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));

        TableColumn<Employee, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn<Employee,String> deptCol=new TableColumn<>("Department");
        deptCol.setCellValueFactory(new PropertyValueFactory<>("Department"));

        table.getColumns().addAll(idCol, nameCol,deptCol);

        TextField nameInput = new TextField();
        nameInput.setPromptText("Nume angajat");

        //===============Combo Box==============
        ComboBox<String> employeeType = new ComboBox<>();
        employeeType.getItems().addAll("Manager","HR","IT","Finance","Marketing","Purchasing","Logistics");
        employeeType.setPromptText("Choose The Department");


        //============Add button================

        Button addBtn = new Button("Add");
        addBtn.setOnAction(e -> {
            String name = nameInput.getText().trim();
            String dept=employeeType.getValue();

            if (!name.isEmpty() && dept != null) {
                int code=getDepartmentCode(dept);
                String newId = generateUniqueId(code);
                employeeData.add(new Employee(newId, name, dept));

                nameInput.clear();
                //Reset the combobox
                employeeType.getSelectionModel().clearSelection();
                employeeType.setButtonCell(new ListCell<>()
                {
                    @Override
                            protected void updateItem(String item,boolean empty){
                        super.updateItem(item,empty);
                        setText(empty ?"Choose The Department": item);
                }
                });
            }
        });
        //===============Delete button================

        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(e -> {
            Employee selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                employeeData.remove(selected);
            }
        });
        //===============Back Button================
        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> app.showMainMenuFromChild());

        HBox controls = new HBox(10, nameInput,addBtn,employeeType,deleteBtn,backBtn);

        BorderPane root = new BorderPane();
        root.setCenter(table);
        root.setBottom(controls);

        scene=new Scene(root,600,400);

    }
    public Scene getScene(){
        return scene;
    }
}