package es.xuan.cacavoleiwdg.migracio;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Date;

import es.xuan.cacavoleiwdg.model.Partit;
import es.xuan.cacavoleiwdg.model.Partits;
import es.xuan.cacavoleiwdg.model.Pavello;
import es.xuan.cacavoleiwdg.model.Torneig;
import es.xuan.cacavoleiwdg.varis.Constants;
import es.xuan.cacavoleiwdg.varis.Utils;

public class VBMigracioRFEVB extends VBMigracio {

    public VBMigracioRFEVB() {
    }

    private String convertClassificacio(String pTableEquips) {
        String arrEquips = "";
        /*
            <table width="100%" border="0" cellspacing="0" cellpadding="0">                <p>
				<tr class="cabeceraTabla">          <td width="50%"><strong>CLASIFICACI&Oacute;N</strong></td>          <td ><strong>Ptos</strong></td>          <td ><strong>J</strong></td>          <td ><strong>G3</strong></td>          <td ><strong>G2</strong></td>          <td ><strong>P1</strong></td>          <td ><strong>P0</strong></td>          <td ><strong>SF</strong></td>          <td ><strong>SC</strong></td>          <td ><strong>PF</strong></td>          <td ><strong>PC</strong></td>         </tr>
				<tr>          <td>1. 		  		<img src=http://licenciasrfevb.icons.es/clubes/logos/web\cl01279&period;png width=20 height=20>		  C&period;V&period; Teruel </td>          <td>41</td>          <td>15</td>          <td>12</td>          <td>2</td>          <td>1</td>          <td>0</td>          <td>44</td>		  <td>11</td>          <td>1304</td>          <td>1111</td>        </tr>
				<tr>          <td>2. 		  		<img src=http://licenciasrfevb.icons.es/clubes/logos/web\cl00135&period;png width=20 height=20>		  Unicaja Almer&iacute;a </td>          <td>38</td>          <td>15</td>          <td>12</td>          <td>1</td>          <td>0</td>          <td>2</td>          <td>40</td>		  <td>15</td>          <td>1311</td>          <td>1105</td>        </tr>
				<tr>          <td>3. 		  		<img src=http://licenciasrfevb.icons.es/clubes/logos/web\cl00287&period;png width=20 height=20>		  Ushua&iuml;a Ibiza Voley </td>          <td>33</td>          <td>15</td>          <td>10</td>          <td>1</td>          <td>1</td>          <td>3</td>          <td>38</td>		  <td>17</td>          <td>1281</td>          <td>1130</td>        </tr>
				<tr>          <td>4. 		  		<img src=http://licenciasrfevb.icons.es/clubes/logos/web\cl00300&period;png width=20 height=20>		  Urbia Voley Palma </td>          <td>30</td>          <td>15</td>          <td>9</td>          <td>1</td>          <td>1</td>          <td>4</td>          <td>34</td>		  <td>18</td>          <td>1193</td>          <td>1082</td>        </tr>
				<tr>          <td>5. 		  		<img src=http://licenciasrfevb.icons.es/clubes/logos/web\cl00460&period;png width=20 height=20>		  UBE L&comma;Illa Grau Castell&oacute;n </td>          <td>25</td>          <td>15</td>          <td>7</td>          <td>1</td>          <td>2</td>          <td>5</td>          <td>31</td>		  <td>28</td>          <td>1310</td>          <td>1295</td>        </tr>            <tr>          <td>6. 		  		<img src=http://licenciasrfevb.icons.es/clubes/logos/web\cl01193&period;png width=20 height=20>		  CDV Textil Santanderina </td>          <td>22</td>          <td>15</td>          <td>5</td>          <td>3</td>          <td>1</td>          <td>6</td>          <td>27</td>		  <td>30</td>          <td>1261</td>          <td>1280</td>        </tr>            <tr>          <td>7. 		  		<img src=http://licenciasrfevb.icons.es/clubes/logos/web\cl01089&period;png width=20 height=20>		  Vecindario ACE Gran Canaria </td>          <td>21</td>          <td>15</td>          <td>4</td>          <td>1</td>          <td>7</td>          <td>3</td>          <td>30</td>		  <td>34</td>          <td>1383</td>          <td>1364</td>        </tr>            <tr>          <td>8. 		  		<img src=http://licenciasrfevb.icons.es/clubes/logos/web\cl01268&period;png width=20 height=20>		  CDV R&iacute;o Duero Soria </td>          <td>16</td>          <td>15</td>          <td>3</td>          <td>2</td>          <td>3</td>          <td>7</td>          <td>27</td>		  <td>34</td>          <td>1317</td>          <td>1355</td>        </tr>            <tr>          <td>9. 		  		<img src=http://licenciasrfevb.icons.es/clubes/logos/web\cl01445&period;png width=20 height=20>		  C&period;V&period; Melilla </td>          <td>14</td>          <td>15</td>          <td>1</td>          <td>5</td>          <td>1</td>          <td>8</td>          <td>23</td>		  <td>38</td>          <td>1239</td>          <td>1390</td>        </tr>            <tr>          <td>10. 		  		<img src=http://licenciasrfevb.icons.es/clubes/logos/web\cl00236&period;png width=20 height=20>		  Conectabalear CV Manacor </td>          <td>14</td>          <td>15</td>          <td>3</td>          <td>2</td>          <td>1</td>          <td>9</td>          <td>22</td>		  <td>36</td>          <td>1251</td>          <td>1337</td>        </tr>            <tr>          <td>11. 		  		<img src=http://licenciasrfevb.icons.es/clubes/logos/web\cl00307&period;png width=20 height=20>		  Bar&ccedil;a Voleibol </td>          <td>11</td>          <td>15</td>          <td>3</td>          <td>0</td>          <td>2</td>          <td>10</td>          <td>15</td>		  <td>39</td>          <td>1122</td>          <td>1276</td>        </tr>            <tr>          <td>12. 		  		<img src=http://licenciasrfevb.icons.es/clubes/logos/web\cl00514&period;png width=20 height=20>		  Intasa San Sadurni&ntilde;o </td>          <td>5</td>          <td>15</td>          <td>0</td>          <td>2</td>          <td>1</td>          <td>12</td>          <td>12</td>		  <td>43</td>          <td>1081</td>          <td>1328</td>        </tr>
			</table>
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
        arrEquips = Utils.parsearText(arrEquips);
        return arrEquips;
    }

    private String convertRow2Cells(String pStrRow) {
		/*
		<tr class="cabeceraTabla">          <td width="50%"><strong>CLASIFICACI&Oacute;N</strong></td>          <td ><strong>Ptos</strong></td>          <td ><strong>J</strong></td>          <td ><strong>G3</strong></td>          <td ><strong>G2</strong></td>          <td ><strong>P1</strong></td>          <td ><strong>P0</strong></td>          <td ><strong>SF</strong></td>          <td ><strong>SC</strong></td>          <td ><strong>PF</strong></td>          <td ><strong>PC</strong></td>         </tr>
		<tr>          <td>1. 		  		<img src=http://licenciasrfevb.icons.es/clubes/logos/web\cl01279&period;png width=20 height=20>		  C&period;V&period; Teruel </td>          <td>41</td>          <td>15</td>          <td>12</td>          <td>2</td>          <td>1</td>          <td>0</td>          <td>44</td>		  <td>11</td>          <td>1304</td>          <td>1111</td>        </tr>
		 */
        //String strCelda[] = new String[]{"","","","","","","","","","","","","","","",""};
        String strCelda = "";
        int iTD = 0;
        int iNTD = 0;
        do {
            if (pStrRow.contains("CLASIFICACI"))
                iTD = pStrRow.indexOf("<td >", iNTD);	// És la primera linia
            else
                iTD = pStrRow.indexOf("<td>", iNTD);	// Són les següents linies
            if (iTD < 0)
                break;
            iNTD = pStrRow.indexOf("</td>", iTD);
            strCelda += pStrRow.substring(iTD, iNTD).trim();
            strCelda += Constants.CTE_SEPARADOR_ELEMENT_CLASSIF;
        } while (iTD > 0);
        strCelda = Utils.netejarText(strCelda);
        return strCelda;
    }

    public Torneig getTornejos(String[] pUrlsTornejos, String[] p_nomTorneig) {
        // <item>RFEVB - Liga Iberdrola - A - 1</item>
        int iNumTorneig = Utils.stringToInt(p_nomTorneig[3]);
        for (String strUrl : pUrlsTornejos) {
            String[] strUrlAux = strUrl.split(" - ");
            // <item>1 - http://www.rfevb.com/superliga-masculina-clasificacion</item>
            if (Utils.stringToInt(strUrlAux[0]) == iNumTorneig) {
                Torneig torneig = new Torneig();
                torneig.setUrlPrincipal(strUrlAux[2]);
                torneig.setUrlClassificacio(strUrlAux[1]);
                torneig.setIdTorneig(Utils.stringToInt(strUrlAux[0]));
                torneig.setNomGrup(p_nomTorneig[2]);
                return torneig;
            }
        }
        return null;
    }

    public void getResultatsTorneig(Torneig pTorneig) {
        Partits partitsTorneig = new Partits();
        Partit partit = null;
        pTorneig.setPartitsTorneig(partitsTorneig);
        int ll1 = 0, ll2 = 0, ll3 = 0;
        int iComptador = 0;
        try {
            //
            String strContingutJornada = getContingutURL(pTorneig.getUrlPrincipal());
            parserJornada(strContingutJornada, partitsTorneig);
            //
            String strContingut = getContingutURL(pTorneig.getUrlClassificacio() +
                    "&Jornada=" + partitsTorneig.getNumJornada());
            //
            int iClasificacio = strContingut.indexOf("<!-- CLASIFICACI");
            int iPartitsProxims = strContingut.indexOf("<!-- PROXIMOS ENCUENTROS -->");
            //
            while (true) {
                // Partits actuals
                partit = new Partit();
                /*
                          <td>6. CV Almendralejo Extremadura - CyL Palencia 2022</td>
                          <td >23/10/21 (18:30) </td>
                          <td>0 - 0 (0-0/0-0/0-0/0-0/0-0)</td>
                 */
                ll1 = strContingut.indexOf("<td>", ll2);
                if (ll1 > iClasificacio)    // Fi partits actuals
                    break;
                ll2 = strContingut.indexOf("</td>", ll1);
                parsearEquips(partit, strContingut.substring(ll1, ll2));
                //String nomArbitres = parsearArbitres(strContingut.substring(ll1 + marcaArbitres.length(), ll2));
                //somplirDadesDivisio(pPartit, pTorneig.getUrlPrincipal());
                ll1 = strContingut.indexOf("<td", ll2);
                ll2 = strContingut.indexOf("</td>", ll1);
                parsearDataPartit(partit, strContingut.substring(ll1, ll2));
                //
                ll1 = strContingut.indexOf("<td>", ll2);
                ll2 = strContingut.indexOf("</td>", ll1);
                parsearResultats(partit, strContingut.substring(ll1, ll2));
                //
                partitsTorneig.addJugats(partit);
                partitsTorneig.addResultats(partit);
            }
            /* TODO
            String strClasif = getClassificacio(pTorneig.getUrlClassificacio());
            pTorneig.setClassificacio(convertClassificacio(strClasif));
            */
            ll2 = iPartitsProxims;
            while (true) {
                // Partits pròxims
                partit = new Partit();
                /*
                    <tr>
                      <td>11</td>
                      <td>30/10/21 (17:30) </td>
                      <td>CyL Palencia 2022 - Voleibol Dumbr&iacute;a</td>
                      <td>Campo de la Juventud</td>
                    </tr>
                 */
                ll1 = strContingut.indexOf("<tr>", ll2);
                if (ll1 < 0)
                    break;
                ll1 = strContingut.indexOf("<td>", ll1);
                ll1 = strContingut.indexOf("<td>", ll1 + 1);
                ll2 = strContingut.indexOf("</td>", ll1);
                parsearDataPartit(partit, strContingut.substring(ll1, ll2));
                ll1 = strContingut.indexOf("<td>", ll2);
                ll2 = strContingut.indexOf("</td>", ll1);
                parsearEquipsSeg(partit, strContingut.substring(ll1, ll2));
                ll1 = strContingut.indexOf("<td>", ll2);
                ll2 = strContingut.indexOf("</td>", ll1);
                partit.setPavello(convertString2Pavello(Utils.parsearText(strContingut.substring(ll1 + 4, ll2))));
                //
                partitsTorneig.addProx(partit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    private void parserJornada(String pContingutJornada, Partits pPartitsTorneig) {
        int numJornada = 1;
        String strDataJornadaAct = Utils.data2StringJSON(new Date());
        try {
            JSONArray arr = new JSONArray(pContingutJornada);
            for (int i = 0; i < arr.length(); i++) {
                String strNumJornada = arr.getJSONObject(i).getString("Jornada");
                String strDataJornada = arr.getJSONObject(i).getString("Fecha");
                if (Utils.string2DataJSON(strDataJornada).compareTo(new Date()) > 0) {
                    break;
                }
                strDataJornadaAct = strDataJornada;
                numJornada = Utils.stringToInt(strNumJornada);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pPartitsTorneig.setDataJornada(strDataJornadaAct);
        pPartitsTorneig.setNumJornada(numJornada);
    }

    private void parsearResultats(Partit partit, String pText) {
        // <td>0 - 0 (0-0/0-0/0-0/0-0/0-0)</td>
        int ll1 = pText.indexOf(">") + 1;
        int ll2 = pText.indexOf("(", ll1);
        String[] strSets = pText.substring(ll1, ll2).trim().split("-");
        partit.setResultatLocal(Utils.stringToInt(strSets[0].trim()));
        partit.setResultatVisitant(Utils.stringToInt(strSets[1].trim()));
        int ll3 = pText.indexOf(")", ll2);
        partit.setResultatSets(pText.substring(ll2 + 1, ll3).trim());
    }

    private void parsearDataPartit(Partit partit, String pText) {
        // <td >23/10/21 (18:30) </td>
        int ll1 = pText.indexOf(">") + 1;
        partit.setDataPartit(Utils.string2DataYY(pText.substring(ll1).trim()));
    }

    private void parsearEquips(Partit pPartit, String pText) {
        // <td>6. CV Almendralejo Extremadura - CyL Palencia 2022</td>
        int ll0 =  pText.indexOf(">");
        int ll = pText.indexOf(".");
        // Núm. jornada
        String strNumJornada = pText.substring(ll0 + 1, ll);
        pPartit.setJornada(Utils.stringToInt(strNumJornada));
        // Equips local i visitant
        pText = pText.substring(ll + 1);
        String[] strAux = pText.split("-");
        pPartit.setEquipLocal(Utils.parsearText(strAux[0].trim()));
        pPartit.setEquipVisitant(Utils.parsearText(strAux[1].trim()));
    }
    private void parsearEquipsSeg(Partit pPartit, String pText) {
        // <td>CV Almendralejo Extremadura - CyL Palencia 2022</td>
        int ll = pText.indexOf("<td>");
        pText = pText.substring(ll + 4);
        String[] strAux = pText.split("-");
        pPartit.setEquipLocal(Utils.parsearText(strAux[0].trim()));
        pPartit.setEquipVisitant(Utils.parsearText(strAux[1].trim()));
    }

    private Pavello convertString2Pavello(String pNomPavello) {
        // <td colspan="5"><div align="center">Lugar:   A Cachada</div></td>
        return new Pavello("", pNomPavello);
    }

}

