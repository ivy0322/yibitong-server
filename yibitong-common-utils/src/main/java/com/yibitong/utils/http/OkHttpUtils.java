package com.yibitong.utils.http;

import com.squareup.okhttp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 包      名：  com.newzqxq.common.amap
 * 创 建 人：   寻欢·李
 * 创建时间：  2016/12/16 0016 16:08
 * 修 改 人：
 * 修改日期：
 *
 * HTTP请求工具类
 */
public class OkHttpUtils {
    static Logger logger = LoggerFactory.getLogger(OkHttpUtils.class);

    public String SyncGet(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                logger.error("请求 " + url + " 发生错误，错误信息： " + response);
            }

//            Headers responseHeaders = response.headers();
//            for (int i = 0; i < responseHeaders.size(); i++){
//                System.out.println("HeadersInfo："+responseHeaders.name(i) + "：" + responseHeaders.value(i));
//            }

            String info = response.body().string();

//            logger.info("请求数据成功，返回信息："+info);
            return  info;
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("HTTP请求错误，错误信息："+e.getMessage());
            return null;
        }
    }


    public void SyncPost(String url) {
        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain");
        String postBody = "Hello World";

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_TEXT, postBody))
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                System.out.println("服务器端错误: " + response);
            }

            System.out.println(response.body().toString());
        }catch (Exception e){
            System.out.println("请求服务错误。");
        }
    }
}
