package com.mappro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MapProActivity extends Activity {
    /** Called when the activity is first created. */
	 public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.main);
	     
	     GridView gridview = (GridView) findViewById(R.id.gridview);
	     gridview.setAdapter(new ImageAdapter(this));
	     
	     //Click item event
	     gridview.setOnItemClickListener(new OnItemClickListener() {
	    	  public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	              //Toast.makeText(MapProActivity.this, "" + position, Toast.LENGTH_LONG).show();
	              
	              Intent intent = new Intent();
	              if(position==0)
	              {
	            	  intent.setClass(MapProActivity.this, MapPlaces.class);
	            	  startActivity(intent);
	              }
	          }
		});
	 }

	 public class ImageAdapter extends BaseAdapter {
	     private Context mContext;
	     public ImageAdapter(Context c) {
	         mContext = c;
	     }
	     public int getCount() {
	         return mThumbIds.length;
	     }
	     public Object getItem(int position) {
	         return null;
	     }
	     public long getItemId(int position) {
	         return 0;
	     }
	     
	     // create a new ImageView for each item referenced by the Adapter
	     public View getView(int position, View convertView, ViewGroup parent) {
	         ImageView imageView;
	         if (convertView == null) {  // if it's not recycled, initialize some attributes
	             imageView = new ImageView(mContext);
	             imageView.setLayoutParams(new GridView.LayoutParams(150, 130));
	             imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	             imageView.setPadding(20, 110, 20, 90);
	         } else {
	             imageView = (ImageView) convertView;
	         }
	         imageView.setImageResource(mThumbIds[position]);
	         return imageView;
	     }
	     // references to our images
	     private Integer[] mThumbIds = {
	             R.drawable.diadiem1, R.drawable.bando,
	             R.drawable.danduong, R.drawable.gioithieu1,
	             
	     };
	 }

	}	