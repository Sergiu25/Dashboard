import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.Benefit;
import service.BenefitManager;
import model.BenefitType;

import java.util.concurrent.ThreadLocalRandom;

public class BenefitUI {
    private  App app;
    private  BenefitManager benefitManager;


    private TableView<Benefit> table;
    private ObservableList<Benefit> benefitData;
    private Scene scene;

    //===========Unique id generator=============
    private String generateUniqueBenefitId() {
        String id;
        boolean exists;
        do {
            int randomPart = ThreadLocalRandom.current().nextInt(10000, 100000); // 5 cifre între 10000 și 99999
            id = String.valueOf(randomPart);
            String finalId = id;
            exists = benefitData.stream()
                    .anyMatch(b -> String.valueOf(b.getId()).equals(finalId));
        } while (exists);
        return id;
    }

    public BenefitUI(App app, BenefitManager benefitManager) {
        this.app = app;
        this.benefitManager = benefitManager;

        //Load the benefits already in json
        benefitData=FXCollections.observableArrayList();
        if(benefitManager.getAllBenefits()!=null){
            benefitData.addAll(benefitManager.getAllBenefits());
        }

        table = new TableView<>();
        table.setItems(benefitData);

        TableColumn<Benefit,Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Benefit,BenefitType> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Benefit,String> costCol = new TableColumn<>("Cost");
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));

        table.getColumns().addAll(idCol,typeCol,costCol);
        //===============Combobox for types=========
        ComboBox<BenefitType> benefitTypeBox = new ComboBox<>();
        benefitTypeBox.getItems().addAll(BenefitType.values());
        benefitTypeBox.setPromptText("Choose benefit type");

        // field for cost
        TextField costInput = new TextField();
        costInput.setPromptText("Enter the benefit cost");

        //===================Add btn====================
       Button addBtn=new Button("Add");
       addBtn.setOnAction(e -> {
           BenefitType type=benefitTypeBox.getValue();
           String costText=costInput.getText().trim();

           if(type!=null && !costText.isEmpty()){
               try{
                   double cost=Double.parseDouble(costText);
                   String newId=generateUniqueBenefitId();
                   Benefit newBenefit=new Benefit(Integer.parseInt(newId),type,cost);

                   benefitData.add(newBenefit);
                   benefitManager.addBenefit(newBenefit);

                   //save in json
                   benefitManager.saveToFile("benefits.json");

                   costInput.clear();
               }catch(NumberFormatException ex){
                   Alert alert = new Alert(Alert.AlertType.ERROR,"Costul trebuie sa fie un numar!");
                   alert.showAndWait();
               }
           }
       });

       //==================delete button=============
        Button deleteBtn=new Button("Delete");
        deleteBtn.setOnAction(e->{
            Benefit selected=table.getSelectionModel().getSelectedItem();
            if(selected!=null){
                benefitData.remove(selected);
                benefitManager.deleteBenefit(selected.getId());
                benefitManager.saveToFile("benefits.json");
            }
        });

        //=============Back button=========
        Button backBtn=new Button("Back");
        backBtn.setOnAction(e->app.showMainMenuFromChild());

        HBox controls = new HBox(10, benefitTypeBox,costInput,addBtn,deleteBtn,backBtn);
        BorderPane root = new BorderPane();
        root.setCenter(table);
        root.setBottom(controls);
        scene = new Scene(root,600,400);
    }
    public Scene getScene(){
        return scene;
    }
}
