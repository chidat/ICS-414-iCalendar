package edu.hawaii.ics414.icalendar;

import java.io.FileOutputStream;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SaveActivity extends Activity {

	private TextView lblTextViewOne;
	private EditText editText1;
	private EditText editText2;

	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        
        lblTextViewOne = (TextView) findViewById(R.id.lblTextViewOne);
        
        editText1 = (EditText) findViewById(R.id.editText1);
        
        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				lblTextViewOne.setText(editText1.getText());
				
				final String content = "BEGIN:VCALENDAR\r\n" + 
		        		"VERSION:2.0\r\n" + 
		        		"PRODID:-//hacksw/handcal//NONSGML v1.0//EN\r\n" + 
		        		"BEGIN:VEVENT\r\n" + 
		        		"UID:uid1@example.com\r\n" + 
		        		"DTSTAMP:20131130T170000Z\r\n" +
		        		"DTSTART:20131225T170000Z\r\n" + 
		        		"DTEND:20131225T035959Z\r\n" + 
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
}
