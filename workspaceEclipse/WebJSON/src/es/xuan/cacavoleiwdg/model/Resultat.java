package es.xuan.cacavoleiwdg.model;

import java.io.Serializable;

public class Resultat implements Serializable {
	private static final long serialVersionUID = 1L;	

	/*
	 * CV SANT BOI	3 - 0	CVB BAR�A BLAU	25-17/25-22/25-16
	 */
	private String texto = ""; 

	public Resultat(String pTexto) {
		texto = pTexto;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
}
