package com.holdtime.ops.module.utils.emay;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 项目名称： hdtm-sms
 * 功能描述： GZipUtils概述
 * 创 建 人： yanxl
 * 创建日期： 2022年10月09日 11:13:00
 * 修 改 人：
 * 修改时间：
 * 修改内容：
 * 版    本： 1.0.0
 */
public class GZipUtil {
    public static void main(String[] args) throws IOException {
        String sst = "hahahah";
        System.out.println(sst);
        System.out.println(System.currentTimeMillis());
        System.out.println("size:" + sst.length());
        byte[] bytes = sst.getBytes();
        System.out.println("length:" + bytes.length);
        System.out.println(System.currentTimeMillis());
        byte[] end = compress(bytes);
        System.out.println(System.currentTimeMillis());
        System.out.println("length:" + end.length);
        System.out.println(System.currentTimeMillis());
        byte[] start = decompress(end);
        System.out.println(System.currentTimeMillis());
        System.out.println("length:" + start.length);
        System.out.println(new String(start));
    }

    /**
     * 数据压缩传输
     * @param bytes 字节数组
     * @param out 输出流
     * @throws IOException IO异常
     */
    public static void compressTransfe(byte[] bytes, OutputStream out) throws IOException {
        try (GZIPOutputStream gos = new GZIPOutputStream(out)) {
            gos.write(bytes);
            gos.finish();
            gos.flush();
        }
    }

    /**
     * 数据压缩
     * @param bytes 字节数组
     * @return 输出流
     * @throws IOException IO异常
     */
    public static byte[] compress(byte[] bytes) throws IOException {
        ByteArrayOutputStream out = null;
        GZIPOutputStream gos = null;
        try {
            out = new ByteArrayOutputStream();
            gos = new GZIPOutputStream(out);
            gos.write(bytes);
            gos.finish();
            gos.flush();
        } finally{
            if(gos != null){
                gos.close();
            }
            if(out != null){
                out.close();
            }
        }
        return out.toByteArray();
    }

    /**
     * 数据解压
     * @param bytes 解压数组
     * @return 解压结果
     * @throws IOException IO异常
     */
    public static byte[] decompress(byte[] bytes) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        GZIPInputStream gin = new GZIPInputStream(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int count;
        byte[] data = new byte[1024];
        while ((count = gin.read(data, 0, 1024)) != -1) {
            out.write(data, 0, count);
        }
        out.flush();
        out.close();
        gin.close();
        return out.toByteArray();
    }
}
