package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsMaintenanceFilesService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsMaintenanceFilesDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsMaintenanceFilesMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 维保材料信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsMaintenanceFilesServiceImpl implements TrafficOpsMaintenanceFilesService {

    @Resource
    private TrafficOpsMaintenanceFilesMapper opsMaintenanceFilesMapper;

    @Override
    public String createOpsMaintenanceFiles(AppTrafficOpsMaintenanceFilesSaveReqVO createReqVO) {
        // 插入
        TrafficOpsMaintenanceFilesDO opsMaintenanceFiles = BeanUtils.toBean(createReqVO, TrafficOpsMaintenanceFilesDO.class);
        opsMaintenanceFilesMapper.insert(opsMaintenanceFiles);
        // 返回
        return opsMaintenanceFiles.getId();
    }

    @Override
    public void updateOpsMaintenanceFiles(AppTrafficOpsMaintenanceFilesSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsMaintenanceFilesExists(updateReqVO.getId());
        // 更新
        TrafficOpsMaintenanceFilesDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsMaintenanceFilesDO.class);
        opsMaintenanceFilesMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsMaintenanceFiles(String id) {
        // 校验存在
        validateOpsMaintenanceFilesExists(id);
        // 删除
        opsMaintenanceFilesMapper.deleteById(id);
    }

    private void validateOpsMaintenanceFilesExists(String id) {
        if (opsMaintenanceFilesMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_MAINTENANCE_FILES_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsMaintenanceFilesDO getOpsMaintenanceFiles(String id) {
        return opsMaintenanceFilesMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsMaintenanceFilesDO> getOpsMaintenanceFilesPage(AppTrafficOpsMaintenanceFilesPageReqVO pageReqVO) {
        return opsMaintenanceFilesMapper.selectPage(pageReqVO);
    }

}