/**
 * 
 */
package com.mappro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author TUAN-NGUYEN
 *
 */
public class MapPlaces extends Activity {
	 /** Called when the activity is first created. */
	 public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.mainplaces);
	     
	     GridView places = (GridView)findViewById(R.id.gvPlaces);
	     places.setAdapter(new ImageAdapter(this));
	     
	     //Click item event
	     places.setOnItemClickListener(new OnItemClickListener() {
	    	  public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	    		  Intent intent = new Intent(); 
	    		  intent.setClass(MapPlaces.this, InputAddress.class);
            	  startActivity(intent);
	          }
		});
	 }
	 
	 public class ImageAdapter extends BaseAdapter{
		 private Context mContext;
		 public ImageAdapter(Context c) {
	         mContext = c;
	     }

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mThumbIds.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		 // create a new ImageView for each item referenced by the Adapter
	     public View getView(int position, View convertView, ViewGroup parent) {
	         ImageView imageView;
	         if (convertView == null) {  // if it's not recycled, initialize some attributes
	             imageView = new ImageView(mContext);
	             imageView.setLayoutParams(new GridView.LayoutParams(120, 100));
	             imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	             imageView.setPadding(20, 8, 8, 8);
	         } else {
	             imageView = (ImageView) convertView;
	         }
	         imageView.setImageResource(mThumbIds[position]);
	         return imageView;
	     }
	     // references to our images
	     private Integer[] mThumbIds = {
	             R.drawable.atmicon1, R.drawable.busicon,
	             R.drawable.hospitalicon, R.drawable.universityicon,
	             R.drawable.hotelicon,R.drawable.gasstation,
	     };
	 }
}
