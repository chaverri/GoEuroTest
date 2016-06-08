package com.goeuro.test.api;

import com.goeuro.test.dto.GoEuroSuggestion;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GoEuroAPIConsumer {

    @Value("${goeuro.api.baseUrl}")
    String baseApiUrl;

    public List<GoEuroSuggestion> getSuggestions(String cityName) throws GoEuroAPICallException {

        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(baseApiUrl + "/v2/position/suggest/en/" + cityName);
            HttpResponse response = httpClient.execute(request);
            String responseAsString = EntityUtils.toString(response.getEntity());

            JSONArray resultsAsJson = new JSONArray(responseAsString);

            List<GoEuroSuggestion> suggestions = new ArrayList<GoEuroSuggestion>();

            for (int i = 0; i < resultsAsJson.length(); i++) {

                Integer id = 0;
                String type = "";
                String name = "";
                Double latitude = 0.0;
                Double longitude = 0.0;

                JSONObject suggestionAsJson = resultsAsJson.getJSONObject(i);

                if (suggestionAsJson.has("_id")) {
                    id = suggestionAsJson.getInt("_id");
                }

                if (suggestionAsJson.has("type")) {
                    type = suggestionAsJson.getString("type");
                }

                if (suggestionAsJson.has("name")) {
                    name = suggestionAsJson.getString("name");
                }

                if (suggestionAsJson.has("geo_position")) {

                    JSONObject geoPosition = suggestionAsJson.getJSONObject("geo_position");
                    if (geoPosition.has("latitude")) {
                        latitude = geoPosition.getDouble("latitude");
                    }

                    if (geoPosition.has("longitude")) {
                        longitude = geoPosition.getDouble("longitude");
                    }
                }

                suggestions.add(new GoEuroSuggestion(id, name, type, latitude, longitude));

            }

            return suggestions;
        } catch (IOException e) {
            throw new GoEuroAPICallException("Error while trying to get suggestions from the GoEuroAPI");
        }
    }
}
