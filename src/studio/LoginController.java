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
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static studio.helper.Global.LOGIN_URL;
import static studio.helper.Global.RANDOM_MODELS;
import studio.helper.debug.TestResponse;

/**
 *
 * @author dev
 */
public class LoginController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    private String model;

    @FXML
    private Label label;
    
    @FXML
    private JFXTextField model1,model2,model3,model4,model5;

    @FXML
    private JFXTextField user;

    @FXML
    private Button btn_login;

    @FXML
    private void handleClose(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {

        model = user.getText();

        if (model.length() > 0) {

            String url = LOGIN_URL;

            String content = fetchContent(url, model);

            System.out.println(content);

            Gson gson = new GsonBuilder().registerTypeAdapter(LoginResponse.class, new JsonDeserializerLoginResponse())
                    .serializeNulls().create();

            LoginResponse loginResponse = gson.fromJson(content, new TypeToken<LoginResponse>() {
            }.getType());

            System.out.println(loginResponse);

            if (loginResponse.getSuccess()) {

                Model model = Model.getInstance();
                model.modelname = loginResponse.getModelname();
                model.profilePicture = loginResponse.getProfilePicture();
                model.onlineHours = loginResponse.getOnlineHours();
                model.totalamount = loginResponse.getTotalamount();
                model.totalhistory = loginResponse.getTotalhistory();
                model.sales = loginResponse.getSales();

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
            //System.exit(0);
            
            System.out.println("Login Error!");
            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
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
             
             for (int i=0; i < dataaaaFromApi.size(); i++) {
                
                arr[i] = dataaaaFromApi.get(i).getAsString();
            }
             
          
             
            model1.setText(arr[0]);
            model2.setText(arr[1]);
            model3.setText(arr[2]);
            model4.setText(arr[3]);
            model5.setText(arr[4]);
            
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
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
