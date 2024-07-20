package com.axr.starrybackend.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Algorithm {
    public static Set<String> parseTags(String tags) {
        // 解析标签字符串为 Set
        if (StringUtils.isEmpty(tags)) {
            return Collections.emptySet();
        }
        return new HashSet<>(Arrays.asList(tags.replaceAll("[\\[\\]\" ]", "").split(",")));
    }

    public static int calculateSimilarity(Set<String> tags1, Set<String> tags2) {
        // 计算标签交集的大小作为相似度
        Set<String> intersection = new HashSet<>(tags1);
        intersection.retainAll(tags2);
        return intersection.size();
    }
}
