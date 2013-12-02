package edu.hawaii.ics414.icalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	
    /*start swipe code*/
	private GestureDetector gestureDetector;
	
	
	  
	  @SuppressWarnings("deprecation")
	@Override
	  public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);

	    gestureDetector = new GestureDetector(
	                      new SwipeGestureDetector());
	  }


	  @Override
	  public boolean onTouchEvent(MotionEvent event) {
	    if (gestureDetector.onTouchEvent(event)) {
	      return true;
	    }
	    return super.onTouchEvent(event);
	  }

	  private void onLeftSwipe() {
		  //change this to something else more useful later
		  Intent intent = new Intent(this, LoadActivity.class);
	      startActivity(intent);
	  }

	  private void onRightSwipe() {
		//change this to something else more useful later
		  Intent intent = new Intent(this, SaveActivity.class);
	      startActivity(intent);
	  }

	  // Private class for gestures
	  private class SwipeGestureDetector 
	          extends SimpleOnGestureListener {
	    // Properties to control the swipe
	    private static final int SWIPE_MIN_DISTANCE = 70;
	    private static final int SWIPE_MAX_OFF_PATH = 200;
	    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

	    @Override
	    public boolean onFling(MotionEvent e1, MotionEvent e2,
	                         float velocityX, float velocityY) {
	      try {
	        float diffAbs = Math.abs(e1.getY() - e2.getY());
	        float diff = e1.getX() - e2.getX();

	        if (diffAbs > SWIPE_MAX_OFF_PATH)
	          return false;
	        
	        // Left swipe
	        if (diff > SWIPE_MIN_DISTANCE
	        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	           MainActivity.this.onLeftSwipe();

	        // Right swipe
	        } else if (-diff > SWIPE_MIN_DISTANCE
	        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	          MainActivity.this.onRightSwipe();
	        }
	      } catch (Exception e) {
	        Log.e("MainActivity", "Error on gestures");
	      }
	      return false;
	    }
	  }
	
	/*end swipe code*/
	  
    public final static String EXTRA_MESSAGE = "edu.hawaii.ics414.icalendar.MESSAGE";

    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void viewCalendar(View view) {
        Intent intent = new Intent(this, CalendarViewActivity.class);
        startActivity(intent);
    }

    public void pickTime(View view) {
        Intent intent = new Intent(this, TimePickerActivity.class);
        startActivity(intent);
    }
    
/*    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/
    
    public void save(View view) {
        Intent intent = new Intent(this, SaveActivity.class);
        startActivity(intent);
    }
    
    public void createEvent(View view) {
    	Intent intent = new Intent(this, CreateEventActivity.class);
    	startActivity(intent);
    }

}