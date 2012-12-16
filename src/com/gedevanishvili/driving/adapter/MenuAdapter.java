/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gedevanishvili.driving.modules.MyResource;
import com.gedevanishvili.driving.R;

/**
 *
 * @author alexx
 * adapter class for menu list view
 */
public class MenuAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] titles;
    private int layoutRes;
    
    public MenuAdapter(Context context, int res, String[] titles){
        super(context, res, titles);
        this.context = context;
        this.titles = titles;
        this.layoutRes = res;
    }
    
    /**
     * changing layout of list item
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater loInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = loInflater.inflate(layoutRes, parent, false);
        
        TextView textView = (TextView)rowView.findViewById(R.id.inner_menu_item);
        textView.setText(titles[position]);
        textView.setTypeface(MyResource.getGeoFont(context));
        
        return rowView;
    }
}
