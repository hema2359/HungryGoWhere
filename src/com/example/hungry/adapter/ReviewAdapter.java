package com.example.hungry.adapter;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hungry.R;

public class ReviewAdapter extends CursorAdapter{

	private LayoutInflater mLayoutInflater;

	public ReviewAdapter(Context context, Cursor c) {
		super(context, c, 0);
		mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder)view.getTag();
		holder.tvReviewName.setText("Sample");
		holder.tvReviewType.setText("Blogger");
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View convertView = mLayoutInflater.inflate(R.layout.item_review,
				parent, false);
		ViewHolder holder = new ViewHolder();
		holder.ivProfileImage = (ImageView)convertView.findViewById(R.id.iv_review_profile);
		holder.tvReviewName =(TextView)convertView.findViewById(R.id.tv_review_name);
		holder.tvReviewType = (TextView)convertView.findViewById(R.id.tv_review_type);
		holder.tvReviewText = (TextView)convertView.findViewById(R.id.tv_review_text);
		holder.tvReviewSeemore = (TextView)convertView.findViewById(R.id.tv_review_see_more);
		convertView.setTag(holder);
		return convertView;
	}
	
	public class ViewHolder{
		public ImageView ivProfileImage;
		public TextView tvReviewName;
		public TextView tvReviewType;
		public TextView tvReviewText;
		public TextView tvReviewSeemore;
	}

}
