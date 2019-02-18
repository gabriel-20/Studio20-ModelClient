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
    private String password;
    public String res_this_month;
    public int res_this_month_nr;

    public int getRes_this_month_nr() {
        return res_this_month_nr;
    }

    public void setRes_this_month_nr(int res_this_month_nr) {
        this.res_this_month_nr = res_this_month_nr;
    }
    public int res_this_year;
    public String res_this_period;
    public String res_this_days;
    public int res_this_count;
    public String res_this_hour;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
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

    public String getRes_this_month() {
        return res_this_month;
    }

    public void setRes_this_month(String res_this_month) {
        this.res_this_month = res_this_month;
    }

    public int getRes_this_year() {
        return res_this_year;
    }

    public void setRes_this_year(int res_this_year) {
        this.res_this_year = res_this_year;
    }

    public String getRes_this_period() {
        return res_this_period;
    }

    public void setRes_this_period(String res_this_period) {
        this.res_this_period = res_this_period;
    }

    public String getRes_this_days() {
        return res_this_days;
    }

    public void setRes_this_days(String res_this_days) {
        this.res_this_days = res_this_days;
    }

    public int getRes_this_count() {
        return res_this_count;
    }

    public void setRes_this_count(int res_this_count) {
        this.res_this_count = res_this_count;
    }

    public String getRes_this_hour() {
        return res_this_hour;
    }

    public void setRes_this_hour(String res_this_hour) {
        this.res_this_hour = res_this_hour;
    }
}
