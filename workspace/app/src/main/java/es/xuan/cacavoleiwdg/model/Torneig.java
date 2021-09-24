package es.xuan.cacavoleiwdg.model;

import java.io.Serializable;

public class Torneig implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*
	  	2� DIVISI� CADET FEMENINA 
		2� CADET FEMENI 
		2� FASE 
		Grupo: .Ascens 2 
		LIGA 2 Vueltas
	 */
	
	private int idTorneig;
	private String nomDivisio;
	private String nomCategoria;
	private String nomFase;
	private String nomGrup;
	private String nomTipus;	
	private Partits partitsTorneig = null;
	private String classificacio = "";
	private String urlClassificacio = "";
	private String urlPrincipal = "";

	public Torneig() {
		super();
		partitsTorneig = new Partits();
	}
	public Partits getPartitsTorneig() {
		return partitsTorneig;
	}

	public String getUrlClassificacio() {
		return urlClassificacio;
	}

	public void setUrlClassificacio(String urlClassificacio) {
		this.urlClassificacio = urlClassificacio;
	}

	public String getUrlPrincipal() {
		return urlPrincipal;
	}

	public void setUrlPrincipal(String urlPrincipal) {
		this.urlPrincipal = urlPrincipal;
	}

	public void setPartitsTorneig(Partits partitsTorneig) {
		this.partitsTorneig = partitsTorneig;
	}
	public String getClassificacio() {
		return classificacio;
	}
	public void setClassificacio(String classificacio) {
		this.classificacio = classificacio;
	}
	public int getIdTorneig() {
		return idTorneig;
	}
	public void setIdTorneig(int idTorneig) {
		this.idTorneig = idTorneig;
	}
	public String getNomDivisio() {
		return nomDivisio;
	}
	public void setNomDivisio(String nomDivisio) {
		this.nomDivisio = nomDivisio;
	}
	public String getNomCategoria() {
		return nomCategoria;
	}
	public void setNomCategoria(String nomCategoria) {
		this.nomCategoria = nomCategoria;
	}
	public String getNomFase() {
		return nomFase;
	}
	public void setNomFase(String nomFase) {
		this.nomFase = nomFase;
	}
	public String getNomGrup() {
		return nomGrup;
	}
	public void setNomGrup(String nomGrup) {
		this.nomGrup = nomGrup;
	}
	public String getNomTipus() {
		return nomTipus;
	}
	public void setNomTipus(String nomTipus) {
		this.nomTipus = nomTipus;
	}
}
