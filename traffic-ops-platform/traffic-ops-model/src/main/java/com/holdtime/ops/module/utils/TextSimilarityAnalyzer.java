package com.holdtime.ops.module.utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 文本相似度分析工具类
 * 提供多种相似度算法和相似文本提取功能
 */
public class TextSimilarityAnalyzer {

    /**
     * 计算两个字符串的Levenshtein距离（编辑距离）
     * 编辑距离越小，字符串越相似
     *
     * @param str1 第一个字符串
     * @param str2 第二个字符串
     * @return 编辑距离
     */
    public static int levenshteinDistance(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        
        // 创建距离矩阵
        int[][] distance = new int[len1 + 1][len2 + 1];
        
        // 初始化第一行和第一列
        for (int i = 0; i <= len1; i++) {
            distance[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            distance[0][j] = j;
        }
        
        // 计算编辑距离
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                int cost = (str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1;
                distance[i][j] = Math.min(
                        Math.min(distance[i - 1][j] + 1, distance[i][j - 1] + 1),
                        distance[i - 1][j - 1] + cost
                );
            }
        }
        
        return distance[len1][len2];
    }
    
    /**
     * 计算Levenshtein相似度（基于编辑距离的相似度）
     * 返回值范围：0.0-1.0，值越大表示越相似
     *
     * @param str1 第一个字符串
     * @param str2 第二个字符串
     * @return 相似度，范围0.0-1.0
     */
    public static double levenshteinSimilarity(String str1, String str2) {
        int maxLength = Math.max(str1.length(), str2.length());
        if (maxLength == 0) {
            return 1.0; // 两个空字符串视为完全相同
        }
        
        int distance = levenshteinDistance(str1, str2);
        return 1.0 - ((double) distance / maxLength);
    }
    
    /**
     * 计算Jaccard相似系数
     * 将文本分词后计算交集与并集的比值
     * 返回值范围：0.0-1.0，值越大表示越相似
     *
     * @param str1 第一个字符串
     * @param str2 第二个字符串
     * @return Jaccard相似系数
     */
    public static double jaccardSimilarity(String str1, String str2) {
        // 将字符串转换为字符集合
        Set<Character> set1 = str1.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
        Set<Character> set2 = str2.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
        
        // 计算交集大小
        Set<Character> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        
        // 计算并集大小
        Set<Character> union = new HashSet<>(set1);
        union.addAll(set2);
        
        // 防止除以零
        if (union.isEmpty()) {
            return 1.0; // 两个空集视为完全相同
        }
        
        // 计算Jaccard系数
        return (double) intersection.size() / union.size();
    }
    
    /**
     * 基于词语的Jaccard相似度
     * 将文本分割为词语后计算Jaccard相似度
     *
     * @param str1 第一个字符串
     * @param str2 第二个字符串
     * @return 基于词语的Jaccard相似度
     */
    public static double wordJaccardSimilarity(String str1, String str2) {
        // 简单分词，按空格分割
        Set<String> words1 = new HashSet<>(Arrays.asList(str1.toLowerCase().split("\\s+")));
        Set<String> words2 = new HashSet<>(Arrays.asList(str2.toLowerCase().split("\\s+")));
        
        // 计算交集大小
        Set<String> intersection = new HashSet<>(words1);
        intersection.retainAll(words2);
        
        // 计算并集大小
        Set<String> union = new HashSet<>(words1);
        union.addAll(words2);
        
        // 防止除以零
        if (union.isEmpty()) {
            return 1.0; // 两个空集视为完全相同
        }
        
        // 计算Jaccard系数
        return (double) intersection.size() / union.size();
    }
    
    /**
     * 计算余弦相似度
     * 将文本转换为词频向量，计算向量夹角的余弦值
     * 返回值范围：0.0-1.0，值越大表示越相似
     *
     * @param str1 第一个字符串
     * @param str2 第二个字符串
     * @return 余弦相似度
     */
    public static double cosineSimilarity(String str1, String str2) {
        // 将字符串转换为词频向量
        Map<String, Integer> vector1 = getTermFrequencyMap(str1);
        Map<String, Integer> vector2 = getTermFrequencyMap(str2);
        
        // 获取所有唯一词语
        Set<String> allTerms = new HashSet<>(vector1.keySet());
        allTerms.addAll(vector2.keySet());
        
        // 计算点积
        double dotProduct = 0.0;
        for (String term : allTerms) {
            dotProduct += vector1.getOrDefault(term, 0) * vector2.getOrDefault(term, 0);
        }
        
        // 计算向量模长
        double magnitude1 = 0.0;
        for (int count : vector1.values()) {
            magnitude1 += count * count;
        }
        magnitude1 = Math.sqrt(magnitude1);
        
        double magnitude2 = 0.0;
        for (int count : vector2.values()) {
            magnitude2 += count * count;
        }
        magnitude2 = Math.sqrt(magnitude2);
        
        // 防止除以零
        if (magnitude1 == 0.0 || magnitude2 == 0.0) {
            return (magnitude1 == magnitude2) ? 1.0 : 0.0;
        }
        
        // 计算余弦相似度
        return dotProduct / (magnitude1 * magnitude2);
    }
    
    /**
     * 获取文本的词频映射
     *
     * @param text 输入文本
     * @return 词频映射
     */
    private static Map<String, Integer> getTermFrequencyMap(String text) {
        Map<String, Integer> termFrequencyMap = new HashMap<>();
        // 简单分词，按空格分割
        String[] words = text.toLowerCase().split("\\s+");
        
        for (String word : words) {
            termFrequencyMap.put(word, termFrequencyMap.getOrDefault(word, 0) + 1);
        }
        
        return termFrequencyMap;
    }
    
    /**
     * 从文本集合中提取与目标文本相似的文本
     *
     * @param targetText 目标文本
     * @param textCollection 文本集合
     * @param similarityThreshold 相似度阈值（0.0-1.0）
     * @param algorithm 相似度算法："levenshtein", "jaccard", "word_jaccard", "cosine"
     * @return 相似文本列表，按相似度降序排列
     */
    public static List<SimilarText> extractSimilarTexts(
            String targetText, 
            Collection<String> textCollection, 
            double similarityThreshold, 
            String algorithm) {
        
        List<SimilarText> result = new ArrayList<>();
        
        for (String text : textCollection) {
            double similarity;
            
            // 根据指定算法计算相似度
            switch (algorithm.toLowerCase()) {
                case "levenshtein":
                    similarity = levenshteinSimilarity(targetText, text);
                    break;
                case "jaccard":
                    similarity = jaccardSimilarity(targetText, text);
                    break;
                case "word_jaccard":
                    similarity = wordJaccardSimilarity(targetText, text);
                    break;
                case "cosine":
                    similarity = cosineSimilarity(targetText, text);
                    break;
                default:
                    throw new IllegalArgumentException("不支持的相似度算法: " + algorithm);
            }
            
            // 如果相似度超过阈值，添加到结果列表
            if (similarity >= similarityThreshold) {
                result.add(new SimilarText(text, similarity));
            }
        }
        
        // 按相似度降序排序
        result.sort(Comparator.comparing(SimilarText::getSimilarity).reversed());
        
        return result;
    }
    
    /**
     * 相似文本类，存储文本及其相似度
     */
    public static class SimilarText {
        private final String text;
        private final double similarity;
        
        public SimilarText(String text, double similarity) {
            this.text = text;
            this.similarity = similarity;
        }
        
        public String getText() {
            return text;
        }
        
        public double getSimilarity() {
            return similarity;
        }
        
        @Override
        public String toString() {
            return String.format("文本: %s, 相似度: %.4f", text, similarity);
        }
    }
    
    /**
     * 提取两个文本中的相似片段
     * 使用滑动窗口方法查找相似片段
     *
     * @param text1 第一个文本
     * @param text2 第二个文本
     * @param windowSize 窗口大小
     * @param similarityThreshold 相似度阈值
     * @return 相似片段列表
     */
    public static List<SimilarSegment> extractSimilarSegments(
            String text1, 
            String text2, 
            int windowSize, 
            double similarityThreshold) {
        
        List<SimilarSegment> result = new ArrayList<>();
        
        // 如果文本长度小于窗口大小，直接比较整个文本
        if (text1.length() < windowSize || text2.length() < windowSize) {
            double similarity = levenshteinSimilarity(text1, text2);
            if (similarity >= similarityThreshold) {
                result.add(new SimilarSegment(text1, text2, similarity));
            }
            return result;
        }
        
        // 对第一个文本进行滑动窗口处理
        for (int i = 0; i <= text1.length() - windowSize; i++) {
            String segment1 = text1.substring(i, i + windowSize);
            
            // 对第二个文本进行滑动窗口处理
            for (int j = 0; j <= text2.length() - windowSize; j++) {
                String segment2 = text2.substring(j, j + windowSize);
                
                // 计算片段相似度
                double similarity = levenshteinSimilarity(segment1, segment2);
                
                // 如果相似度超过阈值，添加到结果列表
                if (similarity >= similarityThreshold) {
                    result.add(new SimilarSegment(segment1, segment2, similarity));
                }
            }
        }
        
        // 合并重叠的相似片段
        return mergeOverlappingSegments(result);
    }
    
    /**
     * 合并重叠的相似片段
     *
     * @param segments 相似片段列表
     * @return 合并后的相似片段列表
     */
    private static List<SimilarSegment> mergeOverlappingSegments(List<SimilarSegment> segments) {
        // 这里简化处理，实际应用中可能需要更复杂的合并逻辑
        // 按相似度降序排序，保留相似度最高的片段
        segments.sort(Comparator.comparing(SimilarSegment::getSimilarity).reversed());
        
        // 简单去重，基于文本内容
        Set<String> addedPairs = new HashSet<>();
        List<SimilarSegment> result = new ArrayList<>();
        
        for (SimilarSegment segment : segments) {
            String pair = segment.getSegment1() + "|||" + segment.getSegment2();
            if (!addedPairs.contains(pair)) {
                addedPairs.add(pair);
                result.add(segment);
            }
        }
        
        return result;
    }
    
    /**
     * 相似片段类，存储两个文本片段及其相似度
     */
    public static class SimilarSegment {
        private final String segment1;
        private final String segment2;
        private final double similarity;
        
        public SimilarSegment(String segment1, String segment2, double similarity) {
            this.segment1 = segment1;
            this.segment2 = segment2;
            this.similarity = similarity;
        }
        
        public String getSegment1() {
            return segment1;
        }
        
        public String getSegment2() {
            return segment2;
        }
        
        public double getSimilarity() {
            return similarity;
        }
        
        @Override
        public String toString() {
            return String.format("片段1: %s\n片段2: %s\n相似度: %.4f", segment1, segment2, similarity);
        }
    }
}