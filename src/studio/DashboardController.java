/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import studio.helper.Model;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import java.util.stream.IntStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Node;
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
import javafx.stage.FileChooser;
import javafx.util.Callback;
import studio.helper.Sales;
import org.controlsfx.control.Notifications;
import static studio.helper.Global.DOWNLOAD_JCAM;
import static studio.helper.Global.DOWNLOAD_SPLITER;
import static studio.helper.Global.DOWNLOAD_TEAMVIEWER;
import static studio.helper.Global.JASMIN_EXE_NAME;
import studio.helper.WinRegistry;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import studio.helper.Global;
import static studio.helper.Global.SEND_RESERVATIONS;
import static studio.helper.Global.WEBSOCKET;
import studio.helper.debug.JsonDeserializerReservationsResponse;
import studio.helper.debug.ReservationsResponse;

/**
 * FXML Controller class
 *
 * @author dev
 */
public class DashboardController implements Initializable {

    Model model = Model.getInstance();

    Preferences prefs = Preferences.userNodeForPackage(DashboardController.class);

    final String JASMIN_PATH = "PATH_JASMIN";

    @FXML
    private JFXTextField timeTextfield, inputChat;

    @FXML
    private JFXTextArea chatArea;

    @FXML
    private Label modelName;

    @FXML
    private Label onlineHours;

    @FXML
    private Label totalamount;

    @FXML
    private Label totalhistory;

    private String jText = "";

    private String messageType = "message";

    @FXML
    private Hyperlink linkTeamviewer, linkSpliter, linkJcam;

    @FXML
    private ImageView profilePicture;
    
     int max = 0;

    

    @FXML
    private AnchorPane calendar1;

    private WebsocketClientEndpoint clientEndPoint;

    private String pathJasmin = prefs.get(JASMIN_PATH, null);

    private static final int HKEY_CURRENT_USER = 0x80000001;
    private static final int KEY_QUERY_VALUE = 1;
    private static final int KEY_SET_VALUE = 2;
    private static final int KEY_READ = 0x20019;

    ObservableList<LocalDate> selectedDates = FXCollections.observableArrayList();

    @FXML
    private void addData(ActionEvent event) {

        System.out.println("sssssssssss");
        model.res_this_days.add("22");
        System.out.println(model.res_this_days);
    }

    @FXML
    private void sendMessasge(MouseEvent event) {

        String text = "";
        text = inputChat.getText();
        //{"type":"message", "message":"asasaaaaasdsadadsboss!"}
        clientEndPoint.sendMessage("{\"type\":\"message\",\"message\":\""+text+"\"}");

    }

    @FXML
    private void clearCache(MouseEvent event) {

        prefs.remove(JASMIN_PATH);
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
    private void showNot(ActionEvent event) {
        Notifications.create()
                .title("Studio20")
                .text("asdasdas")
                .showWarning();

    }

    @FXML
    private void sendReservations(ActionEvent event) {

        System.out.println("Send Reservations");

        try {
            // TODO
            String url = SEND_RESERVATIONS;

            String content = sendReservations(url);

            System.out.println("initialize ..");

            Gson gson = new GsonBuilder().registerTypeAdapter(ReservationsResponse.class, new JsonDeserializerReservationsResponse())
                    .serializeNulls().create();

            ReservationsResponse reservationsResponse = gson.fromJson(content, new TypeToken<ReservationsResponse>() {
            }.getType());

            System.out.println(reservationsResponse);

            String dataFromApi = reservationsResponse.getDataFromApi();

            System.out.println(dataFromApi);

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void modelCenterLogin(MouseEvent event) {

        System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://modelcenter.jasmin.com/en/login");

        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws Exception {

                Thread.sleep(2000);

                WebElement emailBox = null;
                WebElement passBox = null;

                List<WebElement> someElements = driver.findElements(By.cssSelector("input"));

                for (WebElement anElement : someElements) {
                    if (anElement.getAttribute("type").equals("email")) {
                        emailBox = anElement;
                    }

                    if (anElement.getAttribute("type").equals("password")) {
                        passBox = anElement;
                    }
                }

                emailBox.sendKeys("modelname");
                passBox.sendKeys("modelname");

                return null;
            }
        };
        task.setOnSucceeded(ee -> {

        });
        new Thread(task).start();

    }

    @FXML
    public void execApp(ActionEvent event) throws IOException {

        pathJasmin = prefs.get(JASMIN_PATH, null);

        System.out.println("click jas " + pathJasmin);
        System.out.println("-username " + model.artistic_email);
        System.out.println("-password " + model.artistic_password);

        String paramUsername = "-username";
        String paramUsername1 = model.artistic_email;
        String paramPassword = "-password";
        String paramPassword1 = model.artistic_password;

        List alist = new ArrayList<>();

        // add the list of commands to the list
        alist.add(pathJasmin);
        alist.add(paramUsername);
        alist.add(paramUsername1);
        alist.add(paramPassword);
        alist.add(paramPassword1);

        if (pathJasmin != null) {

            Task<Void> task = new Task<Void>() {
                @Override
                public Void call() throws Exception {

                    Process process = new ProcessBuilder().command(alist).start();
                    if (process.isAlive()) {

                        InputStream is = process.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        String line;

                        while ((line = br.readLine()) != null) {
                            System.out.println(line.trim());
                        }

                        System.out.println("is running ");
                        //prefs.put(JASMIN_PATH, pathJasmin + param);
                    } else {
                        System.out.println("is not running ");
                        prefs.remove(JASMIN_PATH);

                    }

                    return null;
                }
            };
            task.setOnSucceeded(ee -> {

            });
            new Thread(task).start();

        } else {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Find JCam.exe ..");

            Node node = (Node) event.getSource();
            File f = fileChooser.showOpenDialog(node.getScene().getWindow());

            String absolutPath = f.getAbsolutePath().toString();
            String filename = f.getName();

            System.out.println(filename);
            if (filename.equals(JASMIN_EXE_NAME)) {
                prefs.put(JASMIN_PATH, absolutPath);

            }

        }

    }

    @FXML
    private void handleCloseDash(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private TableView<Sales> table;

    @FXML
    private Button btnSendReservations;

    private final ObservableList<Sales> data = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        String lday = "01-" + model.res_this_month_nr + "-" + model.res_this_year;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
        LocalDate locDate = LocalDate.parse(lday, formatter);
        Boolean asd = false;
        YearMonth yearMonthObject = YearMonth.of(model.res_this_year, Integer.valueOf(model.res_this_month_nr));
        int daysInMonth = yearMonthObject.lengthOfMonth();
 
        
        String ldaynext = "31-" + model.res_next_month_nr + "-" + model.res_next_year;
        LocalDate locDateNext = LocalDate.parse(ldaynext, formatter);

        btnSendReservations.setDisable(true);

        timeTextfield.setText(model.res_this_hour);

        final String[] daysnext = model.res_next_days.split("\\,");
        int[] daysinext = new int[daysnext.length];
        for (int i = 0; i < daysnext.length; i++) {
            if (daysnext[i].equals("")) {
                daysinext[i] = 0;
            } else {
                daysinext[i] = Integer.parseInt(daysnext[i]);
            }
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

                        //System.out.println(locDate);
                        //System.out.println(item);
                        //System.out.println(locDateNext);
                        if ( (item.isAfter(locDateNext)) || (item.isBefore(locDate)) ){ 
                        setDisable(true);
                        setStyle("-fx-background-color: #ffc0cb;visibility:hidden");
                        } else {
                        System.out.println(day);
                        System.out.println(max);
                        System.out.println(item.lengthOfMonth());
                        if (day == 1) max = item.lengthOfMonth();
                        
                        if (day == max ) max = 0;
                        
                        if ((day >= 1) && (day <= max)) {
                        
                        System.out.println("is here");
                        
                        boolean alreadySelected = selectedDates.contains(item);
                        String dayz = (item.getDayOfMonth() < 10) ? "0" + Integer.toString(item.getDayOfMonth()) : Integer.toString(item.getDayOfMonth());
                        boolean contains = model.res_this_days.contains(dayz);
                        boolean contains2 = IntStream.of(daysinext).anyMatch(x -> x == day);

                        if (((year == model.res_this_year) && (month == Integer.parseInt(model.res_this_month_nr)) && (contains))
                                || ((year == model.res_next_year) && (month == Integer.parseInt(model.res_next_month_nr)) && (contains2))) {
                            setTooltip(new Tooltip("Reservations!"));
                            setStyle("-fx-background-color: #ff4444;");
                            setDisable(true);
                        } else if (!(((year == model.res_this_year) && (month == Integer.parseInt(model.res_this_month_nr)))
                                || ((year == model.res_next_year) && (month == Integer.parseInt(model.res_next_month_nr))))) {
                            setDisable(true);
                        }

                        if (alreadySelected) {
                            setStyle("-fx-background-color: #09a30f;");
                        }
                        
                        }
                        
                        
                        
                        
                        
                       
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

        String imageUrl = model.profilePicture;

        Image image = new Image(imageUrl);
        profilePicture.setImage(image);

        StackPane root1 = new StackPane();

       
        
        DatePicker picker = new DatePicker(locDate);
        picker.setDayCellFactory(dayCellFactory);

        picker.setOnAction((event) -> {
            
            //LocalDate localDatezz = picker.getValue();

            
            System.out.println("Selected date: " + picker.getValue());

            if (!selectedDates.contains(picker.getValue())) {
                selectedDates.add(picker.getValue());
            } else {
                selectedDates.remove(picker.getValue());
            }

            System.out.println("Selected dates: " + selectedDates);
        });

        final DatePickerSkin skin1 = new DatePickerSkin(picker);
        root1.getChildren().add(skin1.getPopupContent());
        calendar1.getChildren().add(root1);

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

//            if (pathJasmin == null) {
//                getJasminPath();
//            }
        try {
            // TODO

            clientEndPoint = new WebsocketClientEndpoint(new URI(WEBSOCKET));
        } catch (URISyntaxException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //send modelname for socket auth
        clientEndPoint.sendMessage(model.modelname);

        // add listener
        clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
            public void handleMessage(String message) {
                System.out.println(message);

                Task<Void> task = new Task<Void>() {
                    @Override
                    public Void call() throws Exception {

                        Thread.sleep(100);

                        Gson gson = new Gson();
                        JsonElement data = gson.fromJson(message, JsonElement.class);

                        if (!data.isJsonNull()) {

                            String jsonType = data.getAsJsonObject().get("type").getAsJsonPrimitive().getAsString();

                            if (!jsonType.isEmpty()) {

                                if (jsonType.equals("message")) {

                                    JsonObject jsonData = data.getAsJsonObject().get("data").getAsJsonObject();
                                    JsonObject parameters = gson.fromJson(jsonData, JsonObject.class);

                                    String jText = parameters.get("text").getAsString();
                                    String jAuthor = parameters.get("author").getAsString();
                                    String jcolor = parameters.get("color").getAsString();
                                    int jtime = parameters.get("time").getAsInt();
                                    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

                                    Date time = Date.from( Instant.ofEpochSecond( jtime ) );

                                    messageType = "message";
                                    chatArea.appendText("\n" + time + "\n" + jAuthor + " : " + jText);
                                    System.out.println(jText);

                                }

                                if (jsonType.equals("notification")) {

                                    JsonObject jsonData = data.getAsJsonObject().get("data").getAsJsonObject();
                                    JsonObject parameters = gson.fromJson(jsonData, JsonObject.class);

                                    messageType = "notification";
                                    jText = parameters.get("text").getAsString();

                                }

                                if (jsonType.equals("history")) {

                                }

                            } else {
                                //error
                            }

                        }

                        return null;
                    }
                };
                task.setOnSucceeded(ee -> {
                    if (messageType == "notification") {
                        showNotification(jText);
                    }

                });
                new Thread(task).start();

            }
        });
            //Thread.sleep(2000);

        //prefs.remove(JASMIN_PATH);
    }

    private void getJasminPath() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

        String value = null;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                String path = "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\JasminCam " + i + "." + j;

                value = WinRegistry.readString(WinRegistry.HKEY_LOCAL_MACHINE, path, "InstallLocation");

                if (value != null) {
                    System.out.println("path:  = " + path);
                    System.out.println("jasmin path = " + value);

                    path = value + "\\App\\" + JASMIN_EXE_NAME;
                    File file = new File(path);

                    if (file.exists()) {
                        pathJasmin = path;
                        prefs.put(JASMIN_PATH, pathJasmin);
                        System.out.println(path);

                    }

                    break;
                }
            }
        }

    }

    private String sendReservations(String uri) throws MalformedURLException, ProtocolException, IOException {

        URL url = new URL(uri);

        ArrayList<String> arr = new ArrayList<>();

        for (LocalDate j : selectedDates) {
            int day = j.getDayOfMonth();
            int month = j.getMonthValue();
            String dayy = "";

            if (Integer.valueOf(model.res_this_month_nr) == month) {
                if (day < 10) {
                    dayy = "0" + String.valueOf(day);
                } else {
                    dayy = String.valueOf(day);
                }
                arr.add(dayy);
            }
        }

        String listArrString = "";

        listArrString = arr.stream().map((s) -> s + ",").reduce(listArrString, String::concat);

        listArrString = listArrString.substring(0, listArrString.lastIndexOf(","));

        Map<String, Object> params = new LinkedHashMap<>();
        params.put("model_id", model.model_id);
        params.put("period", model.res_this_month_nr + "-" + model.res_this_year);
        params.put("days", listArrString);
        params.put("hour", model.res_this_hour);

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        System.out.println(postData.toString());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", "Java client");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + Global.MANAGEMENT_TOKEN);
        connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        connection.getOutputStream().write(postDataBytes);

        StringBuilder content;

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {

            String line;
            content = new StringBuilder();

            while ((line = in.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        }

        return content.toString();

    }

    private void showNotification(String message) {

        Notifications.create()
                .title("Studio20")
                .text(message)
                .showWarning();

    }

}
