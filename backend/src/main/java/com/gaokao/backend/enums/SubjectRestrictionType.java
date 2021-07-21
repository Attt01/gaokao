package com.gaokao.backend.enums;

/**
 * @author attack204
 * date:  2021/7/21
 * email: 757394026@qq.com
 */
public enum SubjectRestrictionType {

    ONEMUST(1, "必选一科"),

    TWOMUST(2, "必选两科"),

    THREEMUST(3, "必选三科"),

    ONEOFTWO(4, "二选一"),

    ONEOFTHREE(5, "三选一"),

    TWOOFTHREE(6, "三选二");

    private final int code;

    private final String desc;

    SubjectRestrictionType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

}
