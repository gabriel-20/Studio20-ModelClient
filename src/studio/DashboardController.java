/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studio;

import studio.helper.Model;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.MonthDay;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import javax.imageio.ImageIO;

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

    }

}
