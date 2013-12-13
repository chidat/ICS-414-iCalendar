package edu.hawaii.ics414.icalendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoadActivity extends Activity {
	
	private static Context context;
	private TextView loadedEventNameTextView;
	private TextView loadedEventDateStartTextView;
	private TextView loadedEventDateEndTextView;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoadActivity.context = getApplicationContext();
        setContentView(R.layout.activity_load);
        
		Button loadButton = (Button) findViewById(R.id.load_button);
		final EditText calendarToLoad = (EditText) findViewById(R.id.calendar_to_load);
		loadedEventNameTextView = (TextView) findViewById(R.id.loaded_event_name);
		loadedEventDateStartTextView = (TextView) findViewById(R.id.loaded_event_start);
		loadedEventDateEndTextView = (TextView) findViewById(R.id.loaded_event_end);

		/**
		 * Listener for the load button.
		 */
		loadButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// grab the text (calendar name) from the input box.
				String calendarName = calendarToLoad.getText().toString();
				
				// String manipulation to get the full path to the file.
				String dir = LoadActivity.getAppContext().getFilesDir().getParentFile().getPath() + "/files/";
				Log.i("load", "directory: " + dir);
				String path = dir + calendarName + ".ics";
				Log.i("load", "path: " + path);
				
				// get the file.
				File file = new File(path);
				Scanner scanner = null;
				try {
					scanner = new Scanner(file);
				}
				catch (FileNotFoundException e) {
					Log.e("load", "File not found", e);
				}

				String line = "";
				
				// parse .ics file
				while (scanner.hasNextLine()) {
					line = scanner.nextLine();
					if (line.equals("BEGIN:VEVENT")) {
						Log.i("load", "event found: " + line);
					}
					else if (line.startsWith("DTSTART")) {
						Log.i("load", "date start: " + line);
						loadTextView(loadedEventDateStartTextView, line.substring(23), "Event start");
					}
					else if (line.startsWith("DTEND")) {
						Log.i("load", "date end: " + line);
						loadTextView(loadedEventDateEndTextView, line.substring(21), "Event end");
					}
					else if (line.startsWith("SUMMARY")) {
						Log.i("load", "summary: " + line);
						loadTextView(loadedEventNameTextView, line.substring(8), "Event");
					}
				}
					scanner.close();
				
			}
		});
	}
	
	protected void loadTextView(TextView textView, String eventName, String title) {
		textView.setText(title + ":\n" + eventName);
	}

	protected static Context getAppContext() {
		return LoadActivity.context;
	}

}
