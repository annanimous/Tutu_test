package com.example.aristarkhov_vm.tutu_test.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aristarkhov_vm.tutu_test.application.TutuApplication;
import com.example.aristarkhov_vm.tutu_test.model.CitiesFrom;
import com.example.aristarkhov_vm.tutu_test.model.CitiesTo;
import com.example.aristarkhov_vm.tutu_test.R;
import com.example.aristarkhov_vm.tutu_test.model.Station;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    LoadJSONtoClass loadJSONtoClass;
    Button buttonTimetable;
    Button buttonAbout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonTimetable = (Button) findViewById(R.id.buttonTimetable);
        buttonAbout = (Button) findViewById(R.id.buttonAbout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        loadJSONtoClass = new LoadJSONtoClass();
        loadJSONtoClass.execute();
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
        String version = pInfo != null ? pInfo.versionName : null;
        int verCode = pInfo != null ? pInfo.versionCode : 0;
        version = "Автор Аристархов В. Версия " + version + "." + verCode;
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * Предварительная загрузка данных в глобальную переменную. Упрощено до локального файла, т.к. согласно условию не указан способ получения данных по сети
     */

    class LoadJSONtoClass extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            buttonTimetable.setEnabled(false);
            buttonAbout.setEnabled(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            downloadfile();
            String jsonString = "";
            try {
                AssetManager assetManager = getBaseContext().getAssets();
                InputStream is = assetManager.open("allStations.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                jsonString = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Gson gson = new Gson();
            CitiesFrom citiesFrom = gson.fromJson(jsonString, CitiesFrom.class);
            List<Station> stationFromFullList  = new ArrayList<>();
            for (int i = 0; i < citiesFrom.getmCountry().length; i++) {
                stationFromFullList.addAll(citiesFrom.getmCountry()[i].getStations());
            }
            TutuApplication.getAppInstance().setStationFromFullList(stationFromFullList);
            CitiesTo citiesTo = gson.fromJson(jsonString, CitiesTo.class);
            List<Station> stationToFullList = new ArrayList<>();
            for (int i = 0; i < citiesTo.getmCountry().length; i++) {
                stationToFullList.addAll(citiesTo.getmCountry()[i].getStations());
            }
            TutuApplication.getAppInstance().setStationToFullList(stationToFullList);
            return null;
        }

        private void downloadfile() {
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
            buttonTimetable.setEnabled(true);
            buttonAbout.setEnabled(true);
        }
    }
}
