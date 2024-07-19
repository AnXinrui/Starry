package com.axr.starrybackend.controller;

import com.axr.starrybackend.common.ErrorCode;
import com.axr.starrybackend.exception.BusinessException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173", originPatterns = "*", allowCredentials = "true")
public class TestController {
    @GetMapping("/test")
    public String test() {
//        throw new BusinessException(ErrorCode.NULL_ERROR);
        return "Hello World!";
    }
}
