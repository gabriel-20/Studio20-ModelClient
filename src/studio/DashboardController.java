/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studio;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import studio.helper.Model;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
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
import org.controlsfx.control.Notifications;
import static studio.helper.Global.DOWNLOAD_JCAM;
import static studio.helper.Global.DOWNLOAD_SPLITER;
import static studio.helper.Global.DOWNLOAD_TEAMVIEWER;
import static studio.helper.Global.PATH_JASMIN;
import static studio.helper.Global.WEBSOCKET;

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
    private Button btnlink;

    @FXML
    private Label totalhistory;

    @FXML
    private Hyperlink linkTeamviewer, linkSpliter, linkJcam;

    @FXML
    private ImageView profilePicture;

    @FXML
    private AnchorPane calendar1, calendar2;

    private WebsocketClientEndpoint clientEndPoint;

    private String str = "Hello World!";

    @FXML
    private void testBtn(MouseEvent event) {

        Notifications.create()
                .title("Studio20")
                .text("Hello ..")
                .showWarning();
    }

    @FXML
    private void link1(ActionEvent event) {

        try {
            Desktop.getDesktop().browse(new URL(DOWNLOAD_JCAM).toURI());
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void link2(ActionEvent event) {

        try {
            Desktop.getDesktop().browse(new URL(DOWNLOAD_SPLITER).toURI());
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void link3(ActionEvent event) {

        try {
            Desktop.getDesktop().browse(new URL(DOWNLOAD_TEAMVIEWER).toURI());
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void execApp(MouseEvent event) throws IOException {

        Process process = new ProcessBuilder(PATH_JASMIN).start();

    }

    @FXML
    private void handleCloseDash(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private TableView<Sales> table;

    private final ObservableList<Sales> data = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Model model = Model.getInstance();
        
        final String[] days = model.res_this_days.split("\\,");
        int[] daysi = new int[days.length]; 
        for (int i = 0; i < days.length; i++){
        daysi[i] = Integer.parseInt(days[i]); 
        }


        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        int year = item.getYear();
                        int month = item.getMonthValue();
                        int day = item.getDayOfMonth();
                        boolean contains = IntStream.of(daysi).anyMatch(x -> x == day);
                        
                        System.out.println("month " + month + " -> " + model.res_this_month_nr);
                        System.out.println("year " + year + " -> " + model.res_this_year);
                        System.out.println("day " + day + " -> " + "contains " + contains);
                        
                        if ( (month == model.res_this_month_nr) && (year == model.res_this_year) && contains) {
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
        
        final Callback<DatePicker, DateCell> dayCellFactory2 = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        int year = item.getYear();
                        int month = item.getMonthValue();
                        int day = item.getDayOfMonth();
                        boolean contains = IntStream.of(daysi).anyMatch(x -> x == day);
                        
//                        System.out.println("month " + month + " -> " + model.res_this_month_nr);
//                        System.out.println("year " + year + " -> " + model.res_this_year);
//                        System.out.println("day " + day + " -> " + "contains " + contains);
                        
                        if ( (month == model.res_this_month_nr+1) && (year == model.res_this_year) && contains) {
                            setTooltip(new Tooltip("Reservations!"));
                            setStyle("-fx-background-color: #ff4444;");
                        } else {
                            setTooltip(null);
                            setStyle("-fx-background-color: #ffffff;");
                        }
                        
                    }
                };
            }
        };

        modelName.setText(model.modelname);
        onlineHours.setText(model.onlineHours);
        totalamount.setText(model.totalamount + " $");
        totalhistory.setText(model.totalhistory + " $");

        JsonArray jsonarray = model.sales;
        for (int i = 0; i < jsonarray.size(); i++) {
            JsonObject jsonobject = jsonarray.get(i).getAsJsonObject();

            String jData = jsonobject.get("date").getAsString();
            String jSum = jsonobject.get("sum").getAsString();
            String jHours = jsonobject.get("hours").getAsString();
            data.add(new Sales(Integer.toString(i + 1), jData, jSum, jHours));
        }

        String imageUrl = "http:" + model.profilePicture;

        System.out.println(imageUrl);

        Image image = new Image(imageUrl);
        profilePicture.setImage(image);

        StackPane root1 = new StackPane();
        StackPane root2 = new StackPane();

        DatePicker picker = new DatePicker();
        picker.setDayCellFactory(dayCellFactory);

        final DatePickerSkin skin1 = new DatePickerSkin(picker);
        
        
        LocalDate nextMonth = LocalDate.now().plusMonths(1);
        DatePicker picker2 = new DatePicker(nextMonth);
        picker2.setDayCellFactory(dayCellFactory2);
        
        final DatePickerSkin skin2 = new DatePickerSkin(picker2);

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

//        try {
//            // TODO
//
//            clientEndPoint = new WebsocketClientEndpoint(new URI(WEBSOCKET));
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//            // add listener
//            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
//                public void handleMessage(String message) {
//                    System.out.println(message);
//
//                    Gson gson = new Gson();
//                    JsonElement data = gson.fromJson(message, JsonElement.class);
//
//                    if (data.isJsonNull()) {
//                    } else {
//                        String jsonString = data.getAsJsonObject().get("data").getAsJsonPrimitive().getAsString();
//
//                        if (jsonString.isEmpty()) {
//                        } else {
//                            JsonObject parameters = gson.fromJson(jsonString, JsonObject.class);
//
//                            String jData = parameters.get("somedata").getAsString();
//
//                            if (jData.length() > 0) {
//                                System.out.println(jData);
//                                str = jData;
//                            }
//                        }
//
//                    }
//
//                }
//            });
        // wait 5 seconds for messages from websocket
        //Thread.sleep(2000);
    }

}
