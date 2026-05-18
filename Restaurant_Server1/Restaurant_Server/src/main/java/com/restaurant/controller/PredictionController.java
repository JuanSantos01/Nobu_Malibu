package com.restaurant.controller;

import com.restaurant.dto.PredictionRequest;
import com.restaurant.dto.PredictionResponse;
import com.restaurant.responce.GeneralResponse;
import com.restaurant.services.ml.WekaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/predict")
public class PredictionController {

    @Autowired
    private WekaService wekaService;

    @PostMapping("/noshow")
    public GeneralResponse predecirNoShow(@RequestBody PredictionRequest req) {

        GeneralResponse response = new GeneralResponse();

        try {
            PredictionResponse pred = wekaService.predecirNoShow(req);

            response.setStatus(HttpStatus.OK);
            response.setMessage("Predicción realizada correctamente");
            response.setData(pred);

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Error en la predicción: " + e.getMessage());
            response.setData(null);
        }

        return response;
    }
}
