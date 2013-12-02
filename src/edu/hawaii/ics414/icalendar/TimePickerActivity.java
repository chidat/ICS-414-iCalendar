package edu.hawaii.ics414.icalendar;

import java.util.Calendar;
import android.app.Activity;
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
	private Button button;
	private int hour;
	private int minute;

	static final int TIME_DIALOG_ID = 999;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_picker);

		setCurrentTimeOnView();
		addButtonListener();

	}

	// display current time
	public void setCurrentTimeOnView() {

		textViewTime = (TextView) findViewById(R.id.txtTime);

		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);

		// set current time into textview
		textViewTime.setText(convertTime(hour, minute));


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
				return new TimePickerDialog(this, timePickerListener, hour, minute, false);

		}
		return null;
	}

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

		public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;

			// Append in a StringBuilder
			String aTime = convertTime(hour, minute);

			// set current time into textview
			textViewTime.setText(aTime);
		}
	};

	public String convertTime(int hour, int minute) {
		String convertedTime;

		String ampm = "";
		if (hour > 12) {
			hour -= 12;
			ampm = "PM";
		}
		else if (hour == 0) {
			hour += 12;
			ampm = "AM";
		}
		else if (hour == 12) {
			ampm = "PM";
		}
		else {
			ampm = "AM";
		}

		String minutes = "";
		if (minute < 10) {
			minutes = "0" + minute;
		}
		else {
			minutes = String.valueOf(minute);
		}

		convertedTime =
				new StringBuilder().append(hour).append(':').append(minutes).append(" ").append(ampm).toString();

		return convertedTime;

	}

}
