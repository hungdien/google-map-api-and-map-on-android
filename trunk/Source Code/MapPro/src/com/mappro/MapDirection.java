/**
 * 
 */
package com.mappro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author TUAN-NGUYEN
 *
 */
public class MapDirection extends Activity {
	 /** Called when the activity is first created. */
	private EditText placeA;
	private EditText placeB;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maindirection);
        
        //Initialize controls
	    final Button btnViewDirection = (Button)findViewById(R.id.btnViewDirection);
	    placeA = (EditText)findViewById(R.id.editTextPlaceA);
	    placeB = (EditText)findViewById(R.id.editTextPlaceB);
	    
	    btnViewDirection.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String src = placeA.getText().toString();
				String dest = placeB.getText().toString();
				
				Intent intent = new Intent();
				intent.setClass(MapDirection.this, DrivingDirectionListDetail.class);
				
				intent.putExtra("src",src);
				intent.putExtra("dest",dest);
				
				
				startActivity(intent);
			}
		});
    }
}
