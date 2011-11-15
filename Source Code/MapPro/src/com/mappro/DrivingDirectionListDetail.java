package com.mappro;

import java.util.ArrayList;

import com.mappro.drivingdirectionlist.DrivingAdapter;
import com.mappro.model.DrivingDirectionModel;
import com.mappro.supportedclass.GoogleDataReader;

import android.app.Activity;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DrivingDirectionListDetail extends Activity{
	private ListView list;
    private DrivingAdapter adapter;
    private ArrayList<DrivingDirectionModel> lstDrivingModel;
    private DrivingDirectionModel drivingDirectionModel;
    
    private String src;
    private String dest;
    
    private TextView txtRoute;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drivingdetail);
        
        list=(ListView)findViewById(R.id.drivinglist);
        txtRoute = (TextView)findViewById(R.id.txtRoute);
        
        //Get data from InputAddress activity
	     Bundle extras = getIntent().getExtras();
	     
	     if(extras !=null) {
	    	 
	    	 GoogleDataReader dataReader = new GoogleDataReader();
	         //current location
		     src = extras.getString("src");
		     dest = extras.getString("dest");
		    	 
		     //get info from google map by given key work
		     lstDrivingModel = dataReader.DrivingDirectionReader(src, dest);
		     
		     if(lstDrivingModel.size()>2)
		     {
		    	 drivingDirectionModel = lstDrivingModel.get(lstDrivingModel.size()-1);
		    	 txtRoute.setText(drivingDirectionModel.getDistance().replace("<br/>", " "));
		    	 
		    	 //
		    	 lstDrivingModel.remove(lstDrivingModel.size()-1);
		    	 lstDrivingModel.remove(lstDrivingModel.size()-2);
		     }
	     }
        
	     adapter=new DrivingAdapter(this, lstDrivingModel);
	     list.setAdapter(adapter);
	     
        Button b=(Button)findViewById(R.id.btnClearCache);
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
