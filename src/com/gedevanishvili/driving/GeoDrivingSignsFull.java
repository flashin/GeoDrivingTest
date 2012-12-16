/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving;

import android.app.ListActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Window;

import com.gedevanishvili.driving.dao.SignsFullDao;
import com.gedevanishvili.driving.adapter.SignsManagerAdapter;
import com.gedevanishvili.driving.modules.CustomTitleBar;
import com.gedevanishvili.driving.modules.MyAlert;

/**
 *
 * @author alexx
 * This class displays signs of the selected category
 */
public class GeoDrivingSignsFull extends ListActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        //Get selected category id
        Intent intent = getIntent();
        int catId = intent.getIntExtra("SIGN_CAT_ID", 0);
        String catName = intent.getStringExtra("SIGN_CAT_NAME");
        
        try {
            SignsFullDao signsdao = new SignsFullDao(this, catId);
            setListAdapter(new SignsManagerAdapter(this, R.layout.signsfull, signsdao.getTitles(), signsdao.getImages()));
            //Set a click event to the list item
            getListView().setOnItemClickListener(signsdao.getClickListener());

            //Set custom title bar
            CustomTitleBar.setCustomTitleBar(this, catName);
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
