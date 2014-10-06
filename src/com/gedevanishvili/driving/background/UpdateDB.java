/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving.background;

import android.app.IntentService;
import android.content.Intent;
import com.gedevanishvili.driving.db.DatabaseManager;
import com.gedevanishvili.driving.db.SQLiteDBManager;
import java.io.ByteArrayOutputStream;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.HttpStatus;
import org.json.JSONObject;

/**
 *
 * @author alexx
 */
public class UpdateDB extends IntentService {
    
    public UpdateDB() {
        super("UpdateDB");
    }
    
    @Override
    protected void onHandleIntent(Intent workIntent){
        
        /*HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpResponse response = httpclient.execute(new HttpGet(URL));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String responseString = out.toString();
                
                JSONObject JO = new JSONObject(responseString);
                
                DatabaseManager dbManager = new SQLiteDBManager(this);
                dbManager.openDatabase();
                
                String query = "update ticket_cats t set t.name_en = t.name_en || '" + JO.getJSONObject("status") + "' where t._id = 1";
                dbManager.qout(query);
            }
        }
        catch (Exception e){
            
        }*/
        
        DatabaseManager dbManager = new SQLiteDBManager(this);
                dbManager.openDatabase();
                
                String query = "update ticket_cats set name_en = '341' where _id = 1";
                dbManager.qout(query);
                dbManager.closeDatabase();
    }
}
