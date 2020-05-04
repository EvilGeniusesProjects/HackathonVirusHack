package com.evilgeniuses.lifecare.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.evilgeniuses.lifecare.R;
import com.evilgeniuses.lifecare.fragments.authentication.LoginFragment;
import com.evilgeniuses.lifecare.fragments.nurse.MonitoringListFragment;
import com.evilgeniuses.lifecare.fragments.nurse.ProfileFragment;
import com.evilgeniuses.lifecare.fragments.nurse.checklist.CheckListFragment;
import com.evilgeniuses.lifecare.fragments.nurse.DiaryListFragment;
import com.evilgeniuses.lifecare.interfaces.SwitchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationNurseActivity extends AppCompatActivity implements SwitchFragment {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_nurse);
        setFragment(CheckListFragment.newInstance(), "");
    }

    @Override
    protected void onStart() {
        super.onStart();
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_1:
                        setFragment(CheckListFragment.newInstance(), "");
                        break;
                    case R.id.tab_2:
                        setFragment(DiaryListFragment.newInstance(), "");
                        break;
                    case R.id.tab_3:
                        setFragment(MonitoringListFragment.newInstance(), "");
                        break;
                    case R.id.tab_4:
                        setFragment(LoginFragment.newInstance(), "");
                        break;
                    case R.id.tab_5:
                        setFragment(ProfileFragment.newInstance(), "");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void setFragment(final Fragment fragment, final String fragmentTitle) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.container, fragment)
                        .addToBackStack(fragmentTitle)
                        .commit();
            }
        });
    }
}