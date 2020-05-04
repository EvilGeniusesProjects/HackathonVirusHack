package com.evilgeniuses.lifecare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.evilgeniuses.lifecare.R;
import com.evilgeniuses.lifecare.activities.pulse.PulseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MonitoringActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    String monitoringID;

    ImageView imageViewBack;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
        imageViewBack = findViewById(R.id.imageViewBack);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        imageViewBack.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);

        intent = getIntent();
        monitoringID = intent.getStringExtra("monitoringID");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewBack:
                finish();
                break;

            case R.id.buttonСreateDiary:
                //writeNewUser();
                Toast.makeText(getApplicationContext(), "text", Toast.LENGTH_LONG).show();
                finish();
                break;

            case  R.id.floatingActionButton:
                Intent intent = new Intent(this, PulseActivity.class);
                startActivity(intent);
                break;
        }
    }
}
