package com.holdtime.ops.module.handler;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public class ExcelStyleHandler {
    /**
     * 创建标题样式
     * @return WriteCellStyle 标题样式
     */
    public static WriteCellStyle getHeadStyle() {
        WriteCellStyle headWriteCellStyle = createBaseStyle();

        // 设置标题字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontName("宋体");
        headWriteFont.setFontHeightInPoints((short) 14);
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);

        return headWriteCellStyle;
    }

    /**
     * 创建内容样式
     * @return WriteCellStyle 内容样式
     */
    public static WriteCellStyle getContentStyle() {
        WriteCellStyle contentWriteCellStyle = createBaseStyle();

        // 设置内容字体
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteFont.setFontName("宋体");
        contentWriteCellStyle.setWriteFont(contentWriteFont);

        return contentWriteCellStyle;
    }

    /**
     * 创建基础样式
     * @return WriteCellStyle 基础样式
     */
    private static WriteCellStyle createBaseStyle() {
        WriteCellStyle writeCellStyle = new WriteCellStyle();

        // 设置边框
        writeCellStyle.setBorderBottom(BorderStyle.THIN);
        writeCellStyle.setBottomBorderColor((short) 0);
        writeCellStyle.setBorderLeft(BorderStyle.THIN);
        writeCellStyle.setLeftBorderColor((short) 0);
        writeCellStyle.setBorderRight(BorderStyle.THIN);
        writeCellStyle.setRightBorderColor((short) 0);
        writeCellStyle.setBorderTop(BorderStyle.THIN);
        writeCellStyle.setTopBorderColor((short) 0);

        // 设置对齐方式
        writeCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        writeCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 设置其他样式属性
        writeCellStyle.setWrapped(true);
//        writeCellStyle.setShrinkToFit(true);

        return writeCellStyle;
    }
}
