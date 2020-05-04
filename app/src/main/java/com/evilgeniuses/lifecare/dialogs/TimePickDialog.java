package com.evilgeniuses.lifecare.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

public class TimePickDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private View mView;

    public TimePickDialog(View view) {
        mView = view;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfDay) {

        String hour;
        String minute;

        if (hourOfDay < 10){
            hour = "0" + hourOfDay;
        }else{
            hour = "" + hourOfDay;
        }

        if (minuteOfDay < 10){
            minute = "0" + minuteOfDay;
        }else{
            minute = "" + minuteOfDay;
        }

        ((Button) mView).setText(hour + ":" + minute);

    }

}