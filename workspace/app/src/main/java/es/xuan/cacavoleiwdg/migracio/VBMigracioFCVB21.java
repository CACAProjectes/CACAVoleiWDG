package es.xuan.cacavoleiwdg.migracio;

import android.util.Log;

import es.xuan.cacavoleiwdg.model.Partit;
import es.xuan.cacavoleiwdg.model.Partits;
import es.xuan.cacavoleiwdg.model.Torneig;
import es.xuan.cacavoleiwdg.model.Tornejos;
import es.xuan.cacavoleiwdg.varis.Utils;

public class VBMigracioFCVB21 extends VBMigracio {

    private final static String CTE_URL_FCVB_COMPETICIONS = "https://fcvolei.cat/apicompeticiones.php?slug=2a-divisio-infantil-femenina-2122";

    public VBMigracioFCVB21() {
    }

    public Tornejos getTornejos() {
        Tornejos tornejos = new Tornejos();
        try {
            tornejos.add(getIdTorneig(CTE_URL_FCVB_COMPETICIONS)); // "https://fcvolei.cat/apicompeticiones.php?slug=2a-divisio-infantil-femenina-2122";
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return tornejos;
    }

    private Torneig getIdTorneig(String pUrl) {
        /*
         * "https://fcvolei.cat/apicompeticiones.php?slug=2a-divisio-infantil-femenina-2122";
         */
        Torneig torneig = new Torneig();
        try {
            torneig.setIdTorneig(Integer.parseInt(pUrl.substring(pUrl.lastIndexOf("-") + 1)));
        } catch(Exception ex) {
            System.err.println(ex);
        }
        return torneig;
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

    private void getResultatsTorneig(Torneig pTorneig, int i) {
        int ll1 = 0, ll2 = 0, ll3 = 0;
        String strUrl = CTE_URL_FCVB_COMPETICIONS;
        //
        String strContingut = getContingutURL(strUrl);
        ///////////////////////////////////////
        //	Jornada
        Partits partitsTorneig = new Partits();
        ///////////////////////////////////////
        //	Data partit
        Partit pPartit = null;
        pTorneig.setPartitsTorneig(partitsTorneig);
        String equipLocal = "";
        String equipVisitant = "";
        String dia = "";
        String hora = "";
        String urlUbicacio = "";
        do {
            pPartit.setJornada(partitsTorneig.getNumJornada());
            pPartit.setEquipLocal(equipLocal);
            pPartit.setEquipVisitant(equipVisitant);
            pPartit.setDataPartit(Utils.string2Data(dia + " " + hora));
            pPartit.setPavello(obtenirAdrecesURL(urlUbicacio));
            //
            partitsTorneig.addJugats(pPartit);
        } while (Boolean.TRUE);
        do{
            partitsTorneig.addResultats(pPartit);
        } while (Boolean.TRUE);
        // Classificiació
        pTorneig.setClassificacio(convertClassificacio(strClas));
        // Següent  Jornada
        afegirSeguentPartits(partitsTorneig, line.substring(ll1), partitsTorneig.getNumJornada());
    }
}
