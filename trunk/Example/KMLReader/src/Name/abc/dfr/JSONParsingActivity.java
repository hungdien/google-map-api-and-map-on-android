package Name.abc.dfr;

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

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

public class JSONParsingActivity extends ListActivity {
    /** Called when the activity is first created. */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.main);
        
        this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.fetchTwitterPublicTimeline()));
        
    }
    
    public ArrayList<String> fetchTwitterPublicTimeline()
    {
        ArrayList<String> listItems = new ArrayList<String>();
 
     // connect to map web service 
    	StringBuilder urlString = new StringBuilder(); 
    	urlString.append("http://maps.google.com/maps?q=atm&sll=21.029505,105.850566&num=5&ie=UTF8&0&om=0&output=kml");  
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
    		
    		if(doc.getElementsByTagName("Placemark").getLength()>0) 
    		{ 
    			for(int i=0;i<doc.getElementsByTagName("Placemark").getLength();i++)
    			{
    				String coor = doc.getElementsByTagName("Placemark").item(i).getChildNodes().item(6).getFirstChild().getFirstChild().getNodeValue();
    				String[] lngLat = coor.split(","); 
    				listItems.add(doc.getElementsByTagName("Placemark").item(i).getChildNodes().item(2).getFirstChild().getNodeValue()+ "-(" + lngLat[0]+"-"+lngLat[1]+")");
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
    	
    	return listItems;
    }
    
}