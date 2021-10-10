package es.xuan.cacavoleiwdg.model;

import java.io.Serializable;

public class FiltreConf implements Serializable {
    private Boolean carregarFCVB;
    private Boolean carregarRFEVB;
    private int numJornada;
    private Boolean carregarFitxer;
    private Boolean carregarFitxerSQL;
    private String nomFitxer;
    private String nomFitxerSQL;

    public Boolean getCarregarFitxerSQL() {
        return carregarFitxerSQL;
    }

    public void setCarregarFitxerSQL(Boolean carregarFitxerSQL) {
        this.carregarFitxerSQL = carregarFitxerSQL;
    }

    public String getNomFitxerSQL() {
        return nomFitxerSQL;
    }

    public void setNomFitxerSQL(String nomFitxerSQL) {
        this.nomFitxerSQL = nomFitxerSQL;
    }

    public Boolean getCarregarFCVB() {
        return carregarFCVB;
    }

    public void setCarregarFCVB(Boolean carregarFCVB) {
        this.carregarFCVB = carregarFCVB;
    }

    public Boolean getCarregarRFEVB() {
        return carregarRFEVB;
    }

    public void setCarregarRFEVB(Boolean carregarRFEVB) {
        this.carregarRFEVB = carregarRFEVB;
    }

    public int getNumJornada() {
        return numJornada;
    }

    public void setNumJornada(int numJornada) {
        this.numJornada = numJornada;
    }

    public Boolean getCarregarFitxer() {
        return carregarFitxer;
    }

    public void setCarregarFitxer(Boolean carregarFitxer) {
        this.carregarFitxer = carregarFitxer;
    }

    public String getNomFitxer() {
        return nomFitxer;
    }

    public void setNomFitxer(String nomFitxer) {
        this.nomFitxer = nomFitxer;
    }
}
