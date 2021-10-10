package es.xuan.cacavoleiwdg.model;

import java.io.Serializable;
import java.util.Calendar;

public class Filtre implements Serializable {
    private String nomEquip;
    private String divisio;
    private Calendar dataPartit;
    private String temporada;
    private Calendar dataInici;
    private Calendar dataFi;
    private int idTorneig;

    public int getIdTorneig() {
        return idTorneig;
    }

    public void setIdTorneig(int idTorneig) {
        this.idTorneig = idTorneig;
    }

    public Calendar getDataInici() {
        return dataInici;
    }

    public void setDataInici(Calendar dataInici) {
        this.dataInici = dataInici;
    }

    public Calendar getDataFi() {
        return dataFi;
    }

    public void setDataFi(Calendar dataFi) {
        this.dataFi = dataFi;
    }

    public String getGrup() {
        return grup;
    }

    public void setGrup(String grup) {
        this.grup = grup;
    }

    private String grup;


    public String getTemporada() {
        return temporada;
    }
    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }
    public Boolean getFavorit() {
        return isFavorit;
    }

    public void setFavorit(Boolean favorit) {
        isFavorit = favorit;
    }

    private Boolean isFavorit;

    public String getNomEquip() {
        return nomEquip;
    }

    public void setNomEquip(String nomEquip) {
        this.nomEquip = nomEquip;
    }

    public String getDivisio() {
        return divisio;
    }

    public void setDivisio(String divisio) {
        this.divisio = divisio;
    }

    public Calendar getDataPartit() {
        return dataPartit;
    }

    public void setDataPartit(Calendar dataPartit) {
        this.dataPartit = dataPartit;
    }
}
