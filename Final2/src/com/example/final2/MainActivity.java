package com.example.final2;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	 private static final String TAG = "ConsumerIrTest";
	    TextView mFreqsText, Bulbul;
	    ConsumerIrManager mCIR;
	    public int[] preamble= {2930, 2930};
//	    public int[] data = {1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953};
	    public int[] data = {1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953, 1953};
	    public int[] endBurst={3905,3905};
	    public int[] final_array = new int[preamble.length + data.length];
//	    public int[] super_final = new int[final_array.length*3 + endBurst.length];
	    public int Hz = 38000;
	    
	    /**
	     * Initialization of the Activity after it is first created.  Must at least
	     * call {@link android.app.Activity#setContentView setContentView()} to
	     * describe what is to be displayed in the screen.
	     */
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        // Be sure to call the super class.
	        super.onCreate(savedInstanceState);
	        
	        for(int i = 0; i < final_array.length; i++)
	        {
	        	if(i < preamble.length)
	        		final_array[i] = preamble[i];
	        	else 
	        		final_array[i] = data[i-preamble.length];      	
	        }
	        
	        
	        // Get a reference to the ConsumerIrManager
	        mCIR = (ConsumerIrManager)getSystemService(Context.CONSUMER_IR_SERVICE);
	        // See assets/res/any/layout/consumer_ir.xml for this
	        // view layout definition, which is being set here as
	        // the content of our screen.
	        setContentView(R.layout.activity_main);
	        // Set the OnClickListener for the button so we see when it's pressed.
	        findViewById(R.id.send_button).setOnClickListener(mSendClickListener);
	        findViewById(R.id.get_freqs_button).setOnClickListener(mGetFreqsClickListener);
	        mFreqsText = (TextView) findViewById(R.id.freqs_text);
	        Bulbul = (TextView) findViewById(R.id.bu);
	       
	    }
	    View.OnClickListener mSendClickListener = new View.OnClickListener() {
	        public void onClick(View v) {
	            if (!mCIR.hasIrEmitter()) {
	                Log.e(TAG, "No IR Emitter found\n");
	                return;
	            }
	            // A pattern of alternating series of carrier on and off periods measured in
	            // microseconds.
	            //int[] pattern = {38028,169,168,21,63,21,63,21,63,21,21,21,21,21,21,21,21,21,21,21,63,21,63,21,63,21,21,21,21,21,21,21,21,21,21,21,21,21,63,21,21,21,21,21,21,21,21,21,21,21,21,21,64,21,21,21,63,21,63,21,63,21,63,21,63,21,63,21,1794,169,168,21,21,21,3694};
	            // transmit the pattern at 38.4KHz
	         /*   for(int i=34000; i<=60000; i++){
	            
	            	Bulbul.setText(Integer.toString(i));
	            	Log.e(TAG,Integer.toString(i));
	            	mCIR.transmit(i, pattern);} */
	            mCIR.transmit(Hz, final_array);
	            mCIR.transmit(Hz, final_array);
	            mCIR.transmit(Hz, final_array);
	            mCIR.transmit(Hz, endBurst);
	        }
	    };
	    View.OnClickListener mGetFreqsClickListener = new View.OnClickListener() {
	        public void onClick(View v) {
	            StringBuilder b = new StringBuilder();
	            if (!mCIR.hasIrEmitter()) {
	                mFreqsText.setText("No IR Emitter found!");
	                Log.e(TAG, "No IR Emitter found!\n");
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
	}
