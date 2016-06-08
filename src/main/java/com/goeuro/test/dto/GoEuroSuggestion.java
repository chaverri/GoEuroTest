package com.goeuro.test.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"_id", "name", "type", "latitude", "longitude"})
public class GoEuroSuggestion {
    private Integer _id;
    private String name;
    private String type;
    private Double latitude;
    private Double longitude;

    public GoEuroSuggestion (Integer id, String name, String type, Double latitude, Double longitude) {
        _id = id;
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
