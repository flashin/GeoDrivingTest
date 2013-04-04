/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.gedevanishvili.driving.adapter.TestCatsAdapter;
import com.gedevanishvili.driving.dao.TestCatsDao;

import com.gedevanishvili.driving.modules.CustomTitleBar;
import com.gedevanishvili.driving.modules.MyAlert;

/**
 *
 * @author alexx
 */
public class GeoDrivingTestCats extends ListActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        try {
            TestCatsDao testCatsDao = new TestCatsDao(this);
            setListAdapter(new TestCatsAdapter(this, R.layout.test_cats, testCatsDao.getTitles()));
            //Set a click event to the list item
            getListView().setOnItemClickListener(testCatsDao.getClickListener());

            //Set custom title bar
            CustomTitleBar.setCustomTitleBar(this, getString(R.string.test_cats_title));
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
