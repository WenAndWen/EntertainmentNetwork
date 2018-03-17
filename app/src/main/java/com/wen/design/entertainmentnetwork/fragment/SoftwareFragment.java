package com.wen.design.entertainmentnetwork.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wen.design.entertainmentnetwork.R;
import com.wen.design.entertainmentnetwork.adapter.SoftwareRvAdapter;
import com.wen.design.entertainmentnetwork.bean.SoftwareBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 2018/2/28.
 */

public class SoftwareFragment extends Fragment {
    private RecyclerView mRecy;
    private List<SoftwareBean> mList;
    private Handler handler;
    private int wenNumber = 0;
    private SoftwareRvAdapter adapter;
    private int i;

    public static Fragment newInstance() {
        SoftwareFragment fragment = new SoftwareFragment();
        return fragment;
    }
    @SuppressLint({"InflateParams", "HandlerLeak"})
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View viewLayout = inflater.inflate(R.layout.fragment_childstory, null);
        mRecy = (RecyclerView)viewLayout. findViewById(R.id.recyclerView);
        //下面的2代表的一行的size是4
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            //返回position对应的条目所占的size
            @Override
            public int getSpanSize(int position) {
                if (position < 4)
                    //这里返回4，指的是当position满足上面条件时，一个条目占得size是4
                    //也就是说这个条目占一行，因为上面设置的一行的size是4
                    return 4;
                else if (3 <= position && position < 6)
                    //这里返回2，指的是当position满足上面条件时，一个条目占得size是2
                    // 也就是说这个条目占半行，因为上面设置的一行的size是4
                    return 2;
                else
                    //这里返回1，指的是当position满足上面条件时，一个条目占得size是1
                    // 也就是说这个条目占1/4行，因为上面设置的一行的size是4
                    return 1;
            }
        });
        //用来添加分割线
        //mRecy.addItemDecoration();
        //设置管理
        mRecy.setLayoutManager(gridLayoutManager);
         adapter = new SoftwareRvAdapter(getActivity(),list());
        //设置适配器
        mRecy.setAdapter(adapter);
        initData();
        return viewLayout;
    }
    private List<SoftwareBean> list() {
        mList = new ArrayList<>();
        return mList;
    }

    private void initData() {
        for(i=0;i<50;i++){
            SoftwareBean bean = new SoftwareBean(""+i, ""+i, ""+i);
            mList.add(bean);
            adapter.notifyDataSetChanged();
        }
    }
}