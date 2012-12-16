/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.View;
import android.view.Window;

import com.gedevanishvili.driving.dao.SignsListDao;
import com.gedevanishvili.driving.adapter.SignsCatAdapter;
import com.gedevanishvili.driving.modules.MyAlert;
import com.gedevanishvili.driving.modules.CustomTitleBar;

/**
 *
 * @author alexx
 * This class displays sign categories in a list
 */
public class GeoDrivingSignsList extends ListActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        try {
            SignsListDao signListDao = new SignsListDao(this);
            setListAdapter(new SignsCatAdapter(this, R.layout.signslist, signListDao.getSignsCategories()));
            //Set a click event to the list item
            getListView().setOnItemClickListener(signListDao.getClickListener());

            //Set custom title bar
            CustomTitleBar.setCustomTitleBar(this, getString(R.string.signslist_name));
        }
        catch (Exception e){
            //alert an exception
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
