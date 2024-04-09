package event.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class country {
    
    public List<String> searchCountry(String searchText) {
        OkHttpClient client = new OkHttpClient();
        String apiUrl = "https://address-from-to-latitude-longitude.p.rapidapi.com/geolocationapi?address=" + searchText;

        Request request = new Request.Builder()
            .url(apiUrl)
            .get()
            .addHeader("X-RapidAPI-Key", "770c02b6e6mshbf1f9997c950932p1f0bf5jsn4223c337bd32")
            .addHeader("X-RapidAPI-Host", "address-from-to-latitude-longitude.p.rapidapi.com")
            .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            // Traitement de la réponse JSON et extraction des suggestions (par exemple, les noms de pays)
            return processJsonResponse(responseBody);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Collections.emptyList(); // En cas d'erreur, retourne une liste vide
    }

    // Méthode pour traiter la réponse JSON et extraire les suggestions
    private List<String> processJsonResponse(String jsonResponse) {
        List<String> suggestions = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray results = json.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String countryName = result.getString("countryName");
                suggestions.add(countryName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return suggestions;
    }
}



