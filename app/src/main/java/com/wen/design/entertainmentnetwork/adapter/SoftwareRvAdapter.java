package com.wen.design.entertainmentnetwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wen.design.entertainmentnetwork.R;
import com.wen.design.entertainmentnetwork.bean.ActivityNewsBean;
import com.wen.design.entertainmentnetwork.bean.SoftwareBean;

import java.util.List;

/**
 * Created by wen on 2018/3/6.
 */

    public class SoftwareRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private final List<SoftwareBean> mList;
        /*
            上面的ViewHolder是这个适配器必要的泛型，必须有。布局里有几种Type，下面就要写几个自定义的
            ViewHlder，这些自定义的ViewHolder都要继承于RecyclerView.ViewHolder。
        */

        public SoftwareRvAdapter(Context context, List<SoftwareBean> list){
            mContext=context;
            mList = list;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            RecyclerView.ViewHolder viewHolder = null;
            //根据viewType生成viewHolder
            switch (viewType) {
                case 0:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.software_item1, null);
                    viewHolder = new VH(view);
                    break;
                case 1:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.software_item2, null);
                    viewHolder = new VH1(view);
                    break;
                case 2:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.software_item3, null);
                    viewHolder = new VH2(view);
                    break;
            }
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            //根据条目的类型给holder中的控件填充数据
            int itemViewType = getItemViewType(position);
            SoftwareBean bean = mList.get(position);
            switch (itemViewType) {
                case 0:
                    VH vh = (VH) holder;
                    vh.mTextView.setText("项目"+bean.getTitle());
                    break;
                case 1:
                    VH1 vh1 = (VH1) holder;
                    vh1.mTextView.setText("项目"+bean.getPic());
                    vh1.mImageView.setImageResource(R.mipmap.ic_launcher);
                    break;
                case 2:
                    VH2 vh2 = (VH2) holder;
                    vh2.mTextView.setText("项目"+bean.getUrl());
                    break;
            }
        }
        @Override
        public int getItemCount() {
            //获取条目数，模拟数据，这里是写死的
            return mList.size();
        }
        @Override
        public int getItemViewType(int position) {
            //跟据position对应的条目返回去对应的样式（Type）
            if (position < 4) {
                return 0;
            } else if (4 <= position && position < 6) {
                return 1;
            } else return 2;
        }

    public class VH extends RecyclerView.ViewHolder {
        TextView mTextView;
        public VH(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_type1);
        }
    }
    public class VH1 extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        public VH1(View itemView) {
            super(itemView);
            mImageView= (ImageView) itemView.findViewById(R.id.iv_type2);
            mTextView= (TextView) itemView.findViewById(R.id.tv_type2);
        }
    }
    public class VH2 extends RecyclerView.ViewHolder {
        TextView mTextView;
        public VH2(View itemView) {
            super(itemView);
            mTextView= (TextView) itemView.findViewById(R.id.tv_type3);
        }
    }
}
