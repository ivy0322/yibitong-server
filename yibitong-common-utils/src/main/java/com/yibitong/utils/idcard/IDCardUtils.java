package com.yibitong.utils.idcard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * ClassName：IDCardUtils
 * Description：身份证工具类
 * Author：叶孤城
 * Created：2017/3/28
 */
public class IDCardUtils {

    // 加权因字数
    private static final int[] WI = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

    // 代码
    private static final char[] CODE = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };

    /**
     * 检查身份证是否合法
     * @param card 身份证号码
     * @return 是/否
     */
    public static boolean verifi(String card) {
        if (card.length() == 15 && Pattern.matches("^\\d{15}$", card)) {
            card = card15$18(card);
        }
        if (card.length() == 18 && isDate(card)) {
            card = card.toUpperCase();
            if (Pattern.matches("^\\d{17}[xX]|\\d{18}$", card)) {
                char[] chars = card.toCharArray();
                int si = 0;
                for (int i = 0; i < 17; i++) {
                    si += (chars[i] - '0') * WI[i];
                }
                return chars[17] == CODE[si % 11];
            }
            return false;
        }
        return false;
    }

    private static boolean isDate(String card) {
        String y = card.substring(6, 10);
        String m = card.substring(10, 12);
        String d = card.substring(12, 14);
        String date = y + "-" + m + "-" + d;
        Pattern p = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        return p.matcher(date).matches();
    }

    /**
     * 身份证15位转18位
     * @param $15
     * @return 18位身份证号码
     */
    public static String card15$18(String $15) {
        try {
            if ($15.length() == 15) {
                int si = 0;
                StringBuffer $18 = new StringBuffer();
                $18.append($15.substring(0, 6));
                $18.append("19");
                $18.append($15.substring(6, 15));
                for (int i = 0; i < 17; i++) {
                    si += ($18.charAt(i) - '0') * WI[i];
                }
                $18.append(CODE[si % 11]);
                return $18.toString();
            }
        } catch (Exception ex) {
            return null;
        }
        return $15;
    }

    //根据身份证号提取出生日期及性别
    public static Map<String, Object> getCardBirthDate(String id_card){
        Map<String, Object> map=new HashMap<String, Object>();
        String newidcard18="";
        int sex=0;
        if(id_card.length()==15){
            newidcard18=IDCardUtils.card15$18(id_card);
        }else{
            newidcard18=id_card;
        }
        // 获取性别
        String id17 = newidcard18.substring(16, 17);
        if (Integer.parseInt(id17) % 2 != 0) {
            sex = 1; //男
        } else {
            sex=2;  //女
        }
        // 获取出生日期
        String yy = newidcard18.substring(6, 10);
        String mm = newidcard18.substring(10, 12);
        String dd = newidcard18.substring(12, 14);
        String birthday=yy.concat("-").concat(mm).concat("-").concat(dd);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date birth_date = null;
        try {
            birth_date = simpleDateFormat.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int age = DateUtils.getYearDiff(birth_date,new Date());
        map.put("sex", sex);
        map.put("birth_date", birth_date);
        map.put("age", age);
        return map;
    }


    public static void main(String[] args) {
        System.out.println(getCardBirthDate("410323199605163020"));
    }
}
