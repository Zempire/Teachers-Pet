package com.example.android.tabsetup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class TabActivity extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    TabItem tabStudent, tabExam, tabTask;
    Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        toolbar = findViewById(R.id.mainToolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tab_layout);
        tabExam = findViewById(R.id.tabExam);
        tabStudent = findViewById(R.id.tabStudent);
        tabTask = findViewById(R.id.tabTask);

        viewPager = findViewById(R.id.pager);

        pageAdapter = new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch(tab.getPosition()) {
                    case 1:
                        toolbar.setBackgroundColor(ContextCompat.getColor(TabActivity.this,
                                R.color.taskPrimary));
                        tabLayout.setBackgroundColor(ContextCompat.getColor(TabActivity.this,
                                R.color.taskPrimary));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(ContextCompat.getColor(TabActivity.this,
                                    R.color.taskPrimary));
                        }
                        break;
                    case 2:
                        toolbar.setBackgroundColor(ContextCompat.getColor(TabActivity.this,
                                R.color.examPrimary));
                        tabLayout.setBackgroundColor(ContextCompat.getColor(TabActivity.this,
                                R.color.examPrimary));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(ContextCompat.getColor(TabActivity.this,
                                    R.color.examPrimary));
                        }
                        break;
                    default:
                        toolbar.setBackgroundColor(ContextCompat.getColor(TabActivity.this,
                                R.color.colorAccent));
                        tabLayout.setBackgroundColor(ContextCompat.getColor(TabActivity.this,
                                R.color.colorAccent));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(ContextCompat.getColor(TabActivity.this,
                                    R.color.colorAccent));
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

}
