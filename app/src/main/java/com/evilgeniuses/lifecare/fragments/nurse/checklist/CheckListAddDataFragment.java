package com.evilgeniuses.lifecare.fragments.nurse.checklist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.evilgeniuses.lifecare.R;
import com.evilgeniuses.lifecare.activities.NavigationNurseActivity;
import com.evilgeniuses.lifecare.dialogs.TimePickDialog;
import com.evilgeniuses.lifecare.fragments.authentication.LoginFragment;
import com.evilgeniuses.lifecare.interfaces.SwitchFragment;
import com.evilgeniuses.lifecare.models.CheckList;
import com.evilgeniuses.lifecare.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;

import java.util.UUID;

public class CheckListAddDataFragment extends Fragment implements View.OnClickListener {

    SwitchFragment switchFragment;
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

    Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check_list_add_data, container,false);
        imageViewBack = rootView.findViewById(R.id.imageViewBack);
        mContext = getContext();

        editTextName = rootView.findViewById(R.id.editTextName);
        editTextDuration = rootView.findViewById(R.id.editTextDuration);
        editTextComment = rootView.findViewById(R.id.editTextComment);

        buttonSetTime = rootView.findViewById(R.id.buttonSetTime);
        buttonСreateChecklist = rootView.findViewById(R.id.buttonСreateChecklist);

        checkBoxNotification = rootView.findViewById(R.id.checkBoxNotification);

        checkBoxMonday = rootView.findViewById(R.id.checkBoxMonday);
        checkBoxTuesday = rootView.findViewById(R.id.checkBoxTuesday);
        checkBoxWednesday = rootView.findViewById(R.id.checkBoxWednesday);
        checkBoxThursday = rootView.findViewById(R.id.checkBoxThursday);
        checkBoxFriday = rootView.findViewById(R.id.checkBoxFriday);
        checkBoxSaturday = rootView.findViewById(R.id.checkBoxSaturday);
        checkBoxSunday = rootView.findViewById(R.id.checkBoxSunday);

        imageViewBack.setOnClickListener(this);
        buttonSetTime.setOnClickListener(this);
        buttonСreateChecklist.setOnClickListener(this);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        return rootView;
    }

    private void writeNewUser() {
        CheckList checkList = new CheckList();

        DateTime dt = new DateTime();
        checkList.setСheckListDate(dt.toString());
        checkList.setСheckListEndDate(dt.plusDays(Integer.parseInt(editTextDuration.getText().toString())).toString());
        checkList.setСheckListStartDate(dt.toString());



        int hour = Integer.valueOf(buttonSetTime.getText().toString().substring(0, 2));
        int minut = Integer.valueOf(buttonSetTime.getText().toString().substring(3, 5));
        mMilliseconds =(hour * 60 * 60) + (minut * 60);

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

        String uuid = UUID.randomUUID().toString();
        checkList.setСheckID(uuid);

        myRef.child("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/CheckList/" + uuid).setValue(checkList);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageViewBack:
                switchFragment.setFragment(CheckListFragment.newInstance(), "");
                break;
            case R.id.buttonСreateChecklist:
                writeNewUser();
                Toast.makeText(getContext(), "Чек-лист успешно добавлен", Toast.LENGTH_LONG).show();
                switchFragment.setFragment(CheckListFragment.newInstance(), "");
                break;
            case R.id.buttonSetTime:
                DialogFragment dialogFragment = new TimePickDialog(v);
                dialogFragment.show(((NavigationNurseActivity) mContext).getSupportFragmentManager(), "timePicker");
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

    public static CheckListAddDataFragment newInstance(){
        return new CheckListAddDataFragment();
    }
}