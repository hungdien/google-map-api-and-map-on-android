/**
 * 
 */
package com.mappro;


import java.io.IOException;

import java.util.List;
import java.util.Locale;




import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.mappro.supportedclass.Help;
import com.mappro.supportedclass.Prefs;
import com.mappro.supportedclass.CustomPinpoint;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;

import android.os.Bundle;


import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;



public class MapDetail extends MapActivity implements OnClickListener {
	MapView map; 
	Drawable d,d2;
	    
	double lat ; // Temporary test values for lat/long 
    double lon  ; 
    private int latE6; 
    private int lonE6; 
    
    int las;
	int los;
    
    GeoPoint gp; 
    GeoPoint touchedPoint;
    
    double lat1;
 	double lon1;
 	
 	MapController controller;
 	List<Overlay> overlayList;
 	int i;
 	
 	long start;
    long stop;
    
    int x,y;
    Touchy t;
   
 	

	public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     //requestWindowFeature(Window.FEATURE_NO_TITLE);  // Suppress title bar to give more space
	     setContentView(R.layout.mainmap);
	    
	 	
	 // Add Click listeners for all buttons
        View firstButton = findViewById(R.id.geocode_button);
        firstButton.setOnClickListener(this);
        View secondButton = findViewById(R.id.presentLocation_button);
        secondButton.setOnClickListener(this);
        ///gps
        

        // Add map controller with zoom controls 
        map=(MapView)findViewById(R.id.mapv);
        controller = map.getController();
        //map.setSatellite(false);
        //map.setStreetView(true);
        map.setBuiltInZoomControls(true); // Set android:clickable=true in mainmap.xml 
        int maxZoom = map.getMaxZoomLevel(); 
        int initZoom = (int)(0.90*(double)maxZoom); 
        controller.setZoom(initZoom); 
        GeoPoint point = new GeoPoint(10972006, 106667428);
        controller.animateTo(point);
        
        //d=getResources().getDrawable(R.drawable.startpoint);
        
        d=getResources().getDrawable(R.drawable.startpoint);
        
        
      //overlay click point
        t=new Touchy();
        overlayList = map.getOverlays();
        overlayList.add(t);
        
        
        
	 }
	
	class Touchy extends Overlay{
    	public boolean onTouchEvent(MotionEvent e,MapView m){
    	if(e.getAction() == MotionEvent.ACTION_DOWN){
    		start=e.getEventTime();
    		x=(int)e.getX();// 
    		y=(int)e.getY();
    		touchedPoint = map.getProjection().fromPixels(x, y);
    	}	
    	if(e.getAction()== MotionEvent.ACTION_UP){
    		stop=e.getEventTime();
    	}
    	if(stop-start>1500){
    		AlertDialog alert = new AlertDialog.Builder(MapDetail.this).create();
    		alert.setTitle("Pick an option");
    		alert.setMessage("T told to pick an option");
    		alert.setButton("Place on pinpoint", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					OverlayItem overlayItem= new OverlayItem(touchedPoint,"What 's up"," 2nd string");
					CustomPinpoint custom = new CustomPinpoint(d, MapDetail.this);
					custom.insertPinpoint(overlayItem);
					overlayList.add(custom);
					
					
					
					
					
				}
			});
    		alert.setButton2("get address", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Geocoder geocoder = new Geocoder(getBaseContext(),Locale.getDefault());
					try{
						List<Address> address = geocoder.getFromLocation(touchedPoint.getLatitudeE6()/1E6, touchedPoint.getLongitudeE6()/1E6, 1);
						if (address.size()>0){
							String display= "";
							for(int i=0;i<address.get(0).getMaxAddressLineIndex();i++){
								
								display +=address.get(0).getAddressLine(i)+"n";
								}
							Toast t= Toast.makeText(getBaseContext(),display, Toast.LENGTH_LONG);
							t.show();
							}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						
					}
				}
			});
    		alert.setButton3("Toggle View", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if(map.isSatellite()){
						map.setSatellite(false);
						map.setStreetView(true);
												
					}else {
						map.setSatellite(true);
						map.setStreetView(false);
					
					}
				}
			});
    		alert.show();
    		return true;
    	}
    		return false;
    	} ; 
    }
		

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.geocode_button:
			// Following adapted from Conder and Darcey, pp.321 ff.		
			EditText placeText = (EditText) findViewById(R.id.geocode_input);			
			String placeName = placeText.getText().toString();
			// Break from execution if the user has not entered anything in the field
			if(placeName.compareTo("")==0) break;	
			//int numberOptions = 5;
			//String [] optionArray = new String[numberOptions];
			Geocoder gcoder = new Geocoder(this);
			
			// Note that the Geocoder uses synchronous network access, so in a serious application
			// it would be best to put it on a background thread to prevent blocking the main UI if network
			// access is slow. Here we are just giving an example of how to use it so, for simplicity, we
			// don't put it on a separate thread.
			
			
			t=new Touchy();
	        overlayList = map.getOverlays();
	        overlayList.add(t);
			//try{
				/*List<Address> results = gcoder.getFromLocationName(placeName,numberOptions);
				//Iterator<Address> locations = results.iterator();
				String raw = "\nRaw String:\n";
				String country;
				int opCount = 0;
				//while(locations.hasNext()){
					//Address location = locations.next();
					lat1 = location.getLatitude();
					lon1 = location.getLongitude();
					country = location.getCountryName();
					if(country == null) {
						country = "";
					} else {
						country =  ", "+country;
					}
					raw += location+"\n";
					optionArray[opCount] = location.getAddressLine(0)+", "+location.getAddressLine(1)
						+country+"\n";
					opCount ++;
				//}
				Log.i("Location-List", raw);
				Log.i("Location-List","\nOptions:\n");
				for(int i=0; i<opCount; i++){
					Log.i("Location-List","("+(i+1)+") "+optionArray[i]);*/
				//}
			//	
			//} catch (IOException e){
			//	Log.e("Geocoder", "I/O Failure; is network available?",e);
			//}
			try {
		        
			       List<Address> addresses = gcoder.getFromLocationName(placeName, 1);
			       if(addresses==null){
			      //return point;
			       }
			        
			       //just get first item of list address
			       Address add = addresses.get(0);
			     
			       lat1 = add.getLatitude();
			       lon1 = add.getLongitude();
			       
			      
			        
			      } catch (IOException e) {
			      
			      }
			
	
	   
			
			//this.putLatLong(lat1, lon1);
			 // Convert lat/long in degrees into integers in microdegrees 
			latE6 = (int) (lat1*1e6); 
			lonE6 = (int) (lon1*1e6); 
			
			gp = new GeoPoint(latE6, lonE6); 
			controller.animateTo(gp);
			//List<Overlay> overlays = map.getOverlays();
		    //myLocationOverlay = new MyMyLocationOverlay(this,map);
			if(i==1)
			{
				overlayList.removeAll(overlayList);
				t=new Touchy();
		        overlayList = map.getOverlays();
		        overlayList.add(t);
			}
			 
			    //overlays.add(myLocationOverlay);
			OverlayItem overlayItem= new OverlayItem(gp,"What 's up"," 2nd string");
			CustomPinpoint custom = new CustomPinpoint(d, MapDetail.this);
			custom.insertPinpoint(overlayItem);
			overlayList.add(custom);
			i=1;
			break;
		
		case R.id.presentLocation_button:
			Intent m = new Intent(this, Mapme.class);
			startActivity(m);
			
			break;	
                
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		int groupId = 0;
		int menuItemOrder = Menu.NONE;
		// Create menu ids for the event handler to reference
		int menuItemId1 = Menu.FIRST;
		int menuItemId2 = Menu.FIRST+1;
		int menuItemId3 = Menu.FIRST+2;
		// Create menu text
		int menuItemText1 = R.string.place;
		int menuItemText2 = R.string.street;
		int menuItemText3 = R.string.settings;
		// Add the items to the menu
		MenuItem menuItem1 = menu.add(groupId, menuItemId1, menuItemOrder, menuItemText1)
			.setIcon(R.drawable.ic_menu_close_clear_cancel);
		MenuItem menuItem2 = menu.add(groupId, menuItemId2, menuItemOrder, menuItemText2)
			.setIcon(R.drawable.ic_menu_help);
		MenuItem menuItem3 = menu.add(groupId, menuItemId3, menuItemOrder, menuItemText3)
			.setIcon(R.drawable.ic_menu_preferences);
		return true;
	}
	
	// Handle events from the popup menu above
	
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
			case (Menu.FIRST):
				//finishUp();
				Intent i = new Intent(this, MapPlaces.class);
				startActivity(i);
				return true;
				
			case (Menu.FIRST+1):
				// Actions for help page
				Intent j = new Intent(this, MapDirection.class);
				startActivity(j);
				return true;
			case(Menu.FIRST+2):
				// Actions for settings page
				Intent k = new Intent(this,Prefs.class);
				startActivity(k);
				return true;
		
		}
		return false;
	}

    
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	} 
	public void finishUp(){
    	finish();
    }
	
	
} 
