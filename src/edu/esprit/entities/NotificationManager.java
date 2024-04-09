/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.entities;


import java.util.List;
import javafx.application.Platform;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


/**
 *
 * @author USER
 */
public class NotificationManager {
    public static void showNotifications(String title, String message) {
        Platform.runLater(() -> {
            Notifications.create()
                    .title(title)
                    .text(message)
                    .darkStyle()
                    .hideAfter(Duration.seconds(20))
                    .showInformation();
        });
    }
    
}
