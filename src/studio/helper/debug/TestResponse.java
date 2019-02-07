/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studio.helper.debug;

import com.google.gson.JsonArray;

/**
 *
 * @author dev
 */
public class TestResponse {

    private String success;

    private JsonArray dataFromApi;

    public JsonArray getDataFromApi() {
        return dataFromApi;
    }

    public void setDataFromApi(JsonArray dataFromApi) {
        this.dataFromApi = dataFromApi;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

   

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Id: ")
                .append("Success: ").append(success).append(", ");
        return builder.toString();
    }
}
