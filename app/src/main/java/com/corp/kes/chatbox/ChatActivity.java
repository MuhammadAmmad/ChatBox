package com.corp.kes.chatbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar;
    Context context;
    EditText inputMsg;

    Firebase firebaseRef;

    // Chat messages list adapter
    private ChatListAdapter adapter;
    private List<Chat> listMessages;
    private ListView listViewMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        Firebase.setAndroidContext(context);
        firebaseRef = new Firebase(getString(R.string.firebase_url));
        setContentView(R.layout.activity_chat);

        // Creating the Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // Get the message from the intent
        final Intent intent = getIntent();
        String message = intent.getStringExtra("item");
        setTitle(message);

        inputMsg = (EditText) findViewById(R.id.inputMsg);
        final Firebase chatRef = firebaseRef.child("chats").child(message.toLowerCase());

        final Button btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chat chat = new Chat("Kes", String.valueOf(inputMsg.getText()), true);
                chatRef.push().setValue(chat);
                inputMsg.setText("");
            }
        });

        listViewMessages = (ListView) findViewById(R.id.list_view_messages);
        listMessages = new ArrayList<>();
        adapter = new ChatListAdapter(context, listMessages);
        listViewMessages.setAdapter(adapter);

        /*chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listMessages = new ArrayList<>();
                if(dataSnapshot != null) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Chat chat = postSnapshot.getValue(Chat.class);
                        listMessages.add(chat);
                    }
                }

                adapter = new ChatListAdapter(context, listMessages);
                listViewMessages.setAdapter(adapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(context, firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Chat newMessage = dataSnapshot.getValue(Chat.class);
                listMessages.add(newMessage);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(context, firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
