# 文本相似度分析工具

这个工具提供了多种字符串相似度算法和相似文本提取功能，可用于文本相似度分析、相似文本检测、文本去重等场景。

## 功能特点

1. **多种相似度算法**：
   - Levenshtein距离（编辑距离）
   - Jaccard相似系数
   - 基于词语的Jaccard相似度
   - 余弦相似度

2. **相似文本提取**：
   - 从文本集合中提取与目标文本相似的文本
   - 按相似度排序

3. **相似片段提取**：
   - 使用滑动窗口方法提取两个文本中的相似片段
   - 合并重叠的相似片段

## 使用方法

### 计算文本相似度

```java
String text1 = "今天天气真好";
String text2 = "今天阳光明媚";

// 计算Levenshtein相似度
double levSimilarity = TextSimilarityAnalyzer.levenshteinSimilarity(text1, text2);

// 计算Jaccard相似度
double jaccardSimilarity = TextSimilarityAnalyzer.jaccardSimilarity(text1, text2);

// 计算基于词语的Jaccard相似度
double wordJaccardSimilarity = TextSimilarityAnalyzer.wordJaccardSimilarity(text1, text2);

// 计算余弦相似度
double cosineSimilarity = TextSimilarityAnalyzer.cosineSimilarity(text1, text2);
```

### 提取相似文本

```java
List<String> textCollection = Arrays.asList(
    "今天天气真好，阳光明媚",
    "今天阳光明媚，天气不错",
    "今天下雨了，天气不好",
    "昨天天气不错，适合出游"
);

String targetText = "今天天气真好";
double threshold = 0.5; // 相似度阈值

List<TextSimilarityAnalyzer.SimilarText> similarTexts = 
    TextSimilarityAnalyzer.extractSimilarTexts(
        targetText, 
        textCollection, 
        threshold, 
        "levenshtein" // 可选: "levenshtein", "jaccard", "word_jaccard", "cosine"
    );

// 输出相似文本
for (TextSimilarityAnalyzer.SimilarText similarText : similarTexts) {
    System.out.println(similarText);
}
```

### 提取相似片段

```java
String longText1 = "今天的天气真是太好了，阳光明媚，适合出去散步。";
String longText2 = "现在天气不错，阳光明媚的天气让人心情愉悦。";

List<TextSimilarityAnalyzer.SimilarSegment> similarSegments = 
    TextSimilarityAnalyzer.extractSimilarSegments(
        longText1, 
        longText2, 
        5, // 窗口大小
        0.6 // 相似度阈值
    );

// 输出相似片段
for (TextSimilarityAnalyzer.SimilarSegment segment : similarSegments) {
    System.out.println(segment);
}
```

## 算法说明

### Levenshtein距离（编辑距离）

编辑距离是指两个字符串之间，由一个转换成另一个所需的最少编辑操作次数。编辑操作包括插入、删除和替换。编辑距离越小，两个字符串越相似。

### Jaccard相似系数

Jaccard相似系数是两个集合交集的大小除以并集的大小。在文本相似度中，可以将字符或词语视为集合元素。Jaccard系数越大，两个文本越相似。

### 余弦相似度

余弦相似度通过计算两个向量的夹角余弦值来度量它们之间的相似性。在文本相似度中，文本被转换为词频向量。余弦值越接近1，两个文本越相似。

## 应用场景

1. **文本去重**：检测并合并相似的文本内容
2. **抄袭检测**：比较文档之间的相似度
3. **搜索优化**：找出与搜索查询相似的内容
4. **推荐系统**：基于内容相似度推荐相关项目
5. **情感分析**：聚类相似的评论或反馈

## 性能考虑

- Levenshtein距离计算的时间复杂度为O(m*n)，其中m和n是两个字符串的长度
- 对于大规模文本集合，建议使用更高效的算法或索引结构
- 滑动窗口方法在处理长文本时可能需要较长时间，可以考虑增加窗口大小或使用其他优化方法
# 