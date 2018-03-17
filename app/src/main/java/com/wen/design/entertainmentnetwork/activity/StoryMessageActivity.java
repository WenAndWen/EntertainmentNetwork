package com.wen.design.entertainmentnetwork.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.pgyersdk.crash.PgyCrashManager;
import com.wen.design.entertainmentnetwork.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by wen on 2018/3/17.
 */

public class StoryMessageActivity extends AppCompatActivity {
    private String strStoryAddress,strStoryName;
    private Handler hander;
    private TextView mTextView;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentReceiveData =getIntent();
        strStoryAddress =intentReceiveData.getStringExtra("storyAddress");
        strStoryName =intentReceiveData.getStringExtra("storyName");
        setContentView(R.layout.activity_activity_news_enter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(strStoryName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //接受文章的地址
        mTextView = (TextView) findViewById(R.id.text1);

        hander = new Handler() {
            @Override
            public void handleMessage(Message msg) {
             mTextView.append("\t\t\t\t"+msg.getData().getString("storyText")+"\n");
                super.handleMessage(msg);
            }
        };
        //子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                parseArticleAddress();
            }
        }).start();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(StoryMessageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void parseArticleAddress() {

        try {   //解析HTML字符串
            Document document = Jsoup.connect(strStoryAddress).get();
            //#main > article > div > div.single-content > p:nth-child(4)
            Elements element = document.select("#main").select("article").select("div").select("div.entry-content").select("div.single-content").select("p");

            int i = 0;
            for (i = 0; i < element.size(); i++) {
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                //往Bundle中存放数据
                bundle.putString("storyText", element.get(i).text());
                msg.setData(bundle);
                hander.sendMessage(msg);
            } }
        catch (IOException e) {
            PgyCrashManager.reportCaughtException(StoryMessageActivity.this, e);
        }

    }}


