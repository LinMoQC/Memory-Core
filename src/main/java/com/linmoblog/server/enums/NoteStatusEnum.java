package com.linmoblog.server.enums;

/**
 * 公开、私密、草稿
 */
public enum NoteStatusEnum {
    PUBLIC("public", "公开"),
    PRIVATE("private", "私密"),
    DRAFT("draft", "草稿");

    private String code;
    private String desc;

    NoteStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }


}
