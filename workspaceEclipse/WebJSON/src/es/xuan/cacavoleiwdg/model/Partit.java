package es.xuan.cacavoleiwdg.model;

import java.io.Serializable;
import java.util.Date;

import es.xuan.cacavoleiwdg.varis.Utils;

public class Partit implements Serializable, Comparable<Partit> {
	private static final long serialVersionUID = 1L;
	//
	private int ordre;
	private int index;
	private int idTorneig;
	private String nomEquip;
	private String divisio;
	private String fase;
	private String grup;
	private String categoria;
	private int jornada;
	private Date dataPartit;
	private String equipLocal;
	private String equipVisitant;
	private Pavello pavello;
	private Arbitres arbitres;
	private String resultatSets;
	private int resultatLocal;
	private int resultatVisitant;
	private String classificacio;
	//
	private static final String CTE_SEPARADOR_CSV = ";";
	
	public Partit(int pContador, int pIndice, int pIdTorneig, String strEquip) {
		ordre = pContador;
		index = pIndice;
		idTorneig = pIdTorneig;
		/*
		 * CVB BAR�A      </div></td>     <td  height="30">        <div align="left">CV SANT BOI      </div></td>      <td>17/03/2018      </td>	<td>16:00</td><td><a href="javascript:popUp('mapa.asp?Campo=11')">INEF Barcelona</a> (Barcelona)	</td>	     <td>      </td>	     <td>      </td>	  </tr>
		 */
		nomEquip = strEquip;
	}
	
	public Partit() {
	}

	public Date getDataPartit() {
		return dataPartit;
	}

	public void setDataPartit(Date dataPartit) {
		this.dataPartit = dataPartit;
	}

	public String getResultatSets() {
		return resultatSets;
	}

	public void setResultatSets(String resultatSets) {
		this.resultatSets = resultatSets;
	}

	public int getResultatLocal() {
		return resultatLocal;
	}

	public void setResultatLocal(int resultatLocal) {
		this.resultatLocal = resultatLocal;
	}

	public int getResultatVisitant() {
		return resultatVisitant;
	}

	public void setResultatVisitant(int resultatVisitant) {
		this.resultatVisitant = resultatVisitant;
	}

	public String getNomEquip() {
		return nomEquip;
	}

	public void setNomEquip(String nomEquip) {
		this.nomEquip = nomEquip;
	}

	public int getIdTorneig() {
		return idTorneig;
	}

	public void setIdTorneig(int idTorneig) {
		this.idTorneig = idTorneig;
	}

	public int getOrdre() {
		return ordre;
	}


	public void setOrdre(int ordre) {
		this.ordre = ordre;
	}


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	public String getDivisio() {
		return divisio;
	}


	public void setDivisio(String divisio) {
		this.divisio = divisio;
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


	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
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


	public Pavello getPavello() {
		return pavello;
	}


	public void setPavello(Pavello pavello) {
		this.pavello = pavello;
	}


	public Arbitres getArbitres() {
		return arbitres;
	}


	public void setArbitres(Arbitres arbitres) {
		this.arbitres = arbitres;
	}

	public String getClassificacio() {
		return classificacio;
	}


	public void setClassificacio(String classificacio) {
		this.classificacio = classificacio;
	}


	@Override
	public int compareTo(Partit pPartit) {
		int iComp = ordre - pPartit.getOrdre();
		if (iComp == 0) {
			iComp = index - pPartit.getIndex();
		}
		return iComp;
	}

	public String toString() {
		String str = "";
		str += idTorneig + CTE_SEPARADOR_CSV + 
			divisio + CTE_SEPARADOR_CSV + 
			categoria + CTE_SEPARADOR_CSV + 
			fase + CTE_SEPARADOR_CSV + 
			grup + CTE_SEPARADOR_CSV + 
			jornada + CTE_SEPARADOR_CSV + 
			Utils.data2StringRed(dataPartit) + CTE_SEPARADOR_CSV +
			Utils.data2StringHora(dataPartit) + CTE_SEPARADOR_CSV +
			equipLocal + CTE_SEPARADOR_CSV + 
			resultatLocal + CTE_SEPARADOR_CSV + 
			equipVisitant + CTE_SEPARADOR_CSV + 
			resultatVisitant + CTE_SEPARADOR_CSV + 
			resultatSets + CTE_SEPARADOR_CSV + 
			arbitres + CTE_SEPARADOR_CSV + 
			pavello.getNom() + CTE_SEPARADOR_CSV + 
			pavello.getAdreca() + CTE_SEPARADOR_CSV + 
			pavello.getUrl() + CTE_SEPARADOR_CSV +
			classificacio;
		return str;
	}
}
