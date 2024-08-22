package com.axr.starrybackend.ws.utils;

import com.axr.starrybackend.ws.pojo.ResultMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

/*
* 如果你写了一个名叫Student的类，需要通过sendObject()方法发送，那么这里就是Text<Student>
*/
public class ServerEncoder implements Encoder.Text<ResultMessage> {
 
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        // 这里不重要
    }
 
    @Override
    public void init(EndpointConfig arg0) {
        // TODO Auto-generated method stub
        // 这里也不重要
 
    }
 
    /*
    *  encode()方法里的参数和Text<T>里的T一致，如果你是Student，这里就是encode（Student student）
    */
    @Override
    public String encode(ResultMessage responseMessage) throws EncodeException {
        try {
            /*
            * 这里是重点，只需要返回Object序列化后的json字符串就行
            * 你也可以使用gosn，fastJson来序列化。
            */
            JsonMapper jsonMapper = new JsonMapper();
            return jsonMapper.writeValueAsString(responseMessage);
 
        } catch ( JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}