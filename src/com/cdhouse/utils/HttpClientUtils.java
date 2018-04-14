package com.cdhouse.utils;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;

public class HttpClientUtils {

    private static PoolingHttpClientConnectionManager connectionManager = null;
    private static HttpClientBuilder httpBuilder = null;
    private static RequestConfig requestConfig = null;
    private static int MAXCONNECTION = 10;
    private static int DEFAULTMAXCONNECTION = 5;
    private static String IP = "cnivi.com.cn";
    private static int PORT = 80;

    static {
        //设置http的状态参数
        requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();

        HttpHost target = new HttpHost(IP, PORT);
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAXCONNECTION);//客户端总并行链接最大数
        connectionManager.setDefaultMaxPerRoute(DEFAULTMAXCONNECTION);//每个主机的最大并行链接数
        connectionManager.setMaxPerRoute(new HttpRoute(target), 20);
        httpBuilder = HttpClients.custom();
        httpBuilder.setConnectionManager(connectionManager);
    }

    /**
     * get请求
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String doGetWithParam(String url, Map<String, String> params)throws Exception{

        //创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
         //创建一个uri对象
        URIBuilder uriBuilder = new URIBuilder(url);
        if(params != null) {
            for (String key : params.keySet()) {
                uriBuilder.addParameter(key, params.get(key));
            }
        }
        HttpGet get = new HttpGet(uriBuilder.build());
         //执行请求
        CloseableHttpResponse response =httpClient.execute(get);
        //取响应的结果
        int statusCode =response.getStatusLine().getStatusCode();
        if(statusCode != HttpStatus.SC_OK){
            LoggerUtils.error("执行get操作失败 url: " + url + " status: " + statusCode);
            return statusCode + "";
        }
        HttpEntity entity =response.getEntity();
        String content = EntityUtils.toString(entity,"utf-8");
        LoggerUtils.info("执行get操作 url: " + url + " status: " + statusCode + " content : " + content);
        response.close();
        httpClient.close();
        return content;
    }

    /**
     * post请求
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String doPostWithParam(String url, Map<String, String> params)throws Exception{

        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个post对象
        HttpPost post =new HttpPost(url);
        //创建一个Entity。模拟一个表单
        List<NameValuePair>kvList = new ArrayList<>();
        if(params != null) {
            for (String key : params.keySet()) {
                kvList.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        //包装成一个Entity对象
        StringEntity entity = new UrlEncodedFormEntity(kvList,"utf-8");
        //设置请求的内容
        post.setEntity(entity);
        post.setConfig(requestConfig);
        //执行post请求
        CloseableHttpResponse response =httpClient.execute(post);
        //取响应的结果
        int statusCode =response.getStatusLine().getStatusCode();
        if(statusCode != HttpStatus.SC_OK){
            LoggerUtils.error("执行get操作失败 url: " + url + " status: " + statusCode);
            return statusCode + "";
        }
        String content = EntityUtils.toString(response.getEntity());
        LoggerUtils.info("执行get操作 url: " + url + " status: " + statusCode + " content : " + content);
        response.close();
        httpClient.close();
        return content;
    }


    /**
     * 获取httpClient实例
     * @return
     */
    public static CloseableHttpClient getConnection() {

        CloseableHttpClient httpClient = httpBuilder.build();
        return httpClient;
    }


    public static HttpUriRequest getRequestMethod(Map<String, String> map, String url, String method) {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> e : entrySet) {
            String name = e.getKey();
            String value = e.getValue();
            NameValuePair pair = new BasicNameValuePair(name, value);
            params.add(pair);
        }
        HttpUriRequest reqMethod = null;
        if ("post".equals(method)) {
            reqMethod = RequestBuilder.post().setUri(url)
                    .addParameters(params.toArray(new BasicNameValuePair[params.size()]))
                    .setConfig(requestConfig).build();
        } else if ("get".equals(method)) {
            reqMethod = RequestBuilder.get().setUri(url)
                    .addParameters(params.toArray(new BasicNameValuePair[params.size()]))
                    .setConfig(requestConfig).build();
        }
        return reqMethod;
    }

    public static void main(String args[]) throws IOException {

        Map<String, String> map = new HashMap<String, String>();
        map.put("account", "");
        map.put("password", "");

        HttpClient client = getConnection();
        HttpUriRequest post = getRequestMethod(map, "http://cnivi.com.cn/login", "post");
        HttpResponse response = client.execute(post);

        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String message = EntityUtils.toString(entity, "utf-8");
            System.out.println(message);
        } else {
            System.out.println("请求失败");
        }
    }


}
