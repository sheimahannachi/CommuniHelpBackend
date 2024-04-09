/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.entities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author ASUS
 */
public class translate {

public static void main(String[] args) {
        try {
            String url = "https://translation-api4.p.rapidapi.com/translation?from=en&to=fr&query=How%20are%20you";
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            // Set request method and headers
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-RapidAPI-Key", "945a7eb66bmsh4e4c93208c7aa5ap13208ejsn42593e13ef61");
            connection.setRequestProperty("X-RapidAPI-Host", "translation-api4.p.rapidapi.com");

            // Get the response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print the response
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

