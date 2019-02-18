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
public class Model {
    
        private static Model single_instance_model = null; 
        
        public String modelname;
        public String password;
        public String profilePicture;
        public String onlineHours;
        public int  totalamount;
        public int  totalhistory;
        public JsonArray sales;
        public String res_this_month;
        public int res_this_month_nr;
        public int res_this_year;
        public String res_this_period;
        public String res_this_days;
        public int res_this_count;
        public String res_this_hour;

        
    private Model() 
    { 
        modelname = null; 
    }
    
    // static method to create instance of Singleton class 
    public static Model getInstance() 
    { 
        if (single_instance_model == null) 
            single_instance_model = new Model(); 
  
        return single_instance_model; 
    } 
    
}
