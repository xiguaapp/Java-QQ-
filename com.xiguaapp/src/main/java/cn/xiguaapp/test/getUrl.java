package cn.xiguaapp.test;

import cn.xiguaapp.pojo.Song;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class getUrl {
    public static void main(String[] args) throws IOException {
        //打开浏览器
        CloseableHttpClient aDefault = HttpClients.createDefault();
        String keyWorld= URLEncoder.encode("火红的萨日朗", "UTF-8");
        //输入url
        HttpGet httpGet=new HttpGet("https://c.y.qq.com/soso/fcgi-bin/music_search_new_platform?searchid=53806572956004615&t=1&aggr=1&cr=1&catZhida=1&lossless=0&flag_qc=0&p=1&n=2&w="+keyWorld);        //按回车
        CloseableHttpResponse execute = aDefault.execute(httpGet);
        //解析响应后的数据
            //判断状态码是否为200 正常
                if(execute.getStatusLine().getStatusCode()==200){
                    HttpEntity httpEntity=execute.getEntity();
                    String content= EntityUtils.toString(httpEntity,"utf-8");
                    content="["+content.substring(9,content.length()-1)+"]";

                    System.out.println("获取到的json"+content);
                    //这里开始要把json变为数组
                 JSONArray json=JSONArray.parseArray(content);
                    System.out.println(json.size());
                 JSONObject jsonObject=json.getJSONObject(0);
                    System.out.println("jsonObj里面的值为"+jsonObject.toString());
                  JSONObject data=jsonObject.getJSONObject("data");
                    System.out.println("data的值"+data.toString());
                    JSONObject song=data.getJSONObject("song");
                    System.out.println("song的值为"+song.toString());
                    JSONArray list=song.getJSONArray("list");
                    System.out.println("list的长度为"+list.size());
                    System.out.println(list);
                    //这里准备需要的东西 Image_id Song_id
                    String Image_id="";
                    String song_id="";
                    String song_name="";
                    List<Song> songList=new ArrayList<Song>();
                    for(int i=0;i<list.size();i++){
                        System.out.println("=========================================================");
                        String file=list.getJSONObject(i).getString("f");
                        //"246969758|火红的萨日朗|4185269|要不要买菜|9583273|火红的萨日朗|0|223|0|1|0|8957075|3582956|0|0|0|23964002|5142255|5431882|0|002cLtlI3iMS0f|001adk3q0FE3XP|0042O3NU4EmPMc|0|4009",
                        String[] split = file.split("\\|");

                        song_name=split[1];
                        Image_id="http://imgcache.qq.com/music/photo/album_300/" +(Integer.valueOf(split[4])% 100) + "/300_albumpic_" + split[4] + "_0.jpg";
                        song_id=split[0];
                        System.out.println(file);
                        getSongMid(song_id);
                    }


//                    System.out.println(song.size());

//                    PrintWriter pw = new PrintWriter(new OutputStreamWriter(new
//                            FileOutputStream("e:/Test2.json")), true);
//                    pw.println(content);
//                    String[]
//                    System.out.println(content);
//                    pw.close();

                }
    }
    public static  String getSongMid(String songid) throws IOException {
        CloseableHttpClient aDefault = HttpClients.createDefault();
        //输入url
        HttpGet httpGet=new HttpGet("https://c.y.qq.com/v8/fcg-bin/fcg_play_single_song.fcg?songid=" +songid+ "&tpl=yqq_song_detail&format=jsonp&callback=getOneSongInfoCallback");
        CloseableHttpResponse execute = aDefault.execute(httpGet);
        if(execute.getStatusLine().getStatusCode()==200){
            HttpEntity httpEntity=execute.getEntity();
            String content= EntityUtils.toString(httpEntity,"utf-8");
            content="["+content.substring(23,content.length()-1)+"]";
            System.out.println(content);
            JSONArray json=JSONArray.parseArray(content);
            JSONObject jsonObject=json.getJSONObject(  0);
            System.out.println("jsonObj里面的值为"+jsonObject.toString());
           JSONArray data=jsonObject.getJSONArray("data");
           //000scoU22MYKkv
            System.out.println("data的值"+data.toString());



        }
        return  null;
    }
}
