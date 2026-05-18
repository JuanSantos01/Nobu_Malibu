package com.restaurant.dto;

public class PredictionResponse {

    private String prediction; // "Si" o "No"
    private double probability;

    public PredictionResponse() {}

    public PredictionResponse(String prediction, double probability) {
        this.prediction = prediction;
        this.probability = probability;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
