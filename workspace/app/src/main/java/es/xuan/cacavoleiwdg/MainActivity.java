package es.xuan.cacavoleiwdg;

import android.content.Intent;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import es.xuan.cacavoleiwdg.logs.LogCACA;
import es.xuan.cacavoleiwdg.migracio.VBMigracioFCVB;
import es.xuan.cacavoleiwdg.migracio.VBMigracioFCVB21;
import es.xuan.cacavoleiwdg.model.Torneig;
import es.xuan.cacavoleiwdg.model.Tornejos;
import es.xuan.cacavoleiwdg.varis.Constants;
import es.xuan.cacavoleiwdg.varis.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        inicialitzar();
        //
        iniXarxesSocials();
        //
        iniTornejos();
        //
        finalitzar();
    }

    private void iniTornejos() {
        //VBMigracioFCVB migracio = new VBMigracioFCVB();
        VBMigracioFCVB21 migracio = new VBMigracioFCVB21();
        migracio.llistatTornejosEquips();
    }
    private void iniXarxesSocials() {
        final Vibrator vibr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        // FCVB
        ImageView button1 = (ImageView)findViewById(R.id.ivFcvb);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Utils.vibrar(vibr, Constants.CTE_VIBRATION_MS);
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.urlFCVB)));
                startActivity(viewIntent);
            }
        });
        // Enviar email - Gmail
        ImageView button2 = (ImageView)findViewById(R.id.ivGmail);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Utils.vibrar(vibr, Constants.CTE_VIBRATION_MS);
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.urlGmail)));
                startActivity(viewIntent);
            }
        });
        // Facebook
        ImageView button3 = (ImageView)findViewById(R.id.ivFacebook);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Utils.vibrar(vibr, Constants.CTE_VIBRATION_MS);
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.urlFacebook)));
                startActivity(viewIntent);
            }
        });
        // Twitter
        ImageView button4 = (ImageView)findViewById(R.id.ivTwitter);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Utils.vibrar(vibr, Constants.CTE_VIBRATION_MS);
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.urlTwitter)));
                startActivity(viewIntent);
            }
        });
        // Instagram
        ImageView button5 = (ImageView)findViewById(R.id.ivInstagram);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Utils.vibrar(vibr, Constants.CTE_VIBRATION_MS);
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.urlInstagram)));
                startActivity(viewIntent);
            }
        });
    }

    private void comprovarPermissos() {
        //
        if (!LogCACA.isWriteStoragePermissionGranted(this))
            Log.e("comprovarPermissos","isWriteStoragePermissionGranted KO");
        if (!LogCACA.isReadStoragePermissionGranted(this))
            Log.e("comprovarPermissos","isReadStoragePermissionGranted KO");
    }
    private void finalitzar() {
        LogCACA.afegirLog("Fi");
    }

    private void inicialitzar() {
        comprovarPermissos();
        LogCACA.afegirLog("Inici");
    }
}