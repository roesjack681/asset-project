package com.holdtime.ops.module.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字符串拆分工具类
 * 提供多种方法将字符串拆分成单个字符
 */
public class StringSplitter {

    /**
     * 将字符串拆分成字符数组
     *
     * @param input 输入字符串
     * @return 字符数组
     */
    public static char[] splitToCharArray(String input) {
        if (input == null) {
            return new char[0];
        }
        return input.toCharArray();
    }

    /**
     * 将字符串拆分成Character对象列表
     *
     * @param input 输入字符串
     * @return Character对象列表
     */
    public static List<Character> splitToCharacterList(String input) {
        if (input == null || input.isEmpty()) {
            return new ArrayList<>();
        }

        List<Character> result = new ArrayList<>(input.length());
        for (int i = 0; i < input.length(); i++) {
            result.add(input.charAt(i));
        }
        return result;
    }

    /**
     * 将字符串拆分成Character对象列表（使用Java 8 Stream API）
     *
     * @param input 输入字符串
     * @return Character对象列表
     */
    public static List<Character> splitToCharacterListUsingStream(String input) {
        if (input == null || input.isEmpty()) {
            return new ArrayList<>();
        }

        return input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
    }

    /**
     * 将字符串拆分成字符串列表，每个元素是单个字符
     *
     * @param input 输入字符串
     * @return 字符串列表
     */
    public static List<String> splitToStringList(String input) {
        if (input == null || input.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>(input.length());
        for (int i = 0; i < input.length(); i++) {
            result.add(String.valueOf(input.charAt(i)));
        }
        return result;
    }

    /**
     * 将字符串拆分成字符串数组，每个元素是单个字符
     *
     * @param input 输入字符串
     * @return 字符串数组
     */
    public static String[] splitToStringArray(String input) {
        if (input == null || input.isEmpty()) {
            return new String[0];
        }

        String[] result = new String[input.length()];
        for (int i = 0; i < input.length(); i++) {
            result[i] = String.valueOf(input.charAt(i));
        }
        return result;
    }

    /**
     * 将字符串拆分成字符串数组（使用正则表达式）
     *
     * @param input 输入字符串
     * @return 字符串数组
     */
    public static String[] splitUsingRegex(String input) {
        if (input == null || input.isEmpty()) {
            return new String[0];
        }

        // 使用零宽度断言(?<=.)(?=.)在每个字符之间分割
        // 但这会丢失第一个和最后一个字符，所以我们使用更简单的方法
        return input.split("");
    }

    /**
     * 演示各种拆分方法的使用
     */
    public static void main(String[] args) {
        String testString = "青山路-青东东路西北角公交站1";

        System.out.println("原始字符串: " + testString);

        // 方法1: 拆分成字符数组
        char[] charArray = splitToCharArray(testString);
        System.out.println("\n1. 字符数组:");
        for (char c : charArray) {
            System.out.print("'" + c + "' ");
        }

        // 方法2: 拆分成Character对象列表
        List<Character> characterList = splitToCharacterList(testString);
        System.out.println("\n\n2. Character对象列表:");
        for (Character c : characterList) {
            System.out.print("'" + c + "' ");
        }

        // 方法3: 使用Stream API拆分成Character对象列表
        List<Character> characterListStream = splitToCharacterListUsingStream(testString);
        System.out.println("\n\n3. 使用Stream API的Character对象列表:");
        for (Character c : characterListStream) {
            System.out.print("'" + c + "' ");
        }

        // 方法4: 拆分成字符串列表
        List<String> stringList = splitToStringList(testString);
        System.out.println("\n\n4. 字符串列表:");
        for (String s : stringList) {
            System.out.print("\"" + s + "\" ");
        }

        // 方法5: 拆分成字符串数组
        String[] stringArray = splitToStringArray(testString);
        System.out.println("\n\n5. 字符串数组:");
        for (String s : stringArray) {
            System.out.print("\"" + s + "\" ");
        }

        // 方法6: 使用正则表达式拆分
        String[] regexArray = splitUsingRegex(testString);
        System.out.println("\n\n6. 使用正则表达式拆分:");
        for (String s : regexArray) {
            System.out.print("\"" + s + "\" ");
        }
    }
}
