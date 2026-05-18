package com.restaurant.dto;

public class PredictionRequest {
    private double anticipacionDias;
    private String diaSemana;
    private String hora;
    private String historialNoShow;
    private String recordatorioEnviado;

    public PredictionRequest() {}

    public double getAnticipacionDias() {
        return anticipacionDias;
    }

    public void setAnticipacionDias(double anticipacionDias) {
        this.anticipacionDias = anticipacionDias;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getHistorialNoShow() {
        return historialNoShow;
    }

    public void setHistorialNoShow(String historialNoShow) {
        this.historialNoShow = historialNoShow;
    }

    public String getRecordatorioEnviado() {
        return recordatorioEnviado;
    }

    public void setRecordatorioEnviado(String recordatorioEnviado) {
        this.recordatorioEnviado = recordatorioEnviado;
    }
}
