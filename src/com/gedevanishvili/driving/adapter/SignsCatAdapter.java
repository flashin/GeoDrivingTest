/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.gedevanishvili.driving.modules.MyResource;
import com.gedevanishvili.driving.R;

/**
 *
 * @author alexx
 * adapter class for sign categories list view
 */
public class SignsCatAdapter extends ArrayAdapter<String> {
    private String[] signcats;
    private Context context;
    private int layoutRes;
    
    public SignsCatAdapter(Context context, int res, String[] signcats){
        super(context, res, signcats);
        this.signcats = signcats;
        this.context = context;
        this.layoutRes = res;
    }
    
    /**
     * changing layout of list item
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater loInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = loInflater.inflate(layoutRes, parent, false);
        
        TextView textView = (TextView)rowView.findViewById(R.id.signlistitem);
        textView.setText(signcats[position]);
        textView.setTypeface(MyResource.getGeoFont(context));
        
        return rowView;
    }
}
