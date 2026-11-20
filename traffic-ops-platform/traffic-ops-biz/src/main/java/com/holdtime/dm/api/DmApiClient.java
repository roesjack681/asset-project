package com.holdtime.dm.api;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.holdtime.ops.module.model.vo.PublicOpsDeviceSaveReqVO;
import org.springframework.util.StringUtils;

/**
 * @author: jinnee
 * @date: 2021/12/22 10:06
 * @description: 零维护接口客户端
 */
public class DmApiClient {
    private static final String DM_URL = "http://dm.xiaoluxueche.com:8380/xlxc-dm";
    private static final String PLATE_NUM = "ZCYWPT-150200";  //资产运维管理平台
    private static final String COMPANY_ID = "10000001";

    public static CommonResult<Boolean> writeCompany(){
        String apiUrl = DM_URL + "/api/writeCompanyInfo";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("companyId", COMPANY_ID);
        jsonObject.put("name", "资产运维管理平台专用");
        jsonObject.put("plateformNum", PLATE_NUM);
        jsonObject.put("delFlag", "0");
        String result = HttpUtil.post(apiUrl, jsonObject.toString());
        System.out.println(result);
        if (StringUtils.hasText(result) && JSONUtil.isTypeJSON(result)){
            JSONObject data = JSONUtil.parseObj(result);
            if (data.getInt("resultCode").equals(0)){
                return CommonResult.success(true);
            }else{
                return CommonResult.error(GlobalErrorCodeConstants.UNKNOWN.getCode(), data.getStr("msg"));
            }
        }
        return CommonResult.error(GlobalErrorCodeConstants.UNKNOWN.getCode(), "给零维护写入资产运维专用驾校失败");
    }

    public static CommonResult<Boolean> writeDevice(PublicOpsDeviceSaveReqVO deviceVO, Boolean isDelete){
        String apiUrl = DM_URL + "/api/writeTimeDevice";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deviceId", deviceVO.getId());
        jsonObject.put("devnum", deviceVO.getId());
        jsonObject.put("imei", deviceVO.getImei());
        jsonObject.put("model", deviceVO.getModel());
        jsonObject.put("companyId", COMPANY_ID);
        jsonObject.put("plateNumber", deviceVO.getPlateNum());
        jsonObject.put("termtype", "1");
        jsonObject.put("plateformNum", PLATE_NUM);
        jsonObject.put("delFlag", "0");
        if (isDelete != null && isDelete){
            jsonObject.put("dmSyncType", "DeviceDelete");
        }
        String result = HttpUtil.post(apiUrl, jsonObject.toString());
        System.out.println(result);
        if (StringUtils.hasText(result) && JSONUtil.isTypeJSON(result)){
            JSONObject data = JSONUtil.parseObj(result);
            if (data.getInt("resultCode").equals(0)){
                return CommonResult.success(true);
            }else{
                return CommonResult.error(GlobalErrorCodeConstants.UNKNOWN.getCode(), data.getStr("msg"));
            }
        }
        return CommonResult.error(GlobalErrorCodeConstants.UNKNOWN.getCode(), "设备写入零维护失败");

    }

    public static void main(String[] args) {
//        writeCompany();
        PublicOpsDeviceSaveReqVO deviceVO = new PublicOpsDeviceSaveReqVO();
        deviceVO.setId("H20250118070705");
        deviceVO.setPlateNum("蒙B532BT");
        deviceVO.setImei("867882050764128");
        deviceVO.setModel("YB-102");
        writeDevice(deviceVO, null);
    }
}
