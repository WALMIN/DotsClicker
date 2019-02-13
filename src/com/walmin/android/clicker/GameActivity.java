package com.walmin.android.clicker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {

	// Values
	public long dot;
		public String dotKeyString = "dots";
		
	public long dpc;
		public String dpcKeyString = "dpc";
		
	public long dps;
		public String dpsKeyString = "dps";
		
	public long shopDpc;
		public String shopDpcKeyString = "shopDpc";
		
	public long shopDps;
		public String shopDpsKeyString = "shopDps";
	
	// Views
	public TextView dotsView;
	public TextView dpsAndDpcView;
	public Button dotBtn;
	
	public Button shopDpcBtn;
	public Button shopDpsBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		loadPref();
		initialize();
		
		Thread thread = new Thread(){
			public void run(){
				try{
					while(!isInterrupted()){
						Thread.sleep(1000);
						
						runOnUiThread(new Runnable(){
							public void run() {
								dot += dps;
								dotsView.setText(dot + " Dots");
								
								savePref(dotKeyString, dot);
								
							}
							
						});
						
					}
					
				}catch(InterruptedException ie){
					ie.printStackTrace();
					
				}
				
			}
			
		};
		thread.start();
		
	}
	
	public void loadPref(){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		
		long dotKey = sharedPref.getLong(dotKeyString, 0);
			dot = dotKey;
			
		long dpcKey = sharedPref.getLong(dpcKeyString, 1);
			dpc = dpcKey;
			
		long dpsKey = sharedPref.getLong(dpsKeyString, 0);
			dps = dpsKey;
			
		long shopDpsKey = sharedPref.getLong(shopDpsKeyString, 10);
			shopDps = shopDpsKey;
			
		long shopDpcKey = sharedPref.getLong(shopDpcKeyString, 1000);
			shopDpc = shopDpcKey;
		
	}
	
	public void savePref(String key, long value){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		
		Editor editor = sharedPref.edit();
		editor.putLong(key, value);
		editor.commit();
		
	}
	
	public void initialize(){
		// Views
		dotsView = (TextView) findViewById(R.id.dotsView);
			dotsView.setText(dot + " Dots");
			
		dpsAndDpcView = (TextView) findViewById(R.id.dpsAndDpcView);
			dpsAndDpcView.setText(dps + " Dps | " + dpc + " Dpc");
		
		dotBtn = (Button) findViewById(R.id.dotBtn);	
		
		shopDpcBtn = (Button) findViewById(R.id.shopDpc);	
			shopDpcBtn.setText("Dpc: x2 | Price: " + shopDpc);
		
		shopDpsBtn = (Button) findViewById(R.id.shopDps);	
			shopDpsBtn.setText("Dps: +1 | Price: " + shopDps);
		
	}
	
	public void dotBtn(View v){
		dot += dpc;
		dotsView.setText(dot + " Dots");
		
		savePref(dotKeyString, dot);
		
	}
	
	public void shopDpc(View v){
		if(dot >= shopDpc){
			dot -= shopDpc;
			dpc *= 2;
			
			shopDpc *= 1.5; 
			
			shopDpcBtn.setText("Dpc: x2 | Price: " + shopDpc);
			dpsAndDpcView.setText(dps + " Dps | " + dpc + " Dpc");
			dotsView.setText(dot + " Dots");
			
			savePref(shopDpcKeyString, shopDpc);
			savePref(dpcKeyString, dpc);
			savePref(dotKeyString, dot);
			
		}else{
			Toast.makeText(this, "You need more dots!", Toast.LENGTH_SHORT).show();
			
		}
		
	}
	
	public void shopDps(View v){
		if(dot >= shopDps){
			dot -= shopDps;
			dps += 1;
			
			shopDps *= 1.5; 
			
			shopDpsBtn.setText("Dps: +1 | Price: " + shopDps);
			dpsAndDpcView.setText(dps + " Dps | " + dpc + " Dpc");
			dotsView.setText(dot + " Dots");
			
			savePref(shopDpsKeyString, shopDps);
			savePref(dpsKeyString, dps);
			savePref(dotKeyString, dot);
			
		}else{
			Toast.makeText(this, "You need more dots!", Toast.LENGTH_SHORT).show();
			
		}
		
	}
	
}