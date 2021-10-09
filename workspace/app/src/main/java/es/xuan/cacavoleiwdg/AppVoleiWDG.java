package es.xuan.cacavoleiwdg;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import java.util.Date;
import es.xuan.cacavoleiwdg.logs.LogCACA;
import es.xuan.cacavoleiwdg.migracio.VBMigracioFCVB;
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

    private static int m_jornada = 1;

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
            String[] strWidgetText = tvWidgetText.split(Constants.CTE_SEPARADOR_TEXT);
            String[] strWidgetEquip = strWidgetText[1].split(" - ");
            try {
                int idTorneig = Integer.parseInt(strWidgetEquip[3]);
                // Tornejos de la FCVB i RFEVB
                Torneig[] tornejos = llegirTorneig(idTorneig);       // Jornada actual i anterior
                //
                omplirDades(p_context, p_appWidgetManager, p_appWidgetId, tornejos, strWidgetEquip[0]);
                //
            } catch (Exception ex) {
                LogCACA.afegirLog("actualitzarDades - Error: " + ex);
            }
        }
    }

    private static void omplirDades(Context p_context, AppWidgetManager p_appWidgetManager,
                                    int p_appWidgetId, Torneig[] p_tornejos, String p_nomEquip) {
        RemoteViews views = new RemoteViews(p_context.getPackageName(), R.layout.app_w_g_volei);
        /*
            p_tornejos[0] - > Torneig Anterior
            p_tornejos[1] - > Torneig Actual
         */
        // DIVISIÓ
        Vista.omplirDadesDivisio(p_context, views, p_tornejos[1]);
        // GRUP
        Vista.omplirDadesGrup(p_context, views, p_tornejos[1]);
        // JORNADA I DATA
        Vista.omplirDadesJornada(p_context, views, p_tornejos[1]);
        // PARTITS JORNADA ACTUAL
        Vista.omplirDadesPartitsJornada(p_context, views, p_tornejos[1].getPartitsTorneig().getPartitsJugats(), p_tornejos[1].getPartitsTorneig().getPartitsResultats(), p_nomEquip);
        // PARTITS JORNADA ANTERIOR
        Vista.omplirDadesPartitsAnteriors(p_context, views, p_tornejos[0].getPartitsTorneig().getPartitsResultats(), p_nomEquip);
        // PARTITS JORNADA SEGÜENT
        Vista.omplirDadesPartitsSeguents(p_context, views, p_tornejos[1].getPartitsTorneig().getPartitsProxims(), p_nomEquip);
        // CLASSIFICACIÓ
        Vista.omplirClassificacio(p_context, views, p_tornejos[1].getClassificacio(), p_nomEquip);
        // Refrescar WIDGET
        Vista.refrescarWidgetUpdate(p_context, views, p_appWidgetId);
        //
        Vista.enllacarXarxesSocials(p_context, views, p_tornejos[1]);
        //Request that the AppWidgetManager updates the application widget
        p_appWidgetManager.updateAppWidget(p_appWidgetId, views);
    }

    private static Torneig[] llegirTorneig(int p_idTorneig) {
        /*
            4368 - 2ª DIVISIÓ JUVENIL FEMENINA  C
            4398 - 3ª DIVISIÓ JUVENIL FEMENINA  I
            4402 - 2ª DIVISIÓ INFANTIL FEMENINA A
        */
        if (p_idTorneig == 2122)
            return llegirTorneigFCVB21(p_idTorneig);
        else if (p_idTorneig < 9000)
            // Torneig de la FCVB
            return llegirTorneigFCVB(p_idTorneig);
        else
            // Torneig de la RFEVB
            return llegirTorneigRFEVB(p_idTorneig);
    }

    private static Torneig[] llegirTorneigFCVB21(int p_idTorneig) {
        Torneig[] tornejos = new Torneig[2];
        return tornejos;
    }

    private static Torneig[] llegirTorneigRFEVB(int p_idTorneig) {
        Torneig[] tornejos = new Torneig[2];
        //
        VBMigracioRFEVB migracioActual = new VBMigracioRFEVB();
        Torneig torneigActual = migracioActual.getTornejos(p_idTorneig);
        //
        try {
            migracioActual.getResultatsTorneig(torneigActual, 0);
            /* TODO
                CALCULAR JORNADA
             */
            //Date dataFin = Utils.addDies2Diumenge(pFiltre.getDataPartit().getTime());   // Cerca només el partits de la setmana de dilluns a diumenge

        } catch (Exception ex) {
            Log.e("llegirTorneigRFEVB", ex.toString());
        }
       //
        tornejos[0] = torneigActual;
        tornejos[1] = torneigActual;
        //
        Log.d("RFEVB - JornadaAnterior", "" + tornejos[0].getPartitsTorneig().getNumJornada());
        LogCACA.afegirLog("RFEVB - JornadaAnterior" + " - " + tornejos[0].getPartitsTorneig().getNumJornada() + " - " +
                tornejos[0].getNomCategoria() + " - " + tornejos[0].getNomDivisio() + " - " + tornejos[0].getNomGrup());

        Log.d("RFEVB - JornadaActual", "" + tornejos[1].getPartitsTorneig().getNumJornada());
        LogCACA.afegirLog("RFEVB - JornadaActual" + " - " + tornejos[1].getPartitsTorneig().getNumJornada() + " - " +
                tornejos[1].getNomCategoria() + " - " + tornejos[1].getNomDivisio() + " - " + tornejos[1].getNomGrup());
        //
        return tornejos;
    }

    private static Torneig[] llegirTorneigFCVB(int p_idTorneig) {
        Torneig[] tornejos = new Torneig[2];
        boolean isJornadaActual = false;
        //
        Torneig torneigActual = new Torneig();
        torneigActual.setIdTorneig(p_idTorneig);
        //
        Torneig torneigAnterior = new Torneig();
        torneigAnterior.setIdTorneig(p_idTorneig);
        //
        //VBMigracioFCVB migracioAnterior = new VBMigracioFCVB();
        VBMigracioFCVB migracioActual = new VBMigracioFCVB();
        //
        Log.d("FCVB - JornadaInicial", "" + m_jornada);
        // Torneig de la Jornada anterior
        migracioActual.getResultatsTorneig(torneigAnterior, (m_jornada > 1 ? m_jornada - 1 : 1)); // Mínim jornada =1
        do {
            // Torneig de la Jornada actual
            migracioActual.getResultatsTorneig(torneigActual, m_jornada++); // Mínim jornada = 1
            isJornadaActual = comprovarJornadaActual(torneigActual.getPartitsTorneig().getDataJornada());
            if (isJornadaActual) {
                // Desplaçar jornades
                torneigAnterior = torneigActual;
                torneigActual = new Torneig();
                torneigActual.setIdTorneig(p_idTorneig);
            }
            Log.d("FCVB - JornadaBucle", "" + m_jornada);
        } while (isJornadaActual);
        //
        tornejos[0] = torneigAnterior;
        tornejos[1] = torneigActual;
        //
        m_jornada = torneigActual.getPartitsTorneig().getNumJornada();
        //
        Log.d("FCVB - JornadaAnterior", "" + tornejos[0].getPartitsTorneig().getNumJornada());
        LogCACA.afegirLog("FCVB - JornadaAnterior" + " - " + tornejos[0].getPartitsTorneig().getNumJornada() + " - " +
                tornejos[0].getNomCategoria() + " - " + tornejos[0].getNomDivisio() + " - " + tornejos[0].getNomGrup());

        Log.d("FCVB - JornadaActual", "" + tornejos[1].getPartitsTorneig().getNumJornada());
        LogCACA.afegirLog("FCVB - JornadaActual" + " - " + tornejos[1].getPartitsTorneig().getNumJornada() + " - " +
                tornejos[1].getNomCategoria() + " - " + tornejos[1].getNomDivisio() + " - " + tornejos[1].getNomGrup());
        //
        return tornejos;
    }

    private static boolean comprovarJornadaActual(String p_dataJornada) {
        Date dataAvui = new Date();
        Date dataJornada = Utils.string2Data(p_dataJornada + " 23:59");  // Jornada a final de dia
        return dataAvui.after(dataJornada) || Utils.dataIguals(dataAvui, dataJornada);
    }
}

