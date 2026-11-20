package com.holdtime.ops.module.utils;

import java.io.*;
import java.math.BigInteger;
import java.util.Collections;

public class IDListCompressorUtil {
    /* ======================= 压缩 ======================= */
    public static byte[] compress(String src) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bos);

        String[] groups = src.split(";");
        out.writeByte(groups.length);          // 组数

        for (String g : groups) {
            String[] kv = g.split("-", 2);
            int tag = Integer.parseInt(kv[0]);
            String[] ids = kv[1].split(",");

            out.writeByte(tag);                // 组标签
            out.writeShort(ids.length);        // id 数量

            String prev = "";
            for (String id : ids) {
                // 公共前缀长度
                int common = 0;
                while (common < prev.length() &&
                        common < id.length() &&
                        prev.charAt(common) == id.charAt(common)) {
                    common++;
                }
                // 写：公共长度 + 差值部分
                out.writeByte(common);
                String delta = id.substring(common);
                BigInteger val = new BigInteger(delta);
                writeBig128(val, out);
                prev = id;
            }
        }
        out.close();
        return bos.toByteArray();
    }

    /* ======================= 解压 ======================= */
    public static String decompress(byte[] data) throws IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
        StringBuilder sb = new StringBuilder();
        int groupCount = in.readByte() & 0xFF;
        for (int g = 0; g < groupCount; g++) {
            if (g > 0) sb.append(';');
            int tag = in.readByte() & 0xFF;
            int idCount = in.readShort() & 0xFFFF;
            sb.append(tag).append('-');
            String prev = "";
            for (int i = 0; i < idCount; i++) {
                if (i > 0) sb.append(',');
                int common = in.readByte() & 0xFF;
                BigInteger delta = readBig128(in);
                String part = delta.toString();
                // 补前导 0（BigInteger 会去掉）
                int needZero = 20 - common - part.length();
                if (needZero > 0) sb.append(String.join("", Collections.nCopies(needZero, "0")));
                sb.append(prev, 0, common).append(part);
                prev = sb.substring(sb.lastIndexOf(",") + 1);
                if (prev.isEmpty()) prev = sb.substring(sb.lastIndexOf("-") + 1);
            }
        }
        return sb.toString();
    }

    /* ---------------- 128 进制变长写/读 ---------------- */
    private static void writeBig128(BigInteger v, DataOutputStream out) throws IOException {
        if (v.equals(BigInteger.ZERO)) {
            out.writeByte(0);
            return;
        }
        byte[] buf = new byte[24];          // 20 位十进制 < 128 进制 12 字节
        int idx = 0;
        BigInteger base = BigInteger.valueOf(128);
        BigInteger val = v;
        while (val.compareTo(BigInteger.ZERO) > 0) {
            buf[idx++] = val.mod(base).byteValue();
            val = val.divide(base);
        }
        out.writeByte(idx);                 // 长度前缀
        for (int i = 0; i < idx; i++) out.writeByte(buf[i]);
    }

    private static BigInteger readBig128(DataInputStream in) throws IOException {
        int len = in.readByte() & 0xFF;
        BigInteger base = BigInteger.valueOf(128);
        BigInteger val = BigInteger.ZERO;
        for (int i = 0; i < len; i++) {
            int b = in.readByte() & 0xFF;
            val = val.add(BigInteger.valueOf(b).multiply(base.pow(i)));
        }
        return val;
    }

    /* ---------------- Base128 文本化（可选） ---------------- */
    public static String toBase64Text(byte[] bin) {
        return java.util.Base64.getEncoder().encodeToString(bin);
    }

    public static byte[] fromBase64Text(String txt) {
        return java.util.Base64.getDecoder().decode(txt);
    }

    /* ---------------- 演示 ---------------- */
    public static void main(String[] args) throws IOException {
        String original = "1-15020017001320500177,15020017001320500178,15020017001320500605,15020017001320500606;2-15020017001320500196,15020017001320500606";

        byte[] zip = compress(original);
        String zip64 = toBase64Text(zip);
        System.out.println("原文长度: " + original.length());
        System.out.println("压缩二进制长度: " + zip.length);
        System.out.println("Base64 文本长度: " + zip64.length());
        System.out.println("压缩率: " + String.format("%.1f%%", 100.0 * zip64.length() / original.length()));

        String restored = decompress(fromBase64Text(zip64));

        System.out.println("原文前：" + original);
        System.out.println("加密后：" + zip64);
        System.out.println("解码：" + restored);
    }
}
