package com.holdtime.ops.module.service;

import java.util.*;
import javax.validation.*;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.entity.TrafficOpsSignBoardDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 标志牌 Service 接口
 *
 * @author 芋道源码
 */
public interface TrafficOpsSignBoardService {

    /**
     * 创建标志牌
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createOpsSignBoard(@Valid TrafficOpsSignBoardSaveReqVO createReqVO);

    /**
     * 更新标志牌
     *
     * @param updateReqVO 更新信息
     */
    void updateOpsSignBoard(@Valid TrafficOpsSignBoardSaveReqVO updateReqVO);

    /**
     * 删除标志牌
     *
     * @param id 编号
     */
    void deleteOpsSignBoard(String id);

    /**
     * 获得标志牌
     *
     * @param id 编号
     * @return 标志牌
     */
    TrafficOpsSignBoardDO getOpsSignBoard(String id);

    /**
     * 获得标志牌分页
     *
     * @param pageReqVO 分页查询
     * @return 标志牌分页
     */
    PageResult<TrafficOpsSignBoardDO> getOpsSignBoardPage(TrafficOpsSignBoardPageReqVO pageReqVO);

}