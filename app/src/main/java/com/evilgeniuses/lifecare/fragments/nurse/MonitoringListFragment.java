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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evilgeniuses.lifecare.R;
import com.evilgeniuses.lifecare.activities.GraphicActivity;
import com.evilgeniuses.lifecare.activities.MonitoringActivity;
import com.evilgeniuses.lifecare.adapters.DiaryAdapter;
import com.evilgeniuses.lifecare.adapters.MonitoringAdapter;
import com.evilgeniuses.lifecare.interfaces.SwitchFragment;
import com.evilgeniuses.lifecare.models.Monitoring;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MonitoringListFragment extends Fragment implements View.OnClickListener {

    SwitchFragment switchFragment;
    FloatingActionButton floatingActionButton;
    Button buttonGraphic;

    private RecyclerView recyclerView;
    private List<Monitoring> mMonitoring;
    private MonitoringAdapter monitoringAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_monitoring_list, container,false);
        floatingActionButton = rootView.findViewById(R.id.floatingActionButton);
        buttonGraphic = rootView.findViewById(R.id.buttonGraphic);

        buttonGraphic.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mMonitoring = new ArrayList<>();
        readUsers();

        return rootView;
    }


    private void readUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users/" + firebaseUser.getUid() + "/Monitoring");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mMonitoring.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Monitoring monitoring = snapshot.getValue(Monitoring.class);
                    mMonitoring.add(monitoring);
                }

                Collections.sort(mMonitoring);

                for(Monitoring str: mMonitoring){
                }


                monitoringAdapter = new MonitoringAdapter(getContext(), mMonitoring, false);
                recyclerView.setAdapter(monitoringAdapter);
                //     }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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