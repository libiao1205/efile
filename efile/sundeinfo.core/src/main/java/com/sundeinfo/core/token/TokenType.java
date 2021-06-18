package com.sundeinfo.core.token;

public enum TokenType{

    UNKNOWN(0,"未知令牌类型"),

    ALL(1,"默认全项目"),

    ROBOT(2,"机器账户"),

    GENERAL(3, "通常令牌"),

    ADMIN(4, "管理员用令牌"),

    THIRD(5, "商用令牌"),

    SERVER(6, "服务器用令牌"),

    DEBUG(7, "开发用令牌"),

    MAINTENANCE(8, "维护令牌");

    private final int type;

    private final String value;

    TokenType(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return this.type;
    }


    public String value() {
        return this.value;
    }


    public static TokenType valueOf(int type) {
        switch (type) {
            case 0:
                return UNKNOWN;
            case 1:
                return ALL;
            case 2:
                return ROBOT;
            case 3:
                return GENERAL;
            case 4:
                return ADMIN;
            case 5:
                return THIRD;
            case 6:
                return SERVER;
            case 7:
                return DEBUG;
            case 8:
                return MAINTENANCE;
            default:
                return null;
        }
    }

    public static TokenType judgeType(String value){
        switch(value)
        {
            case "ALL":
                return TokenType.ALL;
            case "ROBOT":
                return TokenType.ROBOT;
            case "ADMIN":
                return TokenType.ADMIN;
            case "GENERAL":
                return TokenType.GENERAL;
            case "SERVER":
                return TokenType.SERVER;
            case "DEBUG":
                return TokenType.DEBUG;
            case "THIRD":
                return TokenType.THIRD;
            case "MAINTENANCE":
                return TokenType.MAINTENANCE;
            default:
                return TokenType.UNKNOWN;
        }
    }
}
