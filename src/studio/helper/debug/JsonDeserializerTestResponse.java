/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studio.helper.debug;

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
public class JsonDeserializerTestResponse implements JsonDeserializer<TestResponse>{

	public TestResponse deserialize(JsonElement json, Type typeOfT, 
	JsonDeserializationContext context) throws JsonParseException {
		
	    JsonObject loginResponseJson = json.getAsJsonObject();
            
            //System.out.println(loginResponseJson);
            
	    JsonArray  dataFromApi = loginResponseJson.get("data").getAsJsonArray();
            
            String success = loginResponseJson.get("success").getAsString();
        
            System.out.println(dataFromApi);
            
	    TestResponse loginresponse = new TestResponse();
	    loginresponse.setSuccess(success);
            loginresponse.setDataFromApi(dataFromApi);
            
	    
	    return loginresponse;
	}

  
}
