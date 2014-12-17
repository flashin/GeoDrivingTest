/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving.modules;

import android.app.AlertDialog;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ImageView;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.HttpStatus;
import org.json.JSONObject;

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
                    @Override
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
                    @Override
					public void onClick(DialogInterface dialog, int id) {  
                        dialog.dismiss(); 
                    }  
                }
            );

            AlertDialog alertDialog = alertDialogBuilder.create();
            
            //set layout parameters for the text in dialog
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
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
            linearLayout.setOrientation(LinearLayout.VERTICAL);
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
     * draws pop up window with title, description and CLOSE button
     */
    public void drawRequestPopup(final String url){
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
            
            alertDialogBuilder.setPositiveButton(R.string.yes_but, new DialogInterface.OnClickListener(){ 
                    @Override
					public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Activity act = (Activity) context;
                        Window myWin = act.getWindow();
                        myWin.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                        myWin.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        ProgressDialog progDialog = new ProgressDialog(context);
                        progDialog.setMessage(context.getString(R.string.report_wait));
                        progDialog.show();
                        
                        Object[] obj = {url, myWin, progDialog};
                        StartWinRequest winReq = new StartWinRequest();
                        winReq.execute(obj);
                    }  
                }
            );
            
            //Add close button to the dialog
            alertDialogBuilder.setNegativeButton(R.string.no_but, new DialogInterface.OnClickListener(){  
                    @Override
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
            
            buttonview = (TextView)alertDialog.findViewById(android.R.id.button2);
            buttonview.setTypeface(MyResource.getGeoFont(context));
        }
        catch (Exception e){
            //Alert exception text
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
    
    //send request in async task
    private class StartWinRequest extends AsyncTask<Object, Void, String>{
        
        private Window myWin;
        private ProgressDialog progDialog;
    
        @Override
        protected String doInBackground(Object[] urls) {
            
            String url = urls[0].toString();
            myWin = (Window)urls[1];
            progDialog = (ProgressDialog)urls[2];
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(new HttpGet(url));
                StatusLine statusLine = response.getStatusLine();

                if (statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    String responseString = out.toString();

                    JSONObject JO = new JSONObject(responseString);

                    if (JO.getString("success").toString().equals("true")){
                        return context.getString(R.string.report_success);
                    }
                    else {
                        return context.getString(R.string.report_error_2);
                    }
                }
                else {
                    String error = context.getString(R.string.report_error) + statusLine.getStatusCode();
                    return error;
                }
            }
            catch (Exception e){
                return context.getString(R.string.report_error_2);
            }
        }
        
        @Override
        protected void onPostExecute(String result) {
            
            myWin.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            myWin.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progDialog.dismiss();
            MyAlert.alertGeoWin(context, result);
        }
    };
}
