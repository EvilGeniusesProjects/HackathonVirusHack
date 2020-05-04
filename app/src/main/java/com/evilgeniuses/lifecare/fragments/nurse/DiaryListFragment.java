package com.evilgeniuses.lifecare.fragments.nurse;

import android.content.Context;
import android.content.Intent;
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
import com.evilgeniuses.lifecare.activities.DiaryActivity;
import com.evilgeniuses.lifecare.adapters.DiaryAdapter;
import com.evilgeniuses.lifecare.interfaces.SwitchFragment;
import com.evilgeniuses.lifecare.models.Diary;
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

public class DiaryListFragment extends Fragment implements View.OnClickListener {
    SwitchFragment switchFragment;
    FloatingActionButton floatingActionButton;

    private RecyclerView recyclerView;
    private List<Diary> mDiary;
    private DiaryAdapter diaryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_diary_list, container,false);
        floatingActionButton = rootView.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mDiary = new ArrayList<>();
        readUsers();
        return rootView;
    }

    private void readUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users/" + firebaseUser.getUid() + "/Diary");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mDiary.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Diary diary = snapshot.getValue(Diary.class);
                    mDiary.add(diary);
                }

                Collections.sort(mDiary);

                for(Diary str: mDiary){
                }


                diaryAdapter = new DiaryAdapter(getContext(), mDiary, false);
                recyclerView.setAdapter(diaryAdapter);
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
            case R.id.floatingActionButton:

                Intent intent = new Intent(getContext(), DiaryActivity.class);
//                intent.putExtra("checkListID", checkList.getСheckID());
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

    public static DiaryListFragment newInstance(){
        return new DiaryListFragment();
    }
}