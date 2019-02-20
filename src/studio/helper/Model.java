/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studio.helper;

import com.google.gson.JsonArray;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author dev
 */
public class Model {
    
        private static Model single_instance_model = null; 
        
        public String modelname;
        public int model_id;
        public String password;
        public String profilePicture;
        public String onlineHours;
        public int  totalamount;
        public int  totalhistory;
        public JsonArray sales;
        public String res_this_month;
        public String res_this_month_nr;
        public int res_this_year;
        public String res_this_period;
        public ObservableList<String> res_this_days = FXCollections.observableArrayList();
        public int res_this_count;
        public String res_this_hour;
        public String res_next_month;
        public String res_next_month_nr;
        public int res_next_year;
        public String res_next_period;
        public String res_next_days;
        public int res_next_count;
        public String res_next_hour;
        public String artistic_email;
        public String artistic_password;

        
    private Model() 
    { 
        modelname = null; 
        
         res_this_days.addListener(new ListChangeListener() {

            @Override

            public void onChanged(ListChangeListener.Change change) {
                
            System.out.println("Detected a change! ");

            while (change.next()) {

            System.out.println("Was added? " + change.wasAdded());

            System.out.println("Was removed? " + change.wasRemoved());

            }

        }
        });
    }
    
    // static method to create instance of Singleton class 
    public static Model getInstance() 
    { 
        if (single_instance_model == null) 
            single_instance_model = new Model(); 
  
        return single_instance_model; 
    } 
    
    public void setReservations(String str){
    
       final String[] days = str.split("\\,");
        for (int i = 0; i < days.length; i++) {
            
            if (days[i].equals("")) {
                res_this_days.add("0");
            } else {
                res_this_days.add(days[i]);
            }
        }
    }
    
   
    
    
}
