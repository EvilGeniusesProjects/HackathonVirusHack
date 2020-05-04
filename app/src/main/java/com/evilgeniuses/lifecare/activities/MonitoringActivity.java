package com.evilgeniuses.lifecare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.evilgeniuses.lifecare.R;
import com.evilgeniuses.lifecare.activities.pulse.PulseActivity;
import com.evilgeniuses.lifecare.models.Diary;
import com.evilgeniuses.lifecare.models.Monitoring;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.UUID;

public class MonitoringActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    String monitoringID;

    ImageView imageViewBack;
    FloatingActionButton floatingActionButton;

    RatingBar ratingBarFeeling;
    EditText editTextPressure;
    EditText editTextPulse;
    EditText editTextTemperature;
    EditText editTextSleep;
    SeekBar seekBarPain;
    EditText editTextComment;
    Button buttonСreateMonitoring;

    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    FirebaseStorage storage;
    StorageReference storageReference;
    private String text = "Запись успешна создана";

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



        ratingBarFeeling = findViewById(R.id.ratingBarFeeling);
        editTextPressure = findViewById(R.id.editTextPressure);
        editTextPulse = findViewById(R.id.editTextPulse);
        editTextTemperature = findViewById(R.id.editTextTemperature);
        editTextSleep = findViewById(R.id.editTextSleep);
        seekBarPain = findViewById(R.id.seekBarPain);
        editTextComment = findViewById(R.id.editTextComment);
        buttonСreateMonitoring = findViewById(R.id.buttonСreateMonitoring);
        buttonСreateMonitoring.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        TextView textViewSeekBarProgres = findViewById(R.id.textViewSeekBarProgres);
        seekBarPain.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewSeekBarProgres.setText(seekBar.getProgress() + " из 10");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if(monitoringID != null){
            readData();
            text = "Запись успешна изменена";
            buttonСreateMonitoring.setText("Сохранить изменения");
        }
    }


    private void readData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Monitoring/" + monitoringID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Monitoring value = dataSnapshot.getValue(Monitoring.class);

                if (value.getFeeling() != null) {
                    ratingBarFeeling.setProgress(Integer.valueOf(value.getFeeling()));
                }


                if (value.getPressure() != null) {
                    editTextPressure.setText((value.getPressure()));
                }

                if (value.getPulse() != null) {
                    editTextComment.setText((value.getPulse()));
                }

                if (value.getTemperature() != null) {
                    editTextTemperature.setText((value.getTemperature() ));
                }

                if (value.getSleep() != null) {
                    editTextSleep.setText((value.getSleep()));
                }

                if (value.getPulse() != null) {
                    editTextComment.setText((value.getPulse()));
                }

                if (value.getPain() != null) {
                    seekBarPain.setProgress(Integer.valueOf(value.getPain()));
                }

                if (value.getComment() != null) {
                    editTextComment.setText((value.getComment()));
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Log.w(TAG, "Не удалось прочитать значение", error.toException());
            }
        });
    }


    private void writeNewUser() {
        Monitoring monitoring = new Monitoring();
        String uuid = UUID.randomUUID().toString();
        DateTime today = new DateTime();
        int days = today.getYear() * 365 + today.getMonthOfYear() * 30 + today.getDayOfMonth() + today.getHourOfDay() + today.getMinuteOfDay();

        monitoring.setDays(days);
        monitoring.setDate(today.toString());

        monitoring.setFeeling(String.valueOf(ratingBarFeeling.getProgress()));
        monitoring.setPressure(editTextPressure.getText().toString());
        monitoring.setPulse(editTextPulse.getText().toString());
        monitoring.setTemperature(editTextTemperature.getText().toString());
        monitoring.setSleep(editTextSleep.getText().toString());
        monitoring.setPain(String.valueOf(seekBarPain.getProgress()));
        monitoring.setComment(editTextComment.getText().toString());


//        public String Pressure; давление
//        public String Pulse; пульс
//        public String Temperature; температура
//        public String Defecation; дефекация
//        public String Sleep; сон
//        public String SkinDamage; повреждение кожи
//        public String Pain; боль
//        public String Stroll; прогулка
//        public String Leisure; досуг
//        public String Feeling; самочувствие


        if(monitoringID != null){
            DatabaseReference databaseReferenceStatus = FirebaseDatabase.getInstance().getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Monitoring").child(monitoringID);
            HashMap<String, Object> hashMap = new HashMap<>();

            hashMap.put("feeling", String.valueOf(ratingBarFeeling.getProgress()));
            hashMap.put("pressure", editTextPressure.getText().toString());
            hashMap.put("pulse", editTextPulse.getText().toString());
            hashMap.put("temperature", editTextTemperature.getText().toString());
            hashMap.put("sleep", editTextSleep.getText().toString());
            hashMap.put("pain", String.valueOf(seekBarPain.getProgress()));
            hashMap.put("comment", editTextComment.getText().toString());

            databaseReferenceStatus.updateChildren(hashMap);
        }else {
            monitoring.setID(uuid);
            myRef.child("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Monitoring/" + uuid).setValue(monitoring);
        }
    }









    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewBack:
                finish();
                break;

            case R.id.buttonСreateMonitoring:
                writeNewUser();
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                finish();
                break;

            case  R.id.floatingActionButton:
                Intent intent = new Intent(this, PulseActivity.class);
                startActivity(intent);
                break;
        }
    }
}
