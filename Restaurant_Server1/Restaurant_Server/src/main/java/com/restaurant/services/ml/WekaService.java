package com.restaurant.services.ml;

import com.restaurant.dto.PredictionRequest;
import com.restaurant.dto.PredictionResponse;

public interface WekaService {
    void entrenarModelo() throws Exception;
    PredictionResponse predecirNoShow(PredictionRequest req) throws Exception;
}
