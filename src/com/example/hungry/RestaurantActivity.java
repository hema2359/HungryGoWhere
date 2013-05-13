package com.example.hungry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hungry.datatype.Restaurant;
import com.example.hungry.fragment.DetailFragment;
import com.example.hungry.fragment.GalleryFragment;
import com.example.hungry.fragment.ReviewFragment;
import com.example.hungry.utils.Const;

public class RestaurantActivity extends FragmentActivity {
	Restaurant mRestaurant;
	Long mRestId;
	private TextView mTvTitle;
	private LinearLayout mTabbar;
	private ImageView mBtnDetail;
	private ImageView mBtnReview;
	private ImageView mBtnGallery;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant);
		
		mRestId = getIntent().getLongExtra(Const.INTENT_REST_NAME, 0);
		initializeViews();
	//	setListeners();
		setInitialFragment();
	}
	
	private void setInitialFragment() {
		navigateTo(DetailFragment.newInstance(mRestId), true,true);
	}
	
	
	
	private void setListeners() {
		mBtnDetail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(RestaurantActivity.this, "Details", Toast.LENGTH_SHORT).show();
				navigateTo(DetailFragment.newInstance(mRestId),true,true);
				
			}
		});
		mBtnReview.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(RestaurantActivity.this, "Review", Toast.LENGTH_SHORT).show();
				navigateTo(new ReviewFragment(), true, true);
			}
		});
		mBtnGallery.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(RestaurantActivity.this, "Gallery", Toast.LENGTH_SHORT).show();
				navigateTo(new GalleryFragment(), true, true);
			}
		});
		
	}
	private View.OnClickListener TabClickListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ViewGroup tabBar = (ViewGroup)v.getParent();
				int childcount = tabBar.getChildCount();
				for (int i=0; i < childcount; i++){
				      View cv = tabBar.getChildAt(i);
				      if(cv == v) cv.setSelected(true);
				      else cv.setSelected(false);
				}
				switch(v.getId())
				{
				case R.id.iv_detail:
					Toast.makeText(RestaurantActivity.this, "Details", Toast.LENGTH_SHORT).show();
					navigateTo(DetailFragment.newInstance(mRestId),true,true);
					break;
				case R.id.iv_review:
					Toast.makeText(RestaurantActivity.this, "Review", Toast.LENGTH_SHORT).show();
					navigateTo(new ReviewFragment(), true, true);
					break;
				case R.id.iv_gallery:
					Toast.makeText(RestaurantActivity.this, "Gallery", Toast.LENGTH_SHORT).show();
					navigateTo(new GalleryFragment(), true, true);
					break;
				}
			}
	};
	private void initializeViews() {
		//LinearLayout ll = (LinearLayout)findViewById(R.id.ll_top_bar);
		//mTvTitle = (TextView)ll.findViewById(R.id.tv_title);
		mTabbar = (LinearLayout)findViewById(R.id.ll_bottom_bar);
		mBtnDetail = (ImageView)mTabbar.findViewById(R.id.iv_detail);
		mBtnReview = (ImageView)mTabbar.findViewById(R.id.iv_review);
		mBtnGallery = (ImageView)mTabbar.findViewById(R.id.iv_gallery);
		mBtnDetail.setOnClickListener(TabClickListener);
		mBtnReview.setOnClickListener(TabClickListener);
		mBtnGallery.setOnClickListener(TabClickListener);		
		
	}
	
	public void navigateTo(Fragment newFragment, boolean initial, boolean newTabGroup){
		FragmentManager manager = getSupportFragmentManager();
		if (newTabGroup) {
			int i = manager.getBackStackEntryCount();
			for (int j = 0; j < i; j++) {
				manager.popBackStack();
			}
		}
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(R.id.fragment_content, newFragment);
		if (!initial) {
			ft.addToBackStack(null);
		}
		ft.commit();
	}
	
}
