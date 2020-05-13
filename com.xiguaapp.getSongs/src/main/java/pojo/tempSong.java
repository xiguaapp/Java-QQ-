package pojo;

public class tempSong {
    private  String mid ;
    private  String m4a ;
    private  String mp3_l ;
    private  String mp3_h ;
    private  String ape ;
    private  String songname ;
    private  String albumname ;
    private  String singername ;
    private  String pic ;
    private  String mv ;

    public tempSong(String mid, String m4a, String mp3_l, String mp3_h, String ape, String songname, String albumname, String singername, String pic, String mv, String lrc) {
        this.mid = mid;
        this.m4a = m4a;
        this.mp3_l = mp3_l;
        this.mp3_h = mp3_h;
        this.ape = ape;
        this.songname = songname;
        this.albumname = albumname;
        this.singername = singername;
        this.pic = pic;
        this.mv = mv;
        this.lrc = lrc;
    }

    public tempSong() {
    }

    private  String lrc ;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getM4a() {
        return m4a;
    }

    public void setM4a(String m4a) {
        this.m4a = m4a;
    }

    public String getMp3_l() {
        return mp3_l;
    }

    public void setMp3_l(String mp3_l) {
        this.mp3_l = mp3_l;
    }

    public String getMp3_h() {
        return mp3_h;
    }

    public void setMp3_h(String mp3_h) {
        this.mp3_h = mp3_h;
    }

    public String getApe() {
        return ape;
    }

    public void setApe(String ape) {
        this.ape = ape;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }

    public String getSingername() {
        return singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getMv() {
        return mv;
    }

    public void setMv(String mv) {
        this.mv = mv;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }
}
