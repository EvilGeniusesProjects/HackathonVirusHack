package com.evilgeniuses.lifecare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.evilgeniuses.lifecare.R;
import com.evilgeniuses.lifecare.fragments.test.StartFragment;
import com.evilgeniuses.lifecare.interfaces.SwitchFragmentInterface;

public class TestActivity extends AppCompatActivity implements SwitchFragmentInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        setFragment(StartFragment.newInstance());
    }

    @Override
    public void setFragment(Fragment fragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}