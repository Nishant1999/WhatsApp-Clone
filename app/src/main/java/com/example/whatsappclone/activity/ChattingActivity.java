package com.example.whatsappclone.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.whatsappclone.R;
import com.example.whatsappclone.adapter.ChatsItemAdapter;
import com.example.whatsappclone.model.ChatsItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChattingActivity extends AppCompatActivity {
    private CircleImageView userProfileCIV;
    private TextView userNameTXT;
    private EditText textMessageET;
    private ImageView backBtnIV;
    private FloatingActionButton sendFAB;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private String receiverID,userProfile,userName;
    private ChatsItemAdapter chatsItemAdapter;
    private ArrayList<ChatsItem> chatsItemsList;
    private RecyclerView chatItemRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        Intent intent=getIntent();
        userName=intent.getStringExtra("userName");
        receiverID=intent.getStringExtra("userID");
        userProfile=intent.getStringExtra("imageProfile");

        userProfileCIV=findViewById(R.id.image_profile);
        userNameTXT=findViewById(R.id.tv_username);
        backBtnIV=findViewById(R.id.btn_back);
        textMessageET=findViewById(R.id.ed_message);
        sendFAB=findViewById(R.id.btn_send);
        Log.d("ReceiverId",receiverID);
        if(receiverID!=null){
            userNameTXT.setText(userName);
            if(userProfile!=null){
                if(userProfile.equals("")) {
                    userProfileCIV.setImageResource(R.drawable.icon_male_profile);
                }
                else {
                    Glide.with(this).load(userProfile).into(userProfileCIV);
                }
            }

        }

        backBtnIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textMessageET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(textMessageET.getText().toString())){
                    sendFAB.setImageDrawable(getDrawable(R.drawable.ic_keyboard_voice_black_24dp));
                }
                else{
                    sendFAB.setImageDrawable(getDrawable(R.drawable.ic_send_black_24dp));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        initBtnClick();

        chatsItemsList=new ArrayList<>();
        chatItemRV=findViewById(R.id.chat_item_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,true);
        linearLayoutManager.setStackFromEnd(true);
        chatItemRV.setLayoutManager(linearLayoutManager);
        chatItemRV.setHasFixedSize(true);
        readChatList();
    }

    private void readChatList() {
        try {
            DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
            reference.child("Chats").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    chatsItemsList.clear();
                    Log.d("Message","message01");
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        ChatsItem chatsItem=dataSnapshot.getValue(ChatsItem.class);
                        if(chatsItem!=null && chatsItem.getSender().equals(firebaseUser.getUid()) && chatsItem.getReceiver().equals(receiverID)
                            || chatsItem.getReceiver().equals(firebaseUser.getUid()) && chatsItem.getSender().equals(receiverID)) {
                            Log.d("Sender2",chatsItem.getSender());
                            chatsItemsList.add(chatsItem);
                        }
                    }
                    if(chatsItemAdapter!=null){
                        chatsItemAdapter.notifyDataSetChanged();
                    }
                    else{
                        chatsItemAdapter=new ChatsItemAdapter(chatsItemsList,ChattingActivity.this);
                        chatItemRV.setAdapter(chatsItemAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void initBtnClick(){
        Log.d("msg","Message Error");
        sendFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(textMessageET.getText().toString())){
                    sendTextMessage(textMessageET.getText().toString());

                    textMessageET.setText("");
                }
            }
        });

        userProfileCIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChattingActivity.this, UserDetailsProfileActivity.class)
                        .putExtra("userName",userName)
                        .putExtra("receiverId",receiverID)
                        .putExtra("imageProfile",userProfile));
            }
        });

    }

    private void sendTextMessage(String message) {
        Date date= Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        String todayDate=dateFormat.format(date);

        Calendar currentDateTime=Calendar.getInstance();
        SimpleDateFormat df=new SimpleDateFormat("hh-mm a");
        String currentTime=df.format(currentDateTime.getTime());

        ChatsItem chatsItem=new ChatsItem(
                todayDate+ " , " +currentTime,
                message,
                "TEXT",
                firebaseUser.getUid(),
                receiverID
        );

        databaseReference.child("Chats").push().setValue(chatsItem).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Send","OnSuccess");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Send","OnFailure"+e.getMessage());
            }
        });

        //Add To ChatList

        DatabaseReference chatRef1=FirebaseDatabase.getInstance().getReference("ChatList").child(firebaseUser.getUid()).child(receiverID);
        chatRef1.child("chatId").setValue(receiverID);

        DatabaseReference chatRef2=FirebaseDatabase.getInstance().getReference("ChatList").child(receiverID).child(firebaseUser.getUid());
        chatRef2.child("chatId").setValue(firebaseUser.getUid());
    }
}