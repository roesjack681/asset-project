package com.holdtime.ops.module.similarity;

import com.holdtime.ops.module.utils.TextGrouper;

import java.util.*;

public class RoadNameGroupTest {
    public static void main(String[] args) {
        // 测试数据
        List<String> roadNames = Arrays.asList(
                "幸福南路-北重路东北1",
                "幸福南路-北重路东北向南3",
                "幸福南路-北重路东北向西2",
                "幸福南路-北重路东南向北5",
                "幸福南路-北重路东南向西4",
                "幸福南路-北重路西南向北7",
                "幸福南路-北重路西南向东6"
        );

        System.out.println("=== 路名分组分析 ===\n");

        System.out.println("原始路名列表：");
        for (String name : roadNames) {
            System.out.println("- " + name);
        }

        System.out.println("\n分组结果：");
        // 使用方向词汇分组方法，这是最适合这种路名格式的方法
        Map<String, List<String>> groups = TextGrouper.groupTextsByDirection(roadNames);

        // 按照前缀长度排序，确保输出更有序
        List<String> sortedPrefixes = new ArrayList<>(groups.keySet());
        sortedPrefixes.sort((a, b) -> {
            // 首先按长度排序
            int lenCompare = Integer.compare(a.length(), b.length());
            if (lenCompare != 0) {
                return lenCompare;
            }
            // 长度相同时按字典序排序
            return a.compareTo(b);
        });

        // 打印分组结果
        for (String prefix : sortedPrefixes) {
            System.out.println("\n组: " + prefix);
            List<String> groupTexts = groups.get(prefix);
            // 对组内的文本进行排序
            groupTexts.sort(String::compareTo);
            for (String text : groupTexts) {
                System.out.println("  - " + text);
            }
        }

        // 打印统计信息
        System.out.println("\n统计信息：");
        System.out.println("- 总路名数量: " + roadNames.size());
        System.out.println("- 分组数量: " + groups.size());

        // 验证分组的完整性
        int totalGroupedItems = groups.values().stream()
            .mapToInt(List::size)
            .sum();
        System.out.println("- 已分组路名数量: " + totalGroupedItems);

        if (totalGroupedItems == roadNames.size()) {
            System.out.println("- 分组完整性: 所有路名都已被正确分组");
        } else {
            System.out.println("- 分组完整性: 警告！有路名未被正确分组");
        }
    }
}
