package com.holdtime.ops.module.utils;

import java.util.*;

/**
 * 文本一致性提取工具
 * 用于从多个文本中提取一致的部分
 */
public class TextConsistencyExtractor {

    /**
     * 从多个文本中提取最长公共前缀
     *
     * @param texts 文本列表
     * @return 最长公共前缀
     */
    public static String extractCommonPrefix(List<String> texts) {
        if (texts == null || texts.isEmpty()) {
            return "";
        }

        // 以第一个字符串作为基准
        String firstText = texts.get(0);

        // 如果只有一个字符串，直接返回
        if (texts.size() == 1) {
            return firstText;
        }

        // 找出最短字符串的长度，避免越界
        int minLength = firstText.length();
        for (int i = 1; i < texts.size(); i++) {
            minLength = Math.min(minLength, texts.get(i).length());
        }

        // 逐字符比较
        int i = 0;
        while (i < minLength) {
            char c = firstText.charAt(i);
            for (int j = 1; j < texts.size(); j++) {
                if (texts.get(j).charAt(i) != c) {
                    // 发现不匹配的字符，返回当前的公共前缀
                    return firstText.substring(0, i);
                }
            }
            i++;
        }

        // 所有字符都匹配到最短字符串的末尾
        return firstText.substring(0, minLength);
    }

    /**
     * 从多个文本中提取最长公共子串
     * 使用动态规划算法
     *
     * @param texts 文本列表
     * @return 最长公共子串
     */
    public static String extractLongestCommonSubstring(List<String> texts) {
        if (texts == null || texts.isEmpty()) {
            return "";
        }

        if (texts.size() == 1) {
            return texts.get(0);
        }

        // 先找出前两个字符串的最长公共子串
        String result = longestCommonSubstring(texts.get(0), texts.get(1));

        // 然后与剩余的字符串逐个比较，更新最长公共子串
        for (int i = 2; i < texts.size(); i++) {
            result = longestCommonSubstring(result, texts.get(i));
            if (result.isEmpty()) {
                // 如果已经没有公共子串，提前结束
                break;
            }
        }

        return result;
    }

    /**
     * 计算两个字符串的最长公共子串
     *
     * @param str1 第一个字符串
     * @param str2 第二个字符串
     * @return 最长公共子串
     */
    private static String longestCommonSubstring(String str1, String str2) {
        if (str1 == null || str2 == null || str1.isEmpty() || str2.isEmpty()) {
            return "";
        }

        int[][] dp = new int[str1.length() + 1][str2.length() + 1];
        int maxLength = 0;
        int endIndex = 0;

        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    if (dp[i][j] > maxLength) {
                        maxLength = dp[i][j];
                        endIndex = i;
                    }
                }
            }
        }

        return str1.substring(endIndex - maxLength, endIndex);
    }

    /**
     * 使用字符频率分析提取一致文本
     * 找出在所有文本中都出现的字符序列
     *
     * @param texts 文本列表
     * @param threshold 一致性阈值（0.0-1.0），表示字符在多少比例的文本中出现才被视为一致
     * @return 一致文本
     */
    public static String extractConsistentText(List<String> texts, double threshold) {
        if (texts == null || texts.isEmpty()) {
            return "";
        }

        if (texts.size() == 1) {
            return texts.get(0);
        }

        // 计算每个位置的字符在多少文本中出现
        Map<Integer, Map<Character, Integer>> positionCharFrequency = new HashMap<>();
        int maxLength = 0;

        // 统计每个位置的字符频率
        for (String text : texts) {
            maxLength = Math.max(maxLength, text.length());

            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);

                positionCharFrequency.putIfAbsent(i, new HashMap<>());
                Map<Character, Integer> charFreq = positionCharFrequency.get(i);
                charFreq.put(c, charFreq.getOrDefault(c, 0) + 1);
            }
        }

        // 构建一致文本
        StringBuilder consistentText = new StringBuilder();
        int requiredCount = (int) Math.ceil(texts.size() * threshold);

        for (int i = 0; i < maxLength; i++) {
            Map<Character, Integer> charFreq = positionCharFrequency.getOrDefault(i, Collections.emptyMap());

            // 找出在该位置出现次数最多的字符
            char mostFrequentChar = 0;
            int maxFreq = 0;

            for (Map.Entry<Character, Integer> entry : charFreq.entrySet()) {
                if (entry.getValue() > maxFreq) {
                    maxFreq = entry.getValue();
                    mostFrequentChar = entry.getKey();
                }
            }

            // 如果字符出现次数达到阈值，添加到一致文本
            if (maxFreq >= requiredCount) {
                consistentText.append(mostFrequentChar);
            } else {
                // 一旦遇到不一致的位置，就停止
                break;
            }
        }

        return consistentText.toString();
    }

    /**
     * 使用多种方法提取一致文本，并返回最佳结果
     *
     * @param texts 文本列表
     * @return 一致文本
     */
    public static String extractBestConsistentText(List<String> texts) {
        if (texts == null || texts.isEmpty()) {
            return "";
        }

        // 使用不同方法提取一致文本
        String commonPrefix = extractCommonPrefix(texts);
        String longestCommonSubstring = extractLongestCommonSubstring(texts);
        String consistentText = extractConsistentText(texts, 1.0); // 要求100%一致

        // 选择最长的结果作为最佳一致文本
        String bestResult = commonPrefix;
        if (longestCommonSubstring.length() > bestResult.length()) {
            bestResult = longestCommonSubstring;
        }
        if (consistentText.length() > bestResult.length()) {
            bestResult = consistentText;
        }

        return bestResult;
    }

    /**
     * 示例方法：从给定的文本中提取一致部分
     */
    public static void main(String[] args) {
        List<String> texts = Arrays.asList(
            "色楞湾路-小商品餐饮区西北角朝东2",
            "色楞湾路-小商品餐饮区西北角朝北3",
            "色楞湾路-小商品餐饮区西北角1"
        );

        System.out.println("输入文本：");
        for (String text : texts) {
            System.out.println(text);
        }

        System.out.println("\n最长公共前缀：");
        System.out.println(extractCommonPrefix(texts));

        System.out.println("\n最长公共子串：");
        System.out.println(extractLongestCommonSubstring(texts));

        System.out.println("\n字符频率分析（100%一致）：");
        System.out.println(extractConsistentText(texts, 1.0));

        System.out.println("\n字符频率分析（至少66%一致）：");
        System.out.println(extractConsistentText(texts, 0.66));

        System.out.println("\n最佳一致文本：");
        System.out.println(extractBestConsistentText(texts));
    }
}
