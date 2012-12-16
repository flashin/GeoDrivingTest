/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving.modules;

import android.content.Context;
import android.content.res.Resources;

/**
 *
 * @author alexx
 * test question class
 */
public class ExamQuestion {
    private int _id;
    private String image;
    private int cat_id;
    private int answer;
    private int answer_nums;
    private int user_answer;
    private String description;
    
    /**
     * Initializes question properties
     */
    public ExamQuestion(int my_id, String myimage, int mycat_id, int myanswer, int myanswer_nums, String description){
        this._id = my_id;
        this.image = myimage;
        this.cat_id = mycat_id;
        this.answer = myanswer;
        this.answer_nums = myanswer_nums;
        this.description = description;
        this.user_answer = 0;
    }
    
    /**
     * Sets user's answer
     */
    public void setUserAnswer(int ans){
        user_answer = ans;
    }
    
    /**
     * returns true if user's answer is correct
     */
    public boolean isAnswerCorrect(){
        if (user_answer == answer){
            return true;
        }
        return false;
    }
    
    /**
     * returns question images resource
     */
    public int getTicketImage(Context context){
        Resources R = context.getResources();
        int res = R.getIdentifier("tickets_" + cat_id + "_" + cutExtension(image), "raw", context.getPackageName());
        return res;
    }
    
    /**
     * Cuts extension from file name
     */
    private String cutExtension(String str){
        int lastIndexOf = str.lastIndexOf(".");
        if (lastIndexOf == -1){
            return str;
        }
        return str.substring(0, lastIndexOf);
    }
    
    /**
     returns correct answer
     */
    public int getAnswer(){
        return answer;
    }
    
    /**
     returns answers quantity
     */
    public int getAnswerNums(){
        return answer_nums;
    }
    
    /**
     returns user's answer
     */
    public int getUserAnswer(){
        return user_answer;
    }
    
    /**
     returns description of the answer
     */
    public String getDescription(){
        return description;
    }
    
}
