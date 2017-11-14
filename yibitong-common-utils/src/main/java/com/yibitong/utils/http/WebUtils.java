package com.yibitong.utils.http;

import com.alibaba.fastjson.JSON;
import com.yibitong.common.RequestParams;
import com.yibitong.utils.encrypt.MD5Utils;
import com.yibitong.utils.stringUtils.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 包      名：  com.newzqxq.demo.feign
 * 创 建 人：   寻欢 · 李
 * 创建时间：  2017/7/14 12:14
 * 修 改 人：
 * 修改日期：
 *
 * WEB工具类
 */
public class WebUtils {

    protected static Logger logger = LoggerFactory.getLogger(WebUtils.class);

    /**
     * 获取上下文URL全路径
     * @param request
     * @return
     */
    public static String getContextPath(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        sb.append(request.getScheme()).append("://");
        sb.append(request.getServerName());
        if(request.getServerPort() !=80){//80端口无需拼接
            sb.append(":").append(request.getServerPort());
        }
        sb.append(request.getContextPath());
        String path = sb.toString();
        return path;
    }

    /**
     * 获取上次访问的URL链接
     * @param request
     * @return
     */
    public static String getLastURL(HttpServletRequest request) {
        String path = getContextPath(request);
        String referer = request.getHeader("referer");
        if(StrUtils.notEmpty(referer)){
            int index = path.length();
            return referer.substring(index);
        }else{
            return null;
        }
    }



    /**
     * 获取客户端真实ip
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StrUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StrUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StrUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 获取request请求里的参数
     * @param request
     * @return
     */
    public static Map<String,String> getParams(HttpServletRequest request){
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决
            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put(name, valueStr);
        }
        return params;
    }

    /**
     * 去掉url中的路径,留下请求参数部分
     * @param url
     * @return
     */
    private static String truncateUrlPage(String url) {
        String strAllParam = null;
        //url = url.replace("\"", "").trim();
        String[] arrSplit = url.split("[?]");
        if (url.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }else{
                strAllParam = url;
            }
        }
        return strAllParam;
    }

    /**
     * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123",解析出Action:del,id:123存入map中
     * @param url url地址
     * @return
     */
    public static Map<String, String> parseURLParam(String url) {
        Map<String, String> mapRequest = new HashMap<String, String>();
        String strUrlParam = truncateUrlPage(url);
        if (StrUtils.isEmpty(strUrlParam)) {
            return mapRequest;
        }
        // 每个键值为一组
        String[] arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            int index = strSplit.indexOf("=");
            String key = strSplit.substring(0, index);
            String value = strSplit.substring(index+1);
            if(StrUtils.notEmpty(key)){
                mapRequest.put(key, value);
            }
        }
        return mapRequest;
    }

    /**
     * 根据request和sessionKey获取session（如果调用处能提供request时则可调用此方法，性能高）.
     * @param request
     * @param sessionKey
     * @return
     */
    public static Object getSession(HttpServletRequest request, String sessionKey){
        return request.getSession().getAttribute(sessionKey);
    }

    /**
     * 保存Session值
     * @param request
     * @param sessionKey
     * @param sessionValue
     */
    public static void putSession(HttpServletRequest request, String sessionKey, Object sessionValue){
        request.getSession().setAttribute(sessionKey, sessionValue);
    }


    /**
     * 根据session名称删除session值
     * @param request
     * @param sessionKey
     */
    public static void removeSession(HttpServletRequest request, String sessionKey){
        request.getSession().removeAttribute(sessionKey);
    }

    /**
     * 添加Cookie值（切记，为防止XSS劫持Cookie攻击，在向客户端返回Cookie值时记得设置HttpOnly）
     * @param response
     * @param name cookie的名称
     * @param value cookie的值
     * @param maxAge cookie存放的时间(以秒为单位,假如存放三天,即3*24*60*60; 如果值为0, cookie将随浏览器关闭而清除)
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    /**
     * 根据某一Cookie名获取Cookie的值
     * @param request
     * @param name Cookie的名称
     * @return
     */
    public static String getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = cookieMap.get(name);
            return cookie.getValue();
        } else {
            return null;
        }
    }

    /**
     * 从request中读取所有Cookie值,放入Map中
     * @param request
     * @return
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (int num = 0; num < cookies.length; num++) {
                cookieMap.put(cookies[num].getName(), cookies[num]);
            }
        }
        return cookieMap;
    }

    /**
     * 验证接口参数签名
     * @param request
     * @return
     */
    public static boolean valiSignature(HttpServletRequest request) {
        //1、获取请求参数
        Map<String, String> paramMap = RequestParams.getParams(request);
        logger.info("接口参数paramMap：" + JSON.toJSONString(paramMap));
        String paramsStr = RequestParams.getOrderParamString(paramMap);//排序后的参数串
        logger.info("接口参数paramsStr：" + paramsStr);
        String signature = request.getHeader("signature");// 签名信息
        String public_key = "040CD66A84025B94CEAC3EC1B49C92D0";// public_key为“重庆纵情向前科技”的md5值
        String app_key = request.getHeader("app_key");
        String source = request.getHeader("source");//用户来源（安卓：1，苹果：2）
        String client_id = request.getHeader("client_id");//客户端唯一标识
        String channel = request.getHeader("channel");//渠道来源
        String app_version = request.getHeader("app_version");//app版本号
        String timestamp = request.getHeader("timestamp");//时间戳
        if(StrUtils.isEmpty(signature)){//签名为空
            return false;
        }
        //2、后端签名
        String msg = public_key + app_key + source + channel + client_id + app_version + paramsStr + timestamp;
        logger.info("msg：" + msg);
        logger.info("app签名信息：signature：" + signature);
        String local_signature = MD5Utils.encryption(msg);
        logger.info("后端签名信息：local_signature：" + local_signature);
        //3、校验签名是否一致
        return signature.equalsIgnoreCase(local_signature);
    }

    /**
     * 输出响应信息
     * @param response
     * @param result
     */
    public static void outPrint(HttpServletResponse response, String result){
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter pw = response.getWriter();
            pw.write(result);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印请求Request信息
     * @param request
     */
    public static void logRequest(HttpServletRequest request){
        logger.info("=========== this is debug begin =============");
        logger.info("RequestURI():" + request.getRequestURI());
        logger.info("QueryString():" + request.getQueryString());
        logger.info("------------- header -------------");
        for(java.util.Enumeration<String > ss =  request.getHeaderNames();ss.hasMoreElements();){
            String s = ss.nextElement();
            logger.info(s+":"+request.getHeader(s));
        }

        logger.info("------------- parameter -------------");
        for( java.util.	Enumeration<String > pns = request.getParameterNames(); pns.hasMoreElements();){
            String s = pns.nextElement();
            logger.info(s+":"+request.getParameter(s));
        }

        logger.info("------------- sessionid:"+request.getSession().getId());
        for( java.util.	Enumeration<String > pns =request.getSession().getAttributeNames(); pns.hasMoreElements();){
            String s = pns.nextElement();
            logger.info(s+":"+request.getSession().getAttribute(s));
        }

        logger.info("=========== this is debug end =============");
    }


    /**
     * 是否为局域网ip
     * @param ip
     * @return
     */
    public static boolean IsLanIp(String ip) {
        String reg = "(10|172|192)\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})";//正则表达式=。 =、懒得做文字处理了、
        Pattern p = Pattern.compile(reg);
        Matcher matcher = p.matcher(ip);
        return matcher.find();
    }
}
