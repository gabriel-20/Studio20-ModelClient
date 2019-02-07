/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studio.helper;

import com.google.gson.JsonArray;

/**
 *
 * @author dev
 */
public class LoginResponse {
	private Boolean success;
	private String dataFromApi;
	private String modelname;
        private String profilePicture;
        private String onlineHours;
        private int totalamount;
        private int totalhistory;
        private JsonArray sales;

    public JsonArray getSales() {
        return sales;
    }

    public void setSales(JsonArray sales) {
        this.sales = sales;
    }

    public int getTotalhistory() {
        return totalhistory;
    }

    public void setTotalhistory(int totalhistory) {
        this.totalhistory = totalhistory;
    }

    public int getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(int totalamount) {
        this.totalamount = totalamount;
    }

    public String getOnlineHours() {
        return onlineHours;
    }

    public void setOnlineHours(String onlineHours) {
        this.onlineHours = onlineHours;
    }
	
        public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
        
        public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
        
        public String getModelname() {
		return modelname;
	}
	public void setModelname(String modelname) {
		this.modelname = modelname;
	}
        
        public String getDataFromApi() {
		return dataFromApi;
	}
	public void setDataFromApi(String dataFromApi) {
		this.dataFromApi = dataFromApi;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("Id: ")
                            .append("Success: ").append(success).append(", ");
		return builder.toString();
	}
}
