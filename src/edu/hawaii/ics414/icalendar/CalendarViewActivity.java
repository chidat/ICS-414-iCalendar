package edu.hawaii.ics414.icalendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.CalendarView;
/*import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;*/

public class CalendarViewActivity extends Activity {
    CalendarView calendar;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        CalendarView calendarView = new CalendarView(this);
        calendar = (CalendarView)findViewById(R.id.calendar);
        
        setContentView(calendarView);
        
/*        calendar.setOnDateChangeListener(new OnDateChangeListener(){
         
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getBaseContext(),"Selected Date is:\n" + month + "/" + dayOfMonth + "/" + year, Toast.LENGTH_LONG).show();
            };
        });*/
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
