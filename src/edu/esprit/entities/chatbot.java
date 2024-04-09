/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.entities;

import java.io.IOException;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Lenovo
 */
public class chatbot {
    
    
    private final String rapidApiKey;
    private final String rapidApiHost;

    public chatbot(String apiKey, String host) {
        this.rapidApiKey = apiKey;
        this.rapidApiHost = host;
    }

    public String getChatbotResponse(String message, String uid) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            String uri = "https://ai-chatbot.p.rapidapi.com/chat/free?message=" + message + "&uid=" + uid;

            HttpGet request = new HttpGet(uri);
            request.addHeader("X-RapidAPI-Key", rapidApiKey);
            request.addHeader("X-RapidAPI-Host", rapidApiHost);

            CloseableHttpResponse response = httpClient.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());

            return responseBody;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return "An error occurred while communicating with the chatbot.";
        }
    }

    public static void main(String[] args) {
        chatbot chatbot = new chatbot("4a31578ac5msh4fb73389bc8fb33p1714d7jsn047dfd800e53", "ai-chatbot.p.rapidapi.com");
        String message = "What's your name?";
        String uid = "user1";

        String chatbotResponse = chatbot.getChatbotResponse(message, uid);
        System.out.println(chatbotResponse);
    }
}


    
