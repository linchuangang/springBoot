package com.example.Hbase.enums;

/**
 * Created by Administrator on 2017/12/15.
 */

/**
 * 审计操作对象枚举类
 */
public enum AuditTypeEnum {
    QUALITY(1, "0001"),
    WAREHOUSR(2, "0002"),
    PANEL(3, "0003"),
    BOARD(4, "0004"),
    STOP(5, "0005"),
    USER(6, "0006"),
    ROLE(7, "0007"),
    ROLEMENU(8, "0008"),
    JOB(9, "0009"),
    PARTS(10, "0010"),
    DEVICE(11, "0011"),
    TAG(12, "0012"),
    TAGRELATION(13, "0013"),
    PARTSDELIVER(14, "0014"),
    FACTORY(15, "0015"),
    SHIFT(16, "0016"),
    ALARM(17, "0017"),
    HMI(18, "0018"),
    REASON(19, "0019"),
    SCHEDULEJOB(20, "0020");


    AuditTypeEnum(int key, String code) {
        this.key = key;
        this.code = code;

    }

    int key;

    String code;

    public static AuditTypeEnum valueOf(Integer type) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case 1:
                return QUALITY;
            case 2:
                return WAREHOUSR;
            case 3:
                return PANEL;
            case 4:
                return BOARD;
            case 5:
                return STOP;
            case 6:
                return USER;
            case 7:
                return ROLE;
            case 8:
                return ROLEMENU;
            case 9:
                return JOB;
            case 10:
                return PARTS;
            case 11:
                return DEVICE;
            case 12:
                return TAG;
            case 13:
                return TAGRELATION;
            case 14:
                return PARTSDELIVER;
            case 15:
                return FACTORY;
            case 16:
                return SHIFT;
            case 17:
                return ALARM;
            case 18:
                return HMI;
            case 19:
                return REASON;
            case 20:
                return SCHEDULEJOB;
            default:
                return null;
        }
    }

    public String getText() {
        switch (this) {
            case FACTORY:
                return "工厂";
            case SHIFT:
                return "出勤";
            case PANEL:
                return "图表";
            case BOARD:
                return "面板";
            case STOP:
                return "停机";
            case USER:
                return "用户";
            case ROLE:
                return "角色";
            case ROLEMENU:
                return "角色权限";
            case JOB:
                return "任务";
            case PARTS:
                return "零件";
            case DEVICE:
                return "设备";
            case TAG:
                return "分组";
            case TAGRELATION:
                return "分组设备";
            case PARTSDELIVER:
                return "排产管理";
            case QUALITY:
                return "质量";
            case WAREHOUSR:
                return "仓库";
            case ALARM:
                return "报警";
            case HMI:
                return "HMI";
            case REASON:
                return "停机原因";
            case SCHEDULEJOB:
                return "派单";
            default:
                return "未知";
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
