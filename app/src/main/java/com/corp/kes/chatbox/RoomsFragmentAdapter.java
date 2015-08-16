package com.corp.kes.chatbox;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class RoomsFragmentAdapter extends ArrayAdapter<Rooms> {

    private final Context context;
    private final ArrayList<Rooms> roomsArrayList;

    static class ViewHolder {
        public ImageView image;
        public TextView text;
    }

    public RoomsFragmentAdapter(Context context, ArrayList<Rooms> roomsArrayList) {
        super(context, R.layout.fragment_rooms, roomsArrayList);

        this.context = context;
        this.roomsArrayList = roomsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        // 1. Reuse views
        if(rowView == null) {

            // 2. Create inflater
            LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 3. Get rowView from inflater
            rowView = inflater.inflate(R.layout.fragment_rooms, parent, false);

            // 4. Configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) rowView.findViewById(R.id.item_icon);
            viewHolder.text = (TextView) rowView.findViewById(R.id.item_title);
            rowView.setTag(viewHolder);
        }

        // 5. Fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        try {
            Class res = R.mipmap.class;
            Field field = res.getField(roomsArrayList.get(position).getIcon());
            int iconId = field.getInt(null);
            holder.image.setImageResource(iconId);
        }
        catch (Exception e) {
            Log.e("Icon", "Failure to get drawable id.", e);
        }
        holder.text.setText(roomsArrayList.get(position).getName());

        // 6. Return rowView
        return rowView;
    }
}
