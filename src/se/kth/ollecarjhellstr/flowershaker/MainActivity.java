package se.kth.ollecarjhellstr.flowershaker;

import se.kth.ollecarjhellstr.floweshaker.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

@SuppressLint({ "InlinedApi", "NewApi" })
public class MainActivity extends Activity {
	
	TextView sensor1text;
	TextView sensor2text;
	TextView sensor3text;
	
	private final String yaw = "YAW: ";
	private final String pitch = "PITCH: ";
	private final String roll = "ROLL: ";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sensor1text = (TextView) findViewById(R.id.sensor1TextView);
		sensor2text = (TextView) findViewById(R.id.sensor2TextView);
		sensor3text = (TextView) findViewById(R.id.sensor3TextView);
		
		SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		Sensor s = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
		
		SensorEventListener sel = new SensorEventListener() {
			@Override
			public void onSensorChanged(SensorEvent event) {
				float[] rotMa = new float[16];
				float[] rotMa2 = new float[16];
				float[] oriMa = new float[3];
				
				SensorManager.getRotationMatrixFromVector(rotMa, event.values);		
				SensorManager.remapCoordinateSystem(rotMa,SensorManager.AXIS_X, SensorManager.AXIS_Z, rotMa2);
				SensorManager.getOrientation(rotMa2, oriMa);
				
				sensor1text.setText(yaw + Math.toDegrees(oriMa[0]));
				sensor2text.setText(pitch + Math.toDegrees(oriMa[1]));
				sensor3text.setText(roll + Math.toDegrees(oriMa[2]));					
			}
			
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				
			}
		};
		sm.registerListener(sel, s, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
