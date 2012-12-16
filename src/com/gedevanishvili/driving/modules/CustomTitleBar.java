/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving.modules;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Window;
import android.view.View;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView;

import com.gedevanishvili.driving.R;
import com.gedevanishvili.driving.GeoDrivingTest;
import com.gedevanishvili.driving.GeoDrivingExam;
import com.gedevanishvili.driving.GeoDrivingSignsList;
import com.gedevanishvili.driving.GeoDrivingLaws;
import com.gedevanishvili.driving.adapter.MenuAdapter;

/**
 *
 * @author alexx
 * This class is to apply custom title bar to the activity
 */
public class CustomTitleBar {
    
    /**
     * Sets text to the custom title bar
     */
    public static void setCustomTitleBar(Activity context, String title){
        context.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_inner);
        
        TextView textView = (TextView)context.getWindow().findViewById(R.id.header_inner);
        textView.setText(title);
        textView.setTypeface(MyResource.getGeoFont(context));
    }
    
    /**
     * Creates menu on custom title bar
     */
    public static void showMenu(Activity context, View view){
        
        //Menu items array
        String[] menuItems = new String[]{
            context.getString(R.string.goto_main),
            context.getString(R.string.button_test),
            context.getString(R.string.button_signs),
            context.getString(R.string.button_laws)};
        
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        ListView listView = new ListView(context);
        
        //create and set menu adapter to the menu list view
        MenuAdapter menuAdapter = new MenuAdapter(context, R.layout.menu_inner, menuItems);
        listView.setAdapter(menuAdapter);
        
        //set click event for the menu item
        listView.setOnItemClickListener(CustomTitleBar.getClickListener());
        
        //add menu list view to the alert dialog
        alertDialogBuilder.setView(listView);
        
        AlertDialog alertDialog = alertDialogBuilder.create();
        
        //Customize menu alert dialog
        WindowManager.LayoutParams WMLP = alertDialog.getWindow().getAttributes();
        WMLP.gravity = Gravity.TOP | Gravity.RIGHT;
        WMLP.width = WindowManager.LayoutParams.WRAP_CONTENT;
        WMLP.height = WindowManager.LayoutParams.WRAP_CONTENT;
        WMLP.x = 2;
        WMLP.y = 2;
        alertDialog.getWindow().setAttributes(WMLP);
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        
        //show menu alert dialog
	alertDialog.show();
    }
    
    /**
     * Menu item click event
     */
    public static AdapterView.OnItemClickListener getClickListener(){
        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = view.getContext();
                Class cl = null;
                //get activity class by selected menu item
                switch (position){
                    case 0: cl = GeoDrivingTest.class; break;
                    case 1: cl = GeoDrivingExam.class; break;
                    case 2: cl = GeoDrivingSignsList.class; break;
                    case 3: cl = GeoDrivingLaws.class; break;
                }
                
                if (cl != null){
                    //Create intent
                    Intent intent = new Intent(context, cl);
                    context.startActivity(intent);
                }
            }
        };
        return clickListener;
    }
}
