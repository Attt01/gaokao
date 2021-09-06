package com.gaokao.common.utils;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class CreateSignUtils {
    public static String createSign(SortedMap<String, String> packageParams, String paykey) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> es = packageParams.entrySet();
        for (Map.Entry<String, String> entry : es) {
            String k = entry.getKey();
            String v = entry.getValue();
            if ((null != v) && (!("".equals(v))) && (!("sign".equals(k))) && (!("key".equals(k))) && (!("undefined".equals(k)))) {
                sb.append(k).append("=").append(v).append("&");
            }
        }
        sb.append("key=").append(paykey);
        System.out.println(sb);
        return MD5Utils.MD5Encode(sb.toString()).toUpperCase();
    }
}
