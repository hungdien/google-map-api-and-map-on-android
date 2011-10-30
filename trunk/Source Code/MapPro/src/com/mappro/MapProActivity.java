package com.mappro;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.mappro.supportedclass.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


public class MapProActivity extends Activity {
    /** Called when the activity is first created. */
	 Geocoder geocoder;
	 TextView txt;
	 public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.main);
	     
	     //get user-interface controls
	     GridView gridview = (GridView) findViewById(R.id.gridview);
	     txt = (TextView)findViewById(R.id.main_text_place);
	     
	     //Location initiation
	     geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
	     LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	     LocationListener mlocListener = new GPSLocationListener();
	     mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
	     
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
	            	  return;
	              }
	              
	              if(position==1)
	              {
	            	  return;
	              }
	              
	              if(position==2)
	              {
	            	  intent.setClass(MapProActivity.this, MapDirection.class);
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
	 
	 public class GPSLocationListener implements LocationListener
	    {
	    	@Override
	    	public void onLocationChanged(Location location)
	    	{
	    		txt.setText("Loading...");
	    		GoogleDataReader reader = new  GoogleDataReader();
	    		String address = reader.GetAddressFromLatLng(location.getLatitude(),location.getLongitude() 
	    													,geocoder);
	    		txt.setText(address);
	    	}

	    	@Override
	    	public void onProviderDisabled(String provider)
	    	{
	    	}
	    	@Override
	    	public void onProviderEnabled(String provider)
	    	{
	    	}

	    	@Override
	    	public void onStatusChanged(String provider, int status, Bundle extras)
	    	{

	    	}
	    }/* End of Class GPSLocationListener */
	}	