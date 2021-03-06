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
    //
    private final static String[] CTE_URL_RFEVB_CLASSIFICACIO = new String[] {
            "http://www.rfevb.com/superliga-masculina-clasificacion",
            "http://www.rfevb.com/liga-iberdrola-clasificacion",
            "http://www.rfevb.com/superliga-masculina-2-grupo-c-clasificacion"
    };

    private final static String[] CTE_URLS_RFEVB_COMPETICIONS = new String[] {
            "http://www.rfevb.com/svm-calendario",
            "http://www.rfevb.com/liga-iberdrola-calendario",
            "http://www.rfevb.com/sm2-calendario-grupo-c"
    };
    private final static int[] CTE_IDS_RFEVB_TORNEIG = new int[] {
            9999,
            9998,
            9997
    };
    public VBMigracioRFEVB() {
        super();
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
                iTD = pStrRow.indexOf("<td >", iNTD);	// ??s la primera linia
            else
                iTD = pStrRow.indexOf("<td>", iNTD);	// S??n les seg??ents linies
            if (iTD < 0)
                break;
            iNTD = pStrRow.indexOf("</td>", iTD);
            strCelda += pStrRow.substring(iTD, iNTD).trim();
            strCelda += Constants.CTE_SEPARADOR_ELEMENT_CLASSIF;
        } while (iTD > 0);
        strCelda = Utils.netejarText(strCelda);
        return strCelda;
    }

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

    public Tornejos getTornejos() {
        Tornejos tornejos = new Tornejos();
        String[] strUrls = CTE_URLS_RFEVB_COMPETICIONS;
        int iComptador = 0;
        for (String strUrl : strUrls) {
            Torneig torneig = new Torneig();
            torneig.setUrlPrincipal(CTE_URLS_RFEVB_COMPETICIONS[iComptador]);
            torneig.setUrlClassificacio(CTE_URL_RFEVB_CLASSIFICACIO[iComptador]);
            tornejos.add(torneig);
            iComptador++;
        }
        return tornejos;
    }

    public void getResultatsTorneig(Torneig pTorneig, int pNumJornada) {
        Partits partitsTorneig = new Partits();
        Partit pPartit = null;
        pTorneig.setPartitsTorneig(partitsTorneig);
        int ll1 = 0, ll2 = 0, ll3 = 0;
        int iComptador = 0;
        try {
            //
            String strContingut = getContingutURL(pTorneig.getUrlPrincipal());
            strContingut = eliminarCabeceraPagina(strContingut, "<table width=\"100%\" border=\"1\">");
            //
            ll2 = 0;
            // <strong>JORNADA
            //String marcaJornada = "<strong>JORNADA";
            // <div align="center"><strong>
            String marcaFecha = "<div align=\"center\"><strong>";
            String marcaStrongIni = "<strong>";
            String marcaStrongFin = "</strong>";
            while (true) {
                //ll1 = strContingut.indexOf(marcaJornada, ll2);
                ll3 = strContingut.indexOf(marcaFecha, ll2);
                if ((ll3 < 0) || (ll2 < 0) || (ll1 < 0))
                    break;
                /*
                if (ll1 > 0 && ll1 < ll3) {
                    // Encuetra el texto de Jornada abans que el partit
                    ll2 = strContingut.indexOf(marcaStrongIni, ll1 + 1);
                    pNumJornada = parsearJornada(strContingut.substring(ll1 + marcaJornada.length(), ll2));
                }
                 */
                ll2 = strContingut.indexOf(marcaStrongFin, ll3);
                String dia = parsearDia(strContingut.substring(ll3 + marcaFecha.length(), ll2));
                String marcaHora = "<br><br>";
                ll1 = strContingut.indexOf(marcaHora, ll2);
                if ((ll3 < 0) || (ll2 < 0) || (ll1 < 0))
                    break;
                String marcaDIVFin = "</div>";
                ll2 = strContingut.indexOf(marcaDIVFin, ll1);
                String hora = parsearHora(strContingut.substring(ll1 + marcaHora.length(), ll2));
                // <img src=http://licenciasrfevb.icons.es/clubes/logos/web/cl00508&period;png width=50 height=auto>
                //String marcaPartit = "licenciasrfevb.icons.es";
                //ll1 = strContingut.indexOf(marcaPartit, ll2);
                // <td width="42%"><div align="right"><strong>
                String marcaEquip1 = "<td width=\"42%\"><div align=\"right\"><strong>";
                ll1 = strContingut.indexOf(marcaEquip1, ll1);
                ll2 = strContingut.indexOf(marcaStrongFin, ll1);
                if ((ll3 < 0) || (ll2 < 0) || (ll1 < 0))
                    break;
                String equipLocal = parsearEquip(strContingut.substring(ll1 + marcaEquip1.length(), ll2));
                // Marcador
                //	<td width="2%"> <strong>3</strong></td>
                String marcaResultatLocal = "<td width=\"2%\"> <strong>";
                ll1 = strContingut.indexOf(marcaResultatLocal, ll1);
                ll2 = strContingut.indexOf(marcaStrongFin, ll1);
                if ((ll3 < 0) || (ll2 < 0) || (ll1 < 0))
                    break;
                String resultatLocal = parsearEquip(strContingut.substring(ll1 + marcaResultatLocal.length(), ll2));
                //	<td width="2%"><strong>0</strong></td>
                String marcaResultatVisitant = "<td width=\"2%\"><strong>";
                ll1 = strContingut.indexOf(marcaResultatVisitant, ll1);
                ll2 = strContingut.indexOf(marcaStrongFin, ll1);
                if ((ll3 < 0) || (ll2 < 0) || (ll1 < 0))
                    break;
                String resultatVisitant = parsearEquip(strContingut.substring(ll1 + marcaResultatVisitant.length(), ll2));
                // <td width="42%"><div align="left"><strong>
                String marcaEquip2 = "<td width=\"42%\"><div align=\"left\"><strong>";
                ll1 = strContingut.indexOf(marcaEquip2, ll2);
                ll2 = strContingut.indexOf(marcaStrongFin, ll1);
                if ((ll3 < 0) || (ll2 < 0) || (ll1 < 0))
                    break;
                String equipVisitant = parsearEquip(strContingut.substring(ll1 + marcaEquip2.length(), ll2));
                // Lugar:
                String marcaLugar = "<div align=\"center\">Lugar:";
                ll1 = strContingut.indexOf(marcaLugar, ll2);
                //</div>
                ll2 = strContingut.indexOf(marcaDIVFin, ll1);
                if ((ll3 < 0) || (ll2 < 0) || (ll1 < 0))
                    break;
                String nomPavello = parsearPavello(strContingut.substring(ll1 + marcaLugar.length(), ll2));
                // <div align="center">
                String marcaArbitres = "<div align=\"center\">";
                ll1 = strContingut.indexOf(marcaArbitres, ll2);
                ll2 = strContingut.indexOf(marcaDIVFin, ll1);
                if ((ll3 < 0) || (ll2 < 0) || (ll1 < 0))
                    break;
                String nomArbitres = parsearArbitres(strContingut.substring(ll1 + marcaArbitres.length(), ll2));
                //
                pPartit = new Partit();
                omplirDadesDivisio(pPartit, pTorneig.getUrlPrincipal());
                partitsTorneig.setDataJornada("");
                pPartit.setJornada(pNumJornada);
                pPartit.setEquipLocal(equipLocal);
                pPartit.setEquipVisitant(equipVisitant);
                pPartit.setResultatLocal(convertString2Integer(resultatLocal));
                pPartit.setResultatVisitant(convertString2Integer(resultatVisitant));
                pPartit.setDataPartit(sumarAny(Utils.string2Data(dia + " " + hora)));
                pPartit.setPavello(convertString2Pavello(nomPavello));
                pPartit.setArbitres(convertString2Arbitres(nomArbitres));
                //
                partitsTorneig.addJugats(pPartit);
            }
            String strClasif = getClassificacio(pTorneig.getUrlClassificacio());
            pTorneig.setClassificacio(convertClassificacio(strClasif));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }
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
		 */
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
            pPartit.setDivisio("Primera Divisi??n Masculina");
            pPartit.setCategoria("Primera Divisi??n Masculina");
            pPartit.setGrup("A");
            pPartit.setFase("Liga Regular");
            pPartit.setIdTorneig(89993);
        }
        else if (pUrl.contains("primera-division-masculina-grupo-b-calendario")) {
            pPartit.setDivisio("Primera Divisi??n Masculina");
            pPartit.setCategoria("Primera Divisi??n Masculina");
            pPartit.setGrup("B");
            pPartit.setFase("Liga Regular");
            pPartit.setIdTorneig(89992);
        }
        else if (pUrl.contains("primera-division-masculina-grupo-c-calendario")) {
            pPartit.setDivisio("Primera Divisi??n Masculina");
            pPartit.setCategoria("Primera Divisi??n Masculina");
            pPartit.setGrup("C");
            pPartit.setFase("Liga Regular");
            pPartit.setIdTorneig(89991);
        }
        else if (pUrl.contains("primera-division-femenina-grupo-a-calendario")) {
            pPartit.setDivisio("Primera Divisi??n Femenina");
            pPartit.setCategoria("Primera Divisi??n Femenina");
            pPartit.setGrup("A");
            pPartit.setFase("Liga Regular");
            pPartit.setIdTorneig(89990);
        }
        else if (pUrl.contains("primera-division-femenina-grupo-b-calendario")) {
            pPartit.setDivisio("Primera Divisi??n Femenina");
            pPartit.setCategoria("Primera Divisi??n Femenina");
            pPartit.setGrup("B");
            pPartit.setFase("Liga Regular");
            pPartit.setIdTorneig(89989);
        }
        else if (pUrl.contains("primera-division-femenina-grupo-c-calendario")) {
            pPartit.setDivisio("Primera Divisi??n Femenina");
            pPartit.setCategoria("Primera Divisi??n Femenina");
            pPartit.setGrup("C");
            pPartit.setFase("Liga Regular");
            pPartit.setIdTorneig(89988);
        }
    }
    private Date sumarAny(Date pData) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(pData);
        cal.add(Calendar.YEAR, 2000);
        return cal.getTime();
    }
    private String eliminarCabeceraPagina(String pStrContingut, String pStrMarca) {
        // <table width="100%" border="1">
        int ind = pStrContingut.indexOf(pStrMarca);
        return pStrContingut.substring(ind);
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
    private int parsearJornada(String pStrJornada) {
        //	<td colspan="7" style="background:beige;"><div align="center" style="background:beige; font-size:12px;"><strong>JORNADA 1<strong></div></td>
        if (pStrJornada != null && !pStrJornada.equals(""))
            return Integer.parseInt(pStrJornada.trim());
        return 0;
    }

    public Torneig getTornejos(int p_idTorneig) {
        String[] strUrls = CTE_URLS_RFEVB_COMPETICIONS;
        int iComptador = 0;
        for (String strUrl : strUrls) {
            if (p_idTorneig == CTE_IDS_RFEVB_TORNEIG[iComptador]) {
                Torneig torneig = new Torneig();
                torneig.setUrlPrincipal(CTE_URLS_RFEVB_COMPETICIONS[iComptador]);
                torneig.setUrlClassificacio(CTE_URL_RFEVB_CLASSIFICACIO[iComptador]);
                torneig.setIdTorneig(p_idTorneig);
                return torneig;
            }
            iComptador++;
        }
        return null;
    }
}

