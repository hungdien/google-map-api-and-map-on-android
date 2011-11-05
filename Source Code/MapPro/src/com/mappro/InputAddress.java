/**
 * 
 */
package com.mappro;


import java.util.Locale;

import com.mappro.model.CPoint;
import com.mappro.supportedclass.GoogleDataReader;

import android.app.Activity;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author TUAN-NGUYEN
 *
 */
public class InputAddress extends Activity {
	
     Geocoder geocoder;
     CheckBox checkbox_placenow;
     CheckBox checkbox_placechose;
     TextView edit_placenow;
     TextView edit_placechose;
     RadioButton radiob_map;
     RadioButton radiob_list;
     
     String address;
     String keywork;
     double lat;
     double lng;
     
	 public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.inputaddress);
	     
	     final Button button_view = (Button)findViewById(R.id.button_view);
	     checkbox_placenow = (CheckBox)findViewById(R.id.checkbox_placenow);
	     checkbox_placechose = (CheckBox)findViewById(R.id.checkbox_placechose);
	     edit_placenow = (TextView)findViewById(R.id.editText_placenow);
	     edit_placechose = (TextView)findViewById(R.id.editText_placechose);
	     radiob_map = (RadioButton)findViewById(R.id.radiob_map);
	     radiob_list = (RadioButton)findViewById(R.id.radiob_list);
	     
	     /*Demo*/
	     geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
	     //GoogleDataReader reader = new GoogleDataReader();
	     //CPoint point = new CPoint(); 
	    // point = reader.GetLatLngFromAddress("100 Lê Lai, quận 1", geocoder);
	     
	     //Toast.makeText(InputAddress.this, String.valueOf(point.lat), Toast.LENGTH_LONG).show();
	     /*End Demo*/
	     
	     //Get data from MapPlaces activity
	     Bundle extras = getIntent().getExtras();
	     if(extras !=null) {
	    	 address = extras.getString("address");
	    	 keywork = extras.getString("keyword");
	    	 //current location
	    	 lat = extras.getDouble("lat");
	    	 lng = extras.getDouble("lng");
	    	 Toast.makeText(this, keywork, Toast.LENGTH_LONG);
	    	 setPlaceNow();
	     }
	     
	     //
	     //Event(s)
	     //
	     
	     //Click event on checkbox checkbox_placenow (chon vi tri hien tai)
	     checkbox_placenow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 if (((CheckBox) v).isChecked()) 
				 {
					 checkbox_placechose.setChecked(false);
					 setPlaceNow();
			     }
				 else
			     {
					 checkbox_placechose.setChecked(true);
					 
			     }
			}
		});
	     
	     //Click event on check box checkbox_chose (vi tri cho san)
	     checkbox_placechose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 if (((CheckBox) v).isChecked()) 
				 {
					 checkbox_placenow.setChecked(false);
			     }
				 else
			     {
					 checkbox_placenow.setChecked(true);
					 setPlaceNow();
			     }
			}
		});
	     
	     //RadionButton map
	     radiob_list.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (((RadioButton) v).isChecked())
				{
					radiob_map.setChecked(false);
				}
			}
		});
	     
	     radiob_map.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (((RadioButton) v).isChecked())
					{
						radiob_list.setChecked(false);
					}
				}
			});
	     
	     //Click event on button_view
	     button_view.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
            	 
            	 Intent intent = new Intent();
            	 if(checkbox_placenow.isChecked())
            	 {
            		 if(radiob_list.isChecked()){
            			 intent.setClass(InputAddress.this, LocationListDetail.class);
                    	 
                		 intent.putExtra("lat",lat);
                		 intent.putExtra("lng",lng);
                		 intent.putExtra("keywork", keywork);
                		 
                		 startActivity(intent);
                		 return;
            		 }
            		 else{
            			 //Display places on map with current address 
            		 }
            	 }
            	 else
            	 {
            		 if(radiob_list.isChecked()){
            			 
            			 //
            			 //Convert address to point
     		    	     CPoint point = new CPoint();
     		    	     GoogleDataReader reader = new GoogleDataReader();
     		    	     point = reader.GetLatLngFromAddress(edit_placechose.getText().toString(),
     		    	    		 							 geocoder);
     		    	     
     		    	     //send data
     		    	    intent.setClass(InputAddress.this, LocationListDetail.class);
     		    	    intent.putExtra("lat",point.lat);
     		    	    intent.putExtra("lng",point.lng);
     		    	    intent.putExtra("keywork", keywork);
     		    	    
     		    	    Toast.makeText(InputAddress.this,edit_placechose.getText()+";"+String.valueOf(point.lat)+";"+String.valueOf(point.lng), Toast.LENGTH_LONG).show();
     		    	    
     		    	    startActivity(intent);
     		    	    return;
            		 }
            		 else
            		 {
            			 //Display places on map with entered address
            		 }
            	 }
             }
         });
	 }
	 
	 public void setPlaceNow()
	 {
		 if(checkbox_placenow.isChecked())
			 edit_placenow.setText(address);
	 }
	 
	 
}
