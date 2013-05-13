package com.example.hungry;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hungry.datatype.Restaurant;
import com.example.hungry.db.HGWDBStructure.ContentURIs;
import com.example.hungry.utils.Const;

public class ThirdActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor>,OnItemClickListener{
	private final String HUNGRY_KEY = "hungry_session";
	private ListView mListRest;
	private SimpleCursorAdapter adapter;
	private static final int LOADER_REST = 0x01;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third);
		mListRest = (ListView)findViewById(R.id.list_rest);
		String from[] = { Restaurant.NAME_COL };
		int to[] = { R.id.txt_rest_name };
		getSupportLoaderManager().initLoader(LOADER_REST, new Bundle(),this);
		adapter = new SimpleCursorAdapter(this, R.layout.list_item,null,from,to,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		mListRest.setAdapter(adapter);
		mListRest.setOnItemClickListener(this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id,
			Bundle args) {
		CursorLoader loader = new CursorLoader(this,ContentURIs.RESTAURANTS_URI,
				new String[]{Restaurant.ID_COL, Restaurant.NAME_COL},null,null,null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader,
			Cursor cursor) {
		adapter.swapCursor(cursor);
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
		Intent i = new Intent(this,RestaurantActivity.class);
		Cursor c = (Cursor)parent.getItemAtPosition(position);
		Long sid = c.getLong(0);
		i.putExtra(Const.INTENT_REST_NAME, sid);
		startActivity(i);
	}

	

}
