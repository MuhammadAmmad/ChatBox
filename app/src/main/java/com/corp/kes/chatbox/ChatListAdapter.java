package com.corp.kes.chatbox;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ChatListAdapter extends BaseAdapter {

    private Context context;
    private List<Chat> messagesItems;

    public ChatListAdapter(Context context, List<Chat> messages) {
        this.context = context;
        this.messagesItems = messages;
    }

    @Override
    public int getCount() {
        return messagesItems.size();
    }

    @Override
    public Object getItem(int i) {
        return messagesItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        /**
         * The following list not implemented reusable list items as list items
         * are showing incorrect data Add the solution if you have one
         * */

        Chat m = messagesItems.get(i);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // Identifying the message owner
        if (messagesItems.get(i).isSelf()) {
            // message belongs to you, so load the right aligned layout
            view = mInflater.inflate(R.layout.list_item_msg_right, viewGroup, false);
        } else {
            // message belongs to other person, load the left aligned layout
            view = mInflater.inflate(R.layout.list_item_msg_left, viewGroup, false);
        }

        TextView lblFrom = (TextView) view.findViewById(R.id.lblMsgFrom);
        TextView txtMsg = (TextView) view.findViewById(R.id.txtMsg);

        txtMsg.setText(m.getMessage());
        lblFrom.setText(m.getFromName());

        return view;
    }
}
