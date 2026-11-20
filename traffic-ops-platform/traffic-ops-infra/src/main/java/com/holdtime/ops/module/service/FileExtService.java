package com.holdtime.ops.module.service;

import com.holdtime.ops.module.model.vo.FileExtRespVO;

public interface FileExtService {
    /**
     * 上传文件
     * @param name 文件名
     * @param path 文件路径
     * @param content 文件
     * @param isCompress 是否压缩
     * @return 上传结果
     */
    FileExtRespVO uploadFile(String name, String path, byte[] content, String isCompress);
}
