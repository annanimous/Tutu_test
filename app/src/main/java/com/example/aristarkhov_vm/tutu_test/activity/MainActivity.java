package com.example.aristarkhov_vm.tutu_test.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.aristarkhov_vm.tutu_test.application.TutuApplication;
import com.example.aristarkhov_vm.tutu_test.model.CitiesFrom;
import com.example.aristarkhov_vm.tutu_test.model.CitiesTo;
import com.example.aristarkhov_vm.tutu_test.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        loadJSONtoClass();
    }

    /**
     * @param view
     * Метод нажатия на кнопку @+id/buttonTimetable
     */
    public void onClickTimetable(View view) {
        Intent intent = new Intent(this, TimetableActivity.class);
        startActivity(intent);
    }

    /**
     * @param view
     * Метод нажатия на кнопку @+id/buttonAbout
     */
    public void onClickAbout(View view) {
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName;
        int verCode = pInfo.versionCode;
        version = "Автор Аристархов В. Версия " + version + "." + verCode;
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * @return
     * Метод получения строки из JSON-файла. Упрощено до локального файла, т.к. согласно условию не указан способ получения данных по сети
     */
    public String loadJSONFromAsset() {
        String json = null;
        try {
            AssetManager assetManager = getBaseContext().getAssets();
            InputStream is = assetManager.open("allStations.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * Предварительная загрузка данных в глобальную переменную
     */
    public void loadJSONtoClass()
    {
        String jsonString = loadJSONFromAsset();
        Gson gson = new Gson();
        ((TutuApplication) this.getApplication()).setCitiesFrom(gson.fromJson(jsonString, CitiesFrom.class));
        ((TutuApplication) this.getApplication()).setCitiesTo(gson.fromJson(jsonString, CitiesTo.class));

    }
}
