/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 package edu.esprit.services;

import edu.esprit.utilis.MyConnection;
import java.util.Timer;
import java.util.TimerTask;
import edu.esprit.services.EventCrud;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author MSI
 */
public class timer extends TimerTask {
     @Override
    public void run() {
        // Place the task logic you want to execute on a schedule here
        
        EventCrud ev = new EventCrud();
        int lastEventId = getLastEventId(); // Obtenez le dernier ID d'événement

List<String> ml = ev.GetParticipentByEvent(lastEventId);
    
        for( int i=0 ; i< ml.size() ; i++){
               SendMail e=new SendMail();
               e.sendMail( ml.get(i) ,"Evenet Reminder "," dont forget we have an event soon be ready !!!!  ");
        
        }
        //System.out.println("Task executed at: " + System.currentTimeMillis());
        // <[ "aa"  "aa" "aa" ]
    }

  /*  public static void main(String[] args) {
        Timer timer = new Timer();
        

        // Schedule the task to run after a delay (in milliseconds) and then at fixed intervals (in milliseconds)
        timer.schedule(new timer(), 5000 );

        // You can also use scheduleAtFixedRate for fixed-rate scheduling
        // timer.scheduleAtFixedRate(new MyTask(), 1000, 2000);
    */
    
    // Define a method to get the last id_event from the participant table
    public static int getLastEventId() {
        int lastEventId = -1; // Initialize with a default value

        String sql = "SELECT MAX(id_ev) AS last_id_event FROM participant";

        try (Connection connection = MyConnection.getInstance().getCnx();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                lastEventId = resultSet.getInt("last_id_event");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastEventId;
    }
}



