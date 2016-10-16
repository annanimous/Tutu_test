package com.example.aristarkhov_vm.tutu_test.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aristarkhov_vm.tutu_test.R;

enum Destination { FROM, TO }

public class TimetableActivity extends AppCompatActivity {
    TextView fromTextView;
    TextView toTextView;
    int REQUEST_CODE;
    final int REQUEST_CODE_FROM = 0;
    final int REQUEST_CODE_TO = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        fromTextView = (TextView) findViewById(R.id.fromStationTextView);
        toTextView = (TextView) findViewById(R.id.toStationTextView);
        setTitle("Расписание");
    }


    public void onClickFromStationSearch(View view) {
        REQUEST_CODE = REQUEST_CODE_FROM;
        searchStation(Destination.FROM);
    }

    public void onClickToStationSearch(View view) {
        REQUEST_CODE = REQUEST_CODE_TO;
        searchStation(Destination.TO);
    }
    public void searchStation(Destination destination) {
        Intent intent = new Intent(this, ChooseStationActivity.class);
        intent.putExtra("Destination", destination);
        startActivityForResult(intent, REQUEST_CODE);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String stationName;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_FROM:
                    stationName = data.getStringExtra("stationName");
                    fromTextView.setText(stationName);
                    break;
                case REQUEST_CODE_TO:
                    stationName = data.getStringExtra("stationName");
                    toTextView.setText(stationName);
                    break;
            }
        }
    }
}
