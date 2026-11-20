package com.holdtime.ops.module.utils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class StringEncoder {

    /**
     * 编码方法：将整个字符串编码为更短的字符串
     * @param input 输入字符串（支持任意字符）
     * @return 编码后的字符串
     */
    public static String encode(String input) {
        // 压缩整个字符串
        byte[] compressed = compress(input.getBytes());
        return Base64.getUrlEncoder().encodeToString(compressed);
    }

    /**
     * 解码方法：将编码后的字符串还原为原始格式
     * @param encoded 编码后的字符串
     * @return 原始字符串
     */
    public static String decode(String encoded) {
        // 解压整个字符串
        byte[] decoded = Base64.getUrlDecoder().decode(encoded);
        byte[] decompressed = decompress(decoded);
        return new String(decompressed);
    }

    private static byte[] compress(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        byte[] buffer = new byte[1024];
        List<Byte> output = new ArrayList<>();

        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            for (int i = 0; i < count; i++) {
                output.add(buffer[i]);
            }
        }

        byte[] result = new byte[output.size()];
        for (int i = 0; i < output.size(); i++) {
            result[i] = output.get(i);
        }

        return result;
    }

    private static byte[] decompress(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        byte[] buffer = new byte[1024];
        List<Byte> output = new ArrayList<>();

        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                for (int i = 0; i < count; i++) {
                    output.add(buffer[i]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to decompress data", e);
        }

        byte[] result = new byte[output.size()];
        for (int i = 0; i < output.size(); i++) {
            result[i] = output.get(i);
        }

        return result;
    }

    public static void main(String[] args) {
        String original = "1-15020017001320500177,15020017001320500178,15020017001320500605,15020017001320500606;2-15020017001320500196,15020017001320500606";
        String encoded = encode(original);
        System.out.println("Encoded: " + encoded);

        String decoded = decode(encoded);
        System.out.println("Decoded: " + decoded);
    }
}