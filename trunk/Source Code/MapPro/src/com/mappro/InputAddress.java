/**
 * 
 */
package com.mappro;

import java.util.Locale;

import com.mappro.model.CPoint;
import com.mappro.supportedclass.*;

import android.app.Activity;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

/**
 * @author TUAN-NGUYEN
 *
 */
public class InputAddress extends Activity {
	
     String keyword;
     Geocoder geocoder;
     CheckBox checkbox_placenow;
     CheckBox checkbox_placechose;
     
	 public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.inputaddress);
	     
	     final Button button_view = (Button)findViewById(R.id.button_view);
	     checkbox_placenow = (CheckBox)findViewById(R.id.checkbox_placenow);
	     checkbox_placechose = (CheckBox)findViewById(R.id.checkbox_placechose);
	     
	     /*Demo*/
	     //geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
	     //GoogleDataReader reader = new GoogleDataReader();
	     //CPoint point = new CPoint(); 
	     //point = reader.GetLatLngFromAddress("2 hàm nghi, quận 1", geocoder);
	     
	    // Toast.makeText(InputAddress.this, String.valueOf(point.lat), Toast.LENGTH_LONG).show();
	     /*End Demo*/
	     
	     //Get data from MapPlaces activity
	     Bundle extras = getIntent().getExtras();
	     if(extras !=null) {
	    	 keyword = extras.getString("address");
	    	 Toast.makeText(InputAddress.this, keyword, Toast.LENGTH_LONG).show();
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
			     }
			}
		});
	     
	     //Click event on button_view
	     button_view.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
            	 Intent intent = new Intent();
            	 intent.setClass(InputAddress.this, LocationListDetail.class);
            	 startActivity(intent);
             }
         });
	 }
}
