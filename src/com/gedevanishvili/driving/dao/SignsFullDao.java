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
 * Data Access Object class of signs
 */
public class SignsFullDao {
    private int cnt;
    private String[] titles;
    private String[] images;
    private String[] descriptions;
    private Context context;
    private DatabaseManager dbManager;
    
    public SignsFullDao(Context context, int cat_id){
        //Open sqlite database
        dbManager = new SQLiteDBManager(context);
        dbManager.openDatabase();
        
        this.context = context;
        
        String query = "select s._id, s.name_en as name, s.description_en as description, s.image"
                + " from signs s"
                + " where s.cat_id = " + cat_id
                + " order by s._id asc";
        
        Cursor cursor = dbManager.qin(query);
        cnt = cursor.getCount();
        
        if (cnt > 0){
            titles = new String[cnt];
            descriptions = new String[cnt];
            images = new String[cnt];
            int i = 0;
            while (cursor.moveToNext()){
                titles[i] = cursor.getString(1);
                descriptions[i] = cursor.getString(2);
                images[i] = "signs_" + cat_id + "_" + cutExtension(cursor.getString(3));
                i++;
            }
        }
    }
    
    /**
     * returns titles
     */
    public String[] getTitles(){
        return titles;
    }
    
    /**
     * returns descriptions
     */
    public String[] getDescriptions(){
        return descriptions;
    }
    
    /**
     * returns image file names
     */
    public String[] getImages(){
        return images;
    }
    
    /**
     * returns signs quantity
     */
    public int getCount(){
        return cnt;
    }
    
    /**
     * Cuts file extension from string
     */
    private String cutExtension(String str){
        int lastIndexOf = str.lastIndexOf(".");
        if (lastIndexOf == -1){
            return str;
        }
        
        return str.substring(0, lastIndexOf);
    }
    
    /**
     * Click event of the list item
     */
    public AdapterView.OnItemClickListener getClickListener(){
        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            /**
             * Opens window with sign description
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DescWindow descWin = new DescWindow(context, titles[position], descriptions[position]);
                descWin.drawPopupWithImage(images[position]);
            }
        };
        return clickListener;
    }
}
