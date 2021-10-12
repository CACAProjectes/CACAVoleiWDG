package es.xuan.cacavoleiwdg.migracio;

import java.util.Calendar;
import java.util.Date;

import es.xuan.cacavoleiwdg.model.Arbitre;
import es.xuan.cacavoleiwdg.model.Arbitres;
import es.xuan.cacavoleiwdg.model.Partit;
import es.xuan.cacavoleiwdg.model.Partits;
import es.xuan.cacavoleiwdg.model.Pavello;
import es.xuan.cacavoleiwdg.model.Torneig;
import es.xuan.cacavoleiwdg.model.Tornejos;
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
    /*
    private String getClassificacio(String strUrl) {
        try {
            int ll1 = 0, ll2 = 0;
            //
            String strContingut = getContingutURL(strUrl);
            strContingut = eliminarCabeceraPagina(strContingut, "<!-- CLASIFICACI");
            //
            String marcaTable = "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
            ll1 = strContingut.indexOf(marcaTable);
            String marcaFinTable = "</table>";
            ll2 = strContingut.indexOf(marcaFinTable);
            return strContingut.substring(ll1, ll2 + marcaFinTable.length());
        } catch (Exception ex) {
        }
        return "";
    }
    */
    public Torneig getTornejos(String[] pUrlsTornejos, String[] p_nomTorneig) {
        // <item>RFEVB - Liga Iberdrola - A - 1</item>
        int iNumTorneig = Utils.stringToInt(p_nomTorneig[3]);
        for (String strUrl : pUrlsTornejos) {
            String[] strUrlAux = strUrl.split(" - ");
            // <item>1 - http://www.rfevb.com/superliga-masculina-clasificacion</item>
            if (Utils.stringToInt(strUrlAux[0]) == iNumTorneig) {
                Torneig torneig = new Torneig();
                torneig.setUrlPrincipal(strUrlAux[1]);
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
            String strContingut = getContingutURL(pTorneig.getUrlPrincipal());
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
                if (iComptador++ == 0) {
                    partitsTorneig.setDataJornada(Utils.data2StringRed(partit.getDataPartit()));
                    partitsTorneig.setNumJornada(1);
                }
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
        int ll = pText.indexOf(".");
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

    /*
    private void omplirDadesDivisio(Partit pPartit, String pUrl) {
		/*
		 * 	"http://www.rfevb.com/svm-calendario",
			"http://www.rfevb.com/liga-iberdrola-calendario",
			"http://www.rfevb.com/sm2-calendario-grupo-a",
			"http://www.rfevb.com/sm2-calendario-grupo-b",
			"http://www.rfevb.com/sf2-calendario-gr-a",
			"http://www.rfevb.com/sf2-calendario-gr-b",
			"http://www.rfevb.com/primera-division-masculina-grupo-a-calendario",
			"http://www.rfevb.com/primera-division-masculina-grupo-b-calendario",
			"http://www.rfevb.com/primera-division-masculina-grupo-c-calendario",
			"http://www.rfevb.com/primera-division-femenina-grupo-a-calendario",
			"http://www.rfevb.com/primera-division-femenina-grupo-b-calendario",
			"http://www.rfevb.com/primera-division-femenina-grupo-c-calendario"
		 *
        if (pUrl.contains("svm-calendario")) {
            pPartit.setDivisio("SVM");
            pPartit.setCategoria("Superliga Masculina");
            pPartit.setGrup("");
            pPartit.setFase("Liga Regular");
            pPartit.setIdTorneig(89999);
        }
        else if (pUrl.contains("liga-iberdrola-calendario")) {
            pPartit.setDivisio("SFV");
            pPartit.setCategoria("Liga Iberdrola");
            pPartit.setGrup("");
            pPartit.setFase("Liga Regular");
            pPartit.setIdTorneig(89998);
        }
        else if (pUrl.contains("sm2-calendario-grupo-a")) {
            pPartit.setDivisio("SM2");
            pPartit.setCategoria("Superliga-2 Masculina");
            pPartit.setGrup("A");
            pPartit.setFase("Liga Regular");
            pPartit.setIdTorneig(89997);
        }
        else if (pUrl.contains("sm2-calendario-grupo-b")) {
            pPartit.setDivisio("SM2");
            pPartit.setCategoria("Superliga-2 Masculina");
            pPartit.setGrup("B");
            pPartit.setFase("Liga Regular");
            pPartit.setIdTorneig(89996);
        }
        else if (pUrl.contains("sf2-calendario-gr-a")) {
            pPartit.setDivisio("SF2");
            pPartit.setCategoria("Superliga-2 Femenina");
            pPartit.setGrup("A");
            pPartit.setFase("Liga Regular");
            pPartit.setIdTorneig(89995);
        }
        else if (pUrl.contains("sf2-calendario-gr-b")) {
            pPartit.setDivisio("SF2");
            pPartit.setCategoria("Superliga-2 Feminina");
            pPartit.setGrup("B");
            pPartit.setFase("Liga Regular");
            pPartit.setIdTorneig(89994);
        }
        else if (pUrl.contains("primera-division-masculina-grupo-a-calendario")) {
            pPartit.setDivisio("Primera División Masculina");
            pPartit.setCategoria("Primera División Masculina");
            pPartit.setGrup("A");
            pPartit.setFase("Liga Regular");
            pPartit.setIdTorneig(89993);
        }
        else if (pUrl.contains("primera-division-masculina-grupo-b-calendario")) {
            pPartit.setDivisio("Primera División Masculina");
            pPartit.setCategoria("Primera División Masculina");
            pPartit.setGrup("B");
            pPartit.setFase("Liga Regular");
            pPartit.setIdTorneig(89992);
        }
        else if (pUrl.contains("primera-division-masculina-grupo-c-calendario")) {
            pPartit.setDivisio("Primera División Masculina");
            pPartit.setCategoria("Primera División Masculina");
            pPartit.setGrup("C");
            pPartit.setFase("Liga Regular");
            pPartit.setIdTorneig(89991);
        }
        else if (pUrl.contains("primera-division-femenina-grupo-a-calendario")) {
            pPartit.setDivisio("Primera División Femenina");
            pPartit.setCategoria("Primera División Femenina");
            pPartit.setGrup("A");
            pPartit.setFase("Liga Regular");
            pPartit.setIdTorneig(89990);
        }
        else if (pUrl.contains("primera-division-femenina-grupo-b-calendario")) {
            pPartit.setDivisio("Primera División Femenina");
            pPartit.setCategoria("Primera División Femenina");
            pPartit.setGrup("B");
            pPartit.setFase("Liga Regular");
            pPartit.setIdTorneig(89989);
        }
        else if (pUrl.contains("primera-division-femenina-grupo-c-calendario")) {
            pPartit.setDivisio("Primera División Femenina");
            pPartit.setCategoria("Primera División Femenina");
            pPartit.setGrup("C");
            pPartit.setFase("Liga Regular");
            pPartit.setIdTorneig(89988);
        }
    }
     */
    private Date sumarAny(Date pData) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(pData);
        cal.add(Calendar.YEAR, 2000);
        return cal.getTime();
    }

    private int convertString2Integer(String pResultatLocal) {
        if (pResultatLocal != null && !pResultatLocal.equals(""))
            return Integer.parseInt(pResultatLocal);
        return 0;
    }
    private Arbitres convertString2Arbitres(String pNomArbitres) {
        // <td colspan="5"><div align="center">Lugar:   A Cachada</div></td>
        Arbitres arbitres = new Arbitres();
        if (pNomArbitres != null && !pNomArbitres.equals(""))
            arbitres.add(new Arbitre(pNomArbitres));
        return arbitres;
    }
    private Pavello convertString2Pavello(String pNomPavello) {
        // <td colspan="5"><div align="center">Lugar:   A Cachada</div></td>
        return new Pavello("", pNomPavello);
    }
    private String parsearArbitres(String pStrArbitres) {
        // <td colspan="5"><div align="center">1er &Aacute;rbitro: Marcos Antonio Folgar Fraga  </div></td>
        if (pStrArbitres != null && !pStrArbitres.equals(""))
            return pStrArbitres.trim();
        return "";
    }
    private String parsearPavello(String pStrPavello) {
        // <td colspan="5"><div align="center">Lugar:   A Cachada</div></td>
        if (pStrPavello != null && !pStrPavello.equals(""))
            return pStrPavello.trim();
        return "";
    }
    private String parsearEquip(String pStrEquip) {
        // <td width="42%"><div align="right"><strong>Rotogal Boiro </strong></div></td>
        if (pStrEquip != null && !pStrEquip.equals(""))
            return pStrEquip.trim();
        return "";
    }
    private String parsearDia(String pStrDia) {
        // <td><div align="center"><strong>06/10/18 </strong>
        if (pStrDia != null && !pStrDia.equals(""))
            return pStrDia.trim();
        return "";
    }
    private String parsearHora(String pStrHora) {
        // <br><br> 19:00</div></td>
        if (pStrHora != null && !pStrHora.equals(""))
            return pStrHora.trim();
        return "";
    }

}

