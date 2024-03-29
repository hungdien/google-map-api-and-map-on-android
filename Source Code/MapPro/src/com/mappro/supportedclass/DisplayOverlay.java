package com.mappro.supportedclass;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class DisplayOverlay extends Overlay {
	
	private Paint paint;
	private double lat;
	private double lon;
	private double satAccuracy;
	private int numberSats;
	private float bearing;
	private double altitude;
	private float speed;
	private String currentProvider;
	public static boolean showData = true;
	
	@Override
	public void draw(Canvas canvas, MapView mapview, boolean shadow) {
		super.draw(canvas, mapview, shadow);
		if(showData){
			paint = new Paint();
			paint.setAntiAlias(true);
			paint.setARGB(80,255,255,255);
			canvas.drawRect(0,0,350,33,paint);
			paint.setTextSize(11);
			paint.setARGB(180, 0, 0, 0);
			canvas.drawText("Lat = "+lat+"  Long = "+lon+"  Alt = "+(int)altitude+" m", 8, 14, paint);
			canvas.drawText("Sat = "+numberSats+" Accur = "+(int)satAccuracy+" m"
					+" speed = "+(int)speed+" m/s  bearing = "+(int)bearing+" deg", 8, 27, paint);
		}
	}
	
	// Method to insert updated satellite data
	public void putSatStuff(double lat, double lon, double satAccuracy, float bearing, double altitude,
			float speed, String currentProvider, int numberSats){
		this.lat = lat;
		this.lon = lon;
		this.satAccuracy = satAccuracy;
		this.bearing = bearing;
		this.altitude = altitude;
		this.speed = speed;
		this.currentProvider = currentProvider;
		this.numberSats = numberSats;	
	}
}
