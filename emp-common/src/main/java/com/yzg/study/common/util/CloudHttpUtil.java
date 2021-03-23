package com.yzg.study.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 第三方接口的统一工具类
 */
public class CloudHttpUtil {

    public static String   getApiClient(String gatewayUrl, String getUrl) {
        HttpClient client = HttpClients.createDefault();
        String url = gatewayUrl + getUrl;
        HttpPost get = new HttpPost(url);
        JSONObject jsonObject = null;
        try {
            HttpResponse res = client.execute(get);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                jsonObject = JSONObject.parseObject(EntityUtils.toString(res.getEntity()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null == jsonObject?"":jsonObject.toString();
    }
}
