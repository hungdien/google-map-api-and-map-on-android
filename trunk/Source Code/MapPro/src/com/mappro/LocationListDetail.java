package com.mappro;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.mappro.locationlistview.*;
import com.mappro.model.PlaceModel;
import com.mappro.supportedclass.GoogleDataReader;

public class LocationListDetail extends Activity {
	 	private ListView list;
	    private LazyAdapter adapter;
	    private ArrayList<PlaceModel> lstPlaceModel;
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.locationdetail);
	        
	        GoogleDataReader dataReader = new GoogleDataReader();
	        lstPlaceModel = dataReader.PlacesInfoReader("atm", 21.029505, 105.850566, 20);
	        
	        list=(ListView)findViewById(R.id.list);
	        adapter=new LazyAdapter(this, lstPlaceModel);
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
