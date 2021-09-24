package es.xuan.cacavoleiwdg.logs;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import es.xuan.cacavoleiwdg.varis.Constants;
import es.xuan.cacavoleiwdg.varis.Utils;

public class LogCACA {
    public static boolean isReadStoragePermissionGranted(Activity pActivity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (pActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("","Permission is granted1");
                return true;
            } else {
                Log.v("","Permission is revoked1 -> granted1");
                ActivityCompat.requestPermissions(pActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("","Permission is granted1");
            return true;
        }
    }

    public static boolean isWriteStoragePermissionGranted(Activity pActivity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (pActivity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("","Permission is granted2");
                return true;
            } else {

                Log.v("","Permission is revoked2 -> granted2");
                ActivityCompat.requestPermissions(pActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("","Permission is granted2");
            return true;
        }
    }
    public static void afegirLog(String text) {
        File log;
        log = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + Constants.CTE_PATH_APPS);
        if (!log.exists())
            log.mkdir();
        log = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + Constants.CTE_PATH_CACAVOLEI);
        if (!log.exists())
            log.mkdir();
        File logFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + Constants.CTE_PATH_CACAVOLEI_LOGS);
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                Log.e("appendLog", e.getMessage());
                e.printStackTrace();
            }
        }
        try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(Utils.data2StringComp(new Date()));
            buf.append(" - ");
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            Log.e("appendLog", e.getMessage());
            e.printStackTrace();
        }
    }
}
