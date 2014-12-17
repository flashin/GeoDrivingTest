/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving.modules;

import android.os.Handler;
import android.widget.TextView;

/**
 *
 * @author alexx
 * Class to calculate time since the beginning of the test
 */
public class ExamTimer {
    private long startTime;
    private TextView textView;
    
    /**
     * Sets startTime to the current time
     */
    public ExamTimer(){            
        startTime = System.currentTimeMillis();
    }
    
    /**
     * Shows how much time elapsed since the user has begun hits test
     */
    public void showTimeElapsed(TextView t){
        textView = t;
        Handler handler = new Handler();
        
        //Runnable object to call its method once in a secon
        Runnable updateTimeElapsed = new Runnable(){
            @Override
			public void run(){
                //how much minutes, seconds passed
                long diff = System.currentTimeMillis() - startTime;
        
                diff = Math.round(Math.floor(diff / 1000));
                long seconds = diff % 60;
                diff = Math.round(Math.floor(diff / 60));
                long minutes = diff % 60;
                long hours = Math.round(Math.floor(diff / 60));

                String time_elapsed = toDateString(hours) + ":" + toDateString(minutes) + ":" + toDateString(seconds);

                //set timer information in the text view
                textView.setText(time_elapsed);
                
                //call this method again in a second
                Handler handler = new Handler();
                handler.postDelayed(this, 1000);
            }
        };
        
        handler.postDelayed(updateTimeElapsed, 1000);
    }
    
    /**
     * convert number to date String, with 0 in front
     */
    private String toDateString(long i){
        String res = "" + i;
        if (i < 10){
            res = "0" + res;
        }
        return res;
    }
}
