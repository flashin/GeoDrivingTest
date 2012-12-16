/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving.modules;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;

/**
 *
 * @author alexx
 * This class is help class to work with application resources
 */
public class MyResource {
    /**
     * static method to get resource by its name
     */
    public static int getResource(Context context, String id){
        Resources R = context.getResources();
        int res = R.getIdentifier(id, "id", context.getPackageName());
        return res;
    }
    
    /**
     * Static method to get Georgian font face
     */
    public static Typeface getGeoFont(Context context){
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/avant_g.ttf");
        return tf;
    }
}
