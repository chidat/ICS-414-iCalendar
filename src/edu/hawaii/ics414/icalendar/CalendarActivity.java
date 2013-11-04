package edu.hawaii.ics414.icalendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

public class CalendarActivity extends Activity {

    CalendarView calendar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    calendar = (CalendarView)findViewById(R.id.calendar);
    calendar.setOnDateChangeListener(new OnDateChangeListener(){
     
    public void onSelectedDayChange(CalendarView view,
    int year, int month, int dayOfMonth) {
    Toast.makeText(getApplicationContext(),
    dayOfMonth +"/"+month+"/"+ year,Toast.LENGTH_LONG).show();}});
    }
}
