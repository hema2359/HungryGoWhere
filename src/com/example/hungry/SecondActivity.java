package com.example.hungry;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hungry.adapter.CustomPagerAdapter;
import com.example.hungry.share.SessionStore;

public class SecondActivity extends Activity {
	private final String HUNGRY_KEY = "hungry_session";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		ArrayList<String> nlist = new ArrayList<String>();
		for(int i=0; i<5; i++){
			String s= "Near"+i;
			nlist.add(s);
		}		
		ArrayList<String> blist = new ArrayList<String>();
		for(int i=0; i<5; i++){
			String s= "Best"+i;
			blist.add(s);
		}		
		ArrayList<String> wlist = new ArrayList<String>();
		for(int i=0; i<5; i++){
			String s= "Want"+i;
			wlist.add(s);
		}		
		ArrayList<String> tlist = new ArrayList<String>();
		for(int i=0; i<5; i++){
			String s= "Tried"+i;
			tlist.add(s);
		}
		
		ViewPager vpNearby = (ViewPager)findViewById(R.id.vp_nearby);
		CustomPagerAdapter nadapter = new CustomPagerAdapter(this, nlist);
		vpNearby.setAdapter(nadapter);
		ViewPager vpBest = (ViewPager)findViewById(R.id.vp_best);
		CustomPagerAdapter badapter = new CustomPagerAdapter(this, blist);
		vpBest.setAdapter(badapter);	
		ViewPager vpWant = (ViewPager)findViewById(R.id.vp_want);
		CustomPagerAdapter wadapter = new CustomPagerAdapter(this, wlist);
		vpWant.setAdapter(wadapter);
		ViewPager vpTried = (ViewPager)findViewById(R.id.vp_tried);
		CustomPagerAdapter tadapter = new CustomPagerAdapter(this, tlist);
		vpTried.setAdapter(tadapter);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}
	
	
	

}
