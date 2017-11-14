package com.yibitong.utils.http;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * ClassName：HttpUtils
 * Description：
 * Author：叶孤城
 * Created：2017/4/8
 */
public class HttpUtils {

    private static final int TIMEOUT_IN_MILLIONS = 300000;//30秒

    public static final String DEFAULT_CHARSET = "utf-8";

    public interface CallBack {
        void onRequestComplete(String result);
    }

    /**
     * 异步的Get请求
     * @param urlStr
     * @param callBack
     */
    public static void doGetAsyn(final String urlStr, final CallBack callBack) {
        new Thread() {
            public void run() {
                try {
                    String result = doGet(urlStr);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }

    /**
     * 异步的Post请求
     * @param urlStr
     * @param paramMap
     * @param callBack
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static void doPostAsyn(final String urlStr, final Map paramMap, final CallBack callBack) throws Exception {
        new Thread() {
            public void run() {
                try {
                    String result = doPost(urlStr, paramMap);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            };
        }.start();
    }

    /**
     * Get请求，获得返回数据
     * @param urlStr
     * @return
     * @throws Exception
     */
    public static String doGet(String urlStr) {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];
                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
            }
            conn.disconnect();
        }
        return null;

    }

    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param paramMap 请求参数，请求参数应该是key-value 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static String doPost(String url, Map paramMap) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            // 参数转换
            String param = mapToQueryString(paramMap, DEFAULT_CHARSET);
            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            closeStream(in, out);
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param paramMap 请求参数，请求参数应该是key-value 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static String doPostJson(String url, Map paramMap) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            // 参数转换
            //String param = mapToQueryString(paramMap, DEFAULT_CHARSET);
            String param = JSON.toJSONString(paramMap);
            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            closeStream(in, out);
        }
        return result;
    }


    /**
     * 向指定 URL 发送POST方法的请求
     * @param url
     * @param param 转JSON后的数据
     * @return
     */
    public static String doPostJson(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            // 参数转换
            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            closeStream(in, out);
        }
        return result;
    }

    /**
     * map参数转String
     * @param paramMap
     * @param charSet
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static String mapToQueryString(Map paramMap, String charSet) {
        String queryString = "";
        if (paramMap != null && !paramMap.isEmpty()) {
            Set<Map.Entry> entrySet = paramMap.entrySet();
            for (Map.Entry entry : entrySet) {
                try {
                    String key = entry.getKey().toString();
                    Object value = entry.getValue();
                    List values = makeStringList(value);
                    for (Object v : values) {
                        if(charSet !=null){
                            queryString += key
                                    + "="
                                    + URLEncoder.encode(v == null ? "" : v.toString(), charSet)
                                    + "&";
                        }else{
                            queryString += key
                                    + "="
                                    + (v == null ? "" : v.toString())
                                    + "&";
                        }

                    }
                } catch (UnsupportedEncodingException e) {
                    throw new IllegalArgumentException("invalid charset : "
                            + charSet);
                }
            }
            if (queryString.length() > 0) {
                queryString = queryString
                        .substring(0, queryString.length() - 1);
            }
        }
        return queryString;
    }

    /**
     * String参数转map
     * @param queryString
     * @param charSet
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Map queryStringToMap(String queryString, String charSet) {
        if (queryString == null) {
            throw new IllegalArgumentException("queryString must be specified");
        }
        int index = queryString.indexOf("?");
        if (index > 0) {
            queryString = queryString.substring(index + 1);
        }
        String[] keyValuePairs = queryString.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String keyValue : keyValuePairs) {
            if (keyValue.indexOf("=") == -1) {
                continue;
            }
            String[] args = keyValue.split("=");
            if (args.length == 2) {
                try {
                    if(charSet !=null){
                        map.put(args[0], URLDecoder.decode(args[1], charSet));
                    }else{
                        map.put(args[0], args[1]);
                    }
                } catch (UnsupportedEncodingException e) {
                    throw new IllegalArgumentException("invalid charset : "
                            + charSet);
                }
            }
            if (args.length == 1) {
                map.put(args[0], "");
            }
        }
        return map;
    }

    /**
     * 对象转字符串集合
     * @param value
     * @return
     */
    @SuppressWarnings("rawtypes")
    private static List<String> makeStringList(Object value) {
        if (value == null) {
            value = "";
        }
        List<String> result = new ArrayList<String>();
        if (value.getClass().isArray()) {
            for (int j = 0; j < Array.getLength(value); j++) {
                Object obj = Array.get(value, j);
                result.add(obj != null ? obj.toString() : "");
            }
            return result;
        }

        if (value instanceof Iterator) {
            Iterator it = (Iterator) value;
            while (it.hasNext()) {
                Object obj = it.next();
                result.add(obj != null ? obj.toString() : "");
            }
            return result;
        }

        if (value instanceof Collection) {
            for (Object obj : (Collection) value) {
                result.add(obj != null ? obj.toString() : "");
            }
            return result;
        }

        if (value instanceof Enumeration) {
            Enumeration enumeration = (Enumeration) value;
            while (enumeration.hasMoreElements()) {
                Object obj = enumeration.nextElement();
                result.add(obj != null ? obj.toString() : "");
            }
            return result;
        }
        result.add(value.toString());
        return result;
    }

    private static void closeStream(BufferedReader in, PrintWriter out){
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
