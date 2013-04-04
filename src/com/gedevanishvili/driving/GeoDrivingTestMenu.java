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
import android.text.SpannableString;
import android.text.style.UnderlineSpan;

import com.gedevanishvili.driving.modules.CustomTitleBar;
import com.gedevanishvili.driving.modules.MyAlert;
import com.gedevanishvili.driving.modules.MyResource;

/**
 *
 * @author alexx
 */
public class GeoDrivingTestMenu extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        try {
            //set view
            setContentView(R.layout.test_main);
            
            //Set custom title bar
            CustomTitleBar.setCustomTitleBar(this, getString(R.string.button_test));
            
            //Change font
            changeFont(R.id.type_1_but, true);
            changeFont(R.id.type_1_desc, false);
            changeFont(R.id.type_2_but, true);
            changeFont(R.id.type_2_desc, false);
        }
        catch (Exception e){
            //alert exception
            MyAlert.alertWin(this, "" + e);
        }
    }
    
    //Change Font To Text View
    public void changeFont(int res, boolean underlined){
        TextView textView = (TextView)findViewById(res);
        textView.setTypeface(MyResource.getGeoFont(this));
        
        //Underline Text In a Text View
        if (underlined){
            String textCont = (String)textView.getText();
            SpannableString spanString = new SpannableString(textCont);
            spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
            textView.setText(spanString);
        }
    }
    
    
    /**
     * GoTo Test Activity
     * @param view 
     */
    public void gotoExam(View view)
    {
        Intent intent = new Intent(this, GeoDrivingExam.class);
        startActivity(intent);
    }
    
    /**
     * GoTo Test Categories Activity
     * @param view 
     */
    public void gotoTestCats(View view)
    {
        Intent intent = new Intent(this, GeoDrivingTestCats.class);
        startActivity(intent);
    }
    
    /**
     * Generates menu in the right part of the title bar
     * @param view 
     */
    public void showMenu(View view){
        CustomTitleBar.showMenu(this, view);
    }
}
