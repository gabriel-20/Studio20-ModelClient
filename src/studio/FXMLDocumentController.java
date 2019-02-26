/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studio;

import studio.helper.debug.JsonDeserializerTestResponse;
import studio.helper.Model;
import studio.helper.JsonDeserializerLoginResponse;
import studio.helper.LoginResponse;
import studio.helper.Global;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import io.sentry.Sentry;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.UserBuilder;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import static studio.helper.Global.LOGIN_URL;
import static studio.helper.Global.RANDOM_MODELS;
import studio.helper.debug.TestResponse;

/**
 *
 * @author dev
 */
public class FXMLDocumentController implements Initializable {

    private static SentryClient sentry;

    private double xOffset = 0;
    private double yOffset = 0;

    private String model, pass;

    @FXML
    private Label label, loginStatus;

    @FXML
    private JFXTextField model1, model2, model3, model4, model5;

    @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField password;

    @FXML
    private Button btn_login;

    @FXML
    private ImageView imageLogo;

    @FXML
    private AnchorPane debugwindow;

    @FXML
    private Button togBtn;

    @FXML
    private void handleClose(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleToggle(MouseEvent event) {

        debugwindow.setVisible(!debugwindow.isVisible());

    }

    private String content;

    private Double str = -0.1;
    private Double val = 0.0;

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {

        loginStatus.setText("");

        ColorAdjust colorAdjust = new ColorAdjust();

        model = user.getText();
        pass = password.getText();

        if ((model.length() > 0) && (pass.length() > 0)) {
            System.out.println("pass " + pass);
            String url = LOGIN_URL;

            Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(0.05), new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    val = val + str;
                    if (!((val > -0.5) && (val <= -0.1))) {
                        str = str * (-1);
                    }

                    colorAdjust.setSaturation(val);
                    if ((val > -0.5) && (val < -0.1)) {
                        imageLogo.setEffect(colorAdjust);
                    }
                    //System.out.println("this is called every "+ val +" seconds on UI thread");

                }
            }));
            fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
            fiveSecondsWonder.play();

            Task<Void> task = new Task<Void>() {
                @Override
                public Void call() throws Exception {

                    content = fetchContent(url, model);
                    System.out.println(content);

                    return null;
                }
            };
            task.setOnSucceeded(ee -> {
                fiveSecondsWonder.stop();
                Gson gson = new GsonBuilder().registerTypeAdapter(LoginResponse.class, new JsonDeserializerLoginResponse())
                        .serializeNulls().create();

                LoginResponse loginResponse = gson.fromJson(content, new TypeToken<LoginResponse>() {
                }.getType());
                System.out.println(loginResponse);
                if (loginResponse.getSuccess()) {

                    Model model = Model.getInstance();
                    model.modelname = loginResponse.getModelname();
                    model.model_id = loginResponse.getModel_id();
                    model.password = loginResponse.getPassword();
                    model.artistic_email = loginResponse.getArtistic_email();
                    model.artistic_password = loginResponse.getArtistic_password();
                    model.profilePicture = loginResponse.getProfilePicture();
                    model.onlineHours = loginResponse.getOnlineHours();
                    model.totalamount = loginResponse.getTotalamount();
                    model.totalhistory = loginResponse.getTotalhistory();
                    model.sales = loginResponse.getSales();
                    model.res_this_month = loginResponse.getRes_this_month();
                    model.res_this_month_nr = loginResponse.getRes_this_month_nr();
                    model.res_this_year = loginResponse.getRes_this_year();
                    model.res_this_period = loginResponse.getRes_this_period();
                    model.setReservations(loginResponse.getRes_this_days());
                    model.res_this_count = loginResponse.getRes_this_count();
                    model.res_this_hour = loginResponse.getRes_this_hour();
                    model.res_next_month = loginResponse.getRes_next_month();
                    model.res_next_month_nr = loginResponse.getRes_next_month_nr();
                    model.res_next_year = loginResponse.getRes_next_year();
                    model.res_next_period = loginResponse.getRes_next_period();
                    model.res_next_days = loginResponse.getRes_next_days();
                    model.res_next_count = loginResponse.getRes_next_count();
                    model.res_next_hour = loginResponse.getRes_next_hour();

                    System.out.println(model);
                    System.out.println(pass);

                    if (pass.equals(model.password)) {
                        System.out.println("YES!!");

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                            Parent rootl = (Parent) fxmlLoader.load();

                            Stage stage = new Stage();
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.setTitle("Model Dashboard");

                            //grab your root here
                            rootl.setOnMousePressed(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    xOffset = event.getSceneX();
                                    yOffset = event.getSceneY();
                                }
                            });

                            //move around here
                            rootl.setOnMouseDragged(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    stage.setX(event.getScreenX() - xOffset);
                                    stage.setY(event.getScreenY() - yOffset);
                                }
                            });

                            stage.setScene(new Scene(rootl));
                            stage.show();

                        } catch (Exception e) {
                            System.out.println("Error Screen!");
                        }

                    } else {
                        loginStatus.setText("Invalid email/password..");
                        System.out.println("NO!!");
                    }

                } else {
                    //System.exit(0);

                    System.out.println("Login Error!");
                }

            });
            new Thread(task).start();

        }

    }

    void logWithStaticAPI() {
        // Note that all fields set on the context are optional. Context data is copied onto
        // all future events in the current context (until the context is cleared).

        // Record a breadcrumb in the current context. By default the last 100 breadcrumbs are kept.
        Sentry.getContext().recordBreadcrumb(
                new BreadcrumbBuilder().setMessage("User made an action").build()
        );

        // Set the user in the current context.
        Sentry.getContext().setUser(
                new UserBuilder().setEmail("hello@sentry.io").build()
        );

        // Add extra data to future events in this context.
        Sentry.getContext().addExtra("extra", "thing");

        // Add an additional tag to future events in this context.
        Sentry.getContext().addTag("tagName", "tagValue");

        /*
         This sends a simple event to Sentry using the statically stored instance
         that was created in the ``main`` method.
         */
        Sentry.capture("This is a test");

        try {
            unsafeMethod();
        } catch (Exception e) {
            // This sends an exception event to Sentry using the statically stored instance
            // that was created in the ``main`` method.
            Sentry.capture(e);
        }
    }

    void unsafeMethod() {
        throw new UnsupportedOperationException("You shouldn't call this!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        System.out.println("heree");

        Sentry.init("https://e739e480317f45c4ac179558e84dbc6b@sentry.io/1401926");

        sentry = SentryClientFactory.sentryClient();

        FXMLDocumentController myClass = new FXMLDocumentController();
        myClass.logWithStaticAPI();

        debugwindow.setVisible(false);

        try {
            // TODO
            String url2 = RANDOM_MODELS;

            String content = fetchContent2(url2);

            System.out.println("initialize ..");

            Gson gson = new GsonBuilder().registerTypeAdapter(TestResponse.class, new JsonDeserializerTestResponse())
                    .serializeNulls().create();

            TestResponse testResponse = gson.fromJson(content, new TypeToken<TestResponse>() {
            }.getType());

            System.out.println(testResponse);

            JsonArray dataaaaFromApi = testResponse.getDataFromApi();

            String[] arr = new String[5];

            for (int i = 0; i < dataaaaFromApi.size(); i++) {

                arr[i] = dataaaaFromApi.get(i).getAsString();
            }

            model1.setText(arr[0]);
            model2.setText(arr[1]);
            model3.setText(arr[2]);
            model4.setText(arr[3]);
            model5.setText(arr[4]);

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static String fetchContent(String uri, String modelname) throws IOException {

        String urlParameters = "email=" + modelname;
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

        final int OK = 200;
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", "Java client");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + Global.MANAGEMENT_TOKEN);

        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.write(postData);
        }

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

    private static String fetchContent2(String uri) throws IOException {

        final int OK = 200;
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", "Java client");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + Global.MANAGEMENT_TOKEN);

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

}
