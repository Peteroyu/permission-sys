package com.xmj.util;

import java.util.HashMap;
import java.util.Map;

public class AjaxMessage {
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    public static AjaxMessage success() {
        AjaxMessage ajaxMessage = new AjaxMessage();
        ajaxMessage.setCode(200);
        return ajaxMessage;
    }

    public static AjaxMessage error() {
        AjaxMessage ajaxMessage = new AjaxMessage();
        ajaxMessage.setCode(100);
        return ajaxMessage;
    }

    public AjaxMessage addData(String name, Object obj) {
        getData().put(name, obj);
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public AjaxMessage addCode(Integer code) {
        this.code = code;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AjaxMessage addMessage(String message) {
        this.message = message;
        return this;
    }
}
