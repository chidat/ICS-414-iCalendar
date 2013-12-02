package edu.hawaii.ics414.icalendar;

import java.io.FileOutputStream;
import java.util.Calendar;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class CreateEventActivity extends Activity {

	private TextView lblTextViewOne;
	private EditText editText1;
	
	private TextView textViewTime;
	private TextView textViewDate;
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
		
		textViewDate = (TextView) findViewById(R.id.text_view_date);
		datePicker = (DatePicker) findViewById(R.id.datePicker1);
        
        lblTextViewOne = (TextView) findViewById(R.id.lblTextViewOne);
        editText1 = (EditText) findViewById(R.id.editText1);
        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				month = datePicker.getMonth() + 1;
				day = datePicker.getDayOfMonth();
				year = datePicker.getYear();
				
				
				iCalDateTimeStart = new StringBuilder().append(convertDate(year, month, day)).append("T").append(hour).append(minute).append("00").toString();
				iCalDateTimeEnd = new StringBuilder().append(convertDate(year, month, day)).append("T").append(hour + 1).append(minute).append("00").toString();
				
				lblTextViewOne.setText(iCalDateTimeStart);
				textViewDate.setText(iCalDateTimeEnd);
				
				final String content = "BEGIN:VCALENDAR\r\n" + 
		        		"VERSION:2.0\r\n" + 
		        		"PRODID:-//hacksw/handcal//NONSGML v1.0//EN\r\n" + 
		        		"BEGIN:VEVENT\r\n" + 
		        		"UID:uid1@example.com\r\n" + 
		        		"DTSTAMP:20131130T170000Z\r\n" +
		        		"DTSTART;TZID=US/Hawaii:" + iCalDateTimeStart + "\r\n" + 
		        		"DTEND;TZID=US/Hawaii:" + iCalDateTimeEnd + "\r\n" + 
		        		"SUMMARY:" + editText1.getText().toString() + "\r\n" + 
		        		"END:VEVENT\r\n" + 
		        		"END:VCALENDAR";
				
				try {
				    FileOutputStream fos = openFileOutput("test.ics", Context.MODE_PRIVATE);
				    fos.write(content.getBytes());
				    fos.close();
				} catch (Exception e) {
				    e.printStackTrace();
				}
				
			}
		});
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
	
	/**
	 * Converts components of a DatePicker date into iCalendar format.
	 * @param year The year of the event.
	 * @param month The month (of the year) of the event.
	 * @param day The day (of the month) of the event.
	 * @return A String in yyyymmdd format.
	 */
	public String convertDate(int year, int month, int day) {
/*		SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
		String formattedDate = sdf.format(new Date(year, month, day));
*/		
		String formattedDate;
		
		String formattedYear = String.valueOf(year);
		String formattedMonth = checkNumber(month);
		String formattedDay = checkNumber(day);
		
		formattedDate = new StringBuilder().append(formattedYear).append(formattedMonth).append(formattedDay).toString();
		
		return formattedDate;
	}
	
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
