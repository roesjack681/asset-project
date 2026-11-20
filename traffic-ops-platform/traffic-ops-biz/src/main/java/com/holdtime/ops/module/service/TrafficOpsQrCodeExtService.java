package com.holdtime.ops.module.service;

import com.holdtime.ops.module.model.vo.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 设备维保信息 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsQrCodeExtService {

    /**
     * 生成二维码
     *
     * @param saveReqVO 二维码内容
     */
    void genEquipQrCode(@Valid TrafficOpsQrcodeGenRecordExtSaveReqVO saveReqVO);

    /**
     * 根据生产记录删除记录及生成的Qrcode
     *
     * @param genRecordId 生成记录
     */
    void deleteEquipQrCodeByGenId(String genRecordId);

    /**
     * 查询二维码打印数据
     * @param printType 打印类型：single-单张，batch-批量
     * @param keyword 关键字
     * @return 打印数据
     */
    List<TrafficOpsQrcodePrintRespVO> queryOpsQrcodePrintDataList(String printType, String keyword);

    /**
     * 生成公安设备二维码
     * @param saveReqVO 二维码内容
     */
    void genPoliceEquipQrCode(@Valid PoliceOpsQrcodeGenRecordExtSaveReqVO saveReqVO);

}