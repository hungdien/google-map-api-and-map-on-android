/**
 * 
 */
package com.mappro;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author TUAN-NGUYEN
 *
 */
public class InputAddress extends Activity {
	 public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.inputaddress);
	     
	     final Button button_view = (Button)findViewById(R.id.button_view);
	     button_view.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
            	 Intent intent = new Intent();
            	 intent.setClass(InputAddress.this, LocationListDetail.class);
            	 startActivity(intent);
             }
         });
	 }
}
