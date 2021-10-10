package es.xuan.cacavoleiwdg.model;

import java.io.Serializable;
import java.util.ArrayList;

import es.xuan.cacavoleiwdg.varis.Constants;

public class Arbitres implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String CTE_SEPARADOR_ARB = "/";
	
	private ArrayList<Arbitre> llista = null;

	public Arbitres(String pStrArbitres) {
		// 1er.:MONICA TORRENTS REYNES/Anotador:ANNA YUE DE LA RUA CHICO
		llista = new ArrayList<Arbitre>();
		if (pStrArbitres != null && !pStrArbitres.equals("")) {
			String strArb[] = pStrArbitres.split(Constants.CTE_SEPARADOR_ARBITRES);
			for (String strAux : strArb) {
				Arbitre arbitre = new Arbitre(strAux);
				llista.add(arbitre);
			}
		}
	}

    public Arbitres() {
    }

    public ArrayList<Arbitre> getLlista() {
		return llista;
	}

	public void setLlista(ArrayList<Arbitre> llista) {
		this.llista = llista;
	}

	public void add(Arbitre pArbitre) {
		if (llista == null)
			llista = new ArrayList<Arbitre>();
		llista.add(pArbitre);
		
	}
	
	@Override
	public String toString() {
		String strRes = "";
		if (llista != null) {
			for (Arbitre arb : llista) {
				if (!strRes.equals(""))
					strRes += CTE_SEPARADOR_ARB;
				strRes += arb.toString();
			}
		}
		return strRes;
	}	
}
