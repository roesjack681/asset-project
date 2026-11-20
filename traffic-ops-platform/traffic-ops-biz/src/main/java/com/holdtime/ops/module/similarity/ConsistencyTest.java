package com.holdtime.ops.module.similarity;

import com.holdtime.ops.module.utils.TextConsistencyExtractor;

import java.util.Arrays;
import java.util.List;

public class ConsistencyTest {
    public static void main(String[] args) {
        // 测试数据
        List<String> texts = Arrays.asList(
            "青山路-青东路东南向北3",
            "青山路-青东东路东南向北5",
            "青山路-青东路东南向东2", "青山路-青东路西南向北5"
        );

        System.out.println("=== 文本一致性分析结果 ===\n");

        System.out.println("输入文本：");
        for (String text : texts) {
            System.out.println(text);
        }

        System.out.println("\n使用不同方法提取一致文本：");

        // 使用最长公共前缀方法
        String commonPrefix = TextConsistencyExtractor.extractCommonPrefix(texts);
        System.out.println("\n1. 最长公共前缀方法：");
        System.out.println("结果：" + commonPrefix);
        System.out.println("长度：" + commonPrefix.length());

        // 使用最长公共子串方法
        String commonSubstring = TextConsistencyExtractor.extractLongestCommonSubstring(texts);
        System.out.println("\n2. 最长公共子串方法：");
        System.out.println("结果：" + commonSubstring);
        System.out.println("长度：" + commonSubstring.length());

        // 使用字符频率分析方法（100%一致）
        String consistentText = TextConsistencyExtractor.extractConsistentText(texts, 1.0);
        System.out.println("\n3. 字符频率分析方法（要求完全一致）：");
        System.out.println("结果：" + consistentText);
        System.out.println("长度：" + consistentText.length());

        // 使用最佳一致文本方法
        String bestResult = TextConsistencyExtractor.extractBestConsistentText(texts);
        System.out.println("\n4. 最佳一致文本（综合比较）：");
        System.out.println("结果：" + bestResult);
        System.out.println("长度：" + bestResult.length());

        System.out.println("\n=== 分析总结 ===");
        System.out.println("所有方法都成功提取出了文本的一致部分：");
        System.out.println("\"" + bestResult + "\"");
    }
}
