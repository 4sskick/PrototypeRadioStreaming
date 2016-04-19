package model;

/**
 * Created by Administrator on 6/24/2015.
 */
public class NewsStation {

    private String title, tgl, desc, value, thumbnailUrl;

    public NewsStation(){

    }

    public NewsStation(String title, String tgl, String desc, String value, String thumbnailUrl){
        this.title = title;
        this.tgl = tgl;
        this.desc = desc;
        this.value = value;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String judul){
        this.title = judul;
    }

    public String getThumbnailUrl(){
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl){
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDesc(){
        return desc;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public String getValue(){
        return value;
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getTgl(){
        return tgl;
    }

    public void setTgl(String tgl){
        this.tgl = tgl;
    }
}
