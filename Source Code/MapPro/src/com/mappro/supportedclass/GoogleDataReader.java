package com.mappro.supportedclass;

import android.location.Address;
import android.location.Geocoder;

import com.mappro.model.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
 
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class GoogleDataReader {
	
	public GoogleDataReader()
	{
		
	}
	
	//Get place info
	public ArrayList<PlaceModel> PlacesInfoReader(String keyword, double lat, double lng, int num)
	{
		ArrayList<PlaceModel> lstPlaceMode = new ArrayList<PlaceModel>();
		
		//Build url
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.google.com/maps?q=");
		urlString.append(keyword); //search what?
		urlString.append("&sll=");  //How many?
		urlString.append(Double.toString(lat));
		urlString.append(",");
		urlString.append(Double.toString(lng));
		urlString.append("&num=");
		urlString.append(Integer.toString(num));
		urlString.append("&ie=UTF8&output=kml");
		
		// get the kml (XML) doc. And parse it. 
    	Document doc = null; 
    	HttpURLConnection urlConnection= null; 
    	URL url = null; 
    	try 
    	{ 
    		//Declare connection
    		url = new URL(urlString.toString()); 
    		urlConnection=(HttpURLConnection)url.openConnection(); 
    		urlConnection.setRequestMethod("GET"); 
    		urlConnection.setDoOutput(true); 
    		urlConnection.setDoInput(true); 
    		urlConnection.connect(); 

    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
    		DocumentBuilder db = dbf.newDocumentBuilder(); 
    		doc = db.parse(urlConnection.getInputStream()); 
    		
    		if(doc.getElementsByTagName("Placemark").getLength()>0) 
    		{ 
    			for(int i=0;i<doc.getElementsByTagName("Placemark").getLength();i++)
    			{
    				PlaceModel placeModel = new PlaceModel();
    				//Get ATM name
    				String name = doc.getElementsByTagName("Placemark")
						    		 .item(i).getChildNodes().item(0)
						    		 .getFirstChild().getNodeValue();;
    				//Get the address of ATM
    				String address = doc.getElementsByTagName("Placemark")
    								    .item(i).getChildNodes().item(2)
    								    .getFirstChild().getNodeValue();
    				placeModel.setName(name);
    				placeModel.setAddress(address);
    				
    				lstPlaceMode.add(placeModel);
    			}
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
		
		return lstPlaceMode;
	}
	
	//Get full address by given latitude & longitude
	public String GetAddressFromLatLng(double lat, double lng, Geocoder geocoder)
	{
		 String AddressLine = "";
		 try {
	        	
	  	      List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
	  	      if (addresses == null) {
	  	    	  return AddressLine;
	  	      }
	  	      
	  	      
	  	      //just get first item of list address
	  	      Address add = addresses.get(0);
	  	      
	  	      //get full address
	  	      for(int i=0;i< add.getMaxAddressLineIndex()-1;i++)
	  	      {
	  	    	  if(i==(add.getMaxAddressLineIndex()-2))
	  	    		  AddressLine +=  add.getAddressLine(i)+ ".";
	  	    	  else
	  	    		AddressLine +=  add.getAddressLine(i)+ ", ";
	  	      }
	  	    } catch (IOException e) {
	  	    	AddressLine = "Your address is not availablen now!"; 
	  	    }
		 
		return AddressLine;
	}
	
	//Get latitude & longitude from given address 
	public CPoint GetLatLngFromAddress(String locationName, Geocoder geocoder)
	{
		CPoint point = new CPoint();
		try {
	        	
	  	     List<Address> addresses = geocoder.getFromLocationName(locationName, 1);
	  	     if(addresses==null){
	  	    	 //return point;
	  	     }
	  	      
	  	     //just get first item of list address
	  	     Address add = addresses.get(0);
	  	   
	  	     point.lat = add.getLatitude();
	  	     point.lng = add.getLongitude();
	  	     
	  	     return point;
	  	      
	  	    } catch (IOException e) {
	  	    	
	  	    }
		return point;
	}
}
