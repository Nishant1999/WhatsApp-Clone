package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappclone.R;
import com.example.whatsappclone.activity.SettingsActivity;
import com.example.whatsappclone.fragment.CallFragment;
import com.example.whatsappclone.fragment.ChatFragment;
import com.example.whatsappclone.fragment.StatusFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private FloatingActionButton fabAction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);

        viewPager=findViewById(R.id.viewPager);
        tabLayout=findViewById(R.id.tabLayout);
        toolbar=findViewById(R.id.toolbar);
        fabAction=findViewById(R.id.fab_action);

        setUpViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

        setSupportActionBar(toolbar);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeFabIcon(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        switch(id) {
            case R.id.menu_search:
                Toast.makeText(this, "Action Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_new_group:
                Toast.makeText(this, "Action More Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_wa_web:
                Toast.makeText(this, "Action Web Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_starred_message:
                Toast.makeText(this, "Action Starred Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpViewPager(ViewPager viewPager){

        SectionPagerAdapter adapter=new SectionPagerAdapter(getSupportFragmentManager());
        //we need 3 fragments
        adapter.addFragment(new ChatFragment(),"Chats");
        adapter.addFragment(new StatusFragment(),"Status");
        adapter.addFragment(new CallFragment(),"Calls");
        viewPager.setAdapter(adapter);
    }

    private static class SectionPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList=new ArrayList<>();
        private final List<String> mFragmentTitleList=new ArrayList<>();
        public SectionPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFragment(Fragment fragment,String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private void changeFabIcon(final int index){
        fabAction.hide();

        new Handler().postDelayed(new Runnable() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                switch (index){
                    case 0:{
                        fabAction.setImageDrawable(getDrawable(R.drawable.ic_chat));
                        fabAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //startActivity(new Intent(MainActivity.this,ContactActivity.class));
                            }
                        });
                        break;
                    }
                    case 1:{
                        fabAction.setImageDrawable(getDrawable(R.drawable.ic_camera));
                        break;
                    }

                    case 2:{
                        fabAction.setImageDrawable(getDrawable(R.drawable.ic_phone));
                        break;
                    }
                }
                fabAction.show();
            }
        },400);

    }
}