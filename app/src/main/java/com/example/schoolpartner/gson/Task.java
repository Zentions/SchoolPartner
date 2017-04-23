package com.example.schoolpartner.gson;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

/**
 * Created by q on 2017/4/23.
 */
public class Task extends DataSupport{

    private Integer id;
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

    private boolean finished =false;
    private boolean agree;
    public Task(String title, String classification, String content, String time, String money, String addTime, String mattersNeedAttention, Integer FId) {
        this.title = title;
        this.classification = classification;
        this.content = content;
        this.time = time;
        this.money = money;
        this.addTime = addTime;
        this.mattersNeedAttention = mattersNeedAttention;
        this.FId = FId;
    }
    public Task() {

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
                "id=" + id +
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
}
