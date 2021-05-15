package com.example.whatsappclone.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.whatsappclone.R;
import com.example.whatsappclone.adapter.ChatListAdapter;
import com.example.whatsappclone.model.Chat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class ChatFragment extends Fragment {
    private static final String TAG = "ChatsFragment";
    private ArrayList<Chat> chats;
    private RecyclerView chatRV;
    private ChatListAdapter chatListAdapter;
    private ArrayList<String> allUserID;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private FirebaseFirestore firebaseFirestore;
    private Handler handler = new Handler();
    private ProgressBar progressBar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        chats=new ArrayList<>();
        allUserID = new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressBar=view.findViewById(R.id.progress_circular);

        chatRV=view.findViewById(R.id.fragment_chat_view);
        chatRV.setLayoutManager(new LinearLayoutManager(getContext()));
        chatRV.setHasFixedSize(true);
        chatListAdapter=new ChatListAdapter(chats,getContext());
        chatRV.setAdapter(chatListAdapter);
        if (firebaseUser!=null) {
            view.findViewById(R.id.ln_invite).setVisibility(View.GONE);
            getChatList();
        }
        return view;
    }

    private void getChatList() {
            progressBar.setVisibility(View.VISIBLE);
            chats.clear();
            allUserID.clear();
            reference.child("ChatList").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String userID = snapshot.child("chatId").getValue().toString();
                        Log.d(TAG, "onDataChange: userid "+userID);

                        progressBar.setVisibility(View.GONE);
                        allUserID.add(userID);
                    }
                    getUserInfo();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
        }

    private void getUserInfo(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                for (String userID : allUserID){
                    firebaseFirestore.collection("Users").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Log.d(TAG, "onSuccess: ddd"+documentSnapshot.getString("userName"));
                            try {
                                Chat chat = new Chat(
                                        documentSnapshot.getString("userID"),
                                        documentSnapshot.getString("userName"),
                                        "this is description..",
                                        "",
                                        documentSnapshot.getString("imageProfile")
                                );
                                chats.add(chat);
                            }catch (Exception e){
                                Log.d(TAG, "onSuccess: "+e.getMessage());
                            }
                            if (chatListAdapter!=null){
                                chatListAdapter.notifyItemInserted(0);
                                chatListAdapter.notifyDataSetChanged();

                                Log.d(TAG, "onSuccess: adapter "+chatListAdapter.getItemCount());
                            }
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: Error L"+e.getMessage());
                        }
                    });
                }
            }
        });
    }
}