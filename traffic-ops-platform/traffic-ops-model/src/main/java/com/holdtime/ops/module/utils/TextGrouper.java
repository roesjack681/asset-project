package com.holdtime.ops.module.utils;

import java.util.*;

/**
 * 文本分组工具
 * 用于将类似的文本按照共同前缀进行分组
 */
public class TextGrouper {

    /**
     * 根据方向词汇提取文本前缀并进行分组
     * 专门针对包含方向信息的地址文本
     *
     * @param texts 文本列表
     * @return 分组结果，键为前缀，值为具有该前缀的文本列表
     */
    public static Map<String, List<String>> groupTextsByDirection(List<String> texts) {
        // 方向词汇列表 - 按长度降序排列，确保先匹配较长的方向词汇
        List<String> directions = new ArrayList<>(Arrays.asList("东北", "西北", "东南", "西南", "东", "西", "南", "北"));
        // 按长度降序排序方向词汇
        directions.sort((a, b) -> Integer.compare(b.length(), a.length()));

        Map<String, List<String>> groups = new HashMap<>();

        for (String text : texts) {
            String prefix = text;

            // 查找第一个出现的方向词汇及其位置
            int minPos = text.length();
            String foundDirection = "";

            for (String direction : directions) {
                int pos = text.indexOf(direction);
                if (pos != -1 && pos < minPos) {
                    minPos = pos;
                    foundDirection = direction;
                }
            }

            // 如果找到方向词汇，提取前缀（包含方向词汇）
            if (!foundDirection.isEmpty()) {
                prefix = text.substring(0, minPos + foundDirection.length());
            }

            // 将文本添加到对应的分组
            groups.putIfAbsent(prefix, new ArrayList<>());
            groups.get(prefix).add(text);
        }

        return groups;
    }

    /**
     * 主方法，用于测试文本分组功能
     */
    public static void main(String[] args) {
        List<String> texts = Arrays.asList(
            "先锋道-幸福路东北向南1",
            "先锋道-幸福路东北向西2",
            "先锋道-幸福路东南3",
            "先锋道-幸福路东南向北4",
            "先锋道-幸福路东南向西5",
            "先锋道-幸福路西北向东7",
            "先锋道-幸福路西北向南6"
        );

        System.out.println("=== 文本分组结果 ===\n");

        System.out.println("原始文本：");
        for (String text : texts) {
            System.out.println("- " + text);
        }

        // 使用方向词汇分组
        Map<String, List<String>> groups = groupTextsByDirection(texts);

        System.out.println("\n分组结果：");
        for (Map.Entry<String, List<String>> entry : groups.entrySet()) {
            System.out.println("\n组: " + entry.getKey());
            for (String text : entry.getValue()) {
                System.out.println("  - " + text);
            }
        }

        // 打印统计信息
        System.out.println("\n统计信息：");
        System.out.println("- 总文本数量: " + texts.size());
        System.out.println("- 分组数量: " + groups.size());

        // 验证分组的完整性
        int totalGroupedItems = groups.values().stream()
            .mapToInt(List::size)
            .sum();
        System.out.println("- 已分组文本数量: " + totalGroupedItems);

        if (totalGroupedItems == texts.size()) {
            System.out.println("- 分组完整性: 所有文本都已被正确分组");
        } else {
            System.out.println("- 分组完整性: 警告！有文本未被正确分组");
        }
    }
}
