import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pojo.Song;
import pojo.tempSong;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class getUri {
    public static void main(String[] args) {
//        WebClient webClient = new WebClient(BrowserVersion.CHROME);
//
//        webClient.getOptions().setThrowExceptionOnScriptError(false);
//        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//        webClient.getOptions().setActiveXNative(false);
//        webClient.getOptions().setCssEnabled(false);
//        webClient.getOptions().setJavaScriptEnabled(true);
//        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//
//        HtmlPage page = null;
//        String keyWorld="火红的萨日朗";
//        try {
//            page = webClient.getPage("https://y.qq.com/portal/search.html#page=1&searchid=1&remoteplace=txt.yqq.top&t=song&w="+keyWorld);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            webClient.close();
//        }
//
//        webClient.waitForBackgroundJavaScript(30000);
//
//        String pageXml = page.asXml();
//        System.out.println(pageXml);
//        Document document = Jsoup.parse(pageXml);//获取html文档
//        System.out.println(document.getElementById("content").html());

        String keyWorld = "";
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入您需要搜索的歌 这里没做任何判断！");
        keyWorld=scanner.next();
        String uri = getKeyWorld(keyWorld);
        String xmlPage = returnXml(uri, keyWorld);
        //这里咱们获取到了播放页的网站集合 那么我们就有两个选择
        //一个是去http://www.douqq.com/qqmusic/ 访问 另一个是再模拟点事事件 获取网页上的地址
        List<Song> songList = getList(xmlPage);
        List<Song> realList = getRealList(songList);

        for (int i = 0; i <realList.size() ; i++) {
            System.out.println("============第"+(i+1)+"首===============");
            System.out.println("歌曲名字"+realList.get(i).getSong_name());
            System.out.println("歌手名字"+realList.get(i).getSingger_name());
            System.out.println("歌曲图片"+realList.get(i).getSong_pic());
            System.out.println("mv地址"+realList.get(i).getMw_path());
            System.out.println("歌曲地址"+realList.get(i).getSong_path());
            System.out.println("QQ音乐播放页"+realList.get(i).getPlay_path());

        }
        System.exit(0);
    }

    public static String getKeyWorld(String keyWrold) {
        return "https://y.qq.com/portal/search.html#page=1&searchid=1&remoteplace=txt.yqq.top&t=song&w=" + keyWrold;


    }
    public static String toChinese(String key){
/** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
        String[] strs = key.split("\\\\u");
        String returnStr = "";
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < strs.length; i++) {
            String regex = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ABCDEFGHIJKMLNOPQRSTUVWXYZ-]";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(strs[i]);
            //这里判断是不是数字 可能存在特殊符号
            if (m.find()) {

                returnStr += strs[i];
            } else {


                returnStr += (char) Integer.valueOf(strs[i], 16).intValue();

            }
        }
        return returnStr;
    }

    public static String returnXml(String url, String keyWorld) {
        String result = "";
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);

        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);//是否启用CSS
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX

        webClient.getOptions().setTimeout(30000);//设置“浏览器”的请求超时时间
        webClient.setJavaScriptTimeout(30000);//设置JS执行的超时时间

        HtmlPage page = null;
        try {
            page = webClient.getPage(getKeyWorld(keyWorld));
        } catch (Exception e) {
            webClient.close();

        }
        webClient.waitForBackgroundJavaScript(30000);//该方法阻塞线程

        result = page.asXml();
        return result;
    }

    public static List<Song> getList(String xmlPage) {
        //获取main__inner 内的数据
        Document document = Jsoup.parse(xmlPage);//获取html文档
        Elements elementsByClass = document.getElementsByClass("songlist__songname_txt");//获取元素节点等
        //elemets里面的元素
        // <span class="songlist__songname_txt">
        // <a href="https://y.qq.com/n/yqq/song/002cLtlI3iMS0f.html" class="js_song" title="火红的萨日朗 ">
        // <span class="c_tx_highlight"> 火红的萨日朗 </span> </a> </span>
        //开始循环获取里面的url
        List<Song> list = new ArrayList<Song>();
        for (int i = 0; i < elementsByClass.size(); i++) {
            //获取a标签内的herf节点
            String href = elementsByClass.get(i).childNode(1).attr("href");
            list.add(new Song(href));
        }
        return list;
    }

    //这里就是真家伙了 要返回已经ok的真实播放地址
    public static List<Song> getRealList(List<Song> songs) {
        List<Song> songList = new ArrayList<Song>();
        for (int i = 0; i < songs.size(); i++) {
           songList.add( getRealSong(songs.get(i)));

        }


        return songList;
    }

    public static Song getRealSong(Song song) {
        //我们需要向对应的地方发送请求 为post 地址为http://www.douqq.com/qqmusic/qqapi.php
        Song song1 = new Song();
        // 1.拿到一个httpclient的对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 2.设置请求方式和请求信息
//    HttpGet httpGet = new HttpGet("http://www.itcast.cn");
        HttpPost httpPost = new HttpPost("http://www.douqq.com/qqmusic/qqapi.php");

        //2.1 提交header头信息
        httpPost.addHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
        //2.1 提交请求体
        //提交方式1:一般用在原生ajax请求
//    httpPost.setEntity(new StringEntity("username=zhangsan&passwd=123"));
        //提交方式2：大多数的情况下用这种方式
        ArrayList<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        parameters.add(new BasicNameValuePair("mid", song.getPlay_path()));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(parameters));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 3.执行请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 4.获取返回值
        String html = null;
        try {
            html = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 5.打印

        String  result="["+html.substring(1, html.length()-1).replaceAll("\\\\\"","\"")+"]";
                JSONArray jsonArray = JSON.parseArray(result);
                List<tempSong> tempSongs = (List<tempSong>)JSONArray.parseArray(jsonArray.toString(), tempSong.class);
                String pic=tempSongs.get(0).getPic();
                song1.setSong_pic(pic.replaceAll("\\\\",""));
                String mv_path=tempSongs.get(0).getMv();
                if("\\u6682\\u65e0MV".equals(mv_path)){
                    mv_path="\\u6682\\u65e0";
                }
                song1.setMw_path(toChinese(mv_path));
                song1.setPlay_path(song.getPlay_path());
                String name=tempSongs.get(0).getSingername();
                song1.setSingger_name(toChinese(name));
                String song_path=tempSongs.get(0).getM4a();
                song1.setSong_path(song_path.replaceAll("\\\\",""));
                String songName=tempSongs.get(0).getSongname();
                song1.setSong_name(toChinese(songName));


//        try {
//            CloseableHttpClient client = null;
//            CloseableHttpResponse response = null;
//            try {
//                ObjectMapper objectMapper = new ObjectMapper();
//             String json="{\"mid\":\""+song.getPlay_path()+"\"}";
//
//
//                HttpPost httpPost = new HttpPost("http://www.douqq.com/qqmusic/qqapi.php");
//                httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
//                httpPost.setEntity(new StringEntity(json);
//
//                client = HttpClients.createDefault();
//                response = client.execute(httpPost);
//                HttpEntity entity = response.getEntity();
//                String result = EntityUtils.toString(entity);
//                result="["+result.substring(1, result.length()-1).replaceAll("\\\\\"","\"")+"]";
//                JSONArray jsonArray = JSON.parseArray(result);
//                List<tempSong> tempSongs = (List<tempSong>)JSONArray.parseArray(jsonArray.toString(), tempSong.class);
//                String pic=tempSongs.get(0).getPic();
//                song1.setSong_pic(pic.replaceAll("\\\\",""));
//                song1.setMw_path(tempSongs.get(0).getMv());
//                song1.setPlay_path(song.getPlay_path());
//                String name=tempSongs.get(0).getSingername();
//                song1.setSingger_name(toChinese(name));
//                String song_path=tempSongs.get(0).getM4a();
//                song1.setSong_path(song_path.replaceAll("\\\\",""));
//                String songName=tempSongs.get(0).getSongname();
//                song1.setSong_name(toChinese(songName));
//            } finally {
//                if (response != null) {
//                    response.close();
//                }
//                if (client != null) {
//                    client.close();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

            return  song1;

    }

}
