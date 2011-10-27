package com.mappro.locationlistview;

import java.util.ArrayList;

import com.mappro.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.mappro.model.*;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<PlaceModel> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter(Activity a, ArrayList<PlaceModel> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.lvlocationitem, null);

        TextView text=(TextView)vi.findViewById(R.id.text);
        TextView text1=(TextView)vi.findViewById(R.id.small_text);
        
        text.setText(data.get(position).getName());
        text1.setText(data.get(position).getAddress());
        
        return vi;
    }
}