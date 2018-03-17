package com.wen.design.entertainmentnetwork.bean;

/**
 * Created by wen on 2018/2/28.
 */

public class SoftwareBean {
    //活动线报
    private String one,two,three;
    //线报标题，线报地址
    public SoftwareBean(String one, String two,String three){

        this.one = one;
        this.two=two;
        this.three=three;
    }

    public String getTitle() {
        return one;
    }
    public String getUrl() {
        return two;
    }
    public String getPic(){
        return three;
    }
}
