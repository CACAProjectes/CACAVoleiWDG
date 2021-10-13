package es.xuan.cacavoleiwdg;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import org.json.JSONException;

import java.util.Date;
import es.xuan.cacavoleiwdg.logs.LogCACA;
import es.xuan.cacavoleiwdg.migracio.VBMigracioFCVB;
import es.xuan.cacavoleiwdg.migracio.VBMigracioFCVB21;
import es.xuan.cacavoleiwdg.migracio.VBMigracioRFEVB;
import es.xuan.cacavoleiwdg.model.Torneig;
import es.xuan.cacavoleiwdg.model.Tornejos;
import es.xuan.cacavoleiwdg.varis.Constants;
import es.xuan.cacavoleiwdg.varis.Utils;
import es.xuan.cacavoleiwdg.view.Vista;
/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link AppVoleiWDGConfigureActivity AppVoleiWDGConfigureActivity}
 */
public class AppVoleiWDG extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        LogCACA.afegirLog("Inici");
        Log.d("updateAppWidget", "Inici");
        //
        actualitzarDades(context, appWidgetManager, appWidgetId);
        //
        LogCACA.afegirLog("Fi");
        Log.d("updateAppWidget", "Fi");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            AppVoleiWDGConfigureActivity.deletePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    private static void actualitzarDades(Context p_context, AppWidgetManager p_appWidgetManager, int p_appWidgetId) {
        //
        String tvWidgetText = AppVoleiWDGConfigureActivity.loadPref(p_context, p_appWidgetId);
        if (tvWidgetText!=null && !tvWidgetText.equals("")) {
            //
            String[] strUrlsTornejos = p_context.getResources().getStringArray(R.array.urlEquips);
            String[] strWidgetText = tvWidgetText.split(Constants.CTE_SEPARADOR_TEXT);
            String[] strWidgetEquip = strWidgetText[1].split(" - ");
            try {
                // Tornejos de la FCVB i RFEVB
                Torneig tornejos = llegirTorneig(strUrlsTornejos, strWidgetEquip);       // Jornada actual i anterior
                //
                omplirDades(p_context, p_appWidgetManager, p_appWidgetId, tornejos, strWidgetEquip);
                //
            } catch (Exception ex) {
                LogCACA.afegirLog("actualitzarDades - Error: " + ex);
            }
        }
    }

    private static void omplirDades(Context p_context, AppWidgetManager p_appWidgetManager,
                                    int p_appWidgetId, Torneig p_tornejos, String[] pDadesEquip) {
        RemoteViews views = new RemoteViews(p_context.getPackageName(), R.layout.app_w_g_volei);
        // CV RUBI - INF - Grup: E - 2122
        // DIVISIÓ
        if (p_tornejos.getNomDivisio() == null || p_tornejos.getNomDivisio().equals(""))
            p_tornejos.setNomDivisio(pDadesEquip[1]);
        Vista.omplirDadesDivisio(p_context, views, p_tornejos);
        // GRUP
        if (p_tornejos.getNomGrup() == null || p_tornejos.getNomGrup().equals(""))
            p_tornejos.setNomGrup(pDadesEquip[2]);
        Vista.omplirDadesGrup(p_context, views, p_tornejos);
        // JORNADA I DATA
        Vista.omplirDadesJornada(p_context, views, p_tornejos);
        // PARTITS JORNADA ACTUAL
        Vista.omplirDadesPartitsJornada(p_context, views, p_tornejos.getPartitsTorneig().getPartitsResultats(), p_tornejos.getPartitsTorneig().getPartitsResultats(), pDadesEquip[0]);
        // PARTITS JORNADA ANTERIOR
        Vista.omplirDadesPartitsAnteriors(p_context, views, p_tornejos.getPartitsTorneig().getPartitsJugats(), pDadesEquip[0]);
        // PARTITS JORNADA SEGÜENT
        Vista.omplirDadesPartitsSeguents(p_context, views, p_tornejos.getPartitsTorneig().getPartitsProxims(), pDadesEquip[0]);
        // CLASSIFICACIÓ
        //Vista.omplirClassificacio(p_context, views, p_tornejos.getClassificacio(), p_nomEquip);
        // Refrescar WIDGET
        Vista.refrescarWidgetUpdate(p_context, views, p_appWidgetId);
        //
        Vista.enllacarXarxesSocials(p_context, views, p_tornejos);
        //Request that the AppWidgetManager updates the application widget
        p_appWidgetManager.updateAppWidget(p_appWidgetId, views);
    }

    private static Torneig llegirTorneig(String[] pUrlsTornejos, String[] p_nomTorneig) {
        /*
            [1..9]      RFEVB
            [10..100]   FCVB
        */
        int idTorneig = Utils.stringToInt(p_nomTorneig[3]);
        if (idTorneig > 4400 && idTorneig < 4600)
            // Torneig de la RFEVB
            return llegirTorneigRFEVB(pUrlsTornejos, p_nomTorneig); // <item>RFEVB - Liga Iberdrola - A - 1</item>
        // Torneig de la FCVB
        return llegirTorneigFCVB21(pUrlsTornejos, p_nomTorneig);    // <item>FCVOLEI - LLIGA CATALANA DIVISIO DHONOR FEMENINA - A - 10</item>
    }

    private static Torneig llegirTorneigFCVB21(String[] pUrlsTornejos, String[] p_nomTorneig) {
        //
        VBMigracioFCVB21 migracioActual = new VBMigracioFCVB21();
        Torneig torneigActual = migracioActual.getTornejos(pUrlsTornejos, p_nomTorneig);
        // Torneig de la Jornada
        try {
            migracioActual.getResultatsTorneig(torneigActual); //
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return torneigActual;
    }

    private static Torneig llegirTorneigRFEVB(String[] pUrlsTornejos, String[] p_nomTorneig) {
        //
        VBMigracioRFEVB migracioActual = new VBMigracioRFEVB();
        Torneig torneigActual = migracioActual.getTornejos(pUrlsTornejos, p_nomTorneig);
        // Torneig de la Jornada
        try {
            migracioActual.getResultatsTorneig(torneigActual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return torneigActual;
    }

}

