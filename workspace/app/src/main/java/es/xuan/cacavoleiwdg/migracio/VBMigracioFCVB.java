package es.xuan.cacavoleiwdg.migracio;

import android.util.Log;

import es.xuan.cacavoleiwdg.model.Arbitre;
import es.xuan.cacavoleiwdg.model.Arbitres;
import es.xuan.cacavoleiwdg.model.Partit;
import es.xuan.cacavoleiwdg.model.Partits;
import es.xuan.cacavoleiwdg.model.Pavello;
import es.xuan.cacavoleiwdg.model.Torneig;
import es.xuan.cacavoleiwdg.model.Tornejos;
import es.xuan.cacavoleiwdg.varis.Constants;
import es.xuan.cacavoleiwdg.varis.Utils;

public class VBMigracioFCVB extends VBMigracio {

    private final static String CTE_URL_FCVB = "http://competicio.fcvoleibol.cat/";
    private final static String CTE_URL_EQUIPS = "http://competicio.fcvoleibol.cat/calendaris.asp";
    private final static String CTE_URL_FCVB_COMPETICIONS = "http://competicio.fcvoleibol.cat/competiciones.asp";
    private final static String CTE_URL_FCVB_COMPETICIONS_TORNEIG = "?torneo=";
    private final static String CTE_URL_FCVB_COMPETICIONS_JORNADA = "&jornada=";
    private static final String CTE_SEPARADOR_CSV = ";";

    public VBMigracioFCVB() {
    }

    public Tornejos getTornejos() {
        // Obtenir el contingut de la pagina
        String strContingut = getContingutURL(CTE_URL_EQUIPS);
        Tornejos tornejos = new Tornejos();
        // Buscar la marca = "http://competicio.fcvoleibol.cat/competiciones.asp"
        /*
         * 		<a class="html-attribute-value html-external-link" target="_blank" href="
         * http://competicio.fcvoleibol.cat/competiciones.asp?v=18&amp;torneo=2726"
         *		rel="noreferrer noopener">competiciones.asp?v=18&amp;torneo=2726</a>
         */
        //String marcaIdTorneosIni = "http://competicio.fcvoleibol.cat/competiciones.asp";
        String marcaIdTorneosIni = "competiciones.asp?";
        String marcaIdTorneosFin = "\"";
        int ind1 = 0, ind2 = 0;
        // Cerca a partir de CAMPIONATS DE CATALUNYA - 1ª DIVISIÓ SÈNIOR MASCULINA
        //int ll1 = strContingut.indexOf("1ª DIVISIÓ SÈNIOR MASCULINA");
        int ll1 = strContingut.indexOf("SM-2");
        // Cerca la segona referència
        //ll1 = strContingut.indexOf("1ª DIVISIÓ SÈNIOR MASCULINA", ll1 + 1);
        ll1 = strContingut.indexOf("SM-2", ll1 + 1);
        strContingut = strContingut.substring(ll1);
        do {
            try {
                ind1 = strContingut.indexOf(marcaIdTorneosIni, ind1 + 1);
                if (ind1 < 0)
                    break;
                ind2 = strContingut.indexOf(marcaIdTorneosFin, ind1 + 1);
                tornejos.add(getIdTorneig(strContingut.substring(ind1, ind2)));
            } catch (Exception ex) {
                System.err.println(ex);
            }
        } while (true);
        return tornejos;
    }

    private static Torneig getIdTorneig(String pStrTorneig) {
        /*
         * competiciones.asp?v=18&torneo=2724
         */
        String marcaIdTorneosIni = "&torneo=";
        Torneig torneig = new Torneig();
        try {
            int ind = pStrTorneig.indexOf(marcaIdTorneosIni);
            torneig.setIdTorneig(Integer.parseInt(pStrTorneig.substring(ind + marcaIdTorneosIni.length())));
        } catch(Exception ex) {
            System.err.println(ex);
        }
        return torneig;
    }

    public void getResultatsTorneig(Torneig pTorneig, int pNumJornada) {
        try {
            int ll1 = 0, ll2 = 0, ll3 = 0;
            String strUrl = CTE_URL_FCVB_COMPETICIONS + CTE_URL_FCVB_COMPETICIONS_TORNEIG + pTorneig.getIdTorneig();
            if (pNumJornada > 0)
                strUrl += CTE_URL_FCVB_COMPETICIONS_JORNADA + pNumJornada;
            //
            String strContingut = getContingutURL(strUrl);
            /*
             * nombre_competicion
             */
            String marcaNomCompeticio = "nombre_competicion";
            ll1 = strContingut.indexOf(marcaNomCompeticio);
            ///////////////////////////////////////
            // Competició
            // <div id="nombre_competicion"><h2><strong>2ª DIVISIÓ JUVENIL FEMENINA </strong><br>2ª JUVENIL FEMENI  <br>2ª FASE <br> Grupo: .Ascens 2 <br>LIGA 2 Vueltas </h2></div>
            String marcaH2 = "</h2>";
            ll2 = strContingut.indexOf(marcaH2, ll1 + 1);
            parsearCompeticio(pTorneig, strContingut.substring(ll1, ll2));
            ///////////////////////////////////////
            //	Jornada
            // <div id="jornada_numero">JORNADA N&Uacute;MERO 3 <br> 18/02/2018 </div>
            Partits partitsTorneig = new Partits();
            String marcaDIV = "</div>";
            String marcaJornadaNumero = "<div id=\"jornada_numero\"";
            ll1 = strContingut.indexOf(marcaJornadaNumero);
            ll2 = strContingut.indexOf(marcaDIV, ll1 + 1);
            parsearJornada(partitsTorneig, strContingut.substring(ll1, ll2));
            ///////////////////////////////////////
            //	Data partit
            /*
             * <tr class="fondo_01">    <td height="0" width="26%" valign="top">      <div align="left">        <a href="equipos.asp?valor=208&x_equipo_id=1890" class="discreto">CV SANT BOI</a>    </div></td>    <td width="26%" valign="top" class="discreto"><div align="left">  <a href="equipos.asp?valor=197&x_equipo_id=2001" class="discreto">CEV L�HOSPITALET</a></div>     </td>      <td valign="top" align="center">10/03/2018</td>      <td valign="top">10:00 </td>      <td valign="top"><a href="javascript:popUp('mapa.asp?Campo=64')">Poliesportiu La Parellada</a>  </td></tr>
             * <tr class="fondo_01">     <td colspan="5" valign="top"> <b> - 1er.:</b>   VICTOR GRACIA CASTILLO<br>     </td>  </tr>
             */
            Partit pPartit = null;
            pTorneig.setPartitsTorneig(partitsTorneig);
            String equipLocal = "";
            String equipVisitant = "";
            String dia = "";
            String hora = "";
            String urlUbicacio = "";
            String line = strContingut.substring(ll2);
            String marcaH3 = "<h3>Resulta";
            ll2 = 0;
            do {
                ll1 = line.indexOf("<div align=\"left\">", ll2);
                int llResultats = line.indexOf(marcaH3, ll1 + 1);
                equipLocal = "";
                if (ll1 < llResultats && ll1 > 0) {
                    // <div id="jornada_numero">JORNADA N&Uacute;MERO 3 <br> 18/02/2018 </div>
                    ll2 = line.indexOf("</div>", ll1 + 1);
                    equipLocal = parsearEquip(line.substring(ll1, ll2));
                }
                else {
                    break;
                }
                ll1 = line.indexOf("<div align=\"left\">", ll2);
                equipVisitant = "";
                if (ll1 > 0) {
                    ll2 = line.indexOf("</div>", ll1 + 1);
                    equipVisitant = parsearEquip(line.substring(ll1, ll2));
                }
                //<td valign="top" align="center">10/03/2018</td>
                ll1 = line.indexOf("align=\"center\">", ll2);
                dia = "";
                if (ll1 > 0) {
                    ll2 = line.indexOf("</td>", ll1 + 1);
                    dia = line.substring(ll1 + "align=\"center\">".length(), ll2).trim();
                }
                pPartit = new Partit();
                hora = "";
                urlUbicacio = "";
                if (!equipVisitant.equals("-") && !equipLocal.equals("-")) {
                    //<td valign="top">10:00 </td>
                    ll1 = line.indexOf("valign=\"top\">", ll2);
                    if (ll1 > 0) {
                        ll2 = line.indexOf("</td>", ll1 + 1);
                        hora = line.substring(ll1 + "valign=\"top\">".length(), ll2).trim();
                    }
                    ll3 = line.indexOf("<div align=\"left\">", ll2);
                    // <a href="javascript:popUp('mapa.asp?Campo=64')">Poliesportiu La Parellada</a>
                    ll1 = line.indexOf("popUp('", ll2);
                    if (ll3 > ll1) {
                        if (ll1 > 0) {
                            ll2 = line.indexOf("')", ll1 + 1);
                            urlUbicacio = line.substring(ll1 + "popUp('".length(), ll2).trim();
                        }
                        ll1 = line.indexOf("')\">", ll2);
                        if (ll1 > 0) {
                            ll2 = line.indexOf("</a>", ll1 + 1);
                            //nomUbicacio = line.substring(ll1 + "')\">".length(), ll2).trim();
                        }
                        // Arbitres
                        // <tr class="fondo_01">     <td colspan="5" valign="top"> <b> - 1er.:</b>   MONICA TORRENTS REYNES<br>     </td>  </tr>
                        ll1 = line.indexOf("<td colspan=\"5\" valign=\"top\">", ll2);
                        if (ll1 > 0) {
                            ll2 = line.indexOf("</td>", ll1 + 1);
                            pPartit.setArbitres(afegirArbitres(line.substring(ll1 + "<td colspan=\"5\" valign=\"top\">".length(), ll2)));
                        }
                    }
                    else {
                        ll1 = ll3;
                    }
                }
                pPartit.setJornada(partitsTorneig.getNumJornada());
                pPartit.setEquipLocal(equipLocal);
                pPartit.setEquipVisitant(equipVisitant);
                pPartit.setDataPartit(Utils.string2Data(dia + " " + hora));
                pPartit.setPavello(obtenirAdrecesURL(urlUbicacio));
                //
                partitsTorneig.addJugats(pPartit);
            } while (Boolean.TRUE);
            //
            //**************************************************************************************************************************
            System.out.println("Número d'equips (Torneig-"+pTorneig.getIdTorneig()+" - Jornada-"+partitsTorneig.getNumJornada()+"): " + partitsTorneig.getPartitsJugats().size());
            //**************************************************************************************************************************
            // Buscar cabecera 		    RESULTATS D'AQUESTA JORNADA
            String marcaHREF = "<a href=\"equipos.asp";
            String marcaFIN_HREF = "<a href=\'http";
            ll1 = ll2;
            do{
                ll1 = line.indexOf(marcaHREF, ll1 + 1);
                ll2 = line.indexOf(marcaFIN_HREF, ll1 + 1);
                if (ll2>ll1 && ll1>0) {
                    String strAux = parsearPartit(line.substring(ll1, ll2));
                    pPartit = new Partit();
                    afegirEquipsResultat(pPartit, strAux);
                    partitsTorneig.addResultats(pPartit);
                    ll1 = ll2;
                }
                else
                    break;
            } while (Boolean.TRUE);
            // Classificiaci� - <h3>Classificaci�</h3>
            String strClas = "";
            ll1 = line.indexOf("<h3>Classificació</h3>", 0);
            ll2 = line.indexOf("</table>", ll1 + 1);
            if (ll2 > 0) {
                strClas = line.substring(ll1 + "<h3>Classificació</h3>".length(), ll2 + "</table>".length());
            }
            pTorneig.setClassificacio(convertClassificacio(strClas));
            // Següent  Jornada
            ll1 = line.indexOf("Següent  Jornada", ll2);
            if (ll1 > 0)
                afegirSeguentPartits(partitsTorneig, line.substring(ll1), partitsTorneig.getNumJornada());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    private String convertClassificacio(String pTableEquips) {
        String arrEquips = "";
        /*
            <table width=""100%"" align=""center"" cellpadding=""1"" cellspacing=""2"">
                <tr class=""tittr"">
                    <td class=""clasi_numero_partido""></td>                0
                    <td class=""clasi_equipo"">EQUIPO</td>                  1
                    <td class=""clasi_columna"" align=""right"">PJ</td>     2
                    <td class=""clasi_columna"" align=""right"">PG</td>     3
                    <td class=""clasi_columna"" align=""right"">PP</td>     4
                    <td class=""clasi_columna"" align=""right"">NP</td>     5
                    <td class=""clasi_columna"" align=""right"">PS</td>     6
                    <td class=""clasi_columna"" align=""right"">SF</td>     7
                    <td class=""clasi_columna"" align=""right"">SC</td>     8
                    <td class=""clasi_columna"" align=""right"">PF</td>     9
                    <td class=""clasi_columna"" align=""right"">PC</td>     10
                    <td class=""clasi_columna"" align=""right"">G3</td>     11
                    <td class=""clasi_columna"" align=""right"">G2</td>     12
                    <td class=""clasi_columna"" align=""right"">P1</td>     13
                    <td class=""clasi_columna"" align=""right"">P0</td>     14
                    <td class=""clasi_columna"" align=""right"">PUNTS</td>  15
                </tr>
                <tr class=""fondo_01"">    <td class=""clasi_numero"">1</td>  <td class=""clasi_equipo"" valign=""top"" align=""left"">		CV SANT QUIRZE</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">11</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">10</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">1</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">0</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><ps>0</ps></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">32</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">4</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">871</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">505</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><g3>10</g3></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><g2>0</g2></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><p1>1</p1></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><p0>0</p0></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">31</td>      </tr> <tr class=""fondo_02"">    <td class=""clasi_numero"">2</td>  <td class=""clasi_equipo"" valign=""top"" align=""left"">		CEV LHOSPITALET</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">11</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">10</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">1</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">0</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><ps>0</ps></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">31</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">6</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">896</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">652</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><g3>10</g3></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><g2>0</g2></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><p1>0</p1></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><p0>1</p0></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">30</td>      </tr> <tr class=""fondo_01"">    <td class=""clasi_numero"">3</td>  <td class=""clasi_equipo"" valign=""top"" align=""left"">		BARÇA CVB BLAU</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">11</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">9</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">2</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">0</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><ps>0</ps></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">28</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">9</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">868</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">690</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><g3>8</g3></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><g2>1</g2></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><p1>0</p1></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><p0>2</p0></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">26</td>      </tr> <tr class=""fondo_02"">    <td class=""clasi_numero"">4</td>  <td class=""clasi_equipo"" valign=""top"" align=""left"">		CV MOLINS VERMELL</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">11</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">5</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">6</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">0</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><ps>0</ps></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">16</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">23</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">777</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">851</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><g3>4</g3></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><g2>1</g2></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><p1>0</p1></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><p0>6</p0></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">14</td>      </tr> <tr class=""fondo_01"">    <td class=""clasi_numero"">5</td>  <td class=""clasi_equipo"" valign=""top"" align=""left"">		AEE DOLORS MALLAFRÉ</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">11</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">5</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">6</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">0</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><ps>0</ps></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">16</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">24</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">770</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">865</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><g3>3</g3></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><g2>2</g2></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><p1>0</p1></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><p0>6</p0></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">13</td>      </tr> <tr class=""fondo_02"">    <td class=""clasi_numero"">6</td>  <td class=""clasi_equipo"" valign=""top"" align=""left"">		AEE INSTITUT MONTSERRAT 'A'</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">11</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">3</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">8</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">0</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><ps>0</ps></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">15</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">25</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">785</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">849</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><g3>3</g3></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><g2>0</g2></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><p1>2</p1></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><p0>6</p0></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">11</td>      </tr> <tr class=""fondo_01"">    <td class=""clasi_numero"">7</td>  <td class=""clasi_equipo"" valign=""top"" align=""left"">		VÒLEI SANT FRUITÓS</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">11</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">2</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">9</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">0</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><ps>0</ps></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">10</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">30</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">640</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">938</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><g3>1</g3></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><g2>1</g2></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><p1>1</p1></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><p0>8</p0></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">6</td>      </tr> <tr class=""fondo_02"">    <td class=""clasi_numero"">8</td>  <td class=""clasi_equipo"" valign=""top"" align=""left"">		CV PREMIÀ DE DALT MARESME BLAU</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">11</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">0</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">11</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">0</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><ps>0</ps></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">6</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">33</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">672</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">929</td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><g3>0</g3></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><g2>0</g2></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><p1>1</p1></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right""><p0>10</p0></td> <td  class=""clasi_columna"" height=""0"" valign=""top"" align=""right"">1</td>      </tr></table>
        */
        int iTR = 0;
        int iNTR = 0;
        do {
            iTR = pTableEquips.indexOf("<tr", iNTR);
            if (iTR < 0)
                break;
            iNTR = pTableEquips.indexOf("</tr>", iTR);
            arrEquips += (convertRow2Cells(pTableEquips.substring(iTR, iNTR)));
            //arrEquips += System.getProperty("line.separator");
            arrEquips += Constants.CTE_SEPARADOR_CLASSIFICACIO;
        } while (iTR > 0);
        String strRes = Utils.netejarString(arrEquips);
        return strRes;
    }


    private String convertRow2Cells(String pStrRow) {
        //String strCelda[] = new String[]{"","","","","","","","","","","","","","","",""};
        String strCelda = "";
        int iTD = 0;
        int iTDFin = 0;
        int iNTD = 0;
        int iComptador = 0;
        do {
            iTD = pStrRow.indexOf("<td", iNTD);
            if (iTD < 0)
                break;
            iTDFin = pStrRow.indexOf(">", iTD);
            iNTD = pStrRow.indexOf("</td>", iTDFin);
            //strCelda[iComptador++] = pStrRow.substring(iTDFin + 1, iNTD).trim();
            strCelda += pStrRow.substring(iTDFin + 1, iNTD).trim();
            strCelda += Constants.CTE_SEPARADOR_ELEMENT_CLASSIF;
        } while (iTD > 0);
        return strCelda;
    }

    /*
    private String convertClassificacio(String pClassificacioStr) {
        /*
        Filtre filtre = new Filtre();
        filtre.setTemporada(m_temporada);
        filtre.setIdTorneig(pIdTorneig);
        guardarFiltre2SP(filtre);
        // TEMPORAL
        getSQLiteDaoHelper().crearIndexosTaules();
        // TEMPORAL
        String equipsClassificacioStr = getSQLiteDaoHelper().consultaEquipsClassificacio(filtre);
        *
        String classificacio = convertString2Array(pClassificacioStr);
        return classificacio;
    }
    */
    private void afegirSeguentPartits(Partits pPartitsTorneig, String pText, int pJornada) {
        String strDiscreto = "class=\"discreto\">";
        String strA = "</a>";
        int ll1 = 0;
        int ll2 = 0;
        int ll3 = 0;
        do {
            ll1 = pText.indexOf(strDiscreto, ll3);
            if (ll1 < 0)
                break;
            ll2 = pText.indexOf(strA, ll1);
            // Equip Local
            String equipLocal = pText.substring(ll1 + strDiscreto.length(), ll2);
            // Saltar una paraula
            ll1 = pText.indexOf(strDiscreto, ll2);
            if (ll1 < 0)
                break;
            // Equip Visitant
            ll1 = pText.indexOf(strDiscreto, ll1 + 1);
            if (ll1 < 0)
                break;
            ll2 = pText.indexOf(strA, ll1);
            String equipVisitant = pText.substring(ll1 + strDiscreto.length(), ll2);
            // Data
            String strData = "align=\"center\">";
            String strDataHoraFi = "</td>";
            ll1 = pText.indexOf(strData, ll2);
            ll2 = pText.indexOf(strDataHoraFi, ll1);
            String dia = pText.substring(ll1 + strData.length(), ll2);
            // Hora
            String strHora = "<td valign=\"top\">";
            ll1 = pText.indexOf(strHora, ll2);
            ll2 = pText.indexOf(strDataHoraFi, ll1);
            String hora = pText.substring(ll1 + strHora.length(), ll2);
            // Ubicació
            String urlUbicacio = "";
            // <a href="javascript:popUp('mapa.asp?Campo=64')">Poliesportiu La Parellada</a>
            ll1 = pText.indexOf("popUp('", ll2);
            ll3 = pText.indexOf("</tr>", ll2);
            if (ll3 > ll1) {
                if (ll1 > 0) {
                    ll2 = pText.indexOf("')", ll1 + 1);
                    urlUbicacio = pText.substring(ll1 + "popUp('".length(), ll2).trim();
                }
		    	/*
		    	ll1 = pText.indexOf("')\">", ll2);
		    	if (ll1 > 0) {
		    		ll2 = pText.indexOf("</a>", ll1 + 1);
		    		//nomUbicacio = line.substring(ll1 + "')\">".length(), ll2).trim();
		    	}
		    	*/
            }
            //
            Partit pPartit = new Partit();
            pPartit.setJornada(pJornada + 1);
            pPartit.setEquipLocal(equipLocal);
            pPartit.setEquipVisitant(equipVisitant);
            pPartit.setDataPartit(Utils.string2Data(dia + " " + hora));
            pPartit.setPavello(obtenirAdrecesURL(urlUbicacio));
            //
            pPartitsTorneig.addProx(pPartit);
        } while (true);

    }

    private static void afegirEquipsResultat(Partit pPartit, String pResultat) {
        /*
         * V�LEI VILASSAR;3;0;CVB BAR�A;25-16/27-25/25-20
         */
        String strParser = Utils.parsearText(pResultat);
        String strAux[] = strParser.split(CTE_SEPARADOR_CSV);
        try {
            pPartit.setEquipLocal(strAux[0].trim());
            if (!strAux[1].trim().equals(""))
                pPartit.setResultatLocal(Integer.parseInt(strAux[1].trim()));
            if (!strAux[2].trim().equals(""))
                pPartit.setResultatVisitant(Integer.parseInt(strAux[2].trim()));
            pPartit.setEquipVisitant(strAux[3].trim());
            pPartit.setResultatSets(strAux[4].trim());
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
    private static String parsearEquip(String pStrEquip) {
        /*
         * <div align="left">        Descansa    </div>
         * <div align="left">        <a href="equipos.asp?valor=172&x_equipo_id=4123" class="discreto">CV VALL HEBRON Jr. VERD</a>    </div>
         */
        int ind1 = pStrEquip.indexOf("Descansa");
        if (ind1 > 0)
            return "-";
        else {
            String marcaDISCRETO = "class=\"discreto\">";
            int ind2 = pStrEquip.indexOf(marcaDISCRETO);
            int ind3 = pStrEquip.indexOf("</a>");
            String strEquip = "¿?";
            try {
                strEquip = pStrEquip.substring(ind2 + marcaDISCRETO.length(), ind3).trim();
            }
            catch (Exception ex) {
                // <div align="left">        CV BARCELONA - BAR�A
                String marcaALIGN = "<div align=\"left\">";
                int ind4 = pStrEquip.indexOf(marcaALIGN);
                if (ind4 >= 0)
                    strEquip = pStrEquip.substring(ind4 +marcaALIGN.length()).trim();
            }
            return strEquip;
        }
    }

    private static Pavello obtenirAdrecesURL(String pUrlUbicacio) {
        int ll1 = 0, ll2 = 0;
        if (pUrlUbicacio == null || pUrlUbicacio.equals(""))
            return null;
        Pavello pavello = new Pavello(pUrlUbicacio, "");
        String responseData = getContingutURL(CTE_URL_FCVB + pUrlUbicacio);
        try {
            //   <td height="0" align="center" valign="top" class="tittr Estilo2" >Pavell� del Bon Aire</td>
            ll1 = responseData.indexOf("\"tittr Estilo2\" >");
            ll2 = responseData.indexOf("</td>", ll1);
            //System.out.println("Nombre: " + responseData.substring(ll1 + 17, ll2).trim());
            pavello.setNom(responseData.substring(ll1 + 17, ll2).trim());
            // <td align="center"><span class="Estilo1">Carrer de Sabadell s/n - 08225</span></td>
            ll1 = responseData.indexOf("class=\"Estilo1\">", ll2);
            ll2 = responseData.indexOf("</span>", ll1);
            //System.out.println("Direcci�n: " + responseData.substring(ll1 + 16, ll2).trim());
            pavello.setAdreca(responseData.substring(ll1 + 16, ll2).trim());
            // <td align="center"><span class="Estilo1">Terrassa</span></td>
            ll1 = responseData.indexOf("class=\"Estilo1\">", ll2);
            ll2 = responseData.indexOf("</span>", ll1);
            //System.out.println("Poblaci�n: " + responseData.substring(ll1 + 16, ll2).trim());
            pavello.setPoblacio(responseData.substring(ll1 + 16, ll2).trim());
            // <td align="center"><span class="Estilo1">(BARCELONA)</span></td>
            ll1 = responseData.indexOf("class=\"Estilo1\">", ll2);
            ll2 = responseData.indexOf("</span>", ll1);
            //System.out.println("Provincia: " + responseData.substring(ll1 + 16, ll2).trim());
            pavello.setProvincia(responseData.substring(ll1 + 16, ll2).trim());
            // <td align="center"><span class="Estilo1">TEL�FONO: 937352425</span></td>
            ll1 = responseData.indexOf("class=\"Estilo1\">", ll2);
            ll2 = responseData.indexOf("</span>", ll1);
            //System.out.println("Tel�fono: " + responseData.substring(ll1 + 16, ll2).trim());
            pavello.setTelefon(responseData.substring(ll1 + 16, ll2).trim());
            // <td align="center"><span class="Estilo1">FAX: </span></td>
            ll1 = responseData.indexOf("class=\"Estilo1\">", ll2);
            ll2 = responseData.indexOf("</span>", ll1);
            //System.out.println("Fax: " + responseData.substring(ll1 + 16, ll2).trim());
            pavello.setFax(responseData.substring(ll1 + 16, ll2).trim());
            // <a href="http://maps.google.es/maps?f=q&hl=es&q=Carrer+de+Sabadell+s/n+08225+Terrassa+Espa�a" target="_blank">Ver mapa (Google Maps)</a></span></td>
            ll1 = responseData.indexOf("<a href=\"", ll2);
            ll2 = responseData.indexOf("\" target=", ll1);
            //System.out.println("Fax: " + responseData.substring(ll1 + 9, ll2).trim());
            pavello.setUrlMaps(responseData.substring(ll1 + 9, ll2).trim());
            //
        } catch (Exception ex) {
        }
        return pavello;
    }

    private static void parsearCompeticio(Torneig pTorneig, String pStrCompeticio) {
        /*
         *	<div id="nombre_competicion"><h2><strong>2� DIVISI� JUVENIL FEMENINA </strong><br>2� JUVENIL FEMENI  <br>2� FASE <br> Grupo: .Ascens 2 <br>LIGA 2 Vueltas </h2></div>
         */
        String marcaBR = "<br>";
        String strAux[] = pStrCompeticio.split(marcaBR);
        pTorneig.setNomDivisio(treureDivisio(strAux[0]));
        pTorneig.setNomCategoria(strAux[1].trim());
        pTorneig.setNomFase(strAux[2].trim());
        pTorneig.setNomGrup(treureGrup(strAux[3]));
    }

    private static String treureGrup(String pStrGrup) {
        /*
         * Grupo: .Ascens 2
         */
        return pStrGrup.replace("Grupo:", "").replace(".", "").trim();
    }

    private static String treureDivisio(String pStrDivisio) {
        /*
         * <div id="nombre_competicion"><h2><strong>2� DIVISI� JUVENIL FEMENINA </strong>
         */
        int index1 = pStrDivisio.indexOf("<strong>");
        int index2 = pStrDivisio.indexOf("</strong>", index1);
        String strAux = pStrDivisio.substring(index1 + "<strong>".length(), index2);
        return strAux.trim();
    }

    private static void parsearJornada(Partits partitsTorneig, String pJornadaStr) {
        // <div id="jornada_numero">JORNADA N&Uacute;MERO 3 <br> 18/02/2018 </div>
        int ll1 = pJornadaStr.indexOf("MERO") + 4;
        String strSplit[] = pJornadaStr.substring(ll1).replaceAll("</div>", "").split("<br>");
        partitsTorneig.setNumJornada(Integer.parseInt(strSplit[0].trim()));
        partitsTorneig.setDataJornada(strSplit[1].trim());
    }

    private static Arbitres afegirArbitres(String pStrArbitres) {
        //  <b> - 1er.:</b>   MONICA TORRENTS REYNES<br>
        Arbitres arbitres = new Arbitres();
        String strAux[] = pStrArbitres.split("<br>");
        for(String str1 : strAux) {
            str1 = str1.replace("<b>", "");
            str1 = str1.replace("</b>", "");
            if (!str1.trim().equals(""))
                arbitres.add(new Arbitre(str1));
        }
        return arbitres;
    }
    private static String parsearPartit(String pTexto) {
        String str = "";
		/*
		 <a href="equipos.asp?valor=208&x_equipo_id=1902" class="discreto">CV SANT BOI</a></td>   <td valign="top" class="fondo_01" align="center">3 - 0 </td>    <td valign="top" class="fondo_01" align="left">  <a href="equipos.asp?valor=213&x_equipo_id=1983" class="discreto">CVB BAR�A BLAU</a>    <td valign="top" class="fondo_01" align="center">25-17/25-22/25-16</td><td>
		<a href="equipos.asp?valor=219&x_equipo_id=2032" class="discreto">CV ESPLUGUES �B�</a></td>   <td valign="top" class="fondo_02" align="center">2 - 3 </td>    <td valign="top" class="fondo_02" align="left">  <a href="equipos.asp?valor=197&x_equipo_id=2039" class="discreto">CEV L�HOSPITALET</a>    <td valign="top" class="fondo_02" align="center">17-25/25-19/25-23/25-27/13-15</td><td>
		<a href="equipos.asp?valor=175&x_equipo_id=4386" class="discreto">CV LLORET</a></td>   <td valign="top" class="fondo_01" align="center">3 - 0 </td>    <td valign="top" class="fondo_01" align="left">  <a href="equipos.asp?valor=166&x_equipo_id=1906" class="discreto">AEE ESCOLA PIA GRANOLLERS</a>    <td valign="top" class="fondo_01" align="center">25-15/25-15/25-18</td><td>
		<a href="equipos.asp?valor=180&x_equipo_id=2020" class="discreto">CV GAV� VERMELL</a></td>   <td valign="top" class="fondo_02" align="center">3 - 0 </td>    <td valign="top" class="fondo_02" align="left">  <a href="equipos.asp?valor=172&x_equipo_id=4779" class="discreto">CV VALL D�HEBRON GROC</a>    <td valign="top" class="fondo_02" align="center">25-17/25-14/25-22</td><td>
		 */
        if (!pTexto.contains("Descansa")) {
            // Equipo Local
            int ll1 = pTexto.indexOf("class=\"discreto\">");
            int ll2 = pTexto.indexOf("</a>", ll1);
            str = pTexto.substring(ll1 + "class=\"discreto\">".length(), ll2) + CTE_SEPARADOR_CSV;
            // Resultado SETS
            ll1 = pTexto.indexOf("align=\"center\">", ll2);
            ll2 = pTexto.indexOf("</td>", ll1);
            if (pTexto.substring(ll1 + "align=\"center\">".length(), ll2).contains(" - "))
                str += pTexto.substring(ll1 + "align=\"center\">".length(), ll2).trim().replace(" - ", ";") + CTE_SEPARADOR_CSV;
            else
                str += CTE_SEPARADOR_CSV + CTE_SEPARADOR_CSV;
            // Equipo Visitante
            ll1 = pTexto.indexOf("class=\"discreto\">", ll2);
            ll2 = pTexto.indexOf("</a>", ll1);
            str += pTexto.substring(ll1 + "class=\"discreto\">".length(), ll2) + CTE_SEPARADOR_CSV;
            // Puntos SETS
            ll1 = pTexto.indexOf("align=\"center\">", ll2);
            ll2 = pTexto.indexOf("</td>", ll1);
            str += pTexto.substring(ll1 + "align=\"center\">".length(), ll2).trim();
            str += " ";
        }
        else {
            // DESCANSA
            int ll1 = pTexto.indexOf("class=\"discreto\">");
            int ll2 = pTexto.indexOf("</a>", ll1);
            str = pTexto.substring(ll1 + "class=\"discreto\">".length(), ll2) + CTE_SEPARADOR_CSV;
            // Resultado SETS
            ll1 = pTexto.indexOf("align=\"center\">", ll2);
            ll2 = pTexto.indexOf("</td>", ll1);
            if (pTexto.substring(ll1 + "align=\"center\">".length(), ll2).contains(" - "))
                str += pTexto.substring(ll1 + "align=\"center\">".length(), ll2).trim().replace(" - ", ";") + CTE_SEPARADOR_CSV;
            else
                str += CTE_SEPARADOR_CSV + CTE_SEPARADOR_CSV;
            // Equipo Visitante
            /*
            ll1 = pTexto.indexOf("class=\"discreto\">", ll2);
            ll2 = pTexto.indexOf("</a>", ll1);
            str += pTexto.substring(ll1 + "class=\"discreto\">".length(), ll2) + CTE_SEPARADOR_CSV;
             */
            str += "Descansa" + CTE_SEPARADOR_CSV;
            // Puntos SETS
            /*
            ll1 = pTexto.indexOf("align=\"center\">", ll2);
            ll2 = pTexto.indexOf("</td>", ll1);
            str += pTexto.substring(ll1 + "align=\"center\">".length(), ll2).trim();
             */
            str += " ";
        }
        /*
        else {
            System.out.println(pTexto);
            str = CTE_SEPARADOR_CSV +
                    CTE_SEPARADOR_CSV +
                    CTE_SEPARADOR_CSV +
                    CTE_SEPARADOR_CSV +
                    " ";
        }
         */
        //
        return str;
    }
    public void llistatTornejosEquips () {
        for (Torneig torneig : getTornejos().getLlista()) {
            getResultatsTorneig(torneig, 1);
            for (Partit partit : torneig.getPartitsTorneig().getPartitsResultats()) {
                Log.i("Equip",
                        torneig.getIdTorneig() + ";" +
                                torneig.getNomDivisio() + ";" +
                                torneig.getNomGrup() + ";" +
                                partit.getEquipLocal());
                Log.i("Equip",
                        torneig.getIdTorneig() + ";" +
                                torneig.getNomDivisio() + ";" +
                                torneig.getNomGrup() + ";" +
                                partit.getEquipVisitant());
            }
        }
    }
}
