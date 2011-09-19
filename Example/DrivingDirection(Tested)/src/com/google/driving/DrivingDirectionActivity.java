package com.google.driving;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

public class DrivingDirectionActivity extends MapActivity {
	
	MapView mapView; 	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MapView mapView = (MapView) findViewById(R.id.myMapView1); 
        double src_lat = 25.04202; // the testing source 
        double src_long = 121.534761; 
        double dest_lat = 25.05202; // the testing destination 
        double dest_long = 121.554761; 
        GeoPoint srcGeoPoint = new GeoPoint((int) (src_lat * 1E6), 
        (int) (src_long * 1E6)); 
        GeoPoint destGeoPoint = new GeoPoint((int) (dest_lat * 1E6), 
        (int) (dest_long * 1E6)); 

        DrawPath(srcGeoPoint, destGeoPoint, Color.GREEN, mapView); 

        mapView.getController().animateTo(srcGeoPoint); 
        mapView.getController().setZoom(15); 
        
    }
    
    private void DrawPath(GeoPoint src,GeoPoint dest, int color, MapView mMapView01) 
    { 
    	// connect to map web service 
    	StringBuilder urlString = new StringBuilder(); 
    	urlString.append("http://maps.google.com/maps?f=d&hl=en"); 
    	urlString.append("&saddr=");//from 
    	urlString.append( Double.toString((double)src.getLatitudeE6()/1.0E6 )); 
    	urlString.append(","); 
    	urlString.append( Double.toString((double)src.getLongitudeE6()/1.0E6 )); 
    	urlString.append("&daddr=");//to 
    	urlString.append( Double.toString((double)dest.getLatitudeE6()/1.0E6 )); 
    	urlString.append(","); 
    	urlString.append( Double.toString((double)dest.getLongitudeE6()/1.0E6 )); 
    	urlString.append("&ie=UTF8&0&om=0&output=kml"); 
    	Log.d("xxx","URL="+urlString.toString()); 
    	// get the kml (XML) doc. And parse it to get the coordinates(direction route). 
    	Document doc = null; 
    	HttpURLConnection urlConnection= null; 
    	URL url = null; 
    	try 
    	{ 
    		url = new URL(urlString.toString()); 
    		urlConnection=(HttpURLConnection)url.openConnection(); 
    		urlConnection.setRequestMethod("GET"); 
    		urlConnection.setDoOutput(true); 
    		urlConnection.setDoInput(true); 
    		urlConnection.connect(); 

    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
    		DocumentBuilder db = dbf.newDocumentBuilder(); 
    		doc = db.parse(urlConnection.getInputStream()); 

    		if(doc.getElementsByTagName("GeometryCollection").getLength()>0) 
    		{ 
    			//String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getNodeName(); 
    			String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getFirstChild().getNodeValue() ; 
    			Log.d("xxx","path="+ path); 
    			String [] pairs = path.split(" "); 
    			String[] lngLat = pairs[0].split(","); // lngLat[0]=longitude lngLat[1]=latitude lngLat[2]=height 
    			// src 
    			GeoPoint startGP = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6)); 
    			mMapView01.getOverlays().add(new MyOverLay(startGP,startGP,1)); 
    			GeoPoint gp1; 
    			GeoPoint gp2 = startGP; 
    			for(int i=1;i<pairs.length;i++) // the last one would be crash 
    			{ 
    				lngLat = pairs[i].split(","); 
    				gp1 = gp2; 
    				// watch out! For GeoPoint, first:latitude, second:longitude 
    				gp2 = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6)); 
    				mMapView01.getOverlays().add(new MyOverLay(gp1,gp2,2,color)); 
    				Log.d("xxx","pair:" + pairs[i]); 
    			} 
    			mMapView01.getOverlays().add(new MyOverLay(dest,dest, 3)); // use the default color 
    		} 
    	} 
    	catch (MalformedURLException e) 
    	{ 
    		e.printStackTrace(); 
    	} 
    	catch (IOException e) 
    	{ 
    		e.printStackTrace(); 
    	} 
    	catch (ParserConfigurationException e) 
    	{ 
    		e.printStackTrace(); 
    	} 
    	catch (SAXException e) 
    	{ 
    		e.printStackTrace(); 
    	} 
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}