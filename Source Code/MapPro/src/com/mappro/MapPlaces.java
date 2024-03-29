/**
 * 
 */
package com.mappro;

import java.util.Locale;

import com.mappro.supportedclass.GoogleDataReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * @author TUAN-NGUYEN
 *
 */
public class MapPlaces extends Activity {
	 /** Called when the activity is first created. */
	 Geocoder geocoder;
	 TextView txtPosition;
	 static String gps;
	 double lat=MapProActivity .getlat();
	 double lng=MapProActivity .getlong();
	 
	 LocationManager mlocManager;
	 Location loc;
	 String provider = LocationManager.GPS_PROVIDER;
	 
	 public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.mainplaces);
	     
	     GridView places = (GridView)findViewById(R.id.gvPlaces);
	     txtPosition = (TextView)findViewById(R.id.txtPositionPlaces);
	     txtPosition.setText("Loading...");
	     String gps = (String) MapProActivity .gettext();
	     txtPosition.setText(gps);
	     places.setAdapter(new ImageAdapter(this));
	     
	     //Click item event
	     places.setOnItemClickListener(new OnItemClickListener() {
	    	  public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	    		  Intent intent = new Intent(); 
	    		  intent.setClass(MapPlaces.this, InputAddress.class);
	    		  intent.putExtra("keyword", getKeywork(position));
	    		  intent.putExtra("address", txtPosition.getText());
	    		  intent.putExtra("lat", lat);
	    		  intent.putExtra("lng", lng);
            	  startActivity(intent);
	          }
		});
	     
	     //Location initiation
	     geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
	     mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	     LocationListener mlocListener = new GPSLocationListener();
	     mlocManager.requestLocationUpdates(provider, 10000, 5, mlocListener);
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
	             R.drawable.hotelicon,R.drawable.book,
	     };
	 }
	 
	 public class GPSLocationListener implements LocationListener {
	    	@Override
	    	public void onLocationChanged(Location location)
	    	{
	    		loc = mlocManager.getLastKnownLocation(provider);
	    		GoogleDataReader reader = new  GoogleDataReader();
	    		String address = reader.GetAddressFromLatLng(loc.getLatitude(),loc.getLongitude() 
	    													,geocoder);
	    		lat = location.getLatitude();
	    		lng = location.getLongitude();
	    		
	    		txtPosition.setText(address);
	    		gps=(String)txtPosition.getText();
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
	 
	 
	 public String getKeywork(int position){
		 String keywork = "";
		 
		 switch(position)
		 {
		 	case 0: keywork="atm";
		 			break;
		 	case 1: keywork="bus";
		 			break;
		 	case 2: keywork="hospital";
		 			break;
		 	case 3: keywork="school";
		 			break;
		 	case 4: keywork="hotel";
		 			break;
		 	case 5: keywork="bookstore";
		 			break;
		 }
		 
		 return keywork;
	 }
	public static String gettext() {
		// TODO Auto-generated method stub
		return gps;
	}
}
