package com.evilgeniuses.lifecare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.evilgeniuses.lifecare.R;
import com.evilgeniuses.lifecare.adapters.CheckListAdapter;
import com.evilgeniuses.lifecare.models.CheckList;
import com.evilgeniuses.lifecare.models.Monitoring;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GraphicActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageViewBack;
    Spinner spinner;
    ValueLineChart mCubicValueLineChart;
    private List<Monitoring> mMonitoring;

    int index = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(this);

        spinner = findViewById(R.id.spinner);
        final String[] items = {"Артериальное давление", "Температура", "Пульс", "Сон", "Самочувствие"};

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                index = position;
//                Toast.makeText(getApplicationContext(), "pos = " + position, Toast.LENGTH_LONG).show();
                readUsers();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                readUsers();
            }

        });

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        mMonitoring = new ArrayList<>();
        mCubicValueLineChart = findViewById(R.id.cubiclinechart);
    }




    private void readUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users/" + firebaseUser.getUid() + "/Monitoring");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                ValueLineSeries series = new ValueLineSeries();
                series.setColor(0xFF56B7F1);


                mMonitoring.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Monitoring monitoring = snapshot.getValue(Monitoring.class);

                    mMonitoring.add(monitoring);



                }
                Collections.sort(mMonitoring);

                for(Monitoring str: mMonitoring){
                }


                switch (index){
                    case 0:
                        for(int i = 0; i < mMonitoring.size(); i++){
                            series.addPoint(new ValueLinePoint(mMonitoring.get(i).getDate().substring(5, 7) + "." + mMonitoring.get(i).getDate().substring(8, 10), Integer.valueOf(mMonitoring.get(i).getPressure().substring(0, 3))));
                        }
                        break;
                    case 1:
                        for(int i = 0; i < mMonitoring.size(); i++){
                            series.addPoint(new ValueLinePoint(mMonitoring.get(i).getDate().substring(5, 7) + "." + mMonitoring.get(i).getDate().substring(8, 10), Float.valueOf(mMonitoring.get(i).getTemperature())));
                        }
                        break;
                    case 2:
                        for(int i = 0; i < mMonitoring.size(); i++){
                            series.addPoint(new ValueLinePoint(mMonitoring.get(i).getDate().substring(5, 7) + "." + mMonitoring.get(i).getDate().substring(8, 10), Integer.valueOf(mMonitoring.get(i).getPulse())));
                        }
                        break;
                    case 3:
                        for(int i = 0; i < mMonitoring.size(); i++){
                            series.addPoint(new ValueLinePoint(mMonitoring.get(i).getDate().substring(5, 7) + "." + mMonitoring.get(i).getDate().substring(8, 10), Float.valueOf(mMonitoring.get(i).getSleep())));
                        }
                        break;
                    case 4:
                        for(int i = 0; i < mMonitoring.size(); i++){
                            series.addPoint(new ValueLinePoint(mMonitoring.get(i).getDate().substring(5, 7) + "." + mMonitoring.get(i).getDate().substring(8, 10), Float.valueOf(mMonitoring.get(i).getFeeling())));
                        }
                        break;
                }
                mCubicValueLineChart.addSeries(series);
                mCubicValueLineChart.startAnimation();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }






    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewBack:
                finish();
                break;
        }
    }
}
