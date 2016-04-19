package model;

/**
 * Created by Administrator on 6/17/2015.
 */
public class RadioStation {

    private String title;
    private String url_title;

    public RadioStation(){

    }

    public RadioStation(String title, String url_title){
        this.title = title;
        this.url_title = url_title;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getUrl_title(){
        return url_title;
    }

    public void setUrl_title(String url_title){
        this.url_title = url_title;
    }
}
