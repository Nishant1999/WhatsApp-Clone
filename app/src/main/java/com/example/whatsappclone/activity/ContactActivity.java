package com.example.whatsappclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.whatsappclone.R;
import com.example.whatsappclone.adapter.ContactListAdapter;
import com.example.whatsappclone.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    public static final String TAG="ContactActivity";
    private ArrayList<User> userList=new ArrayList<User>();
    private ContactListAdapter contactListAdapter;
    private RecyclerView contactRV;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactRV=findViewById(R.id.activity_contact_view);
        contactRV.setLayoutManager(new LinearLayoutManager(this));
        contactRV.setHasFixedSize(true);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();

        if(firebaseUser!=null){
            getContactList();
        }
    }

    private void getContactList() {
        firebaseFirestore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot snapshots:queryDocumentSnapshots){
                    // Log.d(TAG,"onSuccess:data"+snapshots.toString());
                    String userID=snapshots.getString("userID");
                    String userName=snapshots.getString("userName");
                    String imageUrl=snapshots.getString("imageProfile");
                    String desc=snapshots.getString("bio");

                    User user=new User();
                    user.setUserID(userID);
                    user.setUserName(userName);
                    user.setBio(desc);
                    user.setImageProfile(imageUrl);

                    if(userID!=null && !userID.equals(firebaseUser.getUid())) {
                        userList.add(user) ;
                    }
                }
                contactListAdapter=new ContactListAdapter(userList,ContactActivity.this);
                contactRV.setAdapter(contactListAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}