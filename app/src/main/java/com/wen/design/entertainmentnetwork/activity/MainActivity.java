package com.wen.design.entertainmentnetwork.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.update.PgyUpdateManager;
import com.wen.design.entertainmentnetwork.R;
import com.wen.design.entertainmentnetwork.adapter.FragmentAdapter;
import com.wen.design.entertainmentnetwork.fragment.ActivityNewsFragment;
import com.wen.design.entertainmentnetwork.fragment.BedtimeFragment;
import com.wen.design.entertainmentnetwork.fragment.ChildStoryFragment;
import com.wen.design.entertainmentnetwork.fragment.FableStoryFragment;
import com.wen.design.entertainmentnetwork.fragment.FolkloreFragment;
import com.wen.design.entertainmentnetwork.fragment.PuzzleStoryFragment;
import com.wen.design.entertainmentnetwork.fragment.SoftwareFragment;
import com.wen.design.entertainmentnetwork.fragment.YoungStoryFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String[] sTitle = new String[]{"幼儿故事", "儿童故事", "睡前故事", "益智故事","寓言故事","民间故事"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences themeSp=getSharedPreferences("theme",MODE_PRIVATE);
        String themeName=themeSp.getString("theme","知乎蓝");
        if(themeName.equals("知乎蓝")){
            setTheme(R.style.AppTheme);
        }
        else if(themeName.equals("水鸭绿")){
            setTheme(R.style.AppTheme_Teal);
        }
        else if(themeName.equals("低调灰")){
            setTheme(R.style.AppTheme_Pink);
        }
        setContentView(R.layout.activity_main);
        PgyUpdateManager.setIsForced(true); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
        PgyUpdateManager.register(this);
        PgyCrashManager.register(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView mAboutText = (TextView) findViewById(R.id.aboutText);
        AssetManager mgr=getAssets();//得到AssetManager
        Typeface tf=Typeface.createFromAsset(mgr, "fonts/Tangerine-Bold.ttf");//根据路径得到Typeface
        mAboutText.setTypeface(tf);//设置字体
        initView();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
         if (id == R.id.nav_gallery) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent textIntent = new Intent(Intent.ACTION_SEND);
            textIntent.setType("text/plain");
            textIntent.putExtra(Intent.EXTRA_TEXT, "大家好，我在使用一款富含故事的应用软件。快来下载使用吧，一大堆有趣的故事还在等着你呢！！");
            startActivity(Intent.createChooser(textIntent, "分享"));
        } else if (id == R.id.nav_send) {
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("关于");
            builder.setMessage("故事汇目前由文文独立设计/开发/运营完成的。\n\n故事汇的开发诞生于2018/3/16日，因为我经常趴在电脑上，频感疲惫。\n\n" +
                    "偶然间看到一个故事网站，发现这个网站的故事挺有趣的，偶尔看看小故事也可以放松一下。\n\n" +
                    "我姐姐家的孩子也比较小，于是我决定做一款富含故事的应用，后来就有了故事汇。\n\n由于作者功力尚欠，在使用应用中出现的bug，大家可以向作者反馈。\n\n数据来源(故事365):http://www.gushi365.com" +
                    "\n\nqq87920151");
            builder.create();
            builder.setCancelable(false);
            builder.setPositiveButton("我知道了", null);
            builder.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initView() {
        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[2]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[3]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[4]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[5]));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ChildStoryFragment.newInstance());
        fragments.add(YoungStoryFragment.newInstance());
        fragments.add(BedtimeFragment.newInstance());
        fragments.add(PuzzleStoryFragment.newInstance());
        fragments.add(FableStoryFragment.newInstance());
        fragments.add(FolkloreFragment.newInstance());


        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, Arrays.asList(sTitle));
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
