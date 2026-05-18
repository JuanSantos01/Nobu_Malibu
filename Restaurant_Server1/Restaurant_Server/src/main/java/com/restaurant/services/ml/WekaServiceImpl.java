package com.restaurant.services.ml;

import com.restaurant.dto.PredictionRequest;
import com.restaurant.dto.PredictionResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Service
public class WekaServiceImpl implements WekaService {

    private Classifier modelo;
    private Instances trainingData;

    @PostConstruct
    public void init() throws Exception {
        entrenarModelo();
    }

    @Override
    public void entrenarModelo() throws Exception {

        // Cargar el ARFF desde resources como InputStream
        InputStream is = getClass().getResourceAsStream("/modelos/NobuWeka.arff");

        if (is == null) {
            throw new IllegalStateException("No se encontró el archivo NobuWeka.arff en /resources/modelos/");
        }

        // Copiar a archivo temporal porque Weka NO lee streams
        File tempFile = File.createTempFile("weka_model", ".arff");
        tempFile.deleteOnExit();

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            is.transferTo(fos);
        }

        // Ahora sí cargar con DataSource
        DataSource source = new DataSource(tempFile.getAbsolutePath());
        trainingData = source.getDataSet();

        // La clase es el atributo índice 5
        trainingData.setClassIndex(5);

        RandomForest rf = new RandomForest();
        rf.setNumIterations(150);
        rf.buildClassifier(trainingData);

        modelo = rf;
    }

    @Override
    public PredictionResponse predecirNoShow(PredictionRequest req) throws Exception {

        Instances structure = new Instances(trainingData, 0);
        Instance inst = new DenseInstance(structure.numAttributes());
        inst.setDataset(structure);

        setValue(structure, inst, "anticipacion_dias", req.getAnticipacionDias());
        setValue(structure, inst, "dia_semana", req.getDiaSemana());
        setValue(structure, inst, "hora", req.getHora());
        setValue(structure, inst, "historial_no_show", req.getHistorialNoShow());
        setValue(structure, inst, "recordatorio_enviado", req.getRecordatorioEnviado());

        inst.setMissing(structure.classIndex());

        double clsIndex = modelo.classifyInstance(inst);
        String clase = structure.classAttribute().value((int) clsIndex);

        double prob = modelo.distributionForInstance(inst)[(int) clsIndex];

        return new PredictionResponse(clase, prob);
    }

    private void setValue(Instances h, Instance i, String name, Object v) {
        Attribute attr = h.attribute(name);
        if (attr == null) {
            throw new IllegalArgumentException("Atributo no existente en ARFF: " + name);
        }

        if (attr.isNumeric()) {
            i.setValue(attr, Double.parseDouble(v.toString()));
        } else {
            if (attr.indexOfValue(v.toString()) >= 0)
                i.setValue(attr, v.toString());
            else
                i.setMissing(attr);
        }
    }
}
