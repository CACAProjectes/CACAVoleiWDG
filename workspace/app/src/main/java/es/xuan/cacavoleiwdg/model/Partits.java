package es.xuan.cacavoleiwdg.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Partits implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Partit> partitsJugats = null;
	private ArrayList<Partit> partitsProxims = null;
	private ArrayList<Partit> partitsResultats = null;
	private int numJornada;
	private String dataJornada;
	
	public long numPartits() {
		if (partitsJugats != null)
			return partitsJugats.size();
		return 0;
	}

	public int getNumJornada() {
		return numJornada;
	}

	public void setNumJornada(int numJornada) {
		this.numJornada = numJornada;
	}

	public String getDataJornada() {
		return dataJornada;
	}

	public void setDataJornada(String dataJornada) {
		this.dataJornada = dataJornada;
	}

	public ArrayList<Partit> getPartitsResultats() {
		return partitsResultats;
	}
	public void setPartitsResultats(ArrayList<Partit> partitsResultats) {
		this.partitsResultats = partitsResultats;
	}	
	public ArrayList<Partit> getPartitsJugats() {
		return partitsJugats;
	}
	public void setPartitsJugats(ArrayList<Partit> partitsJugats) {
		this.partitsJugats = partitsJugats;
	}
	public ArrayList<Partit> getPartitsProxims() {
		return partitsProxims;
	}
	public void setPartitsProxims(ArrayList<Partit> partitsProxims) {
		this.partitsProxims = partitsProxims;
	}
	public void addProx(Partit pPartit) {
		if (partitsProxims == null)
			partitsProxims = new ArrayList<Partit>();
		partitsProxims.add(pPartit);
	}
	public void addJugats(Partit pPartit) {
		if (partitsJugats == null)
			partitsJugats = new ArrayList<Partit>();
		partitsJugats.add(pPartit);
	}
	public void addResultats(Partit pPartit) {
		if (partitsResultats == null)
			partitsResultats = new ArrayList<Partit>();
		partitsResultats.add(pPartit);
	}
	
}
