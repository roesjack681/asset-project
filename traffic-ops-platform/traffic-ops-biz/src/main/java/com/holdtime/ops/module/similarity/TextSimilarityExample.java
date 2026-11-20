package com.holdtime.ops.module.similarity;

import com.holdtime.ops.module.utils.TextSimilarityAnalyzer;

import java.util.Arrays;
import java.util.List;

/**
 * 文本相似度分析工具使用示例
 */
public class TextSimilarityExample {

    public static void main(String[] args) {
        // 示例文本
        String text1 = "今天天气真好，阳光明媚";
        String text2 = "今天阳光明媚，天气不错";
        String text3 = "今天下雨了，天气不好";

//        // 1. 使用不同的相似度算法比较两个文本
//        System.out.println("=== 相似度算法比较 ===");
//        System.out.println("文本1: " + text1);
//        System.out.println("文本2: " + text2);
//        System.out.println("Levenshtein相似度: " +
//            TextSimilarityAnalyzer.levenshteinSimilarity(text1, text2));
//        System.out.println("Jaccard相似度: " +
//            TextSimilarityAnalyzer.jaccardSimilarity(text1, text2));
//        System.out.println("词语Jaccard相似度: " +
//            TextSimilarityAnalyzer.wordJaccardSimilarity(text1, text2));
//        System.out.println("余弦相似度: " +
//            TextSimilarityAnalyzer.cosineSimilarity(text1, text2));

        // 2. 从文本集合中提取相似文本
        System.out.println("\n=== 相似文本提取 ===");
//        List<String> textCollection = Arrays.asList(
//            "色楞湾路-小商品餐饮区西北角朝北3",
//            "色楞湾路-小商品餐饮区西北角朝东2",
//            "色楞湾路-小商品餐饮区西北角1",
//            "色楞湾路-豪德西区正门西南角朝北2",
//            "色楞湾路-豪德西区正门西南角朝东1"
//        );

        List<String> textCollection = Arrays.asList(
                "青山路-青东路东南向北3",
                "青山路-青东东路东南向北5",
                "青山路-青东路东南向东2", "青山路-青东路西南向北5"
        );

        String targetText = "青山路-青东路东南向东2";
        double threshold = 0.8; // 相似度阈值

        System.out.println("目标文本: " + targetText);
        System.out.println("使用Levenshtein算法提取相似文本（阈值 > " + threshold + "）：");
        List<TextSimilarityAnalyzer.SimilarText> similarTexts =
            TextSimilarityAnalyzer.extractSimilarTexts(
                targetText,
                textCollection,
                threshold,
                "levenshtein"
            );

        for (TextSimilarityAnalyzer.SimilarText similarText : similarTexts) {
            System.out.println(similarText.getText());
        }

//        // 3. 提取两个文本中的相似片段
//        System.out.println("\n=== 相似片段提取 ===");
//        String longText1 = "色楞湾路-小商品餐饮区西北角朝北3";
//        String longText2 = "色楞湾路-小商品餐饮区西北角朝东2";
//
//        System.out.println("文本1: " + longText1);
//        System.out.println("文本2: " + longText2);
//
//        List<TextSimilarityAnalyzer.SimilarSegment> similarSegments =
//            TextSimilarityAnalyzer.extractSimilarSegments(
//                longText1,
//                longText2,
//                5, // 窗口大小
//                0.6 // 相似度阈值
//            );
//
//        System.out.println("相似片段（窗口大小=5，阈值>0.6）：");
//        for (TextSimilarityAnalyzer.SimilarSegment segment : similarSegments) {
//            System.out.println(segment);
//        }

//        // 4. 实际应用场景示例：查找相似的用户评论
//        System.out.println("\n=== 实际应用场景：相似评论检测 ===");
//        List<String> comments = Arrays.asList(
//            "这个产品质量很好，很满意",
//            "产品质量非常好，我很满意",
//            "这个东西质量还行吧",
//            "价格便宜，但质量一般",
//            "太贵了，不值这个价"
//        );
//
//        String targetComment = "这个产品质量很好";
//        System.out.println("目标评论: " + targetComment);
//        System.out.println("相似评论（余弦相似度 > 0.5）：");
//
//        List<TextSimilarityAnalyzer.SimilarText> similarComments =
//            TextSimilarityAnalyzer.extractSimilarTexts(
//                targetComment,
//                comments,
//                0.5,
//                "cosine"
//            );
//
//        for (TextSimilarityAnalyzer.SimilarText comment : similarComments) {
//            System.out.println(comment);
//        }
    }
}
