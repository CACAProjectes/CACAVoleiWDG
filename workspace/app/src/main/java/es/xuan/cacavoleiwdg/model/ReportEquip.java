package es.xuan.cacavoleiwdg.model;

import java.io.Serializable;
import java.util.Date;

import es.xuan.cacavoleiwdg.varis.Utils;

public class ReportEquip implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String CTE_SEPARADOR_CSV = ";";
	public static final String CTE_CAPCALERA = "ID_TORNEIG;DIVISI�;CATEGORIA;FASE;GRUP;JORNADA;JORN_DATA_INICI;DATA_PARTIT;HORA_PARTIT;EQUIP_LOCAL;RESULTAT_LOCAL;EQUIP_VISITANT;RESULTAT_VISITANT;SETS;ARBITRES;PAVELL�;PROX_JORNADA;PROX_DATA_PARTIT;PROX_HORA_PARTIT;PROX_EQUIP_LOCAL;PROX_EQUIP_VISITANT;PROX_ARBITRES;PROX_PAVELL�;PROX_ADRE�A;PROX_UBICACI�;CLASSIFICACI�";

	private int idTorneig;
	private String divisio;
	private String categoria;
	private String fase;
	private String grup;
	private int jornada;
	private Date data;
	private String equipLocal;
	private int equipLocalResultat;
	private String equipVisitant;
	private int equipVisitantResultat;
	private String sets;
	private String arbitres;
	private String pavello;
	private String adrecaPavello;
	private int jornadaProx;
	private Date dataJornadaIni;
	private Date dataJornadaFi;
	private Date dataProx;
	private String equipLocalProx;
	private String equipVisitantProx;
	private String arbitresProx;
	private String pavelloProx;
	private String adrecaPavelloProx;
	private String ubicacioProx;				
	private String clasificacio;

	public Date getDataJornadaIni() {
		return dataJornadaIni;
	}

	public void setDataJornadaIni(Date dataJornadaIni) {
		this.dataJornadaIni = dataJornadaIni;
	}

	public Date getDataJornadaFi() {
		return dataJornadaFi;
	}

	public void setDataJornadaFi(Date dataJornadaFin) {
		this.dataJornadaFi = dataJornadaFin;
	}

	public int getEquipLocalResultat() {
		return equipLocalResultat;
	}

	public void setEquipLocalResultat(int equipLocalResultat) {
		this.equipLocalResultat = equipLocalResultat;
	}

	public int getEquipVisitantResultat() {
		return equipVisitantResultat;
	}

	public void setEquipVisitantResultat(int equipVisitantResultat) {
		this.equipVisitantResultat = equipVisitantResultat;
	}

	public int getIdTorneig() {
		return idTorneig;
	}

	public void setIdTorneig(int idTorneig) {
		this.idTorneig = idTorneig;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getDataProx() {
		return dataProx;
	}

	public void setDataProx(Date dataProx) {
		this.dataProx = dataProx;
	}

	public ReportEquip() {		
	}

	public String getDivisio() {
		return divisio;
	}

	public void setDivisio(String divisio) {
		this.divisio = divisio;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public String getGrup() {
		return grup;
	}

	public void setGrup(String grup) {
		this.grup = grup;
	}

	public int getJornada() {
		return jornada;
	}

	public void setJornada(int jornada) {
		this.jornada = jornada;
	}

	public String getEquipLocal() {
		return equipLocal;
	}

	public void setEquipLocal(String equipLocal) {
		this.equipLocal = equipLocal;
	}

	public String getEquipVisitant() {
		return equipVisitant;
	}

	public void setEquipVisitant(String equipVisitant) {
		this.equipVisitant = equipVisitant;
	}

	public String getSets() {
		return sets;
	}

	public void setSets(String sets) {
		this.sets = sets;
	}

	public String getArbitres() {
		return arbitres;
	}

	public void setArbitres(String arbitres) {
		this.arbitres = arbitres;
	}

	public String getPavello() {
		return pavello;
	}

	public void setPavello(String pavello) {
		this.pavello = pavello;
	}

	public String getAdrecaPavello() {
		return adrecaPavello;
	}

	public void setAdrecaPavello(String adrecaPavello) {
		this.adrecaPavello = adrecaPavello;
	}

	public int getJornadaProx() {
		return jornadaProx;
	}

	public void setJornadaProx(int jornadaProx) {
		this.jornadaProx = jornadaProx;
	}

	public String getEquipLocalProx() {
		return equipLocalProx;
	}

	public void setEquipLocalProx(String equipLocalProx) {
		this.equipLocalProx = equipLocalProx;
	}

	public String getEquipVisitantProx() {
		return equipVisitantProx;
	}

	public void setEquipVisitantProx(String equipVisitantProx) {
		this.equipVisitantProx = equipVisitantProx;
	}

	public String getArbitresProx() {
		return arbitresProx;
	}

	public void setArbitresProx(String arbitresProx) {
		this.arbitresProx = arbitresProx;
	}

	public String getPavelloProx() {
		return pavelloProx;
	}

	public void setPavelloProx(String pavelloProx) {
		this.pavelloProx = pavelloProx;
	}

	public String getAdrecaPavelloProx() {
		return adrecaPavelloProx;
	}

	public void setAdrecaPavelloProx(String adrecaPavelloProx) {
		this.adrecaPavelloProx = adrecaPavelloProx;
	}

	public String getUbicacioProx() {
		return ubicacioProx;
	}

	public void setUbicacioProx(String ubicacioProx) {
		this.ubicacioProx = ubicacioProx;
	}

	public String getClasificacio() {
		return clasificacio;
	}

	public void setClasificacio(String clasificacio) {
		this.clasificacio = clasificacio;
	}

	@Override
	public String toString() {
		String str = "";
		str += idTorneig + CTE_SEPARADOR_CSV + 
			divisio + CTE_SEPARADOR_CSV + 
			categoria + CTE_SEPARADOR_CSV + 
			fase + CTE_SEPARADOR_CSV + 
			grup + CTE_SEPARADOR_CSV + 
			jornada + CTE_SEPARADOR_CSV + 
			Utils.data2StringRed(dataJornadaIni) + CTE_SEPARADOR_CSV +
			Utils.data2StringRed(dataJornadaFi) + CTE_SEPARADOR_CSV + 
			Utils.data2StringRed(data) + CTE_SEPARADOR_CSV + 
			Utils.data2StringHora(data) + CTE_SEPARADOR_CSV + 
			equipLocal + CTE_SEPARADOR_CSV + 
			equipLocalResultat + CTE_SEPARADOR_CSV + 
			equipVisitant + CTE_SEPARADOR_CSV + 
			equipVisitantResultat + CTE_SEPARADOR_CSV + 
			sets + CTE_SEPARADOR_CSV + 
			arbitres + CTE_SEPARADOR_CSV + 
			pavello + CTE_SEPARADOR_CSV + 
			//adrecaPavello + CTE_SEPARADOR_CSV + 
			jornadaProx + CTE_SEPARADOR_CSV + 
			Utils.data2StringRed(dataProx) + CTE_SEPARADOR_CSV + 
			Utils.data2StringHora(dataProx) + CTE_SEPARADOR_CSV + 
			equipLocalProx + CTE_SEPARADOR_CSV +
			equipVisitantProx + CTE_SEPARADOR_CSV +
			arbitresProx + CTE_SEPARADOR_CSV +
			pavelloProx + CTE_SEPARADOR_CSV +
			adrecaPavelloProx + CTE_SEPARADOR_CSV + 
			ubicacioProx + CTE_SEPARADOR_CSV + 				
			clasificacio;
		return str.replaceAll("null", "");
	}
}
