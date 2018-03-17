package com.wen.design.entertainmentnetwork.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pgyersdk.crash.PgyCrashManager;
import com.wen.design.entertainmentnetwork.OnLoadMoreListener;
import com.wen.design.entertainmentnetwork.R;
import com.wen.design.entertainmentnetwork.activity.StoryMessageActivity;
import com.wen.design.entertainmentnetwork.adapter.ActivityNewsRvAdapter;
import com.wen.design.entertainmentnetwork.bean.ActivityNewsBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 2018/3/17.
 */
//幼儿故事
public class ChildStoryFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private Handler handler;
    private List<ActivityNewsBean> mList;
    private ActivityNewsRvAdapter adapter;
    private int wenNumber = 1;
    public static Fragment newInstance() {
        ChildStoryFragment fragment = new ChildStoryFragment();
        return fragment;
    }
    @SuppressLint({"InflateParams", "HandlerLeak"})
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View viewLayout = inflater.inflate(R.layout.fragment_childstory, null);
        mRecyclerView = (RecyclerView) viewLayout.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ActivityNewsRvAdapter(getActivity(), list());
        mRecyclerView.setAdapter(adapter);
        adapter.setClickListener(new ActivityNewsRvAdapter.OnItemClickListener() {
            @Override
            public void onClick(String storyUrl,String storyName) {

                Intent intent=new Intent(getActivity(),StoryMessageActivity.class);
                intent.putExtra("storyAddress",""+storyUrl);
                intent.putExtra("storyName",""+storyName);
                startActivity(intent);
            }});
        mRecyclerView.addOnScrollListener(new OnLoadMoreListener() {

            @SuppressLint("HandlerLeak")
            @Override
            protected void onLoading(int countItem, int lastItem) {
                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        //RecyclerView列表进行UI数据更新
                        ActivityNewsBean bean = new ActivityNewsBean(msg.getData().getString("titleText"), msg.getData().getString("urlText"), msg.getData().getString("picText"), msg.getData().getString("contentText"));
                        mList.add(bean);
                        adapter.notifyDataSetChanged();
                        //如果在第一项添加模拟数据需要调用 scrollToPosition（0）把列表移动到顶端（可选）
                        //mRecyclerView.scrollToPosition(0);
                        super.handleMessage(msg);
                    }
                };
                wenNumber++;
                //子线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        parseMusic();
                    }
                }).start();
            }
        });
        wenNumber++;
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //RecyclerView列表进行UI数据更新
                ActivityNewsBean bean = new ActivityNewsBean(msg.getData().getString("titleText"), msg.getData().getString("urlText"), msg.getData().getString("picText"),msg.getData().getString("contentText"));
                mList.add(bean);
                adapter.notifyDataSetChanged();
                super.handleMessage(msg);
            }
        };
        //子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                parseMusic();
            }
        }).start();
        return viewLayout;
    }

    private List<ActivityNewsBean> list() {
        mList = new ArrayList<>();
        return mList;
    }

    public void parseMusic() {

        try {   //解析怀旧娱乐网的HTML字符串
            Document document = Jsoup.connect("http://www.gushi365.com/youergushi/index_" + wenNumber+".html").get();
            Elements element = document.select("header.entry-header");
            Elements element1 = document.select("div.archive-content");
            Elements element2 = document.select("span.entry-more");


            int i = 0;
            for (i = 0; i < element.size(); i++) {
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                //往Bundle中存放数据
                bundle.putString("titleText", element.get(i).select("h2.entry-title").select("a").text());
                bundle.putString("contentText", element1.get(i).text());
                bundle.putString("urlText", element2.get(i).select("a").attr("href"));
                bundle.putString("picText", element.get(i).select("a").attr("src"));
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        } catch (IOException e) {
            PgyCrashManager.reportCaughtException(getActivity(), e);
        }

    }
}

