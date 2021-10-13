package es.xuan.cacavoleiwdg.migracio;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import es.xuan.cacavoleiwdg.model.Partit;
import es.xuan.cacavoleiwdg.model.Partits;
import es.xuan.cacavoleiwdg.model.Torneig;
import es.xuan.cacavoleiwdg.varis.Utils;

public class VBMigracio {
	private final static String CTE_PROXY_IP = "10.126.132.10";
	private final static String CTE_PROXY_PORT = "8080";
	private static final String CTE_SEPARADOR_CSV = ";";
	private static final String CTE_CANVI_LINIA = System.lineSeparator();
	private static final String CTE_EXT_FILE_CSV = ".csv";
	//private static final String CTE_CAPCALERA = "ID_TORNEIG;DIVISI�;CATEGORIA;FASE;GRUP;JORNADA;JORN_DATA_INICI;DATA_PARTIT;HORA_PARTIT;EQUIP_LOCAL;RESULTAT_LOCAL;EQUIP_VISITANT;RESULTAT_VISITANT;SETS;ARBITRES;PAVELL�;ADRE�A_PAVELL�;PROVINCIA_PAVELL�;TEL�FON_PAVELL�;FAX_PAVELL�;UBICACI�;CLASSIFICACI�";
	//
	private BufferedWriter m_printWriter = null;

    public VBMigracio() {
    }

    public StringBuilder convertTorneig2Str(Torneig torneig) {
		//	ID_TORNEIG;DIVISI�;CATEGORIA;FASE;GRUP;JORNADA;JORN_DATA_INICI;DATA_PARTIT;HORA_PARTIT;EQUIP_LOCAL;RESULTAT_LOCAL;EQUIP_VISITANT;RESULTAT_VISITANT;SETS;ARBITRES;PAVELL�;PROX_JORNADA;PROX_DATA_PARTIT;PROX_HORA_PARTIT;PROX_EQUIP_LOCAL;PROX_EQUIP_VISITANT;PROX_ARBITRES;PROX_PAVELL�;PROX_ADRE�A;PROX_UBICACI�;CLASSIFICACI�
		StringBuilder strTorneig = new StringBuilder("");
		for (int i=0;i<torneig.getPartitsTorneig().numPartits();i++) {	//
			// Regularitzar GRUP
			String strGrup = (torneig.getNomGrup() != null && !torneig.getNomGrup().equals("") ? Utils.parsearText(torneig.getNomGrup()) : Utils.parsearText(torneig.getPartitsTorneig().getPartitsJugats().get(i).getGrup()));
			if (strGrup.equals(""))
				strGrup = "1";	// GRUP �NIC
			strTorneig.append(
				(torneig.getIdTorneig() != 0 ? torneig.getIdTorneig() : torneig.getPartitsTorneig().getPartitsJugats().get(i).getIdTorneig())
				)
				.append(CTE_SEPARADOR_CSV)
				.append((torneig.getNomDivisio() != null && !torneig.getNomDivisio().equals("") ? torneig.getNomDivisio() : torneig.getPartitsTorneig().getPartitsJugats().get(i).getDivisio()))
				.append(CTE_SEPARADOR_CSV)
				.append((torneig.getNomCategoria() != null && !torneig.getNomCategoria().equals("") ? torneig.getNomCategoria() : torneig.getPartitsTorneig().getPartitsJugats().get(i).getCategoria()))
				.append(CTE_SEPARADOR_CSV)
				.append((torneig.getNomFase() != null && !torneig.getNomFase().equals("") ? torneig.getNomFase() : torneig.getPartitsTorneig().getPartitsJugats().get(i).getFase()))
				.append(CTE_SEPARADOR_CSV)
				.append(strGrup)
				.append(CTE_SEPARADOR_CSV)
				.append(torneig.getPartitsTorneig().getPartitsJugats().get(i).getJornada())
				.append(CTE_SEPARADOR_CSV)
				.append(torneig.getPartitsTorneig().getDataJornada())
				.append(CTE_SEPARADOR_CSV)
				.append(Utils.data2StringRed(torneig.getPartitsTorneig().getPartitsJugats().get(i).getDataPartit()))
				.append(CTE_SEPARADOR_CSV)
				.append(Utils.data2StringHora(torneig.getPartitsTorneig().getPartitsJugats().get(i).getDataPartit()))
				.append(CTE_SEPARADOR_CSV);
			//
			int iOrdreRes = cercarOrdreEquips(torneig.getPartitsTorneig().getPartitsJugats().get(i).getEquipLocal(),
					torneig.getPartitsTorneig().getPartitsJugats().get(i).getEquipVisitant(),
					torneig.getPartitsTorneig().getPartitsResultats());
			if (torneig.getPartitsTorneig().getPartitsResultats() != null && iOrdreRes > -1) {
				// N'hi ha resultats. Partits jugats
				strTorneig
				.append(Utils.parsearText(torneig.getPartitsTorneig().getPartitsJugats().get(i).getEquipLocal()))
				.append(CTE_SEPARADOR_CSV)
				.append(torneig.getPartitsTorneig().getPartitsResultats().get(iOrdreRes).getResultatLocal())
				.append(CTE_SEPARADOR_CSV)
				.append(Utils.parsearText(torneig.getPartitsTorneig().getPartitsJugats().get(i).getEquipVisitant()))
				.append(CTE_SEPARADOR_CSV)
				.append(torneig.getPartitsTorneig().getPartitsResultats().get(iOrdreRes).getResultatVisitant())
				.append(CTE_SEPARADOR_CSV)
				.append(ckeck4Null(torneig.getPartitsTorneig().getPartitsResultats().get(iOrdreRes).getResultatSets()))
				.append(CTE_SEPARADOR_CSV);
			}
			else {
				strTorneig
				.append(Utils.parsearText(torneig.getPartitsTorneig().getPartitsJugats().get(i).getEquipLocal()))
				.append(CTE_SEPARADOR_CSV)
				.append(torneig.getPartitsTorneig().getPartitsJugats().get(i).getResultatLocal())
				.append(CTE_SEPARADOR_CSV)
				.append(Utils.parsearText(torneig.getPartitsTorneig().getPartitsJugats().get(i).getEquipVisitant()))
				.append(CTE_SEPARADOR_CSV)
				.append(torneig.getPartitsTorneig().getPartitsJugats().get(i).getResultatVisitant())
				.append(CTE_SEPARADOR_CSV)
				.append(ckeck4Null(torneig.getPartitsTorneig().getPartitsJugats().get(i).getResultatSets()))
				.append(CTE_SEPARADOR_CSV);
			}
			if (torneig.getPartitsTorneig().getPartitsJugats().get(i).getArbitres() != null) {
				strTorneig
				.append(Utils.parsearText(torneig.getPartitsTorneig().getPartitsJugats().get(i).getArbitres().toString()));
				}
			strTorneig
				.append(CTE_SEPARADOR_CSV);
			if (torneig.getPartitsTorneig().getPartitsJugats().get(i).getPavello() != null &&
					!torneig.getPartitsTorneig().getPartitsJugats().get(i).getPavello().getNom().equals("")) {
				strTorneig
				.append(Utils.parsearText(torneig.getPartitsTorneig().getPartitsJugats().get(i).getPavello().toString()))
				.append(CTE_SEPARADOR_CSV);	
			}
			else {
				strTorneig.append(CTE_SEPARADOR_CSV)
				.append(CTE_SEPARADOR_CSV)
				.append(CTE_SEPARADOR_CSV)
				.append(CTE_SEPARADOR_CSV)
				.append(CTE_SEPARADOR_CSV)
				.append(CTE_SEPARADOR_CSV);	
			}
			//strTorneig.append(parsearTextClassificacio(torneig.getClassificacio()))
			strTorneig.append(torneig.getClassificacio())
				.append(CTE_CANVI_LINIA)
			;
		}
		return strTorneig;
	}

	public StringBuilder convertTorneigSeguents2Str(Torneig torneig) {
		//	ID_TORNEIG;DIVISI�;CATEGORIA;FASE;GRUP;JORNADA;JORN_DATA_INICI;DATA_PARTIT;HORA_PARTIT;EQUIP_LOCAL;RESULTAT_LOCAL;EQUIP_VISITANT;RESULTAT_VISITANT;SETS;ARBITRES;PAVELL�;PROX_JORNADA;PROX_DATA_PARTIT;PROX_HORA_PARTIT;PROX_EQUIP_LOCAL;PROX_EQUIP_VISITANT;PROX_ARBITRES;PROX_PAVELL�;PROX_ADRE�A;PROX_UBICACI�;CLASSIFICACI�
		StringBuilder strTorneig = new StringBuilder("");
		if (torneig.getPartitsTorneig() != null &&
		        torneig.getPartitsTorneig().getPartitsProxims() != null) {
            for (int j = 0; j < torneig.getPartitsTorneig().getPartitsProxims().size(); j++) {    //
                // Regularitzar GRUP
                String strGrup = (torneig.getNomGrup() != null && !torneig.getNomGrup().equals("") ? Utils.parsearText(torneig.getNomGrup()) : Utils.parsearText(torneig.getPartitsTorneig().getPartitsJugats().get(0).getGrup()));
                if (strGrup.equals(""))
                    strGrup = "1";    // GRUP �NIC
                strTorneig
                        .append(
                                (torneig.getIdTorneig() != 0 ? torneig.getIdTorneig() : torneig.getPartitsTorneig().getPartitsJugats().get(0).getIdTorneig())
                        )
                        .append(CTE_SEPARADOR_CSV)
                        .append((torneig.getNomDivisio() != null && !torneig.getNomDivisio().equals("") ? torneig.getNomDivisio() : torneig.getPartitsTorneig().getPartitsJugats().get(0).getDivisio()))
                        .append(CTE_SEPARADOR_CSV)
                        .append((torneig.getNomCategoria() != null && !torneig.getNomCategoria().equals("") ? torneig.getNomCategoria() : torneig.getPartitsTorneig().getPartitsJugats().get(0).getCategoria()))
                        .append(CTE_SEPARADOR_CSV)
                        .append((torneig.getNomFase() != null && !torneig.getNomFase().equals("") ? torneig.getNomFase() : torneig.getPartitsTorneig().getPartitsJugats().get(0).getFase()))
                        .append(CTE_SEPARADOR_CSV)
                        .append(strGrup)
                        .append(CTE_SEPARADOR_CSV)
                        .append(torneig.getPartitsTorneig().getPartitsProxims().get(j).getJornada())
                        .append(CTE_SEPARADOR_CSV)
                        .append("")      // Data Jornada
                        .append(CTE_SEPARADOR_CSV)
                        .append(Utils.data2StringRed(torneig.getPartitsTorneig().getPartitsProxims().get(j).getDataPartit()))
                        .append(CTE_SEPARADOR_CSV)
                        .append(Utils.data2StringHora(torneig.getPartitsTorneig().getPartitsProxims().get(j).getDataPartit()))
                        .append(CTE_SEPARADOR_CSV);
                //	Resultats a Cero
                strTorneig
                        .append(Utils.parsearText(torneig.getPartitsTorneig().getPartitsProxims().get(j).getEquipLocal()))
                        .append(CTE_SEPARADOR_CSV)
                        .append("0")        // Resultat Local
                        .append(CTE_SEPARADOR_CSV)
                        .append(Utils.parsearText(torneig.getPartitsTorneig().getPartitsProxims().get(j).getEquipVisitant()))
                        .append(CTE_SEPARADOR_CSV)
                        .append("0")        // Resultat Visitant
                        .append(CTE_SEPARADOR_CSV)
                        .append("")         // Resultat Sets
                        .append(CTE_SEPARADOR_CSV);
			/*
			if (torneig.getPartitsTorneig().getPartitsJugats().get(i).getArbitres() != null) {
				strTorneig
						.append(Utils.parsearText(torneig.getPartitsTorneig().getPartitsJugats().get(i).getArbitres().toString()));
			}
			*/
                strTorneig
                        .append(CTE_SEPARADOR_CSV);
                if (torneig.getPartitsTorneig().getPartitsProxims().get(j).getPavello() != null &&
                        !torneig.getPartitsTorneig().getPartitsProxims().get(j).getPavello().getNom().equals("")) {
                    strTorneig
                            .append(Utils.parsearText(torneig.getPartitsTorneig().getPartitsProxims().get(j).getPavello().toString()))
                            .append(CTE_SEPARADOR_CSV);
                } else {
                    strTorneig.append(CTE_SEPARADOR_CSV)
                            .append(CTE_SEPARADOR_CSV)
                            .append(CTE_SEPARADOR_CSV)
                            .append(CTE_SEPARADOR_CSV)
                            .append(CTE_SEPARADOR_CSV)
                            .append(CTE_SEPARADOR_CSV);
                }
				//strTorneig.append(parsearTextClassificacio(torneig.getClassificacio()))
				strTorneig.append(torneig.getClassificacio())
                	.append(CTE_CANVI_LINIA)
                ;
            }
        }
		return strTorneig;
	}

	private int cercarOrdreEquips(String equipLocal, String equipVisitant, ArrayList<Partit> partitsResultats) {
		int iOrdre = 0;
		if (partitsResultats!=null) {
			for (Partit partit : partitsResultats) {
				if (partit.getEquipLocal().equals(equipLocal) &&
						partit.getEquipVisitant().equals(equipVisitant))
					return iOrdre;
				iOrdre++;
			}
		}
		return -1;
	}
	private static String parsearTextClassificacio(String pStrText) {
		if (pStrText.startsWith("<table"))
			return Utils.parsearText(pStrText);
		return "";
	}
	private static String ckeck4Null(String pStrText) {
		if (pStrText == null)
			return "";
		return pStrText;
	}

	public static String getContingutURL(String pStrUrl) {
		URLConnection yc = null;
	    InputStreamReader isr = null;
		StringBuilder lineStr = new StringBuilder();
		String strAux = "";
		try {
			yc = new URL(pStrUrl).openConnection();
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			isr = new InputStreamReader(yc.getInputStream(), StandardCharsets.ISO_8859_1);
		} catch (Exception ex) {
			// Hi ha PROXY
			ex.printStackTrace();
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
	/*
	public void afegirPartitsExcel(String pStrPath, String pStrContenido) {
		if (m_printWriter == null) {
		    String fileName = pStrPath + CTE_EXT_FILE_CSV;
		    File file = new File(fileName);
			try {
		        m_printWriter  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.ISO_8859_1));
		    } catch (IOException ioex) {
		        ioex.printStackTrace();
		    } 
		}
        // Escriure partits
		try {
			if (m_printWriter != null) {
				m_printWriter.write(pStrContenido);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tancarExcel() {
        if (m_printWriter != null) {
            try {
            	m_printWriter.flush();
            	m_printWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	*/
}
