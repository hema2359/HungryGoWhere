package com.example.hungry.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomPagerAdapter extends PagerAdapter{
	private final String TAG = "Hungry Second";
	private Context context;
	ArrayList<String> names = new ArrayList<String>();
	
	public CustomPagerAdapter(Context context, ArrayList<String> names){
		this.context = context;
		this.names = names;
	}
	
	@Override
	public Object instantiateItem(View arg0, int position) {
		Log.d(TAG,"instantiateitem");
	LinearLayout ll = new LinearLayout(context);
	ll.setOrientation(LinearLayout.VERTICAL);
	TextView tv = new TextView(context);
	tv.setText(names.get(position));
	ll.addView(tv);
	Button btn = new Button(context);
	btn.setText(names.get(position));
	btn.setOnClickListener(btnlistener);
	ll.addView(btn);
	((ViewPager)  arg0).addView(ll);
	return ll;
	}
	
	private View.OnClickListener btnlistener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Toast.makeText(context, ((Button)v).getText(), Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == (View) arg1;
	}
	
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);
		}

}
