package com.gedevanishvili.driving;

import com.gedevanishvili.driving.modules.MyResource;
import com.gedevanishvili.driving.modules.MyAlert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 *
 * @author alexx
 * Main activity class of the application
 */
public class GeoDrivingTest extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        try {
            //set content
            setContentView(R.layout.main);

            //set custom title
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_main);
            TextView tv = (TextView)getWindow().findViewById(R.id.header_main);
            //Changing font face of the title
            tv.setTypeface(MyResource.getGeoFont(this));
            //Set Custom title label
            tv.setText(R.string.main_title);
        }
        catch (Exception e){
            //alert exception
            MyAlert.alertWin(this, "" + e);
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
     * GoTo Signs Activity
     * @param view 
     */
    public void gotoSignsList(View view)
    {
        Intent intent = new Intent(this, GeoDrivingSignsList.class);
        startActivity(intent);
    }
    
    /**
     * GoTo Laws Activity
     * @param view 
     */
    public void gotoLaws(View view)
    {
        Intent intent = new Intent(this, GeoDrivingLaws.class);
        startActivity(intent);
    }
}
