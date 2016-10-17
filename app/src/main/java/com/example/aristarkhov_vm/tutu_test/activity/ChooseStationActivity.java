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
import com.example.aristarkhov_vm.tutu_test.model.Station;
import com.example.aristarkhov_vm.tutu_test.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

public class ChooseStationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Station> stationList = new ArrayList<>();
    private List<Station> stationFullList = new ArrayList<>();
    private ItemAdapter itemAdapter;
    AlertDialog.Builder ad;
    Context context;
    EditText searchEditText;
    Destination destination;

    private Subscription subscription;

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_station);
        searchEditText = (EditText) findViewById(R.id.searchEditText);
        Intent intent = getIntent();
        destination = (Destination) intent.getSerializableExtra("Destination");
        if (destination == Destination.FROM) {
            stationFullList = TutuApplication.getAppInstance().getStationFromFullList();
        }
        else if (destination == Destination.TO) {
            stationFullList = TutuApplication.getAppInstance().getStationToFullList();
        }
        stationList = stationFullList;
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
        itemAdapter.notifyDataSetChanged();
        subscription = RxTextView.textChangeEvents(searchEditText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(new Func1<TextViewTextChangeEvent, Boolean>() {
                    @Override
                    public Boolean call(TextViewTextChangeEvent changes) {
                        return searchEditText.getText().toString() != null;
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
                stationList = findStationByName(onTextChangeEvent.text().toString());
                itemAdapter.refresh(stationList);
            }
        };
    }

    /**
     * @param searchString строка поиска
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


