package edu.hawaii.ics414.icalendar;

import android.app.Activity;
import java.util.Calendar;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimePickerActivity extends Activity {
    private TextView textViewTime;
    private TimePicker timePicker;
    private Button button;

    private int hour;
    private int minute;

    static final int TIME_DIALOG_ID = 999;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCurrentTimeOnView();
        addButtonListener();

    }

    // display current time
    public void setCurrentTimeOnView() {

        textViewTime = (TextView) findViewById(R.id.txtTime);
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // set current time into textview
        textViewTime.setText(new StringBuilder().append(padding_str(hour)).append(":").append(padding_str(minute)));

        // set current time into timepicker
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);

    }

    public void addButtonListener() {

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new OnClickListener() {

            @SuppressWarnings("deprecation")
            public void onClick(View v) {

                showDialog(TIME_DIALOG_ID);

            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case TIME_DIALOG_ID:
            // set time picker as current time
            return new TimePickerDialog(this, timePickerListener, hour, minute,false);

        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener =  new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;

            // set current time into textview
            textViewTime.setText(new StringBuilder().append(padding_str(hour)).append(":").append(padding_str(minute)));

            // set current time into timepicker
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);

        }
    };

    private static String padding_str(int c) {
        if (c >= 10)
           return String.valueOf(c);
        else
           return "0" + String.valueOf(c);
    }
}