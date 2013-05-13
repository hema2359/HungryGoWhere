package com.example.hungry.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hungry.R;

public class CustomPagerAdapter extends PagerAdapter{
	private final String TAG = "Hungry Second";
	private Context context;
	ArrayList<Integer> names = new ArrayList<Integer>();
	
	public CustomPagerAdapter(Context context, ArrayList<Integer> names){
		this.context = context;
		this.names = names;
	}
	
	@Override
	public Object instantiateItem(View collection, int position) {
		Log.d(TAG,"instantiateitem");
		LayoutInflater inflater = (LayoutInflater)collection.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.item_restaurant, null);
		
		ImageView iv = (ImageView) view.findViewById(R.id.iv_rest_image);
		iv.setImageResource(names.get(position));
	
		((ViewPager)  collection).addView(view);
		return view;
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
