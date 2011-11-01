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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author TUAN-NGUYEN
 *
 */
public class MapPlaces extends Activity {
	 /** Called when the activity is first created. */
	 Geocoder geocoder;
	 TextView txtPosition;
	 
	 public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.mainplaces);
	     
	     GridView places = (GridView)findViewById(R.id.gvPlaces);
	     txtPosition = (TextView)findViewById(R.id.txtPositionPlaces);
	     txtPosition.setText("Loading...");
	     places.setAdapter(new ImageAdapter(this));
	     
	     //Click item event
	     places.setOnItemClickListener(new OnItemClickListener() {
	    	  public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	    		  Intent intent = new Intent(); 
	    		  intent.setClass(MapPlaces.this, InputAddress.class);
	    		  intent.putExtra("keyword", getKeywork(position));
	    		  intent.putExtra("address", txtPosition.getText());
            	  startActivity(intent);
	          }
		});
	     
	     //Location initiation
	     geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
	     LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	     LocationListener mlocListener = new GPSLocationListener();
	     mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
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
	 
	 public class GPSLocationListener implements LocationListener {
	    	@Override
	    	public void onLocationChanged(Location location)
	    	{
	    		GoogleDataReader reader = new  GoogleDataReader();
	    		String address = reader.GetAddressFromLatLng(location.getLatitude(),location.getLongitude() 
	    													,geocoder);
	    		txtPosition.setText(address);
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
		 	case 3: keywork="university";
		 			break;
		 	case 4: keywork="hotel";
		 			break;
		 	case 5: keywork="gas station";
		 			break;
		 }
		 
		 return keywork;
	 }
}
