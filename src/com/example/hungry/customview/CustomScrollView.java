package com.example.hungry.customview;

import com.example.hungry.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {
    private GestureDetector mGestureDetector;
    View.OnTouchListener mGestureListener;

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()){
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    	Log.d("HUNGRY", "scroll");
    	/*View parent = (View) getParent();
    	View photo = parent.findViewById(R.id.ll_photos);
    	View empty = findViewById(R.id.v_empty);
    	if(empty.hasFocus()){
    		photo.bringToFront();
    	}else{
        	bringToFront();

    	}
		photo.bringToFront();

    	View empty = findViewById(R.id.v_empty);
    	LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) empty.getLayoutParams();
    	lp.height = 100;
    	empty.setLayoutParams(lp);*/
        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
    }

    // Return false if we're scrolling in the x direction  
    class YScrollDetector extends SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if(Math.abs(distanceY) > Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }
}