
package com.github.limboc.sample.data.bean;


import java.util.Date;

public class Meizhi{

    public String url;
    public String type;
    public String desc;
    public String who;
    public boolean used;
    public Date createdAt;
    public Date updatedAt;
    public Date publishedAt;
    public int imageWidth;
    public int imageHeight;

    @Override
    public String toString() {
        return "Meizhi{" +
                "url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", who='" + who + '\'' +
                ", used=" + used +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", publishedAt=" + publishedAt +
                ", imageWidth=" + imageWidth +
                ", imageHeight=" + imageHeight +
                '}';
    }
}
