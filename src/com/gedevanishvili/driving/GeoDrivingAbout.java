/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.net.Uri;
import com.gedevanishvili.driving.modules.CustomTitleBar;
import com.gedevanishvili.driving.modules.MyAlert;
import com.gedevanishvili.driving.modules.MyResource;

/**
 *
 * @author alexx
 */
public class GeoDrivingAbout extends Activity {

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
            setContentView(R.layout.about);
            
            //Set custom title bar
            CustomTitleBar.setCustomTitleBar(this, getString(R.string.about_title));
            
            //Change font
            changeFont(R.id.about_main_block);
            changeFont(R.id.about_author_block);
            underlineText(R.id.about_email_block);
        }
        catch (Exception e){
            //alert exception
            MyAlert.alertWin(this, "" + e);
        }
    }
    
    //Change Font To Text View
    public void changeFont(int res){
        TextView textView = (TextView)findViewById(res);
        textView.setTypeface(MyResource.getGeoFont(this));
    }
    
    //Underline text
    public void underlineText(int res){
        TextView textView = (TextView)findViewById(res);
    
        String textCont = (String)textView.getText();
        SpannableString spanString = new SpannableString(textCont);
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        textView.setText(spanString);
    }
    
    /**
     * Send Email To Author
     * @param view 
     */
    public void sendEmailToAuthor(View view)
    {
        try {
            String recepientEmail = this.getString(R.string.about_email);
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + recepientEmail));
            startActivity(intent);
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
