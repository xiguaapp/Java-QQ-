package pojo;

public class Song
{
    private  String singger_name;
    private  String song_pic;
    private  String song_path;
    private  String mw_path;
    private  String play_path;
    private  String song_name;

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public Song(String singger_name, String song_pic, String song_path, String mw_path, String play_path, String song_name) {
        this.singger_name = singger_name;
        this.song_pic = song_pic;
        this.song_path = song_path;
        this.mw_path = mw_path;
        this.play_path = play_path;
        this.song_name = song_name;
    }

    public Song(String play_path) {
        this.play_path = play_path;
    }

    public Song(String singger_name, String song_pic, String song_path, String mw_path, String play_path) {
        this.singger_name = singger_name;
        this.song_pic = song_pic;
        this.song_path = song_path;
        this.mw_path = mw_path;
        this.play_path = play_path;
    }

    public String getPlay_path() {
        return play_path;
    }

    public void setPlay_path(String play_path) {
        this.play_path = play_path;
    }

    public Song(String singger_name, String song_pic, String song_path, String mw_path) {
        this.singger_name = singger_name;
        this.song_pic = song_pic;
        this.song_path = song_path;
        this.mw_path = mw_path;
    }

    public Song() {
    }

    public String getSingger_name() {
        return singger_name;
    }

    public void setSingger_name(String singger_name) {
        this.singger_name = singger_name;
    }

    public String getSong_pic() {
        return song_pic;
    }

    public void setSong_pic(String song_pic) {
        this.song_pic = song_pic;
    }

    public String getSong_path() {
        return song_path;
    }

    public void setSong_path(String song_path) {
        this.song_path = song_path;
    }

    public String getMw_path() {
        return mw_path;
    }

    public void setMw_path(String mw_path) {
        this.mw_path = mw_path;
    }
}
