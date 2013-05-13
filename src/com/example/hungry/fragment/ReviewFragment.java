package com.example.hungry.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.hungry.R;
import com.example.hungry.adapter.ReviewAdapter;



public class ReviewFragment extends Fragment{
	public CursorAdapter mAdapter;
	public ListView mLvReview;
	private FragmentActivity mParent;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		mParent = this.getActivity();
		/*View view  = inflater.inflate(R.layout.fragment_review, container, false);
		mLvReview = (ListView)view.findViewById(R.id.lv_review);
		mAdapter = new ReviewAdapter(mParent,null);
		mLvReview.setAdapter(mAdapter);
		View emptyView = view.findViewById(R.id.list_empty);
		mLvReview.setEmptyView(emptyView);*/
		View view = inflater.inflate(R.layout.item_review, container, false);
		return view;
	}
}
