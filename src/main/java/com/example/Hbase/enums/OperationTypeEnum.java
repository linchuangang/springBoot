package com.example.Hbase.enums;

/**
 * Created by Administrator on 2017/12/19.
 */

/**
 * 审计操作类型枚举类
 */
public enum OperationTypeEnum {
    ADD(1, "1001"),
    MODIFY(2, "1002"),
    REMOVE(3, "1003");

    OperationTypeEnum(int key, String code) {
        this.key = key;
        this.code = code;
    }

    int key;
    String code;

    public String getText() {
        switch (this) {
            case ADD:
                return "添加";
            case MODIFY:
                return "修改";
            case REMOVE:
                return "删除";
            default:
                return "未知";
        }
    }

    public static OperationTypeEnum valueOf(Integer type) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case 1:
                return ADD;
            case 2:
                return MODIFY;
            case 3:
                return REMOVE;
            default:
                return null;
        }

    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
