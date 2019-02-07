/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studio;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import studio.helper.Model;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.time.LocalDate;
import java.time.MonthDay;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import studio.helper.Sales;


/**
 * FXML Controller class
 *
 * @author dev
 */
public class DashboardController implements Initializable {

    @FXML
    private Label modelName;
    
    @FXML
    private Label onlineHours;
    
    @FXML
    private Label totalamount;
    
    @FXML
    private Label totalhistory;

    @FXML
    private ImageView profilePicture;
    
     @FXML
    private AnchorPane calendar1,calendar2;

    @FXML
    private void handleCloseDash(MouseEvent event) {
        System.exit(0);
    }
    
    @FXML
    private TableView<Sales> table;
    
    
//     @FXML 
//     private TableColumn colOnlineH ;
     
     
      private final ObservableList<Sales> data = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        if ( MonthDay.from(item).equals(MonthDay.of(2, 15)) || MonthDay.from(item).equals(MonthDay.of(2, 16))) {
                            setTooltip(new Tooltip("Reservations!"));
                            setStyle("-fx-background-color: #ff4444;");
                        } else {
                            setTooltip(null);
                            setStyle(null);
                        }
                    }
                };
            }
        };
        
        Model model = Model.getInstance();

        modelName.setText(model.modelname);
        onlineHours.setText(model.onlineHours);
        totalamount.setText(model.totalamount+" $");
        totalhistory.setText(model.totalhistory+" $");
        
        JsonArray jsonarray = model.sales;
        for (int i = 0; i < jsonarray.size(); i++) {
        JsonObject jsonobject = jsonarray.get(i).getAsJsonObject();
        
        String jData = jsonobject.get("date").getAsString();
        String jSum = jsonobject.get("sum").getAsString();
        String jHours = jsonobject.get("hours").getAsString();
        data.add(new Sales(Integer.toString(i + 1), jData, jSum, jHours));
}

        
        //data.add(new Sales("5", "10-02-2019", "1000$", "23:02:10"));
        

        String imageUrl = "http:" + model.profilePicture;

        Image image = new Image(imageUrl);
        profilePicture.setImage(image);
        
         StackPane root1 = new StackPane();
         StackPane root2 = new StackPane();
    
        DatePicker picker = new DatePicker();
        picker.setDayCellFactory(dayCellFactory);

        final DatePickerSkin skin1 = new DatePickerSkin(picker); 
        final DatePickerSkin skin2 = new DatePickerSkin(new DatePicker()); 

        root1.getChildren().add(skin1.getPopupContent()); 
        root2.getChildren().add(skin2.getPopupContent()); 

        calendar1.getChildren().add(root1); 
        calendar2.getChildren().add(root2); 
        
        TableColumn indexFieldCol = new TableColumn("#");
        indexFieldCol.setMinWidth(100);
        indexFieldCol.setCellValueFactory(new PropertyValueFactory<>("indexField"));
        
        TableColumn firstDayCol = new TableColumn("Day");
        firstDayCol.setMinWidth(100);
        firstDayCol.setCellValueFactory(new PropertyValueFactory<>("firstDay"));
        
        TableColumn lastSaleCol = new TableColumn("Sale");
        lastSaleCol.setMinWidth(100);
        lastSaleCol.setCellValueFactory(new PropertyValueFactory<>("lastSale"));
        
        TableColumn lastHourCol = new TableColumn("Hours");
        lastHourCol.setMinWidth(100);
        lastHourCol.setCellValueFactory(new PropertyValueFactory<>("lastHour"));

        table.setItems(data);
        table.getColumns().addAll(indexFieldCol, firstDayCol, lastSaleCol, lastHourCol);

    }
    
    



}