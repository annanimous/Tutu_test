package com.example.aristarkhov_vm.tutu_test.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;


import com.example.aristarkhov_vm.tutu_test.adapter.ItemAdapter;
import com.example.aristarkhov_vm.tutu_test.application.TutuApplication;
import com.example.aristarkhov_vm.tutu_test.model.CitiesFrom;
import com.example.aristarkhov_vm.tutu_test.model.CitiesTo;
import com.example.aristarkhov_vm.tutu_test.model.Country;
import com.example.aristarkhov_vm.tutu_test.model.Station;
import com.example.aristarkhov_vm.tutu_test.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

public class ChooseStationActivity extends AppCompatActivity {
    List<Country> cities = new ArrayList<>();
    private RecyclerView recyclerView;
    private List<Station> stationList = new ArrayList<>();
    private List<Station> stationFullList = new ArrayList<>();
    private ItemAdapter itemAdapter;
    AlertDialog.Builder ad;
    Context context;

    @Bind(R.id.searchview) EditText inputSearchText;

    private Subscription subscription;

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
        ButterKnife.unbind(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_station);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Destination destination = (Destination) intent.getSerializableExtra("Destination");
        if (destination == Destination.FROM) {
            CitiesFrom citiesFrom = ((TutuApplication) this.getApplication()).getCitiesFrom();
            Collections.addAll(cities, citiesFrom.getmCountry());
        }
        else if (destination == Destination.TO) {
            CitiesTo citiesTo = ((TutuApplication) this.getApplication()).getCitiesTo();
            Collections.addAll(cities, citiesTo.getmCountry());
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        itemAdapter = new ItemAdapter(stationList, new ItemAdapter.OnItemClickListener() {
            @Override
            //С точки зрения удобства использования, предпочтительнее две разных кнопки для выбора станции и просмотра информации. упростил согласно условиям задачи
            public void onItemClick(final Station station) {
                context = ChooseStationActivity.this;
                ad = new AlertDialog.Builder(context);
                ad.setTitle("Информация о станции");
                String region = "";
                if (station.getRegionTitle() != null && !station.getRegionTitle().isEmpty()) region = "\n" + "Регион: " + station.getRegionTitle();
                String district = "";
                if (station.getDistrictTitle() != null && !station.getDistrictTitle().isEmpty()) region = "\n" + "Район: " + station.getDistrictTitle();
                ad.setMessage("Станция: "+ station.getStationTitle()
                        + "\n" + "Страна: " + station.getCountryTitle()
                        + region
                        + district
                        + "\n" + "Город: " + station.getCityTitle()
                );
                ad.setPositiveButton("Выбрать", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Intent intent = new Intent();
                        //Тут конечно надо помещать station, а не строку, на так как это тестовое задание я сократил согласно условию, т.к. придётся использовать интерфейс Parcealable, а это "несоразмерно увеличивает трудоемкость работ"
                        intent.putExtra("stationName", station.getStationTitle() + ", " + station.getCityTitle() + ", " + station.getCountryTitle());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                ad.setNegativeButton("Назад к списку", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                    }
                });
                ad.setCancelable(true);
                ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                    }
                });
                ad.show();

            }
        });

        recyclerView.setAdapter(itemAdapter);

        for (int i = 0; i < cities.size(); i++)
        {
            stationFullList.addAll(cities.get(i).getStations());
        }
        stationList.addAll(stationFullList);
        itemAdapter.notifyDataSetChanged();
        subscription = RxTextView.textChangeEvents(inputSearchText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(new Func1<TextViewTextChangeEvent, Boolean>() {
                    @Override
                    public Boolean call(TextViewTextChangeEvent changes) {
                        return inputSearchText.getText().toString() != null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSearchObserver());
    }
    private Observer<TextViewTextChangeEvent> getSearchObserver() {
        return new Observer<TextViewTextChangeEvent>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
                itemAdapter.clear();
                stationList = findStationByName(onTextChangeEvent.text().toString());
                itemAdapter.refresh(stationList);
            }
        };
    }

    /**
     * @param searchString
     * @return
     * Фильтрование списка станций
     */
    public List<Station> findStationByName(final String searchString) {
        List<Station> returnList = new ArrayList<>();
        for (int i = 0; i < stationFullList.size(); i++)
        {
            if (stationFullList.get(i).getStationTitle().toLowerCase().contains(searchString.toLowerCase()))
            {
                returnList.add(stationFullList.get(i));
            }
        }
        return returnList;
    }
}


