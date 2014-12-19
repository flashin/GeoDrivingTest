/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.content.res.Configuration;

import com.gedevanishvili.driving.modules.MyAlert;
import com.gedevanishvili.driving.modules.ExamTimer;
import com.gedevanishvili.driving.modules.ExamManager;
import com.gedevanishvili.driving.modules.CustomTitleBar;

/**
 *
 * @author alexx
 * This is exam activity class with timer and changing questions
 */
public class GeoDrivingExam extends Activity {
    
    public ExamManager ExamManager;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        this.activateExamBoard(getIntent());
    }
    
    /**
     * When starting new intent
     */
    @Override
    public void onNewIntent(Intent intent){
    	
    	this.activateExamBoard(intent);
    }
    
    /**
     * Calling activate method to adjust question image to the screen dimensions
     * @param newConfig 
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        ExamManager.activateTicket();
    }
    
    /**
     * Activate exam board (on first launch and onNewIntent event)
     */
    public void activateExamBoard(Intent intent){

        setContentView(R.layout.exam);
        
        try {
            //Timer
            ExamTimer ExamTimer = new ExamTimer();
            ExamTimer.showTimeElapsed((TextView)findViewById(R.id.test_timer));
            
            //get exam type and category id if type is 2
            int examType = intent.getIntExtra("EXAM_TYPE", 1);
            int examCatId = intent.getIntExtra("TICKET_CAT_ID", 0);

            //Exam part
            ExamManager = new ExamManager(this, examType, examCatId);
        
            //Set custom title bar
            CustomTitleBar.setCustomTitleBar(this, getString(R.string.exam_name));
        }
        catch (Exception e){
            //alert exception
            MyAlert.alertWin(this, "" + e);
        }
    }
    
    /**
     * Generates menu in the right part of the title bar
     * @param view 
     */
    public void showMenu(View view){
        CustomTitleBar.showMenu(this, view);
    }
}
