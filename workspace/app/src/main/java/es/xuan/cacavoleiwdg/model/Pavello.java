package es.xuan.cacavoleiwdg.model;

import java.io.Serializable;

public class Pavello implements Serializable {
	private static final long serialVersionUID = 1L;
	//
	private String nom;
	private String adreca;
	private String poblacio;
	private String provincia;
	private String telefon;
	private String fax;
	private String urlMaps;
	private String url;	

	public Pavello(String strUrl, String nomUbicacio) {
		setUrl(strUrl);
		setUrlMaps(strUrl);
		setNom(nomUbicacio);
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdreca() {
		return adreca;
	}

	public void setAdreca(String adreca) {
		this.adreca = adreca;
	}

	public String getPoblacio() {
		return poblacio;
	}

	public void setPoblacio(String poblacio) {
		this.poblacio = poblacio;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getUrlMaps() {
		return urlMaps;
	}
	public void setUrlMaps(String urlMaps) {
		this.urlMaps = urlMaps;
	}
	@Override
	public String toString() {
		return nom + ";" +
				poblacio + ";" +
				provincia + ";" +
				telefon + ";" +
				fax + ";" +
				urlMaps;
	}
}
