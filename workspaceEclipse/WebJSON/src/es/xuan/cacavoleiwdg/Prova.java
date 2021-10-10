/**
 * 
 */
package es.xuan.cacavoleiwdg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONObject;

import es.xuan.cacavoleiwdg.model.Arbitre;
import es.xuan.cacavoleiwdg.model.Arbitres;
import es.xuan.cacavoleiwdg.model.Partit;
import es.xuan.cacavoleiwdg.model.Partits;
import es.xuan.cacavoleiwdg.model.Pavello;
import es.xuan.cacavoleiwdg.model.Torneig;
import es.xuan.cacavoleiwdg.varis.Utils;

/**
 * @author jcamposp			
 *
 */
public class Prova implements Serializable {
	private static final long serialVersionUID = 1L;
	//
	private final static String CTE_URL_FCVB_COMPETICIONS = "https://fcvolei.cat/apicompeticiones.php?slug=2a-divisio-infantil-femenina-2122";
	private final static String CTE_URL_UBICACIO = "https://fcvolei.cat/instalaciones/?instalacion=";
	private final static String CTE_PROXY_IP = "10.126.132.10";
	private final static String CTE_PROXY_PORT = "8080";
	private static final String CTE_GRUP_E = "GRUP E";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = getContingutURL(CTE_URL_FCVB_COMPETICIONS);
		System.out.println(str.length());
		Torneig torneig = new Torneig();
		parserJson(torneig, str);
		System.out.println(torneig);
	}


	private static void parserJson(Torneig pTorneig, String pText) {
		JSONObject obj = new JSONObject(pText);
        ///////////////////////////////////////
        //	Jornada
        Partits partitsTorneig = new Partits();
        ///////////////////////////////////////
        //	Data partit
        Partit pPartit = null;
        //
		JSONArray arr = obj.getJSONArray("grupos"); // 
		for (int i = 0; i < arr.length(); i++)
		{
		    String nomGrup = arr.getJSONObject(i).getString("nombreGrupo");
		    if (nomGrup.equalsIgnoreCase(CTE_GRUP_E)) {
		    	String jornadaActual = arr.getJSONObject(i).getString("jornadaActual");
		    	int iJornadaActual = Utils.stringToInt(jornadaActual);
		    	//String dataJornadaActual = "3"; //arr.getJSONObject(i).getString("fecha");
		        partitsTorneig.setNumJornada(Utils.stringToInt(jornadaActual));
		        
		        //partitsTorneig.setDataJornada(strSplit[1].trim());
		        pTorneig.setPartitsTorneig(partitsTorneig);
		    	System.out.println("Jornada actual: " + jornadaActual);
		        //
			    JSONArray arrClassificacio = arr.getJSONObject(i).getJSONArray("clasificacion"); //
			    String strClassificacio = "";
			    for (int h = 0; h < arrClassificacio.length(); h++) {
			    	strClassificacio += arrClassificacio.getJSONObject(h).getInt("posicion") + ",";
			    	strClassificacio += arrClassificacio.getJSONObject(h).getString("equipo") + ",";
			    	strClassificacio += arrClassificacio.getJSONObject(h).getString("hkpartidosJugados") + ",";
			    	strClassificacio += arrClassificacio.getJSONObject(h).getString("vbpartidosGanados3") + ",";
			    	strClassificacio += arrClassificacio.getJSONObject(h).getString("vbpartidosGanados2") + ",";
			    	strClassificacio += arrClassificacio.getJSONObject(h).getString("vbpartidosPerdidos1") + ",";
			    	strClassificacio += arrClassificacio.getJSONObject(h).getString("vbpartidosPerdidos0") + ",";
			    	strClassificacio += arrClassificacio.getJSONObject(h).getString("puntos") + ",";
			    	strClassificacio += "/";
			    }
		    	pTorneig.setClassificacio(strClassificacio);
		    	//
			    JSONArray arrPartidos = arr.getJSONObject(i).getJSONArray("partidos"); // 
			    for (int j = 0; j < arrPartidos.length(); j++) {
			    	String numJornada = arrPartidos.getJSONObject(j).getString("jornada");
			    	int iJornada = Utils.stringToInt(numJornada);
			    	if (iJornada >= iJornadaActual - 1 && 
			    		iJornada <= iJornadaActual + 1) {
			    		String strDataPartit = arrPartidos.getJSONObject(j).getString("fecha");
			    		String strUbicacio = arrPartidos.getJSONObject(j).getString("idInstalacion");
			    		String strNomPavello = arrPartidos.getJSONObject(j).getString("instalacion");
			    		String strEquipLocal = arrPartidos.getJSONObject(j).getString("local");
			    		String strEquipVisitant = arrPartidos.getJSONObject(j).getString("visitante");
			    		String strResLocal = arrPartidos.getJSONObject(j).getString("resultadoLocal");
			    		String strResVisitant = arrPartidos.getJSONObject(j).getString("resultadoVisitante");
			    		// Arbitres
			    		JSONArray arrArbitres = arrPartidos.getJSONObject(i).getJSONArray("arbitros");
			    		Arbitres arbitres = new Arbitres();
			    		for (int k = 0; k < arrArbitres.length(); k++) {
			    			String strArbitreNom = arrArbitres.getJSONObject(k).getString("nombre");
			    			String strArbitreCognoms = arrArbitres.getJSONObject(k).getString("apellido1");
			    			String strArbitreRol = arrArbitres.getJSONObject(k).getString("funcion");
			    			Arbitre arbitre = new Arbitre(strArbitreRol + ":" + strArbitreNom + strArbitreCognoms);
				    		arbitres.add(arbitre);			    			
			    		}			    		
			    		// Partit
			    		pPartit = new Partit();
			            pPartit.setJornada(Utils.stringToInt(numJornada));
			            pPartit.setDataPartit(Utils.string2DataRed(strDataPartit));
			            pPartit.setEquipLocal(strEquipLocal);
			            pPartit.setEquipVisitant(strEquipVisitant);
			            pPartit.setResultatLocal(Utils.stringToInt(strResLocal));
			            pPartit.setResultatVisitant(Utils.stringToInt(strResVisitant));
			            pPartit.setResultatSets(getResultatsSets(arrPartidos.getJSONObject(j)));	// 25-16/27-25/25-20
			            pPartit.setPavello(obtenirAdrecesURL(CTE_URL_UBICACIO + strUbicacio, strNomPavello));
			            pPartit.setArbitres(arbitres);	
			            if (iJornada == iJornadaActual - 1) {
				            // Anterior
				            partitsTorneig.addJugats(pPartit);
			            }
			            if (iJornada == iJornadaActual) {
				            // Actual
				            partitsTorneig.addResultats(pPartit);
			            }
			            if (iJornada == iJornadaActual + 1) {
				            // Seg�ent
				            partitsTorneig.addProx(pPartit);
				            break;
			            }
			    	}
			    }
			    break;
		    }
		}
	}

    private static String getResultatsSets(JSONObject jsonObject) {
    	// 25-16/27-25/25-20
    	String strRes = 
	    	jsonObject.getString("set1Local") + "-" +
	    	jsonObject.getString("set1Visitante") + "/" +
	    	jsonObject.getString("set2Local") + "-" +
	    	jsonObject.getString("set2Visitante") + "/" +
	    	jsonObject.getString("set3Local") + "-" +
	    	jsonObject.getString("set3Visitante");
	    	if (!jsonObject.getString("set4Local").equals("")) {
	    		strRes += "/" +
	    			jsonObject.getString("set4Local") + "-" +
	    			jsonObject.getString("set4Visitante");
		    	}
	    	if (!jsonObject.getString("set5Local").equals("")) {
	    		strRes += "/" +
		    		jsonObject.getString("set5Local") + "-" +
		    		jsonObject.getString("set5Visitante");
	    	}
		return strRes;
	}


	private static Pavello obtenirAdrecesURL(String pUrlUbicacio, String pNom) {
        Pavello pavello = new Pavello(pUrlUbicacio, pNom);
        return pavello;
    }


	//@SuppressWarnings("null")
	private static String getContingutURL(String pStrUrl) {
		URLConnection yc = null;
	    InputStreamReader isr = null;
		StringBuilder lineStr = new StringBuilder();
		String strAux = "";
		try {
			yc = new URL(pStrUrl).openConnection();
			//StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			//StrictMode.setThreadPolicy(policy);
			isr = new InputStreamReader(yc.getInputStream(), StandardCharsets.ISO_8859_1);
		} catch (Exception ex) {	
			// Hi ha PROXY
		}	
		if (isr == null) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(CTE_PROXY_IP, Integer.parseInt(CTE_PROXY_PORT)));
		    try {
				yc = new URL(pStrUrl).openConnection(proxy);
				isr = new InputStreamReader(yc.getInputStream(), StandardCharsets.ISO_8859_1);
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
		}
		try {
			//
			BufferedReader in = new BufferedReader(isr);
		    while((strAux = in.readLine()) != null) {
		    	lineStr.append(strAux);
		    }
		    in.close();
		    isr.close();
		}
		catch (Exception ex) {
			System.err.println("Error -getContingutURL.1-: " + ex);
		}
		return lineStr.toString();
	}
}
