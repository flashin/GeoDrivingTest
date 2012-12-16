/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving.modules;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ImageView;

import com.gedevanishvili.driving.R;

/**
 *
 * @author alexx
 * This class creates and draws pop up window with a description
 */
public class DescWindow {
    private String desc;
    private String title;
    private Context context;
    
    /**
     * Initialize parameters 
     */
    public DescWindow(Context context, String title, String desc){
        this.context = context;
        this.title = title;
        this.desc = desc;
    }
    
    /**
     * draws pop up window with title, description and CLOSE button
     */
    public void drawPopup(){
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            //Customize title layout of the window
            TextView titleView = new TextView(context);
            titleView.setText(title);
            titleView.setPadding(10, 7, 10, 7);
            titleView.setTextSize(15);
            titleView.setTextColor(Color.rgb(255, 255, 255));
            titleView.setTypeface(MyResource.getGeoFont(context));
            alertDialogBuilder.setCustomTitle(titleView);
            
            //Add close button to the dialog
            alertDialogBuilder.setPositiveButton(R.string.but_close_alert_dia, new DialogInterface.OnClickListener(){  
                    public void onClick(DialogInterface dialog, int id) {  
                        dialog.dismiss(); 
                    }  
                }
            );
            
            //add text to dialog
            alertDialogBuilder.setMessage(desc);

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            
            //Customize text layout of the dialog
            TextView textview = (TextView)alertDialog.findViewById(android.R.id.message);
            textview.setTextSize(13);
            textview.setTypeface(MyResource.getGeoFont(context));

            //Customize button style
            TextView buttonview = (TextView)alertDialog.findViewById(android.R.id.button1);
            buttonview.setTypeface(MyResource.getGeoFont(context));
        }
        catch (Exception e){
            //Alert exception text
            MyAlert.alertWin(context, "" + e);
        }
    }
    
    /**
     * draws pop up window with title, image, description and CLOSE button
     */
    public void drawPopupWithImage(String im){
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            //Customize title layout of the window
            TextView titleView = new TextView(context);
            titleView.setText(title);
            titleView.setPadding(10, 7, 10, 7);
            titleView.setTextSize(15);
            titleView.setTextColor(Color.rgb(255, 255, 255));
            titleView.setTypeface(MyResource.getGeoFont(context));
            alertDialogBuilder.setCustomTitle(titleView);
            
            //Add close button to the dialog
            alertDialogBuilder.setPositiveButton(R.string.but_close_alert_dia, new DialogInterface.OnClickListener(){  
                    public void onClick(DialogInterface dialog, int id) {  
                        dialog.dismiss(); 
                    }  
                }
            );

            AlertDialog alertDialog = alertDialogBuilder.create();
            
            //set layout parameters for the text in dialog
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            lp1.gravity = Gravity.CENTER_HORIZONTAL;
            lp1.setMargins(10, 4, 10, 4);
            
            //Create text view for the dialog's text
            TextView textView = new TextView(context);
            textView.setTextSize(13);
            textView.setTypeface(MyResource.getGeoFont(context));
            textView.setText(desc);
            textView.setLayoutParams(lp1);
            
            //Create image view for dialog image
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(getRawImage(context, im));
            imageView.setLayoutParams(lp1);
            
            //add text and image to linear layout
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(linearLayout.VERTICAL);
            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            
            //Create scroll view and put layout with text and image in it
            ScrollView scrView = new ScrollView(context);
            scrView.addView(linearLayout);
            
            //add scroll view to dialog and show it
            alertDialog.setView(scrView);
            alertDialog.show();

            //Customize close button style
            TextView buttonview = (TextView)alertDialog.findViewById(android.R.id.button1);
            buttonview.setTypeface(MyResource.getGeoFont(context));
        }
        catch (Exception e){
            //alert exception text
            MyAlert.alertWin(context, "" + e);
        }
    }
    
    /**
     * get image resource by file name
     */
    private int getRawImage(Context context, String image){
        Resources R = context.getResources();
        int res = R.getIdentifier(image, "raw", context.getPackageName());
        return res;
    }
}
