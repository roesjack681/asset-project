package com.holdtime.ops.module.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.util.io.FileUtils;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.infra.dal.dataobject.file.FileDO;
import cn.iocoder.yudao.module.infra.dal.mysql.file.FileMapper;
import cn.iocoder.yudao.module.infra.framework.file.core.client.FileClient;
import cn.iocoder.yudao.module.infra.framework.file.core.utils.FileTypeUtils;
import cn.iocoder.yudao.module.infra.service.file.FileConfigService;
import com.holdtime.ops.module.enums.OpsConstants;
import com.holdtime.ops.module.model.vo.FileExtRespVO;
import com.holdtime.ops.module.service.FileExtService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

@Service
@Validated
public class FileExtServiceImpl implements FileExtService {
    @Resource
    private FileConfigService fileConfigService;
    @Resource
    private FileMapper fileMapper;

    @Override
    @SneakyThrows
    public FileExtRespVO uploadFile(String name, String path, byte[] content, String isCompress) {
        // 计算默认的 path 名
        String type = FileTypeUtils.getMineType(content, name);
        if (StrUtil.isEmpty(path)) {
            path = FileUtils.generatePath(content, name);
        }
        // 如果 name 为空，则使用 path 填充
        if (StrUtil.isEmpty(name)) {
            name = path;
        }

        // 上传到文件存储器
        FileClient client = fileConfigService.getMasterFileClient();
        Assert.notNull(client, "客户端(master) 不能为空");
        String url = client.upload(content, path, type);

        // 保存到数据库
        FileDO file = new FileDO();
        file.setConfigId(client.getId());
        file.setName(name);
        file.setPath(path);
        file.setUrl(url);
        file.setType(type);
        file.setSize(content.length);
        fileMapper.insert(file);

        return BeanUtils.toBean(file, FileExtRespVO.class);
    }

    private String judgeFileType(String mineType) {
        if (mineType.startsWith("image/")) {
            return OpsConstants.STRING_ONE;
        } else if (mineType.startsWith("video/")) {
            return OpsConstants.STRING_TWO;
        } else {
            return OpsConstants.STRING_THREE;
        }
    }
}
