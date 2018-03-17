package com.wen.design.entertainmentnetwork.adapter;

/**
 * Created by wen on 2018/2/28.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wen.design.entertainmentnetwork.R;
import com.wen.design.entertainmentnetwork.bean.ActivityNewsBean;

public class ActivityNewsRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ActivityNewsBean> mList;
    private final static int TYPE_CONTENT=0;//正常内容
    private final static int TYPE_FOOTER=1;//下拉刷新

    public interface OnItemClickListener{
        void onClick(String storyUrl,String Name);
    }
    private OnItemClickListener clickListener;
    public void setClickListener(OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }
    public ActivityNewsRvAdapter(Context context,List<ActivityNewsBean> list){
        mContext=context;
        mList = list;
    }
    @Override
    public int getItemViewType(int position) {
        if (position==mList.size()){
            return TYPE_FOOTER;
        }
        return TYPE_CONTENT;
    }
   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if (viewType == TYPE_FOOTER) {
           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_foot, parent, false);

           return new FootViewHolder(view);
       } else {
           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_news_item, parent, false);
           ActivityNewsRvAdapter.ViewHolder holder = new ActivityNewsRvAdapter.ViewHolder(view);
           return holder;
       }
   }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position)==TYPE_FOOTER){

        }
        else {
            ViewHolder viewHolder = (ViewHolder) holder;
            final ActivityNewsBean bean = mList.get(position);
            viewHolder.mStoryName.setText(bean.getTitle());
            viewHolder.mStoryContent.setText(bean.getContent());
            viewHolder.mStoryContent.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    String mUrl="http://www.gushi365.com"+bean.getUrl();
                    String mName=bean.getTitle();
                    clickListener.onClick(mUrl,mName);


                }
            });
          //  Glide.with(mContext).load(bean.getPic()).thumbnail(Glide.with(mContext).load(R.mipmap.loading)).override(720, 840).fitCenter().into(((ViewHolder) holder).mImageView);

        }
    }

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        private final TextView mStoryName,mStoryContent;

        public ViewHolder(View itemView) {
            super(itemView);

            mStoryName=(TextView)itemView.findViewById(R.id.storyTitle);
            mStoryContent=(TextView)itemView.findViewById(R.id.storyContent);


        }
    }
    class FootViewHolder extends RecyclerView.ViewHolder {
        private ContentLoadingProgressBar progressBar;

        public FootViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.pb_progress);
          /*  Glide.with(mContext)
                    .load(R.mipmap.loading2)
                    .asGif()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(progressBar);*/
        }
    }
}

