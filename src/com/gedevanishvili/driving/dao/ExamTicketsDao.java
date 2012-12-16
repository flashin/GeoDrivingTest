/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving.dao;

import android.content.Context;
import android.database.Cursor;

import com.gedevanishvili.driving.db.DatabaseManager;
import com.gedevanishvili.driving.db.SQLiteDBManager;
import com.gedevanishvili.driving.modules.ExamQuestion;

/**
 *
 * @author alexx
 * Data Access Object class of test questions
 */
public class ExamTicketsDao {
    /*
     * type = 1 exam; type = 2 theme tickets;
     * theme tickets would be added in the next edition
     */
    private int type;
    private int cnt;
    DatabaseManager dbManager;
    
    /**
     Initialize class properties
     */
    public ExamTicketsDao(Context context, int t){
        type = t;
        //open sqlite database
        dbManager = new SQLiteDBManager(context);
        dbManager.openDatabase();
    }
    
    /**
     * Returns array of questions
     */
    public ExamQuestion[] getQuestions(){
        String query = "select r._id, r.image, r.cat_id, r.answer, r.answer_nums, r.description_en as description "
                + "from (select t.cat_id, (select q._id from tickets q where q.cat_id = t.cat_id "
                + "order by random() "
                + "limit 0,1) ticket_id "
                + "from tickets t group by t.cat_id order by cat_id asc) u, tickets r "
                + "where r._id = u.ticket_id order by r.cat_id asc;";
        
        Cursor cursor = dbManager.qin(query);
        cnt = cursor.getCount();
        if (cnt == 0){
            return null;
        }
        
        ExamQuestion[] eq = new ExamQuestion[cnt];
        int i = 0;
        while (cursor.moveToNext()){
            eq[i] = new ExamQuestion(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5));
            i++;
        }
        
        return eq;
    }
    
    /**
     * Returns questions quantity
     */
    public int getCnt(){
        return cnt;
    }
}
