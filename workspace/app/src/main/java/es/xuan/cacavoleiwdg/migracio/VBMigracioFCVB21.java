package es.xuan.cacavoleiwdg.migracio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import es.xuan.cacavoleiwdg.model.Arbitre;
import es.xuan.cacavoleiwdg.model.Arbitres;
import es.xuan.cacavoleiwdg.model.Partit;
import es.xuan.cacavoleiwdg.model.Partits;
import es.xuan.cacavoleiwdg.model.Pavello;
import es.xuan.cacavoleiwdg.model.Torneig;
import es.xuan.cacavoleiwdg.varis.Utils;

public class VBMigracioFCVB21 extends VBMigracio {

    private String CTE_URL_FCVB_UBICACIO = "https://fcvolei.cat/instalaciones/?instalacion=";

    public VBMigracioFCVB21() {
    }

    public Torneig getTornejos(String[] pUrlsTornejos, String[] p_nomTorneig) {
        // <item>FCVOLEI - LLIGA CATALANA DIVISIO DHONOR FEMENINA - A - 10</item>
        int iNumTorneig = Utils.stringToInt(p_nomTorneig[3]);
        for (String strUrl : pUrlsTornejos) {
            String[] strUrlAux = strUrl.split(" - ");
            // <item>10 - https://fcvolei.cat/voleibol/lliga-catalana-divisio-dhonor-femenina-2122/</item>
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
    /*
    public void llistatTornejosEquips () throws JSONException {
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
    */
    public void getResultatsTorneig(Torneig pTorneig) throws JSONException {
        String strJson = getContingutURL(pTorneig.getUrlPrincipal());
        //
        JSONObject obj = new JSONObject(strJson);
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
            if (nomGrup.equalsIgnoreCase("GRUP " + pTorneig.getNomGrup())) {
                String jornadaActual = arr.getJSONObject(i).getString("jornadaActual");
                int iJornadaActual = Utils.stringToInt(jornadaActual);
                partitsTorneig.setNumJornada(Utils.stringToInt(jornadaActual));
                // Data jornada
                JSONArray arrJornades = arr.getJSONObject(i).getJSONArray("jornadas"); //
                for (int j = 0; j < arrJornades.length(); j++) {
                    String numJornada = arrJornades.getJSONObject(j).getString("jornada");
                    int iJornada = Utils.stringToInt(numJornada);
                    if (iJornada == iJornadaActual) {
                        partitsTorneig.setDataJornada(arrJornades.getJSONObject(j).getString("fechaMin"));
                        break;
                    }
                }
                pTorneig.setPartitsTorneig(partitsTorneig);
                //System.out.println("Jornada actual: " + jornadaActual);
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
                        String strHoraPartit = arrPartidos.getJSONObject(j).getString("hora");
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
                        pPartit.setDataPartit(Utils.string2Data(strDataPartit + " " + strHoraPartit));
                        pPartit.setEquipLocal(strEquipLocal);
                        pPartit.setEquipVisitant(strEquipVisitant);
                        pPartit.setResultatLocal(Utils.stringToInt(strResLocal));
                        pPartit.setResultatVisitant(Utils.stringToInt(strResVisitant));
                        pPartit.setResultatSets(getResultatsSets(arrPartidos.getJSONObject(j)));	// 25-16/27-25/25-20
                        pPartit.setPavello(obtenirAdrecesURL(CTE_URL_FCVB_UBICACIO + strUbicacio, strNomPavello));
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
                            // Següent
                            partitsTorneig.addProx(pPartit);
                        }
                        if (iJornada > iJornadaActual + 1) {
                            // Fi de bucle
                            break;
                        }
                    }
                }
                break;
            }
        }
    }

    private static String getResultatsSets(JSONObject jsonObject) throws JSONException {
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

    private Pavello obtenirAdrecesURL(String pUrlUbicacio, String pNom) {
        Pavello pavello = new Pavello(pUrlUbicacio, pNom);
        return pavello;
    }

}
