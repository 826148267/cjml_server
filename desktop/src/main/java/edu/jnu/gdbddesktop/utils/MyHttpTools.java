package edu.jnu.gdbddesktop.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.SerializableEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月01日 22时14分
 * @功能描述: 我的http工具类
 */
public class MyHttpTools {

    public static HttpResponse sendHttpGetRequestWithString(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            return httpClient.execute(httpGet);
        } catch (IOException ex) {
            throw new RuntimeException("请求" + url + "异常",ex);
        }
    }

    public static HttpResponse sendHttpPostRequestWithString(String url, Map<String, String> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String jsonString = JSONObject.toJSONString(params);
        httpPost.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
        try {
            return httpClient.execute(httpPost);
        } catch (IOException ex) {
            throw new RuntimeException("请求" + url + "异常",ex);
        }
    }
}
