package com.example.hungry.fragment;

import java.util.ArrayList;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hungry.R;
import com.example.hungry.RestaurantActivity;
import com.example.hungry.adapter.CustomPagerAdapter;
import com.example.hungry.datatype.Restaurant;
import com.example.hungry.db.HGWDBStructure.ContentURIs;

public class DetailFragment extends Fragment {
	private Long mRestId;
	private RestaurantActivity mParent;
	private Restaurant mRestaurant;
	private TextView mTvTitle;
	private View emptyView;
	private View mPhotos;
	private String TAG = "HUNGRY";

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View view  = inflater.inflate(R.layout.fragment_detail, container, false);
		mParent = (RestaurantActivity)getActivity();
		getRestaurant();
		initializeViews(view);
        return view;
    }
	public static DetailFragment newInstance(Long id){
		DetailFragment df= new DetailFragment();
		df.mRestId = id;
		return df;
	}
	private void getRestaurant() {
		Cursor c = mParent.getContentResolver().query(ContentURIs.RESTAURANTS_URI, null,
				""+Restaurant.ID_COL+ " = "+mRestId, null, null);
		if(c.moveToFirst())
			mRestaurant = new Restaurant(c);

	}
	private void initializeViews(final View v) {
		ArrayList<Integer> nlist = new ArrayList<Integer>();
		nlist.add(R.drawable.photo1);
		nlist.add(R.drawable.photo2);
		nlist.add(R.drawable.photo3);
		
		View topbar  = mParent.findViewById(R.id.ll_top_bar);
		mTvTitle = (TextView)topbar.findViewById(R.id.tv_title);
		mTvTitle.setText(mRestaurant.mName);
		TextView addrBlob = (TextView)v.findViewById(R.id.tv_address);
		addrBlob.setText(mRestaurant.mAddrBlob);
		TextView operHour = (TextView)v.findViewById(R.id.tv_oper_hour);
		operHour.setText(mRestaurant.mOperHour);
		final ViewPager vpRest = (ViewPager)v.findViewById(R.id.vp_restaurant);
		CustomPagerAdapter nadapter = new CustomPagerAdapter(mParent, nlist);
		vpRest.setAdapter(nadapter);
		final ScrollView ContentView = (ScrollView)v.findViewById(R.id.ll_content);
		emptyView = v.findViewById(R.id.v_empty);
		emptyView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				vpRest.onTouchEvent(event);
				/*LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) emptyView.getLayoutParams();
		    	lp.height = 200;
		    	emptyView.setLayoutParams(lp);*/
				ContentView.fullScroll(ScrollView.FOCUS_UP);
				
				return true;
			}
		});
		
		View fb = v.findViewById(R.id.ll_ratings);
		TextView tv = (TextView) fb.findViewById(R.id.tv_content);
		tv.setText("Ratings");
		
		fb = v.findViewById(R.id.ll_price);
		tv = (TextView) fb.findViewById(R.id.tv_content);
		tv.setText("Price Range");
		
		fb = v.findViewById(R.id.ll_cuisine);
		tv = (TextView) fb.findViewById(R.id.tv_content);
		tv.setText("Cuisine");
		
		fb = v.findViewById(R.id.ll_place);
		tv = (TextView) fb.findViewById(R.id.tv_content);
		tv.setText("Type of Place");
		
		fb = v.findViewById(R.id.ll_rate_fb);
		tv = (TextView) fb.findViewById(R.id.tv_rate_title);
		tv.setText("Food & Beverage");
		
		fb = v.findViewById(R.id.ll_rate_ambience);
		tv = (TextView) fb.findViewById(R.id.tv_rate_title);
		tv.setText("Ambience");
		
		fb = v.findViewById(R.id.ll_rate_value);
		tv = (TextView) fb.findViewById(R.id.tv_rate_title);
		tv.setText("Value");
		
		fb = v.findViewById(R.id.ll_rate_service);
		tv = (TextView) fb.findViewById(R.id.tv_rate_title);
		tv.setText("Service");
		
		
	}
}
