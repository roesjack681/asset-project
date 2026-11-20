package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.service.ViewOpsMonitorResultsService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.ViewOpsMonitorResultsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import com.holdtime.ops.module.entity.mapper.ViewOpsMonitorResultsMapper;


/**
 * VIEW Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class ViewOpsMonitorResultsServiceImpl implements ViewOpsMonitorResultsService {

    @Resource
    private ViewOpsMonitorResultsMapper viewOpsMonitorResultsMapper;

    @Override
    public ViewOpsMonitorResultsDO getViewOpsMonitorResults(String id) {
        return viewOpsMonitorResultsMapper.selectById(id);
    }

    @Override
    public PageResult<ViewOpsMonitorResultsDO> getViewOpsMonitorResultsPage(ViewOpsMonitorResultsPageReqVO pageReqVO) {
        return viewOpsMonitorResultsMapper.selectPage(pageReqVO);
    }

}