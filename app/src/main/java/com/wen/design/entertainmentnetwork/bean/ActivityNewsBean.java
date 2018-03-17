package com.wen.design.entertainmentnetwork.bean;

/**
 * Created by wen on 2018/2/28.
 */

public class ActivityNewsBean {

    //活动线报
    private String title,url,pic,content;
    //线报标题，线报地址
    public ActivityNewsBean(String title, String url,String pic,String content){

        this.title = title;
        this.url=url;
        this.pic=pic;
        this.content=content;
    }

    public String getTitle() {
        return title;
    }
    public String getUrl() {
        return url;
    }
    public String getContent() {
        return content;
    }
    public String getPic(){
        return pic;
    }
}
