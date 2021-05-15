package com.example.whatsappclone.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.whatsappclone.R;

public class UserDetailsProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imageProfileIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_profile);

        Intent intent=getIntent();

        String userName=intent.getStringExtra("userName");
        String receiverID=intent.getStringExtra("receiverId");
        String userProfile=intent.getStringExtra("imageProfile");

        toolbar=findViewById(R.id.toolbar);
        imageProfileIV=findViewById(R.id.image_profile);
        if(receiverID!=null){
            toolbar.setTitle(userName);
            if(userProfile!=null){
                if(userProfile.equals("")) {
                    imageProfileIV.setImageResource(R.drawable.icon_male_profile);
                }
                else {
                    Glide.with(this).load(userProfile).into(imageProfileIV);
                }
            }

        }
        initToolbar();
    }
    private void initToolbar() {

        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}