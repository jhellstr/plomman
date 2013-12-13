package se.kth.ollecarjhellstr.flowershaker;

import se.kth.ollecarjhellstr.floweshaker.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
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
					float[] orientation = new float[3];
					SensorManager.getRotationMatrixFromVector(matrix, event.values);
					SensorManager.remapCoordinateSystem(matrix,SensorManager.AXIS_X, SensorManager.AXIS_Z, matrix);
					orientation = SensorManager.getOrientation(matrix, orientation);
					xText.setText("X: "+Math.toDegrees(orientation[1]));
					yText.setText("Y: "+Math.toDegrees(orientation[2]+Math.PI));
					zText.setText("Z: "+Math.toDegrees(orientation[0]));
					blomman.setPivotX(blomman.getWidth()/2);
					blomman.setPivotY(blomman.getHeight());
					blomman.setRotation((float) Math.toDegrees(orientation[2]));
					loven.setPivotX(blomman.getWidth()/2);
					loven.setPivotY(blomman.getHeight());
					loven.setRotation((float) Math.toDegrees(orientation[2]));
				
				}
			}
			
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {

			}
		};
		sm.registerListener(sel, s, SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
