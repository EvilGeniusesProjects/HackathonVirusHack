package com.evilgeniuses.lifecare.fragments.nurse.checklist;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

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

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

public class CheckListTimerFragment extends Fragment implements View.OnClickListener {
    SwitchFragment switchFragment;

    //final int[] belltime = {68400, 68402, 68403, 684010, 684010, 684010, 684010, 684010, 684010, 684010, 684010, 684010, 684010, 684010};


    TextView textViewProcedureName;
    TextView textViewHours;
    TextView textViewMinutes;
    TextView textViewSeconds;

    LinearLayout linearLayoutTimer;

    ProgressBar progressBar;

    private List<CheckList> mCheckList;

    private Handler mHandler;

    @SuppressLint("HandlerLeak")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check_list_timer, container,false);

        mCheckList = new ArrayList<>();
        readUsers();


        progressBar = rootView.findViewById(R.id.progressBar);

        textViewProcedureName = rootView.findViewById(R.id.textViewProcedureName);
        textViewHours = rootView.findViewById(R.id.textViewHours);
        textViewMinutes = rootView.findViewById(R.id.textViewMinutes);
        textViewSeconds = rootView.findViewById(R.id.textViewSeconds);

        linearLayoutTimer = rootView.findViewById(R.id.linearLayoutTimer);

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handlerUpdate();
                mHandler.sendEmptyMessageDelayed(0, 1000);
            }
        };
        mHandler.sendEmptyMessage(0);

        return rootView;
    }


    public void handlerUpdate(){

        DateTime datetime = DateTime.now();

        int currentTime;
        int timeToLecture;

        int timeToLectureHour;
        int timeToLectureMin;
        int timeToLectureSec;

        int hour = datetime.getHourOfDay();
        int min = datetime.getMinuteOfHour();
        int sec = datetime.getSecondOfMinute();

        int belltimeToLecture = 0;



        currentTime = hour * 60 * 60 + min * 60 + sec;

        for (int i = 0; i < mCheckList.size(); i++) {

            textViewProcedureName.setText(mCheckList.get(i).getСheckListName());



            if (Integer.valueOf(mCheckList.get(i).getСheckListMilliseconds()) > currentTime) {
                belltimeToLecture =  Integer.valueOf(mCheckList.get(i).getСheckListMilliseconds());
                break;
            }

            if(i == mCheckList.size()){
                textViewProcedureName.setText("На сегодня все");
                linearLayoutTimer.setVisibility(View.GONE);
            }else{
                progressBar.setVisibility(View.GONE);
                linearLayoutTimer.setVisibility(View.VISIBLE);

            }
        }

        timeToLecture = belltimeToLecture - currentTime;

        timeToLectureHour = timeToLecture / 3600;
        timeToLectureMin = timeToLecture / 60 - timeToLectureHour * 60;
        timeToLectureSec = timeToLecture - timeToLectureHour * 3600 - timeToLectureMin * 60;

        String StringTimeToLectureHour;
        String StringTimeToLectureMin;
        String StringTimeToLectureSec;

        if (timeToLectureHour < 10) {
            StringTimeToLectureHour = "0" + timeToLectureHour;
        } else {
            StringTimeToLectureHour = String.valueOf(timeToLectureHour);
        }
        if (timeToLectureMin < 10) {
            StringTimeToLectureMin = "0" + timeToLectureMin;
        } else {
            StringTimeToLectureMin = String.valueOf(timeToLectureMin);
        }
        if (timeToLectureSec < 10) {
            StringTimeToLectureSec = "0" + timeToLectureSec;
        } else {
            StringTimeToLectureSec = String.valueOf(timeToLectureSec);
        }

        if (StringTimeToLectureHour.equals("00 ") && StringTimeToLectureMin.equals("00 ")) {
            textViewHours.setText("00");
            textViewMinutes.setText("00");
            textViewSeconds.setText(StringTimeToLectureSec);
        } else {
            if (StringTimeToLectureHour.equals("00 ")) {
                textViewHours.setText("00");
                textViewMinutes.setText(StringTimeToLectureMin);
                textViewSeconds.setText(StringTimeToLectureSec);
            } else {
                textViewHours.setText(StringTimeToLectureHour);
                textViewMinutes.setText(StringTimeToLectureMin);
                textViewSeconds.setText(StringTimeToLectureSec);
            }
        }



    }


    private void readUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users/" + firebaseUser.getUid() + "/CheckList");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mCheckList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CheckList checkList = snapshot.getValue(CheckList.class);
                    mCheckList.add(checkList);
                }


                Collections.sort(mCheckList);

                for(CheckList str: mCheckList){
                }


//                CheckList t;
//                for (int i = 0; i < mCheckList.size(); i++)
//                    for (int j = i + 1; j < mCheckList.size(); j++)
//                        if (Integer.valueOf(mCheckList.get(j).getСheckListMilliseconds()) < Integer.valueOf(mCheckList.get(i).getСheckListMilliseconds())) {
//                            t = mCheckList.get(i);
//
//                            mCheckList.get(i)= mCheckList.get(j);
//                            mCheckList.get(j) = t;
//
//
//                        }

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

    public static CheckListTimerFragment newInstance(){
        return new CheckListTimerFragment();
    }
}