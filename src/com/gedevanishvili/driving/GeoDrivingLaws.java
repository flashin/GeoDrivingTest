/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.Window;
import android.view.View;

import com.gedevanishvili.driving.modules.MyAlert;
import com.gedevanishvili.driving.dao.LawsDao;
import com.gedevanishvili.driving.adapter.LawsAdapter;
import com.gedevanishvili.driving.modules.CustomTitleBar;

/**
 *
 * @author alexx
 * This class displays driving laws list
 */
public class GeoDrivingLaws extends ListActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here       
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        try {
            LawsDao lawsDao = new LawsDao(this);
            setListAdapter(new LawsAdapter(this, R.layout.laws, lawsDao.getTitles()));
            //Set a click event to the list item
            getListView().setOnItemClickListener(lawsDao.getClickListener());
        
            //Set custom title bar
            CustomTitleBar.setCustomTitleBar(this, getString(R.string.button_laws));
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
