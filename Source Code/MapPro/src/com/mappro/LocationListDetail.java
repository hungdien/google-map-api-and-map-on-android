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
	    
	  /*  private String[] mStrings={
	            "http://a3.twimg.com/profile_images/956404323/androinica-avatar_normal.png",
	            "http://a1.twimg.com/profile_images/909231146/Android_Biz_Man_normal.png",
	            "http://a3.twimg.com/profile_images/72774055/AndroidHomme-LOGO_normal.jpg",
	            "http://a1.twimg.com/profile_images/349012784/android_logo_small_normal.jpg",
	            "http://a1.twimg.com/profile_images/841338368/ea-twitter-icon.png",
	            "http://a3.twimg.com/profile_images/64827025/android-wallpaper6_2560x160_normal.png",
	            "http://a3.twimg.com/profile_images/77641093/AndroidPlanet_normal.png"
	    };*/
}
