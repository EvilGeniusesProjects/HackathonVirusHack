package com.evilgeniuses.lifecare.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import com.evilgeniuses.lifecare.databases.TinyDB;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = null;

        TinyDB tinyDB = new TinyDB(this);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String savedText = tinyDB.getString("UserCategories");
            switch (savedText){
                case "Врач":
                    intent = new Intent(this, NavigationDoctorActivity.class);
                    break;
                default:
                    intent = new Intent(this, NavigationNurseActivity.class);
                    break;
            }

        } else {
            intent = new Intent(this, AuthenticationActivity.class);
        }

        startActivity(intent);
        finish();
    }
}