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
import static studio.helper.Global.AVATAR;

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
            
            String profilePicture;

            String modelname = dataFromApi.get("sync_Modelname").getAsString();
            String password = dataFromApi.get("password").getAsString();
            JsonObject reservations = loginResponseJson.get("reservations").getAsJsonObject();
            
            /** @TODO fix error if no avatar */
            if(dataFromApi.get("data_profilePictureUrl").isJsonNull()){
                profilePicture = AVATAR;
            } else {
                    profilePicture = dataFromApi.get("data_profilePictureUrl").getAsString();
            }
            
            
            JsonObject total = loginResponseJson.get("total").getAsJsonObject();
            String onlineHours = total.get("hours").getAsString();
            int totalamount = total.get("totalamount").getAsInt();
            int totalhistory = total.get("totalhistory").getAsInt();
            
            String res_this_month = reservations.get("this_month").getAsString();
            int res_this_month_nr = reservations.get("this_month_nr").getAsInt();
            int res_this_year = reservations.get("this_year").getAsInt();
            String res_this_period = reservations.get("this_period").getAsString();
            String res_this_days = reservations.get("this_days").getAsString();
            int res_this_count = reservations.get("this_count").getAsInt();
            String res_this_hour = reservations.get("this_hour").getAsString();
            
            
            JsonArray sales = loginResponseJson.get("sales").getAsJsonArray();
            System.out.println(sales);
            
	    LoginResponse loginresponse = new LoginResponse();
	    loginresponse.setSuccess(success);
            loginresponse.setModelname(modelname);
            loginresponse.setPassword(password);
            loginresponse.setProfilePicture(profilePicture);
            loginresponse.setOnlineHours(onlineHours);
            loginresponse.setTotalamount(totalamount);
            loginresponse.setTotalhistory(totalhistory);
            loginresponse.setSales(sales);
            
            loginresponse.setRes_this_month(res_this_month);
            loginresponse.setRes_this_month_nr(res_this_month_nr);
            loginresponse.setRes_this_year(res_this_year);
            loginresponse.setRes_this_period(res_this_period);
            loginresponse.setRes_this_days(res_this_days);
            loginresponse.setRes_this_count(res_this_count);
            loginresponse.setRes_this_hour(res_this_hour);
	    
	    return loginresponse;
            } else {
                LoginResponse loginresponse = new LoginResponse();
                loginresponse.setSuccess(success);
                return loginresponse;
            }
	    
	}

  
}
