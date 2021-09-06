package com.gaokao.common.utils;

import org.springframework.util.DigestUtils;

public class MD5Utils {
    public static String MD5Encode(String origin)
    {
        return DigestUtils.md5DigestAsHex(origin.getBytes());
    }

}
