package com.axr.starrybackend.ws.pojo;

public class MessageUtils {
    public static ResultMessage success(Integer isSystem, Boolean isReply, String message) {
        ResultMessage result = new ResultMessage();
        result.setIsSystem(isSystem);
        result.setIsReply(isReply);
        result.setMessage(message);
        return result;
    }

    public static ResultMessage error(String message) {
        ResultMessage result = new ResultMessage();
        result.setIsSystem(-1);
        result.setMessage(message);
        return result;
    }
}
