package com.holdtime.ops.module.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.holdtime.ops.module.model.vo.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 设备维保信息 Service 接口
 *
 * @author 芋道源码
 */
public interface PoliceOpsMemberExtService {

    /**
     * 保存或修改杆件信息
     *
     * @param saveReqVO 二维码内容
     */
    String saveOpsMemberInfo(@Valid PoliceOpsMemberExtSaveReqVO saveReqVO);

    /**
     * 查询杆件信息
     *
     * @param memberId 杆件ID
     */
    PoliceOpsMemberExtRespVO getOpsMember(String memberId);

    /**
     * 查询杆件分页信息
     *
     * @param pageReqVO 杆件查询条件
     * @param isPaging 是否分页
     */
    PageResult<PoliceOpsMemberExtRespVO> getOpsMemberPage(PoliceOpsMemberPageReqVO pageReqVO, String isPaging);

    /**
     * 根据电警查询附近可选的杆件名称
     * @param lng 经度
     * @param lat 纬度
     * @return 附近可选的杆件名称
     */
    List<String> queryNearByPoliceMember(String lng, String lat);

    /**
     * 根据名称自动补全查询杆件电警信息
     * @param equName 设备名称
     * @return 公安交警杆件信息
     */
    List<PoliceOpsMemberExtRespVO> autocompletePoliceOpsMemberList(String equName);

    /**
     * 查询公安交警电警信息
     * @param equName 杆件名称
     * @return 公安交警杆件信息
     */
    List<PoliceOpsElectricPoliceRespVO> queryPoliceElectricPolice(String equName);

    /**
     * 查询杆件分页信息
     *
     * @param pageReqVO 杆件查询条件
     */
    PageResult<PoliceOpsMemberExtRespVO> getOpsMemberPage(PoliceOpsMemberPageReqVO pageReqVO);

    /**
     * 删除杆件信息
     * @param id 杆件ID
     */
    void deleteOpsMember(String id);
}