package com.evilgeniuses.lifecare.fragments.nurse.checklist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evilgeniuses.lifecare.R;
import com.evilgeniuses.lifecare.adapters.CheckListAdapter;
import com.evilgeniuses.lifecare.interfaces.SwitchFragment;
import com.evilgeniuses.lifecare.models.CheckList;
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

public class CheckListDataFragment extends Fragment implements View.OnClickListener {


    SwitchFragment switchFragment;

    private RecyclerView recyclerView;
    private List<CheckList> mCheckList;

    private CheckListAdapter checkListAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check_list_data, container,false);


        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mCheckList = new ArrayList<>();

        readUsers();




        return rootView;
    }



    private void readUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users/" + firebaseUser.getUid() + "/CheckList");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


             ///   if (editTextSearch.getText().toString().equals("")) {
                    mCheckList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        CheckList checkList = snapshot.getValue(CheckList.class);

                       // if (user.getUserCategory() != null && user.getUserCategory().equals("Ментор")) {
                            mCheckList.add(checkList);
                      //  }

                    }
                Collections.sort(mCheckList);

                for(CheckList str: mCheckList){
                }

                    checkListAdapter = new CheckListAdapter(getContext(), mCheckList, false);
                    recyclerView.setAdapter(checkListAdapter);
           //     }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }







    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof SwitchFragment){
            switchFragment = (SwitchFragment) context;
        }
    }

    public static CheckListDataFragment newInstance(){
        return new CheckListDataFragment();
    }
}