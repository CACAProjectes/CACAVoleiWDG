package es.xuan.cacavoleiwdg.model;

import java.io.Serializable;

public class Arbitre implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String rol;
	private String nombre;
	
	public Arbitre(String strArbitro) {
		String[] strAux = strArbitro.split(":");
		if (strAux.length > 1) {
			rol = strAux[0].trim().replace("- ", "");
			nombre = strAux[1].trim();
		}
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return rol + ":" +
				nombre;		
	}

}
