/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving.dao;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;

import com.gedevanishvili.driving.db.DatabaseManager;
import com.gedevanishvili.driving.db.SQLiteDBManager;
import com.gedevanishvili.driving.GeoDrivingSignsFull;

/**
 *
 * @author alexx
 * Data Access Object class of sign categories
 */
public class SignsListDao {
    private int cnt;
    private int[] catids;
    private String[] catnames;
    private Context context;
    private DatabaseManager dbManager;
    
    public SignsListDao(Context context){
        //open sqlite database
        dbManager = new SQLiteDBManager(context);
        dbManager.openDatabase();
        
        this.context = context;
        
        String query = "select c._id, c.name_en as name from sign_cats c order by c._id asc";
        
        Cursor cursor = dbManager.qin(query);
        cnt = cursor.getCount();
        
        if (cnt > 0){
            catnames = new String[cnt];
            catids = new int[cnt];
            int i = 0;
            while (cursor.moveToNext()){
                catids[i] = cursor.getInt(0);
                catnames[i] = cursor.getString(1);
                i++;
            }
        }
    }
    
    /**
     * returns category names
     */
    public String[] getSignsCategories(){        
        return catnames;
    }
    
    /**
     * click event of the list item 
     */
    public OnItemClickListener getClickListener(){
        OnItemClickListener clickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Starts intent of the signs of the selected category
                Intent intent = new Intent(context, GeoDrivingSignsFull.class);
                intent.putExtra("SIGN_CAT_ID", catids[position]);
                intent.putExtra("SIGN_CAT_NAME", catnames[position]);
                context.startActivity(intent);
            }
        };
        return clickListener;
    }
}
