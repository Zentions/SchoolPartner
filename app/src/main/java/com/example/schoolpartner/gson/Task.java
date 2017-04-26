package com.example.schoolpartner.gson;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by q on 2017/4/23.
 */
public class Task extends DataSupport implements Serializable{
    @SerializedName("id")
    private String number;
    private String title;
    private String classification;
    private String content;
    private String time;
    private String money;
    private String addTime;
    private String acceptTime;
    private String mattersNeedAttention;
    @SerializedName("fid")
    private Integer FId;
    private Integer helper;
    private boolean accept;
    private String phoneNumber;
    private boolean finished;
    private boolean agree;
    public Task(String title, String classification, String content, String time, String money, String addTime, String mattersNeedAttention, Integer FId,String phoneNumber) {
        this.title = title;
        this.classification = classification;
        this.content = content;
        this.time = time;
        this.money = money;
        this.addTime = addTime;
        this.mattersNeedAttention = mattersNeedAttention;
        this.FId = FId;
        this.phoneNumber = phoneNumber;
    }
    public Task() {

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getMattersNeedAttention() {
        return mattersNeedAttention;
    }

    public void setMattersNeedAttention(String mattersNeedAttention) {
        this.mattersNeedAttention = mattersNeedAttention;
    }

    public Integer getFId() {
        return FId;
    }

    public void setFId(Integer FId) {
        this.FId = FId;
    }

    public Integer getHelper() {
        return helper;
    }

    public void setHelper(Integer helper) {
        this.helper = helper;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + number +
                ", title='" + title + '\'' +
                ", classification='" + classification + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", money='" + money + '\'' +
                ", addTime='" + addTime + '\'' +
                ", acceptTime='" + acceptTime + '\'' +
                ", mattersNeedAttention='" + mattersNeedAttention + '\'' +
                ", FId=" + FId +
                ", helper=" + helper +
                ", accept=" + accept +
                ", finished=" + finished +
                ", agree=" + agree +
                '}';
    }
    public String getState(){
        if(finished)return "已完成";
        else if(agree) return "任务进行中";
        else if(accept) return "等待同意";
        else return  "无人响应";
    }
}
