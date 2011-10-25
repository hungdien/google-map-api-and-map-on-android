package com.mappro.supportedclass;

import com.mappro.model.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
 
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class GoogleDataReader {
	
	public GoogleDataReader()
	{
		
	}
	
	public ArrayList<PlaceModel>  PlacesInfoReader(String keyword, double lat, double lng,
												   int num)
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
}
