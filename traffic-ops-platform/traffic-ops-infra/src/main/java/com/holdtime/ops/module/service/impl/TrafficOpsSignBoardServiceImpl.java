package com.holdtime.ops.module.service.impl;

import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.service.TrafficOpsSignBoardService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsSignBoardDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import com.holdtime.ops.module.entity.mapper.TrafficOpsSignBoardMapper;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 标志牌 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TrafficOpsSignBoardServiceImpl implements TrafficOpsSignBoardService {

    @Resource
    private TrafficOpsSignBoardMapper opsSignBoardMapper;

    @Override
    public String createOpsSignBoard(TrafficOpsSignBoardSaveReqVO createReqVO) {
        // 插入
        TrafficOpsSignBoardDO opsSignBoard = BeanUtils.toBean(createReqVO, TrafficOpsSignBoardDO.class);
        opsSignBoardMapper.insert(opsSignBoard);
        // 返回
        return opsSignBoard.getId();
    }

    @Override
    public void updateOpsSignBoard(TrafficOpsSignBoardSaveReqVO updateReqVO) {
        // 校验存在
        validateOpsSignBoardExists(updateReqVO.getId());
        // 更新
        TrafficOpsSignBoardDO updateObj = BeanUtils.toBean(updateReqVO, TrafficOpsSignBoardDO.class);
        opsSignBoardMapper.updateById(updateObj);
    }

    @Override
    public void deleteOpsSignBoard(String id) {
        // 校验存在
        validateOpsSignBoardExists(id);
        // 删除
        opsSignBoardMapper.deleteById(id);
    }

    private void validateOpsSignBoardExists(String id) {
        if (opsSignBoardMapper.selectById(id) == null) {
            throw exception(OpsErrorCodeConstants.OPS_SIGN_BOARD_NOT_EXISTS);
        }
    }

    @Override
    public TrafficOpsSignBoardDO getOpsSignBoard(String id) {
        return opsSignBoardMapper.selectById(id);
    }

    @Override
    public PageResult<TrafficOpsSignBoardDO> getOpsSignBoardPage(TrafficOpsSignBoardPageReqVO pageReqVO) {
        return opsSignBoardMapper.selectPage(pageReqVO);
    }

}