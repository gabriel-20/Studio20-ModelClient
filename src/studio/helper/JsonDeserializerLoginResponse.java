/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studio.helper;

import com.google.gson.JsonArray;
import studio.helper.LoginResponse;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author dev
 */
public class JsonDeserializerLoginResponse implements JsonDeserializer<LoginResponse>{

	public LoginResponse deserialize(JsonElement json, Type typeOfT, 
	JsonDeserializationContext context) throws JsonParseException {
		
	    JsonObject loginResponseJson = json.getAsJsonObject();
		
            Boolean success = loginResponseJson.get("success").getAsBoolean();
            
            if (success){
                JsonObject dataFromApi = loginResponseJson.get("data").getAsJsonObject();
            
            String profilePicture = "//www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwj8vPXHrZjgAhXPyKQKHYZvCyEQjRx6BAgBEAU&url=https%3A%2F%2Fplanetbotanix.com%2Ftestimonials%2Fconnie-h%2Ffemale-avatar-1-300x300%2F&psig=AOvVaw11KID46vMwj4XpcE4njPS3&ust=1549035630454379";

            String modelname = dataFromApi.get("sync_Modelname").getAsString();
            
            /** @TODO fix error if no avatar */
            if(dataFromApi.get("data_profilePictureUrl").getAsString() instanceof  String){
                profilePicture = dataFromApi.get("data_profilePictureUrl").getAsString();
            } 
            
            
            JsonObject total = loginResponseJson.get("total").getAsJsonObject();
            String onlineHours = total.get("hours").getAsString();
            int totalamount = total.get("totalamount").getAsInt();
            int totalhistory = total.get("totalhistory").getAsInt();
	    
            
            JsonArray sales = loginResponseJson.get("sales").getAsJsonArray();
            //System.out.println(sales);
            
	    LoginResponse loginresponse = new LoginResponse();
	    loginresponse.setSuccess(success);
            loginresponse.setModelname(modelname);
            loginresponse.setProfilePicture(profilePicture);
            loginresponse.setOnlineHours(onlineHours);
            loginresponse.setTotalamount(totalamount);
            loginresponse.setTotalhistory(totalhistory);
            loginresponse.setSales(sales);
	    
	    return loginresponse;
            } else {
                LoginResponse loginresponse = new LoginResponse();
                loginresponse.setSuccess(success);
                return loginresponse;
            }
	    
	}

  
}
