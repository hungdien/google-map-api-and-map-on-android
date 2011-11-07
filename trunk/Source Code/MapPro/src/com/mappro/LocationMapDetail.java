package com.mappro;

import java.util.ArrayList;

import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.mappro.supportedclass.GoogleDataReader;

public class LocationMapDetail extends MapActivity {

	private MapController mapControl;
	private MapView mapView;
	
	  private double lat;
	  private double lng;
	  //To determine what kind of place will be searched (atm, school, bookstore...)
	  private String keywork;
	
	  private ArrayList<GeoPoint> lstGeoPoint;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.mapme);
	        
	        //Get data from InputAddress activity
		    Bundle extras = getIntent().getExtras();
		    if(extras !=null) {
		    	 GoogleDataReader dataReader = new GoogleDataReader();
		    	 keywork = extras.getString("keywork");
		    	 
		         //current location
			     lat = extras.getDouble("lat");
			     lng = extras.getDouble("lng");
			    	 
			     //get coordinate from google map by given key work
			     lstGeoPoint = dataReader.PlacesCoordinateReader(keywork, lat, lng, 10);
		     }
	        
	        
	        //Add map controller with zoom controls
	        mapView = (MapView) findViewById(R.id.mv2);
	        mapView.setSatellite(false);
	        mapView.setTraffic(false);
	        mapView.setBuiltInZoomControls(true);   // Set android:clickable=true in main.xml
	        int maxZoom = mapView.getMaxZoomLevel();
	        int initZoom = (int)(0.95*(double)maxZoom);
	        mapControl = mapView.getController();
	        mapControl.setZoom(initZoom);
	 }
	 
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
