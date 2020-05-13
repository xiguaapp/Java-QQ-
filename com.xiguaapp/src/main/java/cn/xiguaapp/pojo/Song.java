package cn.xiguaapp.pojo;

public class Song {
    private  String name;

    public Song() {
    }

    public String getPicPath() {
        return picPath;
    }

    public Song(String name, String picPath, String uri, String isEnable) {
        this.name = name;
        this.picPath = picPath;
        this.uri = uri;
        this.isEnable = isEnable;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    private  String picPath;
    private  String uri;
    private  String isEnable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
