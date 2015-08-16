package com.corp.kes.chatbox;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class RoomsFragment extends ListFragment {

    Context context;
    Firebase firebaseRef;
    ArrayList<Rooms> chatRooms;
    RoomsFragmentAdapter roomsFragmentAdapter;

    public RoomsFragment() {
        // Required empty public constructor
    }


    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rooms, container, false);
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = getActivity();
        Firebase.setAndroidContext(context);
        firebaseRef = new Firebase(getString(R.string.firebase_url));

        firebaseRef.child("rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                chatRooms = new ArrayList<>();
                for(DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Rooms post = postSnapshot.getValue(Rooms.class);
                    chatRooms.add(post);
                }

                roomsFragmentAdapter = new RoomsFragmentAdapter(context, chatRooms);
                setListAdapter(roomsFragmentAdapter);

                ListView list = getListView();
                list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Toast.makeText(context, "Item in " + String.valueOf(position) + " position clicked", Toast.LENGTH_LONG).show();
                        return true;
                    }
                });
            }
            @Override public void onCancelled(FirebaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        //super.onListItemClick(l, v, position, id);
        // Get the item that was clicked
        Rooms rooms = roomsFragmentAdapter.getItem(position);
        String item = rooms.getName();
        //Toast.makeText(context, item + " selected", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    public void _toast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void _(String message){
        Log.d("msg", message);
    }
}
