package com.cdhouse.utils;

import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

public class HttpClientTest {

    //这个是为了跳过ssl验证的
    public static SSLConnectionSocketFactory buildSSLConnectionSocketFactory(){
        try {
            return new SSLConnectionSocketFactory(createIgnoreVersifySSL());
        }catch (Exception e){
            e.printStackTrace();
        }
        return SSLConnectionSocketFactory.getSocketFactory();
    }
    public static SSLContext createIgnoreVersifySSL(){
        X509TrustManager trustManager=new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        SSLContext sslContext=null;
        try{
            sslContext=SSLContext.getInstance("SSLv3");
            sslContext.init(null,new TrustManager[]{trustManager},null);
        }catch ( Exception e){
            e.printStackTrace();
        }

        return sslContext;
    }
    //这里是给一个url生成请求builder类，设置了请求的方法和post时所要发出去的数据
    public static RequestBuilder selectRequestMethod(String method,Map<String,String>map){
        if(method != null || method.equalsIgnoreCase("GET")){
            return RequestBuilder.get();
        }else if(method.equalsIgnoreCase("POST")){
            RequestBuilder requestBuilder= RequestBuilder.post();
            if(map!=null && map.size()>0){
                for(Map.Entry<String,String>mapentry:map.entrySet())
                    requestBuilder.addParameter(mapentry.getKey(),mapentry.getValue());
            }
        } else if (method.equalsIgnoreCase("HEAD")) {
            return RequestBuilder.head();
        } else if (method.equalsIgnoreCase("PUT")) {
            RequestBuilder.put();
        }else if(method.equalsIgnoreCase("DELETE")){
            RequestBuilder.delete();
        }else if(method.equalsIgnoreCase("TRACE")){
            RequestBuilder.trace();
        }
        throw new IllegalArgumentException("illegal http method"+method);
    }
    //主要流程
    public static void main(String[] args) throws Exception {

        String doman="https://www.baidu.com";
        String useragent="Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:52.0) Gecko/20100101 Firefox/52.0";

        //生成httpclient，根据相关的配置对httpclient进行配置
        HttpClientBuilder httpClientBuilder= HttpClients.custom();
        //设置httpclient的链接池管理类
        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create().
                register("http", PlainConnectionSocketFactory.INSTANCE).
                register("https", buildSSLConnectionSocketFactory()).build();

        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(reg);
        //连接池的相关配置
        SocketConfig config= SocketConfig.custom().setSoTimeout(3000).
                setSoKeepAlive(true).setTcpNoDelay(true).build();
        manager.setDefaultSocketConfig(config);
        //控制最大连接数
        manager.setDefaultMaxPerRoute(1000);
        //为连接池配置管链接理器
        httpClientBuilder.setConnectionManager(manager);
        //配置user-agent欺骗服务器
        httpClientBuilder.setUserAgent(useragent);
        //为请求添加压缩属性
        httpClientBuilder.addInterceptorFirst(new HttpRequestInterceptor() {
            @Override
            public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
                if(!httpRequest.containsHeader("Accept-Encoding")){
                    httpRequest.addHeader("Accept-Encoding","gzip");
                }
            }
        });
        //httpclientbuilder配置链接设置
        httpClientBuilder.setDefaultSocketConfig(config);

        //设置重新请求使用的类
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(
                3,true
        ));
        //配置cookie位生成的http请求都添加这个cookie，也是为了欺骗服务器
        CookieStore cookieStore=new BasicCookieStore();
        Map<String,String> cookie=new HashMap();
        for(Map.Entry<String,String> cookieEntry:cookie.entrySet()){
            BasicClientCookie clientCookie=new BasicClientCookie(cookieEntry.getKey(),cookieEntry.getValue());
            clientCookie.setDomain(doman);
            cookieStore.addCookie(clientCookie);
        }
        //位即将生成的httpclient配置cookie
        httpClientBuilder.setDefaultCookieStore(cookieStore);
        //为httpclient设置重定向时的处理方法
        httpClientBuilder.setRedirectStrategy(new LaxRedirectStrategy(){
            public HttpUriRequest getRedirect(HttpRequest request, HttpResponse response, HttpContext context) throws ProtocolException {
                URI uri = getLocationURI(request, response, context);
                String method = request.getRequestLine().getMethod();
                if ("post".equalsIgnoreCase(method)) {
                    try {
                        HttpRequestWrapper httpRequestWrapper = (HttpRequestWrapper) request;
                        httpRequestWrapper.setURI(uri);
                        httpRequestWrapper.removeHeaders("Content-Length");
                        return httpRequestWrapper;
                    } catch (Exception e) {
                    }
                    return new HttpPost(uri);
                } else {
                    return new HttpGet(uri);
                }
            }
        });

        //生成我们配置好的httpclient类
        CloseableHttpClient closeableHttpClient=httpClientBuilder.build();
        //创建httpurirequest
        String url="https://www.baidu.com";
        String methods="GET";
        //为httpurirequest添加请求头
        RequestBuilder requestBuilder=selectRequestMethod(methods,new HashMap<String, String>());
        requestBuilder.setUri(url);
        Map<String,String> headers=new HashMap();
        headers.put("Accept-Encoding","gzip");
        if(headers!=null){
            for (Map.Entry<String, String> entry :
                    headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(),entry.getValue());
            }
        }
        //配置httpurirequest的链接设置
        RequestConfig.Builder requestConfig=RequestConfig.custom().
                setConnectionRequestTimeout(3000).
                setSocketTimeout(3000).
                setConnectTimeout(3000).
                setCookieSpec(CookieSpecs.BEST_MATCH);
        requestBuilder.setConfig(requestConfig.build());
        //利用builder生成具体的httpurirequest
        HttpUriRequest httpUriRequest=requestBuilder.build();





        //利用我们的httpclient 执行配置好的请求。
        CloseableHttpResponse response=closeableHttpClient.execute(httpUriRequest);
        //response.getEntity().getContent();

        //获取返回头
        response.getAllHeaders();
        System.out.println(response.getAllHeaders());

        //输出返回头
        /*for (Header responseheader :
                response.getAllHeaders()) {
            System.out.println(responseheader.getName()+" -->  "+responseheader.getValue());
        }*/
        //输出返回内容
        InputStream inputStream=response.getEntity().getContent();
        int k;
        StringBuffer sbf=new StringBuffer();
        byte[ ]bytes=new byte[1024];
        while((k=inputStream.read(bytes))!=-1){
            sbf.append(new String(bytes,0,k));
        }
        System.out.println(sbf);
        //while (inputStream.read(new byte[]))
    }

}
