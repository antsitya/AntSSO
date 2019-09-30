package com.ant.sso.Utils;

import com.ant.sso.Common.AntException;
import com.ant.sso.Common.AntHttpResult;
import com.ant.sso.Common.AntResponseCode;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * HTTP请求工具类
 */
public class HttpUtils {
    private static final String ENCODING = "UTF-8";// 编码格式。发送编码格式统一用UTF-8
    private static final int CONNECT_TIMEOUT = 6000;// 设置连接超时时间，单位毫秒。
    private static final int SOCKET_TIMEOUT = 6000;// 请求获取数据的超时时间(即响应时间)，单位毫秒。

    /**
     * 发送get请求；不带请求头和请求参数
     */
    public static AntHttpResult doGet(String url) throws Exception {
        return doGet(url, null, null);
    }

    /**
     * 发送get请求；带请求参数
     */
    public static AntHttpResult doGet(String url, Map<String, String> params) throws Exception {
        return doGet(url, null, params);
    }

    /**
     * 发送get请求；带请求头和请求参数
     */
    public static AntHttpResult doGet(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();// 创建httpClient对象
        URIBuilder uriBuilder = new URIBuilder(url);// 创建访问的地址
        if (params != null) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }
        HttpGet httpGet = new HttpGet(uriBuilder.build()); // 创建http对象
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        CloseableHttpResponse httpResponse =packageHeader(headers, httpGet); // 设置请求头返回空的httpResponse对象
        httpGet.setConfig(RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build());
        try {
            return getHttpClientResult(httpResponse, httpClient, httpGet);// 执行请求并获得响应结果
        } finally {
            release(httpResponse, httpClient);// 释放资源
        }
    }

    /**
     * 发送post请求；不带请求头和请求参数
     */
    public static AntHttpResult doPost(String url) throws Exception {
        return doPost(url, null, null);
    }

    /**
     * 发送post请求；带请求参数
     */
    public static AntHttpResult doPost(String url, Map<String, String> params) throws Exception {
        return doPost(url, null, params);
    }

    /**
     * 发送post请求；带请求头和请求参数
     */
    public static AntHttpResult doPost(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();// 创建httpClient对象
        HttpPost httpPost = new HttpPost(url);// 创建http对象
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        httpPost.setConfig(RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build());
        CloseableHttpResponse httpResponse =packageHeader(headers, httpPost); // 设置请求头返回空的httpResponse对象
        packageParam(params, httpPost);// 封装请求参数
        try {
            return getHttpClientResult(httpResponse, httpClient, httpPost); // 执行请求并获得响应结果
        } finally {
            release(httpResponse, httpClient);// 释放资源
        }
    }

    /**
     * 发送put请求；不带请求参数
     */
    public static AntHttpResult doPut(String url) throws Exception {
        return doPut(url);
    }

    /**
     * 发送put请求；带请求参数
     */
    public static AntHttpResult doPut(String url, Map<String, String> params) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        httpPut.setConfig(RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build());
        packageParam(params, httpPut);
        CloseableHttpResponse httpResponse = null;
        try {
            return getHttpClientResult(httpResponse, httpClient, httpPut);
        } finally {
            release(httpResponse, httpClient);
        }
    }

    /**
     * 发送delete请求；不带请求参数
     */
    public static AntHttpResult doDelete(String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setConfig(RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build());
        CloseableHttpResponse httpResponse = null;
        try {
            return getHttpClientResult(httpResponse, httpClient, httpDelete);
        } finally {
            release(httpResponse, httpClient);
        }
    }

    /**
     * 发送delete请求；带请求参数
     */
    public static AntHttpResult doDelete(String url, Map<String, String> params) throws Exception {
        if (params == null) {
            params = new HashMap<String, String>();
        }
        params.put("_method", "delete");
        return doPost(url, params);
    }

    /**
     *  多文件上传
     */
    public static AntHttpResult upload(String url, List<MultipartFile> multipartFiles, String fileParName, Map<String, Object> params, int timeout){
        AntHttpResult antHttpResult=new AntHttpResult();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result;
        params.put("upload_file_name",fileParName);
        try {
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(java.nio.charset.Charset.forName("UTF-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            String fileName = null;
            for(MultipartFile multipartFile:multipartFiles){
                fileName = multipartFile.getOriginalFilename();
                builder.addBinaryBody(fileParName, multipartFile.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
            }
            ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);//解决中文乱码
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if(entry.getValue() == null) continue;
                builder.addTextBody(entry.getKey(), entry.getValue().toString(), contentType);// 类似浏览器表单提交，对应input的name和value
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).build();// 设置连接超时时间
            httpPost.setConfig(requestConfig);
            HttpEntity responseEntity = response.getEntity();
            antHttpResult.setCode(response.getStatusLine().getStatusCode());
            if (responseEntity != null) {// 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, java.nio.charset.Charset.forName("UTF-8"));
                antHttpResult.setRes(result);
            }
        } catch (Exception e) {
            Writer w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            throw new AntException(AntResponseCode.HTTP_UPLOAD_EXCEPTION);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return antHttpResult;
    }

    /**
     * Description: 封装请求头
     */
    public static CloseableHttpResponse packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
        /*httpPost.setHeader("Cookie", "");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
		httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");*/
        if (params != null) {// 封装请求头
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                httpMethod.setHeader(entry.getKey(), entry.getValue());// 设置到请求头到HttpRequestBase对象中
            }
        }
        return null;
    }

    /**
     * Description: 封装请求参数
     */
    public static void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod)
            throws UnsupportedEncodingException {
        if (params != null) {// 封装请求参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));// 设置到请求的http对象中
        }
    }

    /**
     * Description: 获得响应结果
     */
    public static AntHttpResult getHttpClientResult(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws Exception {
        httpResponse = httpClient.execute(httpMethod);// 执行请求
        if (httpResponse != null && httpResponse.getStatusLine() != null) {// 获取返回结果
            String content = "";
            if (httpResponse.getEntity() != null) {
                content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
            }
            return new AntHttpResult(httpResponse.getStatusLine().getStatusCode(), content);
        }
        return new AntHttpResult(AntResponseCode.HTTP_SERVER_EXCEPTION);
    }

    /**
     * Description: 释放资源
     */
    public static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
        if (httpResponse != null) {// 释放资源
            httpResponse.close();
        }
        if (httpClient != null) {
            httpClient.close();
        }
    }
}
