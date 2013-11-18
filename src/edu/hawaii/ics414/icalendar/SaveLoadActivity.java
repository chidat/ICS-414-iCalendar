package edu.hawaii.ics414.icalendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

public class SaveLoadActivity extends Activity{
	/*start swipe code*/
	private GestureDetector gestureDetector;
	  
	  @SuppressWarnings("deprecation")
	@Override
	  public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_save_load);

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
		  
	  }

	  private void onRightSwipe() {
		//change this to something else more useful later
		  
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
	           SaveLoadActivity.this.onLeftSwipe();

	        // Right swipe
	        } else if (-diff > SWIPE_MIN_DISTANCE
	        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	          SaveLoadActivity.this.onRightSwipe();
	        }
	      } catch (Exception e) {
	        Log.e("SaveLoadActivity", "Error on gestures");
	      }
	      return false;
	    }
	  }
	
	/*end swipe code*/

}
