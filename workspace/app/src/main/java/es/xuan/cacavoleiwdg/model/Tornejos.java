package es.xuan.cacavoleiwdg.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Tornejos implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Torneig> llista = new ArrayList<Torneig>();

	public ArrayList<Torneig> getLlista() {
		return llista;
	}

	public void setLlista(ArrayList<Torneig> llista) {
		this.llista = llista;
	}

	public void add(Torneig pTorneig) {
		if (llista == null)
			llista = new ArrayList<Torneig>();
		llista.add(pTorneig);
		
	}

}
