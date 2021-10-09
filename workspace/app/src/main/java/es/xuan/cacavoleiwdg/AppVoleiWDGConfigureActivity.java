package es.xuan.cacavoleiwdg;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import es.xuan.cacavoleiwdg.varis.Constants;

/**
 * The configuration screen for the {@link AppVoleiWDG AppVoleiWDG} AppWidget.
 */
public class AppVoleiWDGConfigureActivity extends Activity {

    private static final String PREFS_NAME = "es.xuan.cacavoleiwdg.AppVoleiWDG";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private static EditText mAppWidgetTorneig;

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = AppVoleiWDGConfigureActivity.this;

            // When the button is clicked, store the string locally
            savePref(context, mAppWidgetId);

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            AppVoleiWDG.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            //
            finish();
        }
    };
    View.OnClickListener mOnClickListener1 = new View.OnClickListener() {
        public void onClick(View v) {
            String idTorneo = mAppWidgetTorneig.getText().toString();
            Intent viewIntent = null;
            if (!idTorneo.equals(""))
                // Fcvb
                viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.urlFCVB) + "?torneo=" + idTorneo));
            else
                // Fcvb
                viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.urlFCVB)));
            //
            startActivity(viewIntent);
        }
    };

    public AppVoleiWDGConfigureActivity() {
        super();
    }

    static void savePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        String strIdWidgetEquip = mAppWidgetTorneig.getText().toString();
        //String strIdTorneig = obtenirIdTorneig(mAppWidgetTorneig.getText().toString());
        //String strIdColor = mAppWidgetColor.getText().toString();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId,
                "0" + Constants.CTE_SEPARADOR_TEXT +
                        strIdWidgetEquip + Constants.CTE_SEPARADOR_TEXT +
                        "ROSA"
                );
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return "";
        }
    }

    static void deletePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);
        //
        setContentView(R.layout.app_volei_w_d_g_configure);
        mAppWidgetTorneig = (EditText) findViewById(R.id.equips_list);
        //mAppWidgetColor = (TextView) findViewById(R.id.tvWidgetText);
        //mAppWidgetText = (TextView) findViewById(R.id.etIdWidget);
        //
        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);
        //
        findViewById(R.id.ivFcvb).setOnClickListener(mOnClickListener1);
        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
        //mAppWidgetText.setText(loadTitlePref(AppVoleiWDGConfigureActivity.this, mAppWidgetId));
        //mAppWidgetText.setText("" + mAppWidgetId);
        //
        iniCercaEquips();
    }
    private void iniCercaEquips() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.custom_autocomplete,
                getResources().getStringArray(R.array.equips));
        AutoCompleteTextView textView = (AutoCompleteTextView)findViewById(R.id.equips_list);
        textView.setAdapter(adapter);
    }
}

