package com.nextyu.mall.service;

import com.qiniu.storage.model.DefaultPutRet;

/**
 * 2017-07-10 20:33
 *
 * @author nextyu
 */
public interface UploadService {
    DefaultPutRet uploadImg(byte[] bytes, String name);

    String getImgDomain();
}
