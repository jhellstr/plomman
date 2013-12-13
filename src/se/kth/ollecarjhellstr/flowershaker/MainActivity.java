package se.kth.ollecarjhellstr.flowershaker;

import java.util.Timer;

import se.kth.ollecarjhellstr.floweshaker.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint({ "InlinedApi", "NewApi" })
public class MainActivity extends Activity {
	
	TextView sensor1text;
	TextView sensor2text;
	TextView sensor3text;
	
	private final String yaw = "YAW: ";
	private final String pitch = "PITCH: ";
	private final String roll = "ROLL: ";

	TextView xText;
	TextView yText;
	TextView zText;
	ImageView blomman;
	ImageView loven;
	
	Timer timer;
	
	Long timerFloat = 0l;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		xText = (TextView) findViewById(R.id.sensor1TextView);
		yText = (TextView) findViewById(R.id.sensor2TextView);
		zText = (TextView) findViewById(R.id.sensor3TextView);
		
		loven = (ImageView) findViewById(R.id.lofenView);
		
		blomman = (ImageView) findViewById(R.id.plommanView);
		
		SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		
		
		Sensor s = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
		
		SensorEventListener sel = new SensorEventListener() {
			@Override
			public void onSensorChanged(SensorEvent event) {
				if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
					float[] matrix = new float[16];
					float[] remapMatrix = new float[16];
					float[] orientation = new float[3];
					SensorManager.getRotationMatrixFromVector(matrix, event.values);
					SensorManager.remapCoordinateSystem(matrix,SensorManager.AXIS_X, SensorManager.AXIS_Z, remapMatrix);
					orientation = SensorManager.getOrientation(remapMatrix, orientation);
					xText.setText("Pitch: "+Math.toDegrees(orientation[1]));
					yText.setText("Roll: "+Math.toDegrees(orientation[2]));
					zText.setText("Azimuth: "+Math.toDegrees(orientation[0]));
					blomman.setPivotX(blomman.getWidth()/2);
					blomman.setPivotY(blomman.getHeight());
					blomman.setRotation((float) Math.toDegrees(orientation[2]));
					loven.setPivotX(blomman.getWidth()/2);
					loven.setPivotY(blomman.getHeight());
					loven.setRotation((float) Math.toDegrees(orientation[2]));
					}
				else if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
					double acc = ((Math.pow(event.values[0],2) + Math.pow(event.values[1],2) + Math.pow(event.values[2],2))/(Math.pow(SensorManager.GRAVITY_EARTH,2)));
					if(acc > 2.0){
						if((System.currentTimeMillis() - timerFloat) > 1000l){
							loven.animate().setDuration(1000).y(loven.getHeight());
							timerFloat = System.currentTimeMillis();
						}
					}
					else{
						timerFloat = System.currentTimeMillis();
					}
				}
			}
			
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {

			}
		};
		sm.registerListener(sel, s, SensorManager.SENSOR_DELAY_UI);
		sm.registerListener(sel, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
