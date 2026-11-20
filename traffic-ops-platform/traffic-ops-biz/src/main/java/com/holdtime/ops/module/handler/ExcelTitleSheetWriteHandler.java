package com.holdtime.ops.module.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelTitleSheetWriteHandler implements SheetWriteHandler {
    private final String bigTitle;
    private final String subTitle;
    private final int lastCol;

    public ExcelTitleSheetWriteHandler(String bigTitle, String subTitle, int lastCol) {
        this.bigTitle = bigTitle;
        this.subTitle = subTitle;
        this.lastCol = lastCol;
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        Sheet sheet = workbook.getSheetAt(0);
        //设置大标题
        Row row = sheet.createRow(0);
        row.setHeight((short) 800);
        Cell cell = row.createCell(0);
        cell.setCellValue(bigTitle);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight((short) 400);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        sheet.addMergedRegionUnsafe(new CellRangeAddress(0, 0, 0, lastCol));

        // 设置副标题
        if (StringUtils.isNotBlank(subTitle)) {
            Row subTitleRow = sheet.createRow(1);
            subTitleRow.setHeight((short) 400);
            Cell subTitleCell = subTitleRow.createCell(0);
            subTitleCell.setCellValue(subTitle);
            CellStyle subTitleCellStyle = workbook.createCellStyle();
            subTitleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            subTitleCellStyle.setAlignment(HorizontalAlignment.RIGHT);
            Font subTitleFont = workbook.createFont();
            subTitleFont.setBold(true);
            subTitleFont.setFontHeight((short) 300);
            subTitleCellStyle.setFont(subTitleFont);
            subTitleCell.setCellStyle(subTitleCellStyle);
            sheet.addMergedRegionUnsafe(new CellRangeAddress(1, 1, 0, lastCol));
        }
    }


}
