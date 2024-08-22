package com.axr.starrybackend.ws.pojo;

import lombok.Data;

@Data
public class ResultMessage {
    private Integer isSystem;
    private Boolean isReply;
    private String message;
}
