package edu.hawaii.ics414.icalendar;

import java.io.FileOutputStream;
import java.util.Calendar;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class CreateEventActivity extends Activity {

	private EditText eventName;

	private TextView textViewTime;
	private Button timeSelectButton;
	private int hour;
	private int minute;

	private DatePicker datePicker;
	private int day;
	private int month;
	private int year;

	private String iCalDateTimeStart;
	private String iCalDateTimeEnd;

	static final int TIME_DIALOG_ID = 999;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);

		setCurrentTimeOnView();
		addTimeButtonListener();

		datePicker = (DatePicker) findViewById(R.id.datePicker1);

		eventName = (EditText) findViewById(R.id.editText1);
		Button saveButton = (Button) findViewById(R.id.save_button);
		
		/**
		 * Listener for clicking the "Save Event" button.
		 */
		saveButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				month = datePicker.getMonth() + 1;
				day = datePicker.getDayOfMonth();
				year = datePicker.getYear();

				iCalDateTimeStart =
						new StringBuilder().append(convertDate(year, month, day)).append("T").append(hour)
								.append(minute).append("00").toString();
				iCalDateTimeEnd =
						new StringBuilder().append(convertDate(year, month, day)).append("T").append(hour + 1)
								.append(minute).append("00").toString();

				final String content =
						new StringBuilder().append("BEGIN:VCALENDAR\r\n").append("VERSION:2.0\r\n")
								.append("PRODID:-//hacksw/handcal//NONSGML v1.0//EN\r\n").append("BEGIN:VEVENT\r\n")
								.append("UID:uid1@example.com\r\n").append("DTSTAMP:20131130T170000Z\r\n")
								.append("DTSTART;TZID=US/Hawaii:").append(iCalDateTimeStart).append("\r\n")
								.append("DTEND;TZID=US/Hawaii:").append(iCalDateTimeEnd).append("\r\n")
								.append("SUMMARY:").append(eventName.getText().toString()).append("\r\n")
								.append("END:VEVENT\r\n").append("END:VCALENDAR").toString();

				// write the file to the device.
				try {
					FileOutputStream fos = openFileOutput((eventName.getText().toString() + ".ics"), Context.MODE_PRIVATE);
					fos.write(content.getBytes());
					fos.close();
					Log.i("writeCalendar", "saved event: " + eventName.getText().toString());
				}
				// log any errors.
				catch (Exception e) {
					Log.e("writeCalendar", "error writing ics file.", e);
				}

			}
		});
	}

	/**
	 * Displays the current time.
	 */
	public void setCurrentTimeOnView() {
		textViewTime = (TextView) findViewById(R.id.txtTime);

		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);

		// set current time into textview
		textViewTime.setText(convertTime(hour, minute));
	}

	/**
	 * Add a listener to the timepicker button.
	 */
	public void addTimeButtonListener() {
		timeSelectButton = (Button) findViewById(R.id.button);
		timeSelectButton.setOnClickListener(new OnClickListener() {

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

	/**
	 * Converts the time from 24-hour format to AM/PM format.
	 * @param hour The hour 0-23.
	 * @param minute The minute 0-59.
	 * @return A {@link String} of the hour and minute in AM/PM format.
	 */
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

		convertedTime =	new StringBuilder().append(hour).append(':').append(minutes).append(" ").append(ampm).toString();

		return convertedTime;

	}

	/**
	 * Converts components of a DatePicker date into iCalendar format.
	 * 
	 * @param year The year of the event.
	 * @param month The month (of the year) of the event.
	 * @param day The day (of the month) of the event.
	 * @return A String in yyyymmdd format.
	 */
	public String convertDate(int year, int month, int day) {
		/*
		 * SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd"); String formattedDate = sdf.format(new Date(year,
		 * month, day));
		 */
		String formattedDate;

		String formattedYear = String.valueOf(year);
		String formattedMonth = checkNumber(month);
		String formattedDay = checkNumber(day);

		formattedDate =
				new StringBuilder().append(formattedYear).append(formattedMonth).append(formattedDay).toString();

		return formattedDate;
	}

	/**
	 * Checks to see if the number is a single digit or double digits.
	 * @param num The number to check.
	 * @return A {@link String} representation of the number in two-digits.
	 */
	public String checkNumber(int num) {
		return (num < 10) ? ("0" + num) : (String.valueOf(num));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_event, menu);
		return true;
	}

}
