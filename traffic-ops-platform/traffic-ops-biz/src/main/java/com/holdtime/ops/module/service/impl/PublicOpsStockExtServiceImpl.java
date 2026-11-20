package com.holdtime.ops.module.service.impl;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.holdtime.ops.module.entity.*;
import com.holdtime.ops.module.entity.mapper.PublicOpsInoutDetailMapper;
import com.holdtime.ops.module.entity.mapper.PublicOpsInoutStockMapper;
import com.holdtime.ops.module.entity.mapper.PublicOpsStockDetailMapper;
import com.holdtime.ops.module.entity.mapper.PublicOpsStockMapper;
import com.holdtime.ops.module.enums.OpsErrorCodeConstants;
import com.holdtime.ops.module.model.vo.*;
import com.holdtime.ops.module.service.PublicOpsStockExtService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 设备库存管理 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
@Transactional(rollbackFor = Exception.class)
public class PublicOpsStockExtServiceImpl implements PublicOpsStockExtService {

    @Resource
    private PublicOpsStockMapper opsStockMapper;
    @Resource
    private PublicOpsStockDetailMapper opsStockDetailMapper;
    @Resource
    private PublicOpsInoutStockMapper opsInoutStockMapper;
    @Resource
    private PublicOpsInoutDetailMapper opsInoutDetailMapper;

    @Override
    public String instock(PublicOpsInoutStockSaveReqVO saveReqVO) {
        // 插入入库表
        PublicOpsInoutStockDO opsInoutStock = BeanUtils.toBean(saveReqVO, PublicOpsInoutStockDO.class);
        opsInoutStockMapper.insert(opsInoutStock);
        //插入入库详情表
        if (CollectionUtils.isEmpty(saveReqVO.getInoutDetailList())) {
            throw exception(OpsErrorCodeConstants.SYSTEM_COMMON_ERROR_PARAM_MISSING);
        }

        List<PublicOpsInoutDetailDO> detailList = new ArrayList<>();

        for (PublicOpsInoutDetailSaveReqVO detailSaveReqVO : saveReqVO.getInoutDetailList()) {
            // 1. 转换并设置关联ID
            PublicOpsInoutDetailDO instDetailDO = BeanUtils.toBean(detailSaveReqVO, PublicOpsInoutDetailDO.class);
            instDetailDO.setInoutId(opsInoutStock.getId());
            detailList.add(instDetailDO);

            // 2. 查询库存记录（按 cmSort）
            LambdaQueryWrapperX<PublicOpsStockDO> stockQueryWrapperX = new LambdaQueryWrapperX<>();
            stockQueryWrapperX.eq(PublicOpsStockDO::getCmSort, detailSaveReqVO.getCmSort());
            List<PublicOpsStockDO> stockList = opsStockMapper.selectList(stockQueryWrapperX);

            // 3. 更新库存（如果存在）
            if (!CollectionUtils.isEmpty(stockList)) {
                // 假设 cmSort 对应唯一库存记录，否则需明确业务规则
                PublicOpsStockDO stock = stockList.get(0);
                if (stock.getSurplusNum() != null) {
                    PublicOpsStockDO updateObj = new PublicOpsStockDO();
                    updateObj.setId(stock.getId());
                    updateObj.setSurplusNum(stock.getSurplusNum() + instDetailDO.getCmNum());
                    updateObj.setTotalNum(stock.getTotalNum() + instDetailDO.getCmNum());
                    opsStockMapper.updateById(updateObj);

                    // 4. 处理库存明细
                    LambdaQueryWrapperX<PublicOpsStockDetailDO> detailQueryWrapperX = new LambdaQueryWrapperX<>();
                    detailQueryWrapperX.eq(PublicOpsStockDetailDO::getStockId, stock.getId());
                    detailQueryWrapperX.eq(PublicOpsStockDetailDO::getFlag,detailSaveReqVO.getFlag());
                    List<PublicOpsStockDetailDO> stockDetailList = opsStockDetailMapper.selectList(detailQueryWrapperX);

                    if (!CollectionUtils.isEmpty(stockDetailList)) {
                        // 更新明细
                        PublicOpsStockDetailDO detail = stockDetailList.get(0); // 假设唯一明细
                        PublicOpsStockDetailDO updateDetail = new PublicOpsStockDetailDO();
                        updateDetail.setId(detail.getId());
                        updateDetail.setCmNum(detail.getCmNum() + instDetailDO.getCmNum());
                        opsStockDetailMapper.updateById(updateDetail);
                    } else {
                        // 新增明细（必须关联 stockId）
                        if (instDetailDO.getCmNum() != null) {
                            PublicOpsStockDetailDO newDetail = new PublicOpsStockDetailDO();
                            newDetail.setStockId(stock.getId()); // 关键修复：关联库存ID
                            newDetail.setFlag(detailSaveReqVO.getFlag());
                            newDetail.setCmSort(detailSaveReqVO.getCmSort());
                            newDetail.setCmNum(instDetailDO.getCmNum());
                            opsStockDetailMapper.insert(newDetail);
                        }
                    }
                }
            }else{
                //如果不存在新增库存
                PublicOpsStockDO newObj = new PublicOpsStockDO();
                newObj.setCmSort(detailSaveReqVO.getCmSort());
                newObj.setSurplusNum(instDetailDO.getCmNum());
                newObj.setTotalNum(instDetailDO.getCmNum());
                opsStockMapper.insert(newObj);
                //新增明细
                PublicOpsStockDetailDO newDetail = new PublicOpsStockDetailDO();
                newDetail.setStockId(newObj.getId()); // 关键修复：关联库存ID
                newDetail.setFlag(detailSaveReqVO.getFlag());
                newDetail.setCmSort(detailSaveReqVO.getCmSort());
                newDetail.setCmNum(instDetailDO.getCmNum());
                opsStockDetailMapper.insert(newDetail);
            }

        }

        // 5. 批量插入明细
        opsInoutDetailMapper.insertBatch(detailList);
        // 返回
        return opsInoutStock.getId();
    }

    @Override
    public void outstock(PublicOpsInoutStockSaveReqVO updateReqVO) {

    }

//    @Override
//    public void deleteOpsStock(String id) {
//        // 校验存在
//        validateOpsStockExists(id);
//        // 删除
//        opsStockMapper.deleteById(id);
//    }
//
//    private void validateOpsStockExists(String id) {
//        if (opsStockMapper.selectById(id) == null) {
//            throw exception(OpsErrorCodeConstants.OPS_STOCK_NOT_EXISTS);
//        }
//    }
//
//    @Override
//    public PublicOpsStockDO getOpsStock(String id) {
//        return opsStockMapper.selectById(id);
//    }
//
//    @Override
//    public PageResult<PublicOpsStockDO> getOpsStockPage(PublicOpsStockPageReqVO pageReqVO) {
//        return opsStockMapper.selectPage(pageReqVO);
//    }

}