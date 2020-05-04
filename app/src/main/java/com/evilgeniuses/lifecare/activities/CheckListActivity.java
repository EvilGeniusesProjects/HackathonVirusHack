package com.evilgeniuses.lifecare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.evilgeniuses.lifecare.R;
import com.evilgeniuses.lifecare.dialogs.TimePickDialog;
import com.evilgeniuses.lifecare.fragments.nurse.checklist.CheckListFragment;
import com.evilgeniuses.lifecare.models.CheckList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;

import java.util.UUID;


public class CheckListActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    String checkListID;

    ImageView imageViewBack;

    EditText editTextName;
    EditText editTextDuration;
    EditText editTextComment;

    Button buttonSetTime;
    Button buttonСreateChecklist;

    CheckBox checkBoxNotification;

    CheckBox checkBoxMonday;
    CheckBox checkBoxTuesday;
    CheckBox checkBoxWednesday;
    CheckBox checkBoxThursday;
    CheckBox checkBoxFriday;
    CheckBox checkBoxSaturday;
    CheckBox checkBoxSunday;

    DatabaseReference myRef;

    int mMilliseconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_check_list_add_data);
        imageViewBack = findViewById(R.id.imageViewBack);
        intent = getIntent();
        checkListID = intent.getStringExtra("checkListID");


        editTextName = findViewById(R.id.editTextName);
        editTextDuration = findViewById(R.id.editTextDuration);
        editTextComment = findViewById(R.id.editTextComment);

        buttonSetTime = findViewById(R.id.buttonSetTime);
        buttonСreateChecklist = findViewById(R.id.buttonСreateChecklist);
        buttonСreateChecklist.setText("Изменить");


        checkBoxNotification = findViewById(R.id.checkBoxNotification);

        checkBoxMonday = findViewById(R.id.checkBoxMonday);
        checkBoxTuesday = findViewById(R.id.checkBoxTuesday);
        checkBoxWednesday = findViewById(R.id.checkBoxWednesday);
        checkBoxThursday = findViewById(R.id.checkBoxThursday);
        checkBoxFriday = findViewById(R.id.checkBoxFriday);
        checkBoxSaturday = findViewById(R.id.checkBoxSaturday);
        checkBoxSunday = findViewById(R.id.checkBoxSunday);

        imageViewBack.setOnClickListener(this);
        buttonSetTime.setOnClickListener(this);
        buttonСreateChecklist.setOnClickListener(this);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/CheckList/" + checkListID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CheckList value = dataSnapshot.getValue(CheckList.class);

                if (value.getСheckListName() != null) {
                    editTextName.setText((value.getСheckListName()));
                }

                if (value.getСheckListDuration() != null) {
                    editTextDuration.setText((value.getСheckListDuration()));
                }

                if (value.getСheckListComment() != null) {
                    editTextComment.setText((value.getСheckListComment()));
                }

                if (value.getСheckListTime()!= null) {
                    buttonSetTime.setText((value.getСheckListTime()));
                }

                if (value.isСheckListNotification()) {
                    checkBoxNotification.setChecked(true);
                }


                if (value.isСheckListMonday()) {
                    checkBoxMonday.setChecked(true);
                }

                if (value.isСheckListTuesday()) {
                    checkBoxTuesday.setChecked(true);
                }

                if (value.isСheckListWednesday()) {
                    checkBoxWednesday.setChecked(true);
                }

                if (value.isСheckListThursday()) {
                    checkBoxThursday.setChecked(true);
                }

                if (value.isСheckListFriday()) {
                    checkBoxFriday.setChecked(true);
                }

                if (value.isСheckListSaturday()) {
                    checkBoxSaturday.setChecked(true);
                }

                if (value.isСheckListSunday()) {
                    checkBoxSunday.setChecked(true);
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Log.w(TAG, "Не удалось прочитать значение", error.toException());
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewBack:
                finish();
                break;
            case R.id.buttonСreateChecklist:
                writeNewUser();
                Toast.makeText(this, "Данные успешно изменены", Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.buttonSetTime:
                DialogFragment dialogFragment = new TimePickDialog(v);
                dialogFragment.show(this.getSupportFragmentManager(), "timePicker");
                break;
        }
    }

    private void writeNewUser() {
        CheckList checkList = new CheckList();

        DateTime dt = new DateTime();
        checkList.setСheckListDate(dt.toString());
        checkList.setСheckListEndDate(dt.plusDays(Integer.parseInt(editTextDuration.getText().toString())).toString());
        checkList.setСheckListStartDate(dt.toString());


        int hour = Integer.valueOf(buttonSetTime.getText().toString().substring(0, 2));
        int minut = Integer.valueOf(buttonSetTime.getText().toString().substring(3, 5));
        mMilliseconds = (hour * 60 * 60) + (minut * 60);

        checkList.setСheckListMilliseconds(mMilliseconds);

        checkList.setСheckListName(editTextName.getText().toString());
        checkList.setСheckListDuration(editTextDuration.getText().toString());
        checkList.setСheckListComment(editTextComment.getText().toString());
        checkList.setСheckListTime(buttonSetTime.getText().toString());


        checkList.setСheckListMonday(checkBoxMonday.isChecked());
        checkList.setСheckListTuesday(checkBoxTuesday.isChecked());
        checkList.setСheckListWednesday(checkBoxWednesday.isChecked());
        checkList.setСheckListThursday(checkBoxThursday.isChecked());
        checkList.setСheckListFriday(checkBoxFriday.isChecked());
        checkList.setСheckListSaturday(checkBoxSaturday.isChecked());
        checkList.setСheckListSunday(checkBoxSunday.isChecked());
        checkList.setСheckListNotification(checkBoxNotification.isChecked());
        checkList.setСheckID(checkListID);

        FirebaseDatabase.getInstance().getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/CheckList/" + checkListID).setValue(checkList);
    }
}
