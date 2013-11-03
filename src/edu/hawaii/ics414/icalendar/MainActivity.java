package edu.hawaii.ics414.icalendar;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
 
public class MainActivity extends Activity {
	
	RelativeLayout rl;
	CalendarView cal;
 
    @SuppressLint({ "NewApi", "NewApi" })
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        rl = (RelativeLayout) findViewById(R.id.rl);
        
        cal = new CalendarView(MainActivity.this);
        
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
        		((int) LayoutParams.MATCH_PARENT, (int) LayoutParams.MATCH_PARENT);
        
        params.topMargin = 100;
        cal.setLayoutParams(params);
        
        rl.addView(cal);
        
        cal.setOnDateChangeListener(new OnDateChangeListener() {
			
		public void onSelectedDayChange(CalendarView view, int year, int month,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			
			Toast.makeText(getBaseContext(),"Selected Date is\n\n"
				+dayOfMonth+" : "+month+" : "+year ,
				Toast.LENGTH_LONG).show();
		}
	});
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}