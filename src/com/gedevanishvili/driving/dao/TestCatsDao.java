/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving.dao;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;

import com.gedevanishvili.driving.GeoDrivingExam;
import com.gedevanishvili.driving.db.DatabaseManager;
import com.gedevanishvili.driving.db.SQLiteDBManager;

/**
 *
 * @author alexx
 */
public class TestCatsDao {
    private int cnt;
    private int[] ids;
    private String[] titles;
    private Context context;
    private DatabaseManager dbManager;
    
    public TestCatsDao(Context context){
        //open sqlite database
        dbManager = new SQLiteDBManager(context);
        dbManager.openDatabase();
        
        this.context = context;
        
        String query = "select t._id, t.name_en as name from ticket_cats t"
                + " order by t._id asc";
        
        Cursor cursor = dbManager.qin(query);
        cnt = cursor.getCount();
        
        if (cnt > 0){
            ids = new int[cnt];
            titles = new String[cnt];
            int i = 0;
            while (cursor.moveToNext()){
                ids[i] = cursor.getInt(0);
                titles[i] = cursor.getString(1);
                i++;
            }
        }
    }
    
    /**
     * returns category titles
     */
    public String[] getTitles(){
        return titles;
    }
    
    /**
     * returns category ids
     */
    public int[] getIds(){
        return ids;
    }
    
    /**
     * returns category quantity
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
             * Opens Pop up window with ticket category
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Starts intent of the tickets of the selected category
                Intent intent = new Intent(context, GeoDrivingExam.class);
                intent.putExtra("EXAM_TYPE", 2);
                intent.putExtra("TICKET_CAT_ID", ids[position]);
                context.startActivity(intent);
            }
        };
        return clickListener;
    }
}
