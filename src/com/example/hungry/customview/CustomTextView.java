package com.example.hungry.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView extends TextView {

	public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
	    super(context, attrs, defStyle);
	    init();
	}
	
	public CustomTextView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    init();
	}
	
	public CustomTextView(Context context) {
	    super(context);
	    init();
	}
	
	public void init() {
	
	    Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/helveticaneue.ttf");
	    setTypeface(tf ,1);
	
	}
}
