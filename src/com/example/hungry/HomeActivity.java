package com.example.hungry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hungry.asynctask.RestAsyncTask;
import com.example.hungry.customview.AnimationLayout;

public class HomeActivity extends Activity{
	LinearLayout mLayoutRD, mLayoutSearch, mLayoutDiscover;
	Button mBtnDiscover;
	ImageView mBtnHB, mBtnFilter;
	private OnClickListener mHBListener;
	private OnClickListener mSearchListener;
	private boolean mSideOpened=false;
	private View mLlMain,mLlSide,mLlTop;
	private ListView mListSide,mListTop,mListFilter;
	private TextView mBtnRestaurant;
	private AnimationLayout mLayout;
	private static final String TAG = "HUNGRY";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initializeViews();
		setListeners();
		new RestAsyncTask(this).execute();

	}

	private void initializeViews() {
		mLayout = (AnimationLayout)findViewById(R.id.anim_layout);
		mBtnHB = (ImageView)findViewById(R.id.btn_hb);
		mBtnDiscover = (Button)findViewById(R.id.btn_discover);
		mBtnFilter = (ImageView)findViewById(R.id.btn_filter);
		
		mLlSide = findViewById(R.id.lt_sidebar);
		mLlMain = findViewById(R.id.lt_main);
		mLlTop = findViewById(R.id.lt_toplist);
		mListSide = (ListView)findViewById(R.id.lv_side);
		mListTop = (ListView)findViewById(R.id.lv_top);
		mListFilter = (ListView)findViewById(R.id.lv_filter);
		ImageView iv = (ImageView)findViewById(R.id.imageView1);
		iv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(HomeActivity.this,ThirdActivity.class);
				startActivity(i);
			}
		});
		
		String slist[] = {"sideone","sidetwo","sidethree"};
		String tlist[] = {"topone","toptwo","topthree"};
		String flist[] = {"filterone","filtertwo","filterthree"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,slist);
		mListSide.setAdapter(adapter);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tlist);
		mListTop.setAdapter(adapter);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,flist);
		mListFilter.setAdapter(adapter);
	}

	private void setListeners() {

		
		mBtnHB.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Intent i = new Intent(HomeActivity.this,FourthActivity.class);
				//startActivity(i);
				Toast.makeText(HomeActivity.this, "HB clicked", Toast.LENGTH_SHORT).show();
				mLayout.toggleLeftbar();
				
			}
		});
		mBtnDiscover.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mLayout.toggleMenu();
				
			}
		});
		mBtnFilter.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mLayout.toggleRightbar();
			}
		});
		
	}
	
}
