/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gedevanishvili.driving.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.View;
import android.view.ViewGroup;

import com.gedevanishvili.driving.modules.MyResource;
import com.gedevanishvili.driving.R;

/**
 *
 * @author alexx
 * adapter class for signs list view
 */
public class SignsManagerAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] signs;
    private String[] images;
    private int layoutRes;
    
    public SignsManagerAdapter(Context context, int res, String[] signs, String[] images){
        super(context, res, signs);
        this.context = context;
        this.signs = signs;
        this.images = images;
        this.layoutRes = res;
    }
    
    /**
     * changing layout of list item
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater loInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = loInflater.inflate(layoutRes, parent, false);
        
        //set sign description
        TextView textView = (TextView)rowView.findViewById(R.id.signlabel);
        textView.setText(signs[position]);
        textView.setTypeface(MyResource.getGeoFont(context));
        
        //set sign icon
        ImageView imageView = (ImageView)rowView.findViewById(R.id.signicon);
        imageView.setImageResource(getSignImage(context, images[position]));
        
        return rowView;
    }
    
    /**
     * get image resource by file name
     */
    private int getSignImage(Context context, String image){
        Resources myRes = context.getResources();
        int res = myRes.getIdentifier(image, "raw", context.getPackageName());
        return res;
    }
}
