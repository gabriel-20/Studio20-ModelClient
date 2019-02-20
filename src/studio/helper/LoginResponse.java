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
    private int model_id;

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }
    private String password;
    public String res_this_month;
    public String res_this_month_nr;
    public int res_this_year;
    public String res_this_period;
    public String res_this_days;
    public int res_this_count;
    public String res_this_hour;
    public String res_next_month;
    public String res_next_month_nr;
    public int res_next_year;
    public String res_next_period;
    public String res_next_days;
    public int res_next_count;
    public String artistic_email;

    public String getArtistic_email() {
        return artistic_email;
    }

    public void setArtistic_email(String artistic_email) {
        this.artistic_email = artistic_email;
    }

    public String getArtistic_password() {
        return artistic_password;
    }

    public void setArtistic_password(String artistic_password) {
        this.artistic_password = artistic_password;
    }
    public String artistic_password;

    public String getRes_next_month() {
        return res_next_month;
    }

    public void setRes_next_month(String res_next_month) {
        this.res_next_month = res_next_month;
    }

    public String getRes_next_month_nr() {
        return res_next_month_nr;
    }

    public void setRes_next_month_nr(String res_next_month_nr) {
        this.res_next_month_nr = res_next_month_nr;
    }

    public int getRes_next_year() {
        return res_next_year;
    }

    public void setRes_next_year(int res_next_year) {
        this.res_next_year = res_next_year;
    }

    public String getRes_next_period() {
        return res_next_period;
    }

    public void setRes_next_period(String res_next_period) {
        this.res_next_period = res_next_period;
    }

    public String getRes_next_days() {
        return res_next_days;
    }

    public void setRes_next_days(String res_next_days) {
        this.res_next_days = res_next_days;
    }

    public int getRes_next_count() {
        return res_next_count;
    }

    public void setRes_next_count(int res_next_count) {
        this.res_next_count = res_next_count;
    }

    public String getRes_next_hour() {
        return res_next_hour;
    }

    public void setRes_next_hour(String res_next_hour) {
        this.res_next_hour = res_next_hour;
    }
    public String res_next_hour;

    public String getRes_this_month_nr() {
        return res_this_month_nr;
    }

    public void setRes_this_month_nr(String res_this_month_nr) {
        this.res_this_month_nr = res_this_month_nr;
    }

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
