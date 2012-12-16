/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving.modules;

import android.app.AlertDialog;
import android.content.Context;

/**
 *
 * @author alexx
 * This class is to alert error messages
 */
public class MyAlert {
    /**
     * Static method to alert message in alert dialog
     */
    public static void alertWin(Context context, String str){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Error Alert");
        alertDialogBuilder.setMessage(str);
        
        AlertDialog alertDialog = alertDialogBuilder.create();
	alertDialog.show();
    }
}
