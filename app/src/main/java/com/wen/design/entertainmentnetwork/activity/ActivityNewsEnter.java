package com.wen.design.entertainmentnetwork.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.pgyersdk.crash.PgyCrashManager;
import com.wen.design.entertainmentnetwork.R;
import com.wen.design.entertainmentnetwork.bean.ActivityNewsBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by wen on 2018/3/2.
 */

public class ActivityNewsEnter extends AppCompatActivity {
    private String strArticleAddress;
    private Handler hander;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_news_enter);
        //接受文章的地址
        Intent intentReceiveData =getIntent();
        strArticleAddress =intentReceiveData.getStringExtra("articleAddress");
        hander = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(ActivityNewsEnter.this,msg.getData().getString("imageUrlText")+msg.getData().getString("imageUrlText"),Toast.LENGTH_LONG).show();
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
    public void parseArticleAddress() {

        try {   //解析小刀娱乐网的HTML字符串
            Document document = Jsoup.connect(strArticleAddress).get();
            Elements element = document.select("div.wow fadeInUpBig");

            int i = 0;
            for (i = 0; i < element.size(); i++) {
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                //往Bundle中存放数据
                bundle.putString("imageUrlText", element.select("p").get(i).select("image").attr("src"));
                bundle.putString("messageText", element.get(i).select("image").attr("src"));
                msg.setData(bundle);
                hander.sendMessage(msg);
            } }
        catch (IOException e) {
            PgyCrashManager.reportCaughtException(ActivityNewsEnter.this, e);
        }

    }}

