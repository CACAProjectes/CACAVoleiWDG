package es.xuan.cacavoleiwdg.view;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import java.util.ArrayList;
import es.xuan.cacavoleiwdg.AppVoleiWDG;
import es.xuan.cacavoleiwdg.R;
import es.xuan.cacavoleiwdg.model.Partit;
import es.xuan.cacavoleiwdg.model.Torneig;
import es.xuan.cacavoleiwdg.varis.Constants;
import es.xuan.cacavoleiwdg.varis.Utils;

public class Vista {

    private static int CTE_TAMANY_EQUIP = 19;
    private static int CTE_TAMANY_PAVELLO = 27;
    private static int CTE_TAMANY_NOM_EQUIP = 30;
    private static int CTE_TAMANY_SETS = 2;
    private static int CTE_TAMANY_PUNTS = 3;
    private static int CTE_MAX_TAMANY_NOM_CLASIFICACIO = 48;

    public static void omplirDadesDivisio(Context p_context, RemoteViews views, Torneig p_torneig) {
        // DIVISIÓ
        views.setTextViewText(R.id.tvTitol1,
                p_torneig.getNomDivisio());
    }
    public static void omplirDadesGrup(Context p_context, RemoteViews views, Torneig p_torneig) {
        // GRUP
        views.setTextViewText(R.id.tvTitol2,
                p_context.getResources().getString(R.string.titol_grup) +
                        p_torneig.getNomGrup());
    }
    public static void omplirDadesJornada(Context p_context, RemoteViews views, Torneig p_torneig) {
        // JORNADA I DATA
        views.setTextViewText(R.id.tvTitol4,
                p_context.getResources().getString(R.string.titol_jornada) +
                        p_torneig.getPartitsTorneig().getNumJornada() +
                        p_context.getResources().getString(R.string.titol_espai_blanc) +
                        p_context.getResources().getString(R.string.titol_espai_blanc) +
                        p_context.getResources().getString(R.string.titol_espai_blanc) +
                        p_torneig.getPartitsTorneig().getDataJornada());
    }
    private static String resultat2String(Context p_context, int pResultatLocal, int pResultatVisitant) {
        if (pResultatLocal == 0 && pResultatVisitant == 0)
            return p_context.getResources().getString(R.string.titol_separador_res);
        return "" + pResultatLocal + p_context.getResources().getString(R.string.titol_separador) + pResultatVisitant;
    }
    private static String tallarText(Context p_context, String pText, int pTamany) {
        if (pText.length() > pTamany)
            return pText.substring(0, pTamany);
        String strText = pText;
        for (int i=0 ; i<pTamany - pText.length(); i++) {
            strText += p_context.getResources().getString(R.string.titol_espai_blanc);
        }
        return strText;
    }
    private static String omplirText(Context p_context, String pText, int pTamany) {
        if (pText.length() > pTamany)
            return pText.substring(0, pTamany);
        String strText = "";
        for (int i=0 ; i<pTamany - pText.length(); i++) {
            strText += p_context.getResources().getString(R.string.titol_espai_blanc);
        }
        return strText + pText;
    }
    private static String validarNull(Context p_context, String pText) {
        if (pText != null && !pText.equals("")) {
            return pText;
        }
        return p_context.getResources().getString(R.string.titol_espai_blanc);
    }

    private static String validarNullData(Context p_context, Partit p_partit) {
        if (p_partit != null &&
                p_partit.getDataPartit() != null) {
                return validarNull(p_context, Utils.data2String(p_partit.getDataPartit()));
        }
        return p_context.getResources().getString(R.string.titol_espai_blanc);
    }
    private static String validarNullPavello(Context p_context, Partit p_partit) {
        if (p_partit != null &&
                p_partit.getPavello() != null &&
                !p_partit.getPavello().getNom().equals("")) {
            return validarNull(p_context, p_partit.getPavello().getNom());
        }
        return p_context.getResources().getString(R.string.titol_espai_blanc);
    }

    private static void canviarColorText(Context p_context, RemoteViews views, String pNomEquip, String pNomEquipPartit, int idText) {
        /*
        if (pNomEquip.startsWith(pNomEquipPartit))
            views.setTextColor(idText, p_context.getColor(R.color.colorEquip));
        else
            views.setTextColor(idText, p_context.getColor(R.color.colorText));

         */
        if (pNomEquipPartit.contains(pNomEquip))
            views.setTextColor(idText, p_context.getColor(R.color.colorEquip));
        else
            views.setTextColor(idText, p_context.getColor(R.color.colorText));
    }
    public static void omplirDadesPartitsSeguents(Context p_context, RemoteViews views, ArrayList<Partit> p_partitsRes, String p_nomEquip) {
        // PARTITS JORNADA SEGÜENT
        int iPartit = 0;
        // Partit 1
        while (p_partitsRes!=null && iPartit < p_partitsRes.size()) {
            if (p_partitsRes.get(iPartit).getEquipLocal().contains(p_nomEquip) ||
                p_partitsRes.get(iPartit).getEquipVisitant().contains(p_nomEquip)) {
                canviarColorText(p_context, views, p_nomEquip, p_partitsRes.get(iPartit).getEquipLocal(), R.id.tvPartitColSeg11);
                canviarColorText(p_context, views, p_nomEquip, p_partitsRes.get(iPartit).getEquipVisitant(), R.id.tvPartitColSeg13);
                views.setTextViewText(R.id.tvPartitColSeg11, tallarText(p_context, p_partitsRes.get(iPartit).getEquipLocal(), CTE_TAMANY_EQUIP));
                views.setTextViewText(R.id.tvPartitColSeg12, resultat2String(p_context, p_partitsRes.get(iPartit).getResultatLocal(), p_partitsRes.get(iPartit).getResultatVisitant()));
                views.setTextViewText(R.id.tvPartitColSeg13, tallarText(p_context, p_partitsRes.get(iPartit).getEquipVisitant(), CTE_TAMANY_EQUIP));
                //
                views.setTextViewText(R.id.tvPartitSegCol101s, validarNullData(p_context, p_partitsRes.get(iPartit)));
                views.setTextViewText(R.id.tvPartitSegCol102s, tallarText(p_context, validarNullPavello(p_context, p_partitsRes.get(iPartit)), CTE_TAMANY_PAVELLO));
                try {
                    enllacPavello(p_context, views, R.id.tvPartitSegCol102s, p_partitsRes.get(iPartit).getPavello().getUrlMaps());
                } catch (Exception ex) {
                }
            }
            //
            iPartit++;
        }
    }
    public static void omplirDadesPartitsAnteriors(Context p_context, RemoteViews views, ArrayList<Partit> p_partitsRes, String p_nomEquip) {
        // PARTITS JORNADA ANTERIOR
        int iPartit = 0;
        // Partit 1
        canviarColorText(p_context, views, p_nomEquip, p_partitsRes.get(iPartit).getEquipLocal(), R.id.tvPartitCol11);
        canviarColorText(p_context, views, p_nomEquip, p_partitsRes.get(iPartit).getEquipVisitant(), R.id.tvPartitCol13);
        views.setTextViewText(R.id.tvPartitCol11, tallarText(p_context, p_partitsRes.get(iPartit).getEquipLocal(), CTE_TAMANY_EQUIP));
        views.setTextViewText(R.id.tvPartitCol12, resultat2String(p_context, p_partitsRes.get(iPartit).getResultatLocal(), p_partitsRes.get(iPartit).getResultatVisitant()));
        views.setTextViewText(R.id.tvPartitCol13, tallarText(p_context, p_partitsRes.get(iPartit).getEquipVisitant(), CTE_TAMANY_EQUIP));
        // Partit 2
        iPartit++;
        canviarColorText(p_context, views, p_nomEquip, p_partitsRes.get(iPartit).getEquipLocal(), R.id.tvPartitCol21);
        canviarColorText(p_context, views, p_nomEquip, p_partitsRes.get(iPartit).getEquipVisitant(), R.id.tvPartitCol23);
        views.setTextViewText(R.id.tvPartitCol21, tallarText(p_context, p_partitsRes.get(iPartit).getEquipLocal(), CTE_TAMANY_EQUIP));
        views.setTextViewText(R.id.tvPartitCol22, resultat2String(p_context, p_partitsRes.get(iPartit).getResultatLocal(), p_partitsRes.get(iPartit).getResultatVisitant()));
        views.setTextViewText(R.id.tvPartitCol23, tallarText(p_context, p_partitsRes.get(iPartit).getEquipVisitant(), CTE_TAMANY_EQUIP));
        // Partit 3
        iPartit++;
        canviarColorText(p_context, views, p_nomEquip, p_partitsRes.get(iPartit).getEquipLocal(), R.id.tvPartitCol31);
        canviarColorText(p_context, views, p_nomEquip, p_partitsRes.get(iPartit).getEquipVisitant(), R.id.tvPartitCol33);
        views.setTextViewText(R.id.tvPartitCol31, tallarText(p_context, p_partitsRes.get(iPartit).getEquipLocal(), CTE_TAMANY_EQUIP));
        views.setTextViewText(R.id.tvPartitCol32, resultat2String(p_context, p_partitsRes.get(iPartit).getResultatLocal(), p_partitsRes.get(iPartit).getResultatVisitant()));
        views.setTextViewText(R.id.tvPartitCol33, tallarText(p_context, p_partitsRes.get(iPartit).getEquipVisitant(), CTE_TAMANY_EQUIP));
        // Partit 4
        iPartit++;
        if (p_partitsRes.size() > 3) {  // Torneig amb més de 3 partits
            canviarColorText(p_context, views, p_nomEquip, p_partitsRes.get(iPartit).getEquipLocal(), R.id.tvPartitCol41);
            canviarColorText(p_context, views, p_nomEquip, p_partitsRes.get(iPartit).getEquipVisitant(), R.id.tvPartitCol43);
            views.setTextViewText(R.id.tvPartitCol41, tallarText(p_context, p_partitsRes.get(iPartit).getEquipLocal(), CTE_TAMANY_EQUIP));
            views.setTextViewText(R.id.tvPartitCol42, resultat2String(p_context, p_partitsRes.get(iPartit).getResultatLocal(), p_partitsRes.get(iPartit).getResultatVisitant()));
            views.setTextViewText(R.id.tvPartitCol43, tallarText(p_context, p_partitsRes.get(iPartit).getEquipVisitant(), CTE_TAMANY_EQUIP));
        }
    }

    private static void enllacPavello(Context p_context, RemoteViews views, int pIdView, String pUrl) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(pUrl));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                p_context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        views.setOnClickPendingIntent(pIdView, pendingIntent);
    }
    public static void omplirDadesPartitsJornada(Context p_context, RemoteViews views, ArrayList<Partit> p_partits, ArrayList<Partit> p_partitsRes, String p_nomEquip) {
        // PARTITS JORNADA ACTUAL
        int iPartit = 0;
        // Partit 1
        canviarColorText(p_context, views, p_nomEquip, p_partitsRes.get(iPartit).getEquipLocal(), R.id.tvPartitSegCol11);
        canviarColorText(p_context, views, p_nomEquip, p_partitsRes.get(iPartit).getEquipVisitant(), R.id.tvPartitSegCol13);
        views.setTextViewText(R.id.tvPartitSegCol11, tallarText(p_context, p_partits.get(iPartit).getEquipLocal(), CTE_TAMANY_EQUIP));
        views.setTextViewText(R.id.tvPartitSegCol12, resultat2String(p_context, p_partitsRes.get(iPartit).getResultatLocal(), p_partitsRes.get(iPartit).getResultatVisitant()));
        views.setTextViewText(R.id.tvPartitSegCol13, tallarText(p_context, p_partits.get(iPartit).getEquipVisitant(), CTE_TAMANY_EQUIP));
        //
        views.setTextViewText(R.id.tvPartitSegCol101, validarNullData(p_context, p_partits.get(iPartit)));
        views.setTextViewText(R.id.tvPartitSegCol102, tallarText(p_context, validarNullPavello(p_context, p_partits.get(iPartit)), CTE_TAMANY_PAVELLO));
        try {
            enllacPavello(p_context, views, R.id.tvPartitSegCol102, p_partits.get(iPartit).getPavello().getUrlMaps());
        } catch(Exception ex) {}
        // Partit 2
        iPartit++;
        canviarColorText(p_context, views, p_nomEquip, p_partitsRes.get(iPartit).getEquipLocal(), R.id.tvPartitSegCol21);
        canviarColorText(p_context, views, p_nomEquip, p_partitsRes.get(iPartit).getEquipVisitant(), R.id.tvPartitSegCol23);
        views.setTextViewText(R.id.tvPartitSegCol21, tallarText(p_context, p_partits.get(iPartit).getEquipLocal(), CTE_TAMANY_EQUIP));
        views.setTextViewText(R.id.tvPartitSegCol22, resultat2String(p_context, p_partitsRes.get(iPartit).getResultatLocal(), p_partitsRes.get(iPartit).getResultatVisitant()));
        views.setTextViewText(R.id.tvPartitSegCol23, tallarText(p_context, p_partits.get(iPartit).getEquipVisitant(), CTE_TAMANY_EQUIP));
        //
        views.setTextViewText(R.id.tvPartitSegCol201, validarNullData(p_context, p_partits.get(iPartit)));
        views.setTextViewText(R.id.tvPartitSegCol202, tallarText(p_context, validarNullPavello(p_context, p_partits.get(iPartit)), CTE_TAMANY_PAVELLO));
        try {
            enllacPavello(p_context, views, R.id.tvPartitSegCol202, p_partits.get(iPartit).getPavello().getUrlMaps());
        } catch(Exception ex) {}
        // Partit 3
        iPartit++;
        canviarColorText(p_context, views, p_nomEquip, p_partitsRes.get(iPartit).getEquipLocal(), R.id.tvPartitSegCol31);
        canviarColorText(p_context, views, p_nomEquip, p_partitsRes.get(iPartit).getEquipVisitant(), R.id.tvPartitSegCol33);
        views.setTextViewText(R.id.tvPartitSegCol31, tallarText(p_context, p_partits.get(iPartit).getEquipLocal(), CTE_TAMANY_EQUIP));
        views.setTextViewText(R.id.tvPartitSegCol32, resultat2String(p_context, p_partitsRes.get(iPartit).getResultatLocal(), p_partitsRes.get(iPartit).getResultatVisitant()));
        views.setTextViewText(R.id.tvPartitSegCol33, tallarText(p_context, p_partits.get(iPartit).getEquipVisitant(), CTE_TAMANY_EQUIP));
        //
        views.setTextViewText(R.id.tvPartitSegCol301, validarNullData(p_context, p_partits.get(iPartit)));
        views.setTextViewText(R.id.tvPartitSegCol302, tallarText(p_context, validarNullPavello(p_context, p_partits.get(iPartit)), CTE_TAMANY_PAVELLO));
        try {
            enllacPavello(p_context, views, R.id.tvPartitSegCol302, p_partits.get(iPartit).getPavello().getUrlMaps());
        } catch(Exception ex) {}
        // Partit 4
        iPartit++;
        if (p_partitsRes.size() > 3) {  // Torneig amb més de 3 partits
            canviarColorText(p_context, views, p_nomEquip, p_partitsRes.get(iPartit).getEquipLocal(), R.id.tvPartitSegCol41);
            canviarColorText(p_context, views, p_nomEquip, p_partitsRes.get(iPartit).getEquipVisitant(), R.id.tvPartitSegCol43);
            views.setTextViewText(R.id.tvPartitSegCol41, tallarText(p_context, p_partits.get(iPartit).getEquipLocal(), CTE_TAMANY_EQUIP));
            views.setTextViewText(R.id.tvPartitSegCol42, resultat2String(p_context, p_partitsRes.get(iPartit).getResultatLocal(), p_partitsRes.get(iPartit).getResultatVisitant()));
            views.setTextViewText(R.id.tvPartitSegCol43, tallarText(p_context, p_partits.get(iPartit).getEquipVisitant(), CTE_TAMANY_EQUIP));
            //
            views.setTextViewText(R.id.tvPartitSegCol401, validarNullData(p_context, p_partits.get(iPartit)));
            views.setTextViewText(R.id.tvPartitSegCol402, tallarText(p_context, validarNullPavello(p_context, p_partits.get(iPartit)), CTE_TAMANY_PAVELLO));
            try {
                enllacPavello(p_context, views, R.id.tvPartitSegCol402, p_partits.get(iPartit).getPavello().getUrlMaps());
            } catch (Exception ex) {
            }
        }
    }
    private static ArrayList<String[]> convertStr2Classificacio(String strClassificacio) {
        ArrayList<String[]> arrEquips = new ArrayList<String[]>();
        String[] strLinia = strClassificacio.split(Constants.CTE_SEPARADOR_CLASSIFICACIO);
        for (String str : strLinia) {
            arrEquips.add(str.split(Constants.CTE_SEPARADOR_ELEMENT_CLASSIF));
        }
        return  arrEquips;
    }

    private static void canviarTextViewClas(Context p_context, RemoteViews views, String pNomEquip, String pNomEquipPartit, int idText1, int idText2, int idText3, int idText4, int idText5, int idText6) {
        if (pNomEquip.startsWith(pNomEquipPartit)) {
            views.setTextColor(idText1, p_context.getColor(R.color.colorEquip));
            views.setTextColor(idText2, p_context.getColor(R.color.colorEquip));
            views.setTextColor(idText3, p_context.getColor(R.color.colorEquip));
            views.setTextColor(idText4, p_context.getColor(R.color.colorEquip));
            views.setTextColor(idText5, p_context.getColor(R.color.colorEquip));
            views.setTextColor(idText6, p_context.getColor(R.color.colorEquip));
        }
        else {
            views.setTextColor(idText1, p_context.getColor(R.color.colorText));
            views.setTextColor(idText2, p_context.getColor(R.color.colorText));
            views.setTextColor(idText3, p_context.getColor(R.color.colorText));
            views.setTextColor(idText4, p_context.getColor(R.color.colorText));
            views.setTextColor(idText5, p_context.getColor(R.color.colorText));
            views.setTextColor(idText6, p_context.getColor(R.color.colorText));
        }
    }
    public static void omplirClassificacio(Context p_context, RemoteViews views, String p_classificacio, String p_nomEquip) {
        ArrayList<String[]> equipsClassificacio = convertStr2Classificacio(p_classificacio);
        //
        int iTamanyMaxNomsEquips = CTE_MAX_TAMANY_NOM_CLASIFICACIO - calcularTamanyMaxNomsEquips(equipsClassificacio);
        //
        int iLinia = 0;
        views.setTextViewText(R.id.tvClassCol1, p_context.getResources().getString(R.string.simbol_num));
        views.setTextViewText(R.id.tvClassCol2, tallarText(p_context, equipsClassificacio.get(iLinia)[0], iTamanyMaxNomsEquips));
        views.setTextViewText(R.id.tvClassCol3, equipsClassificacio.get(iLinia)[1]);
        views.setTextViewText(R.id.tvClassCol4, equipsClassificacio.get(iLinia)[6]);
        views.setTextViewText(R.id.tvClassCol5, equipsClassificacio.get(iLinia)[7]);
        views.setTextViewText(R.id.tvClassCol6, p_context.getResources().getString(R.string.titol_punts));
        iLinia++;
        canviarTextViewClas(p_context, views, p_nomEquip, equipsClassificacio.get(iLinia)[1],
                R.id.tvClassCol11, R.id.tvClassCol12, R.id.tvClassCol13, R.id.tvClassCol14, R.id.tvClassCol15, R.id.tvClassCol16);
        views.setTextViewText(R.id.tvClassCol11, equipsClassificacio.get(iLinia)[0]);
        views.setTextViewText(R.id.tvClassCol12, tallarText(p_context, equipsClassificacio.get(iLinia)[1], iTamanyMaxNomsEquips));
        views.setTextViewText(R.id.tvClassCol13, omplirText(p_context, equipsClassificacio.get(iLinia)[2], CTE_TAMANY_SETS));
        views.setTextViewText(R.id.tvClassCol14, omplirText(p_context, equipsClassificacio.get(iLinia)[7], CTE_TAMANY_SETS));
        views.setTextViewText(R.id.tvClassCol15, omplirText(p_context, equipsClassificacio.get(iLinia)[8], CTE_TAMANY_SETS));
        views.setTextViewText(R.id.tvClassCol16, omplirText(p_context, equipsClassificacio.get(iLinia)[11], CTE_TAMANY_PUNTS));
        iLinia++;
        canviarTextViewClas(p_context, views, p_nomEquip, equipsClassificacio.get(iLinia)[1],
                R.id.tvClassCol21, R.id.tvClassCol22, R.id.tvClassCol23, R.id.tvClassCol24, R.id.tvClassCol25, R.id.tvClassCol26);
        views.setTextViewText(R.id.tvClassCol21, equipsClassificacio.get(iLinia)[0]);
        views.setTextViewText(R.id.tvClassCol22, tallarText(p_context, equipsClassificacio.get(iLinia)[1], iTamanyMaxNomsEquips));
        views.setTextViewText(R.id.tvClassCol23, omplirText(p_context, equipsClassificacio.get(iLinia)[2], CTE_TAMANY_SETS));
        views.setTextViewText(R.id.tvClassCol24, omplirText(p_context, equipsClassificacio.get(iLinia)[7], CTE_TAMANY_SETS));
        views.setTextViewText(R.id.tvClassCol25, omplirText(p_context, equipsClassificacio.get(iLinia)[8], CTE_TAMANY_SETS));
        views.setTextViewText(R.id.tvClassCol26, omplirText(p_context, equipsClassificacio.get(iLinia)[11], CTE_TAMANY_PUNTS));
        iLinia++;
        canviarTextViewClas(p_context, views, p_nomEquip, equipsClassificacio.get(iLinia)[1],
                R.id.tvClassCol31, R.id.tvClassCol32, R.id.tvClassCol33, R.id.tvClassCol34, R.id.tvClassCol35, R.id.tvClassCol36);
        views.setTextViewText(R.id.tvClassCol31, equipsClassificacio.get(iLinia)[0]);
        views.setTextViewText(R.id.tvClassCol32, tallarText(p_context, equipsClassificacio.get(iLinia)[1], iTamanyMaxNomsEquips));
        views.setTextViewText(R.id.tvClassCol33, omplirText(p_context, equipsClassificacio.get(iLinia)[2], CTE_TAMANY_SETS));
        views.setTextViewText(R.id.tvClassCol34, omplirText(p_context, equipsClassificacio.get(iLinia)[7], CTE_TAMANY_SETS));
        views.setTextViewText(R.id.tvClassCol35, omplirText(p_context, equipsClassificacio.get(iLinia)[8], CTE_TAMANY_SETS));
        views.setTextViewText(R.id.tvClassCol36, omplirText(p_context, equipsClassificacio.get(iLinia)[11], CTE_TAMANY_PUNTS));
        iLinia++;
        canviarTextViewClas(p_context, views, p_nomEquip, equipsClassificacio.get(iLinia)[1],
                R.id.tvClassCol41, R.id.tvClassCol42, R.id.tvClassCol43, R.id.tvClassCol44, R.id.tvClassCol45, R.id.tvClassCol46);
        views.setTextViewText(R.id.tvClassCol41, equipsClassificacio.get(iLinia)[0]);
        views.setTextViewText(R.id.tvClassCol42, tallarText(p_context, equipsClassificacio.get(iLinia)[1], iTamanyMaxNomsEquips));
        views.setTextViewText(R.id.tvClassCol43, omplirText(p_context, equipsClassificacio.get(iLinia)[2], CTE_TAMANY_SETS));
        views.setTextViewText(R.id.tvClassCol44, omplirText(p_context, equipsClassificacio.get(iLinia)[7], CTE_TAMANY_SETS));
        views.setTextViewText(R.id.tvClassCol45, omplirText(p_context, equipsClassificacio.get(iLinia)[8], CTE_TAMANY_SETS));
        views.setTextViewText(R.id.tvClassCol46, omplirText(p_context, equipsClassificacio.get(iLinia)[11], CTE_TAMANY_PUNTS));
        iLinia++;
        if (equipsClassificacio.size() > 5) {  // Torneig amb més de 4 equips

            canviarTextViewClas(p_context, views, p_nomEquip, equipsClassificacio.get(iLinia)[1],
                    R.id.tvClassCol51, R.id.tvClassCol52, R.id.tvClassCol53, R.id.tvClassCol54, R.id.tvClassCol55, R.id.tvClassCol56);
            views.setTextViewText(R.id.tvClassCol51, equipsClassificacio.get(iLinia)[0]);
            views.setTextViewText(R.id.tvClassCol52, tallarText(p_context, equipsClassificacio.get(iLinia)[1], iTamanyMaxNomsEquips));
            views.setTextViewText(R.id.tvClassCol53, omplirText(p_context, equipsClassificacio.get(iLinia)[2], CTE_TAMANY_SETS));
            views.setTextViewText(R.id.tvClassCol54, omplirText(p_context, equipsClassificacio.get(iLinia)[7], CTE_TAMANY_SETS));
            views.setTextViewText(R.id.tvClassCol55, omplirText(p_context, equipsClassificacio.get(iLinia)[8], CTE_TAMANY_SETS));
            views.setTextViewText(R.id.tvClassCol56, omplirText(p_context, equipsClassificacio.get(iLinia)[11], CTE_TAMANY_PUNTS));
        }
        iLinia++;
        if (equipsClassificacio.size() > 6) {  // Torneig amb més de 5 equips
            canviarTextViewClas(p_context, views, p_nomEquip, equipsClassificacio.get(iLinia)[1],
                    R.id.tvClassCol61, R.id.tvClassCol62, R.id.tvClassCol63, R.id.tvClassCol64, R.id.tvClassCol65, R.id.tvClassCol66);
            views.setTextViewText(R.id.tvClassCol61, equipsClassificacio.get(iLinia)[0]);
            views.setTextViewText(R.id.tvClassCol62, tallarText(p_context, equipsClassificacio.get(iLinia)[1], iTamanyMaxNomsEquips));
            views.setTextViewText(R.id.tvClassCol63, omplirText(p_context, equipsClassificacio.get(iLinia)[2], CTE_TAMANY_SETS));
            views.setTextViewText(R.id.tvClassCol64, omplirText(p_context, equipsClassificacio.get(iLinia)[7], CTE_TAMANY_SETS));
            views.setTextViewText(R.id.tvClassCol65, omplirText(p_context, equipsClassificacio.get(iLinia)[8], CTE_TAMANY_SETS));
            views.setTextViewText(R.id.tvClassCol66, omplirText(p_context, equipsClassificacio.get(iLinia)[11], CTE_TAMANY_PUNTS));
        }
        iLinia++;
        if (equipsClassificacio.size() > 7) {  // Torneig amb més de 6 equips
            canviarTextViewClas(p_context, views, p_nomEquip, equipsClassificacio.get(iLinia)[1],
                    R.id.tvClassCol71, R.id.tvClassCol72, R.id.tvClassCol73, R.id.tvClassCol74, R.id.tvClassCol75, R.id.tvClassCol76);
            views.setTextViewText(R.id.tvClassCol71, equipsClassificacio.get(iLinia)[0]);
            views.setTextViewText(R.id.tvClassCol72, tallarText(p_context, equipsClassificacio.get(iLinia)[1], iTamanyMaxNomsEquips));
            views.setTextViewText(R.id.tvClassCol73, omplirText(p_context, equipsClassificacio.get(iLinia)[2], CTE_TAMANY_SETS));
            views.setTextViewText(R.id.tvClassCol74, omplirText(p_context, equipsClassificacio.get(iLinia)[7], CTE_TAMANY_SETS));
            views.setTextViewText(R.id.tvClassCol75, omplirText(p_context, equipsClassificacio.get(iLinia)[8], CTE_TAMANY_SETS));
            views.setTextViewText(R.id.tvClassCol76, omplirText(p_context, equipsClassificacio.get(iLinia)[11], CTE_TAMANY_PUNTS));
        }
        iLinia++;
        if (equipsClassificacio.size() > 8) {  // Torneig amb més de 7 equips
            canviarTextViewClas(p_context, views, p_nomEquip, equipsClassificacio.get(iLinia)[1],
                    R.id.tvClassCol81, R.id.tvClassCol82, R.id.tvClassCol83, R.id.tvClassCol84, R.id.tvClassCol85, R.id.tvClassCol86);
            views.setTextViewText(R.id.tvClassCol81, equipsClassificacio.get(iLinia)[0]);
            views.setTextViewText(R.id.tvClassCol82, tallarText(p_context, equipsClassificacio.get(iLinia)[1], iTamanyMaxNomsEquips));
            views.setTextViewText(R.id.tvClassCol83, omplirText(p_context, equipsClassificacio.get(iLinia)[2], CTE_TAMANY_SETS));
            views.setTextViewText(R.id.tvClassCol84, omplirText(p_context, equipsClassificacio.get(iLinia)[7], CTE_TAMANY_SETS));
            views.setTextViewText(R.id.tvClassCol85, omplirText(p_context, equipsClassificacio.get(iLinia)[8], CTE_TAMANY_SETS));
            views.setTextViewText(R.id.tvClassCol86, omplirText(p_context, equipsClassificacio.get(iLinia)[11], CTE_TAMANY_PUNTS));
        }
    }

    private static int calcularTamanyMaxNomsEquips(ArrayList<String[]> p_equipsClassificacio) {
        int iTamnyMax = 0;
        for (String[] linia : p_equipsClassificacio) {
            if (iTamnyMax < linia[1].length())
                iTamnyMax = linia[1].length();
        }
        if (iTamnyMax > CTE_TAMANY_NOM_EQUIP)
            iTamnyMax = CTE_TAMANY_NOM_EQUIP;
        return iTamnyMax;
    }

    public static void refrescarWidgetUpdate(Context p_context, RemoteViews views, int p_appWidgetId) {
        //Create an Intent with the AppWidgetManager.ACTION_APPWIDGET_UPDATE action//
        Intent intentUpdate = new Intent(p_context, AppVoleiWDG.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        //Update the current widget instance only, by creating an array that contains the widget’s unique ID//
        int[] idArray = new int[]{p_appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);
        //Wrap the intent as a PendingIntent, using PendingIntent.getBroadcast()//
        PendingIntent pendingUpdate = PendingIntent.getBroadcast(
                p_context, p_appWidgetId, intentUpdate,
                PendingIntent.FLAG_UPDATE_CURRENT);
        //Send the pending intent in response to the user tapping the ‘Update’ TextView//
        views.setOnClickPendingIntent(R.id.ic_refrescar, pendingUpdate);
    }
    public static void enllacarXarxesSocials(Context p_context, RemoteViews views, Torneig p_torneig) {
        String idTorneig = "";
        if (p_torneig != null)
            idTorneig = "?torneo=" + p_torneig.getIdTorneig();
        // Fcvb
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(p_context.getString(R.string.urlFCVB) + idTorneig));
        PendingIntent pendingIntent0 = PendingIntent.getActivity(p_context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.ivFcvb, pendingIntent0);
        // Enviar email - Gmail
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(p_context.getString(R.string.urlGmail)));
        PendingIntent pendingIntent1 = PendingIntent.getActivity(p_context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.ivGmail, pendingIntent1);
        // Facebook
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(p_context.getString(R.string.urlFacebook)));
        PendingIntent pendingIntent2 = PendingIntent.getActivity(p_context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.ivFacebook, pendingIntent2);
        // Twitter
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(p_context.getString(R.string.urlTwitter)));
        PendingIntent pendingIntent3 = PendingIntent.getActivity(p_context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.ivTwitter, pendingIntent3);
        // Instagram
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(p_context.getString(R.string.urlInstagram)));
        PendingIntent pendingIntent4 = PendingIntent.getActivity(p_context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.ivInstagram, pendingIntent4);
    }
}
