package com.github.limboc.sample.data.bean;



public class DemoModel {

    private Object content;

    /**
     * 这个model中决定数据类型的字段
     */
    private String type;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DemoModel{" +
                "content=" + content +
                ", type='" + type + '\'' +
                '}';
    }
}
