package com.example.final2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	    TextView mFreqsText, Bulbul;
	    ConsumerIrManager mCIR;
	   
	    
	   	@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	     
	        // Get a reference to the ConsumerIrManager
	        this.mCIR = ((ConsumerIrManager)getSystemService("consumer_ir"));
	        setContentView(R.layout.activity_main);
	        
	        // Set the OnClickListener for the button so we see when it's pressed.
	        findViewById(R.id.send_button).setOnClickListener(mSendClickListener);
	        findViewById(R.id.get_freqs_button).setOnClickListener(mGetFreqsClickListener);
	        mFreqsText = (TextView) findViewById(R.id.freqs_text);
	        Bulbul = (TextView) findViewById(R.id.bu);
	       
	    }
	    View.OnClickListener mSendClickListener = new View.OnClickListener() {
	        public void onClick(View v) {
	        	
	            if (!mCIR.hasIrEmitter())
	                return;
	     
	            ElectraOnOff();
	        }
	    };
	    
	    View.OnClickListener mGetFreqsClickListener = new View.OnClickListener() {
	        public void onClick(View v) {
	        ElectraHeat();
	          StringBuilder b = new StringBuilder();
	            if (!mCIR.hasIrEmitter()) {
	                mFreqsText.setText("No IR Emitter found!");
	                //Log.e(TAG, "No IR Emitter found!\n");
	                return;
	            }
	            // Get the available carrier frequency ranges
	            ConsumerIrManager.CarrierFrequencyRange[] freqs = mCIR.getCarrierFrequencies();
	            b.append("IR Carrier Frequencies:\n");
	            for (ConsumerIrManager.CarrierFrequencyRange range : freqs) {
	                b.append(String.format("    %d - %d\n", range.getMinFrequency(),
	                            range.getMaxFrequency()));
	            }
	            mFreqsText.setText(b.toString());
	        }
	    };
	    
	    public void ElectraOnOff()
	    {
	      int[] electra = {116,141,76,31,41,69,41,31,41,31,76,31,41,69,76,31,41,69,76,69,76,32,41,31,41,31,41,31,41,31,41,31,41,31,41,31,41,31,41,31,41,31,41,31,41,31,41,31,41,31,41,31,41,30,41,69,76,31,115,141,76,30,41,69,41,30,41,31,76,30,41,68,76,31,41,68,76,68,76,30,41,31,41,30,41,31,41,30,41,30,41,31,41,30,41,31,41,30,41,31,41,30,41,30,41,31,41,30,41,31,41,30,41,68,76,32,114,142,76,30,41,68,41,31,41,30,76,30,41,69,76,30,41,68,76,68,76,31,41,30,41,31,41,30,41,30,42,30,41,31,41,30,41,30,41,31,41,30,41,31,41,30,41,31,40,31,41,31,41,31,41,69,76,31,150,2500 };
	      SendIR(electra);
	      
	    }
	    
	    public void ElectraHeat() 
	    {
		   int[] heat = {116,141,27,54,26,15,26,54,26,54,27,14,26,15,25,54,27,15,25,15,26,54,26,15,26,15,25,54,27,53,27,15,26,54,26,54,27,15,25,54,27,54,26,54,27,54,26,54,27,54,27,14,26,54,26,15,26,15,25,15,25,15,26,15,25,15,26,15,25,54,27,13,26,15,25,15,26,15,25,15,26,15,25,54,26,15,26,54,26,55,26,54,27,53,27,54,27,54,26,207,174,160,27,54,26,15,26,54,26,54,27,15,25,15,25,55,26,15,26,15,25,54,27,14,26,15,25,54,27,54,26,15,26,54,26,55,26,15,25,54,27,54,26,54,27,54,26,55,26,54,27,15,25,54,27,15,25,15,25,15,26,14,26,15,26,14,26,15,25,54,27,15,25,15,25,15,26,15,25,15,26,15,25,54,27,15,25,54,27,54,26,54,27,54,26,54,27,54,150,2500};
		   SendIR(heat);  
	    }
	    
	    
	    public void SendIR(int[] paramString)
	    {
	    	mCIR.transmit(38000, paramString);
	    }
}

	        
	        
	        
	  
