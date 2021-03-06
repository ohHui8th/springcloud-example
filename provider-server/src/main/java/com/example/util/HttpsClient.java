package com.example.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpsClient {

    public static String trim(String str) {
        if (str == null) {
            return null;
        }
        return str.trim();
    }

    public static String getStringByObj(Object obj) {
        if (obj == null || "".equals((obj + "").trim()) || "undefined".equals((obj + "").trim()) || "null".equals((obj + "").trim())) {
            return "";
        }
        return trim(obj + "");
    }

    public static boolean isNotEmptyObject(Object object) {
        if (object == null || "".equals((object + "").trim()) || "null".equals((object + "").trim()) || "undefined".equals((object + "").trim())) {
            return false;
        } else {
            return true;
        }
    }

    public static String doPost(String jsonstr, String charset, Map map){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = new SSLClient();
            httpPost = new HttpPost(getStringByObj(map.get("url")));

            if(isNotEmptyObject(map.get("C-DynAmic-Password-Foruser"))){
                httpPost.addHeader("C-DynAmic-Password-Foruser", getStringByObj(map.get("C-DynAmic-Password-Foruser")));
            }
            if(isNotEmptyObject(map.get("C-Business-Id"))){
                httpPost.addHeader("C-Business-Id", getStringByObj(map.get("C-Business-Id")));
            }
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("C-App-Id", getStringByObj(map.get("C-App-Id")));
            httpPost.addHeader("Referer", getStringByObj(map.get("Referer")));
            httpPost.addHeader("C-Tenancy-Id", getStringByObj(map.get("C-Tenancy-Id")));

            StringEntity se = new StringEntity(jsonstr, Charset.forName("UTF-8"));
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    public static String doGet(Map<String, Object> paramMap) {
        HttpClient httpClient = null;
        HttpResponse response = null;
        String result = "";
        try {
            // ??????httpGet??????????????????
            httpClient = new SSLClient();
            HttpGet httpGet = new HttpGet(getStringByObj(paramMap.get("url")));
            // ??????????????????????????????
            httpGet.setHeader("C-App-Id", getStringByObj(paramMap.get("C-App-Id")));
            httpGet.setHeader("C-Business-Id", getStringByObj(paramMap.get("C-Business-Id")));
            httpGet.setHeader("C-Dynamic-Password", getStringByObj(paramMap.get("C-Dynamic-Password")));
            httpGet.setHeader("C-Tenancy-Id", getStringByObj(paramMap.get("C-Tenancy-Id")));
            httpGet.setHeader("Referer", getStringByObj(paramMap.get("Referer")));
            // ????????????????????????
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// ??????????????????????????????
                    .setConnectionRequestTimeout(35000)// ??????????????????
                    .setSocketTimeout(60000)// ????????????????????????
                    .build();
            // ???httpGet??????????????????
            httpGet.setConfig(requestConfig);
            try {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // ??????get????????????????????????
            response = httpClient.execute(httpGet);
            // ????????????????????????????????????
            HttpEntity entity = response.getEntity();
            // ??????EntityUtils??????toString?????????????????????????????????
            result = EntityUtils.toString(entity,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
