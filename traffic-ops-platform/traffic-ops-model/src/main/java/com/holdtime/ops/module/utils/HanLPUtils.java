package com.holdtime.ops.module.utils;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HanLPUtils {
    public static List<String> stopWordList;

    static {
        stopWordList = new ArrayList<>();
        stopWordList.add("与");
        stopWordList.add("交叉口");
    }

    public static List<String> segmentForRoad(String text) {
        text = extractChinese(text);
        if (StringUtils.isBlank(text)) {
            return null;
        }
        Segment segment = HanLP.newSegment().enablePlaceRecognize(true);
        List<String> result = segment.seg(text).stream().map(term -> term.word).collect(Collectors.toList());
        if (!result.isEmpty()) {
            result.removeAll(stopWordList);
        }
        return result;
    }

    public static String extractChinese(String input) {
        if (StringUtils.isBlank(input)) {
            return null;
        }
        // 使用正则表达式匹配所有汉字
//        String regex = "[^\\u4E00-\\u9fa5]";
        String regex = "[^\\u4E00-\\u9FA5\\dA-Za-z]+";
        Pattern pattern = Pattern.compile(regex);
        // 常见匹配器
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }

    public static void main(String[] args) {
        System.out.println(segmentForRoad("通顺街-通顺西街往西50米"));
    }
}
