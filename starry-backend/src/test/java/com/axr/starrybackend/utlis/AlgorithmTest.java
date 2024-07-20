package com.axr.starrybackend.utlis;

import com.axr.starrybackend.utils.Algorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

//@SpringBootTest
public class AlgorithmTest {
    @Test
    public void test() {
        // 测试标签解析
        String s1 = "[\"大三\",\"Java\",\"跑步\",\"读书\"]";
        String s2 = "[\"大四\",\"JavaScript\",\"游泳\",\"电影\"]";
        String s3 = "[\"大二\",\"Python\",\"篮球\",\"音乐\"]";
        Set<String> st1 = Algorithm.parseTags(s1);
        Set<String> st2 = Algorithm.parseTags(s2);
        Set<String> st3 = Algorithm.parseTags(s3);
        System.out.println(st1);
        System.out.println(st2);
        System.out.println(st3);
    }
}
