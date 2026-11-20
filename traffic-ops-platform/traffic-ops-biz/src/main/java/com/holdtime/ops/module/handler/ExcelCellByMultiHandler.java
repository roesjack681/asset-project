package com.holdtime.ops.module.handler;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ExcelCellByMultiHandler implements CellWriteHandler {

    /**
     * 用第几行开始合并 ，默认为1，因为第0行是标题，EasyExcel 的默认也是
     */
    private int mergeRowIndex = 1;


    /**
     * 参与合并的列，起始列位置，默认为0
     */
    private int mergeColumnStart = 0;


    /**
     * 参与合并的列，最后一列的位置，默认为4
     */
    private int mergeColumnEnd = 9;


    /**
     * 默认初始合并区域大小，标头有合并的，则需要指定标头合并后的合并区域大小，没有的话就是0
     */
    private int defaultMergeRegionSize = 0;


    public ExcelCellByMultiHandler(int defaultMergeRegionSize) {
        this.defaultMergeRegionSize = defaultMergeRegionSize;
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }


    /*
     * 在对单元格的所有操作完成后调用
     */
    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        //当前行
        int curRowIndex = cell.getRowIndex();
        //当前列
        int curColIndex = cell.getColumnIndex();
        if (curRowIndex > mergeRowIndex && curColIndex >= mergeColumnStart) {
            // 读取到mergeColumnEnd列，开始合并
            if (curColIndex == mergeColumnEnd) {
                mergeWithPrevRow(writeSheetHolder, cell, curRowIndex);
            }
        }

    }

    /**
     * @Description: 合并比较多列值是否相同
     * @Date: 2023/9/8  16:31
     * @Param writeSheetHolder: 通过此对象获取sheet等信息
     * @Param cell: 当前单元格
     * @Param curRowIndex: 当前行
     * @return: void
     **/
    private void mergeWithPrevRow(WriteSheetHolder writeSheetHolder, Cell cell, int curRowIndex) {
        StringBuilder curString = new StringBuilder();
        StringBuilder preString = new StringBuilder();
        try {
            for (int i = mergeColumnStart; i <= mergeColumnEnd; i++) {
                Cell curCell = cell.getSheet().getRow(curRowIndex).getCell(i);
                Cell preCell = cell.getSheet().getRow(curRowIndex - 1).getCell(i);
                //根据单元格类型获取单元格的值
                Object curData = curCell.getCellType() == CellType.STRING ? curCell.getStringCellValue() : curCell.getNumericCellValue();
                Object preData = preCell.getCellType() == CellType.STRING ? preCell.getStringCellValue() : preCell.getNumericCellValue();
                curString.append(curData);
                preString.append(preData);
            }
            // 比较当前行的单元格与上一行是否相同，相同合并当前单元格与上一行
            Sheet sheet = writeSheetHolder.getSheet();
            // 获取合并信息
            List<CellRangeAddress> mergeRegions = sheet.getMergedRegions();
            if (curString.toString().equals(preString.toString())) {
                boolean isMerged = false;
                // 下标
                int index = 0;
                for (int i = 0; i < mergeRegions.size(); i++) {
                    CellRangeAddress cellRangeAddr = mergeRegions.get(i);
                    // 若上一个单元格已经被合并，则先移出原有的合并单元，再重新添加合并单元
                    if (cellRangeAddr.isInRange(curRowIndex - 1, index)) {
                        //这个移除的要保证是也被合并的那个
                        sheet.removeMergedRegion(defaultMergeRegionSize);
                        cellRangeAddr.setLastRow(curRowIndex);
                        sheet.addMergedRegion(cellRangeAddr);
                        isMerged = true;
                    }
                    index++;
                    // 这个是为了在默认合并单元大小后，再重置index值，index表示的就是列，这样才能保证进行isInRange判断的时候能够对应上
                    if (index > defaultMergeRegionSize-1) {
                        index = 0;
                    }
                }
                if (!isMerged) {
                    for (int i = 0; i <= mergeColumnEnd; i++) {
                        CellRangeAddress cellRangeAddress = new CellRangeAddress(curRowIndex - 1, curRowIndex, i, i);
                        sheet.addMergedRegion(cellRangeAddress);
                    }
                }
            }else {  //不相等则更新默认合并区域大小
                defaultMergeRegionSize = mergeRegions.size();
            }
        } catch (Exception e) {
            log.error("合并单元格当前str==========" + curString, e);
            log.error("合并单元格上一次str==========" + preString, e);
        }

    }
}


