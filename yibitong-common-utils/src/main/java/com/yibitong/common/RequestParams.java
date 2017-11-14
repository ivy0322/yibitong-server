package com.yibitong.common;

import org.apache.http.message.BasicNameValuePair;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * ClassName：RequestParams
 * Description：请求参数
 * Author：yy
 * Created：2017/11/14
 */
public class RequestParams {

    private static String contentEncoding = "UTF-8";

    /**
     * 获取request请求里的参数
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Map<String,String> getParams(HttpServletRequest request){
        Map<String, String> urlParams = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        if(requestParams !=null){
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                try {
                    valueStr = URLDecoder.decode(valueStr,contentEncoding);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                urlParams.put(name, valueStr);
            }
        }
        return urlParams;
    }

    /**
     * 参数key排序
     * @param urlParams
     * @return
     */
    public static String getOrderParamString(Map<String, String> urlParams) {
        //return URLEncodedUtils.format(getOrderParamsList(urlParams), contentEncoding);
        return getParamStr(urlParams);
    }

    private static String getParamStr(Map<String, String> parms){
        StringBuffer sb = new StringBuffer();
        try {
            ArrayList<String> aa = new ArrayList<String>(parms.keySet());
            Collections.sort(aa);
            for (String key : aa) {
                sb.append(key).append("=").append(java.net.URLEncoder.encode(parms.get(key),"UTF-8")).append("&");
            }
            if (sb.length() > 1) {
                sb.deleteCharAt(sb.length()-1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static List<BasicNameValuePair> getOrderParamsList(Map<String, String> urlParams) {
        List<BasicNameValuePair> lparams = new LinkedList<BasicNameValuePair>();
        ArrayList<String> keys = new ArrayList<String>(urlParams.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            lparams.add(new BasicNameValuePair(key, urlParams.get(key)));
        }
        return lparams;
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", "+86");
        map.put("consume_time", "29776");
        map.put("userUrgentInfoList", "[{'ad_code':110101,'ad_path':'北京市-北京市-东城区','contact_name':'阿 爸','contact_phone':'13709496014','contact_relation':1,'contact_type':1,'detail_address':'Wed ','id':''},{'ad_code':110101,'ad_path':'北京市-北京市-东城区','contact_name':'阿 姐','contact_phone':'18799122708','contact_relation':6,'contact_type':2,'detail_address':'Wer ','id':''}]");
        System.out.println(getOrderParamString(map));
    }
}
