package com.evilgeniuses.lifecare.fragments.nurse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.evilgeniuses.lifecare.R;
import com.evilgeniuses.lifecare.activities.GraphicActivity;
import com.evilgeniuses.lifecare.activities.MonitoringActivity;
import com.evilgeniuses.lifecare.interfaces.SwitchFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MonitoringListFragment extends Fragment implements View.OnClickListener {

    SwitchFragment switchFragment;
    FloatingActionButton floatingActionButton;
    Button buttonGraphic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_monitoring_list, container,false);
        floatingActionButton = rootView.findViewById(R.id.floatingActionButton);
        buttonGraphic = rootView.findViewById(R.id.buttonGraphic);

        buttonGraphic.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.floatingActionButton:
                intent = new Intent(getContext(), MonitoringActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.buttonGraphic:
                intent = new Intent(getContext(), GraphicActivity.class);
                getContext().startActivity(intent);
                break;
        }
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof SwitchFragment){
            switchFragment = (SwitchFragment) context;
        }
    }

    public static MonitoringListFragment newInstance(){
        return new MonitoringListFragment();
    }
}