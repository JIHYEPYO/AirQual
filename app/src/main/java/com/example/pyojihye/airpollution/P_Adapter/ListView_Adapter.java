package com.example.pyojihye.airpollution.P_Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pyojihye.airpollution.R;

import java.util.ArrayList;

import com.example.pyojihye.airpollution.P_Data.ListViewItem;

/**
 * Created by user on 2016-07-25.
 */
public class ListView_Adapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItems=new ArrayList<ListViewItem>();

    @Override
    public int getCount() {
        return listViewItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem( String title, String desc,String color) {
        ListViewItem item = new ListViewItem();
        item.setTitle(title);
        item.setDesc(desc);
        item.setColor(Color.parseColor(color));
        listViewItems.add(item);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int position=i;
        Context context=viewGroup.getContext();
        /*if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }*/
        if(view==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.air_listview,viewGroup,false);

        }
        LinearLayout linearLayout=(LinearLayout)view.findViewById(R.id.listview_color);
        TextView titleTextView = (TextView)view.findViewById(R.id.listview_text1);
        TextView descTextView = (TextView)view.findViewById(R.id.listview_text2) ;
        ListViewItem listViewItem = listViewItems.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        linearLayout.setBackgroundColor(listViewItem.getColor());
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(listViewItem.getDesc());

        return view;
    }
}
