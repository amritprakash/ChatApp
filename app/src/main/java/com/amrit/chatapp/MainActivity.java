package com.amrit.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    DatabaseReference chatDatabaseRef;
    String TAG = "MainActivity";
    ImageButton sendBtn;
    TextView messageTxt;
    static {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        chatRecyclerView = findViewById(R.id.chatList);
        FirebaseApp.initializeApp(this);
        startService(new Intent(this,NewMessageService.class));
        chatAdapter = new ChatAdapter(new ChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {
                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
                //startActivity(intent);
            }
        });
        chatRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(layoutManager);

        chatRecyclerView.setNestedScrollingEnabled(false);

        chatDatabaseRef = FirebaseDatabase.getInstance().getReference("chat");

        messageTxt = findViewById(R.id.messageTxt);
        sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != messageTxt.getText() && messageTxt.getText().length()>0){
                    Message msg = new Message();
                    msg.setDate(new Date());
                    msg.setId(ChatAdapter.id);
                    msg.setText(messageTxt.getText().toString());
                    chatDatabaseRef.push().setValue(msg);
                    messageTxt.setText("");
                }

            }
        });


        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey() +"..." + dataSnapshot.getValue());

                // A new comment has been added, add it to the displayed list
                Message msg = dataSnapshot.getValue(Message.class);
                chatAdapter.addChatMessage(msg);


                chatRecyclerView.scrollToPosition(chatAdapter.getItemCount()-1);



                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                //Comment newComment = dataSnapshot.getValue(Comment.class);
                //String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                //String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                //Comment movedComment = dataSnapshot.getValue(Comment.class);
                //String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                //Toast.makeText(mContext, "Failed to load comments.",
                //      Toast.LENGTH_SHORT).show();
            }
        };

        chatDatabaseRef.addChildEventListener(childEventListener);



        /*userDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userDetail = dataSnapshot.getValue(UserDetail.class);

                if(userDetail!= null){
                    mainViewBalanceTxt.setVisibility(View.VISIBLE);
                    getBonusSignUpTxt.setVisibility(View.INVISIBLE);
                    getBonusTxt.setVisibility(View.INVISIBLE);
                    mainViewBalanceTxt.setText(rupeeSign+userDetail.getUserAccount().getTotalBalance());
                    walletArrowImg.setVisibility(View.VISIBLE);
                } else{
                    mainViewBalanceTxt.setText(rupeeSign+0);
                    mainViewBalanceTxt.setVisibility(View.INVISIBLE);
                    getBonusSignUpTxt.setVisibility(View.VISIBLE);
                    getBonusTxt.setVisibility(View.VISIBLE);
                    walletArrowImg.setVisibility(View.INVISIBLE);
                }

                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                spinner.setVisibility(View.GONE);
            }

        });*/

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
