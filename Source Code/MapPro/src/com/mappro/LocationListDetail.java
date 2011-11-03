package com.mappro;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.mappro.locationlistview.*;
import com.mappro.model.PlaceModel;
import com.mappro.supportedclass.GoogleDataReader;

public class LocationListDetail extends Activity {
	 	private ListView list;
	    private LazyAdapter adapter;
	    private ArrayList<PlaceModel> lstPlaceModel;
	    
	    private double lat;
	    private double lng;
	    private String keywork;
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.locationdetail);
	        list=(ListView)findViewById(R.id.list);
	        
	        //Get data from InputAddress activity
		     Bundle extras = getIntent().getExtras();
		     if(extras !=null) {
		    	 //current location
		    	 lat = extras.getDouble("lat");
		    	 lng = extras.getDouble("lng");
		    	 keywork = extras.getString("keywork");
		    	 
		    	 GoogleDataReader dataReader = new GoogleDataReader();
				 lstPlaceModel = dataReader.PlacesInfoReader(keywork, lat, lng, 40);
		     }
	        
		    
	        
	        Toast.makeText(LocationListDetail.this, keywork, Toast.LENGTH_LONG).show();
	        adapter=new LazyAdapter(this, lstPlaceModel,keywork);
	        list.setAdapter(adapter);
	        
	        Button b=(Button)findViewById(R.id.button1);
	        b.setOnClickListener(listener);
	    }
	    
	    @Override
	    public void onDestroy()
	    {
	        adapter.imageLoader.stopThread();
	        list.setAdapter(null);
	        super.onDestroy();
	    }
	    
	    public OnClickListener listener=new OnClickListener(){
	        @Override
	        public void onClick(View arg0) {
	            adapter.imageLoader.clearCache();
	            adapter.notifyDataSetChanged();
	        }
	    };	    
	    
}
