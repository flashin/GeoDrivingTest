/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving.dao;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;

import com.gedevanishvili.driving.db.DatabaseManager;
import com.gedevanishvili.driving.db.SQLiteDBManager;
import com.gedevanishvili.driving.modules.DescWindow;

/**
 *
 * @author alexx
 * Data Access Object class of laws
 */
public class LawsDao {
    private int cnt;
    private String[] titles;
    private String[] descriptions;
    private Context context;
    private DatabaseManager dbManager;
    
    public LawsDao(Context context){
        //open sqlite database
        dbManager = new SQLiteDBManager(context);
        dbManager.openDatabase();
        
        this.context = context;
        
        String query = "select t._id, t.name_en as name, t.law_en as law from laws t"
                + " order by t._id asc";
        
        Cursor cursor = dbManager.qin(query);
        cnt = cursor.getCount();
        
        if (cnt > 0){
            titles = new String[cnt];
            descriptions = new String[cnt];
            int i = 0;
            while (cursor.moveToNext()){
                titles[i] = cursor.getString(1);
                descriptions[i] = cursor.getString(2);
                i++;
            }
        }
    }
    
    /**
     * returns law titles
     */
    public String[] getTitles(){
        return titles;
    }
    
    /**
     * returns law descriptions
     */
    public String[] getDescriptions(){
        return descriptions;
    }
    
    /**
     * returns laws quantity
     */
    public int getCount(){
        return cnt;
    }
    
    /**
     * Click event of the law
     */
    public AdapterView.OnItemClickListener getClickListener(){
        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            /**
             * Opens Pop up window with law description
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DescWindow descWin = new DescWindow(context, titles[position], descriptions[position]);
                descWin.drawPopup();
            }
        };
        return clickListener;
    }
}
