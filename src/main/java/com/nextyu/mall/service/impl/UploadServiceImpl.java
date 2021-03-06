package com.nextyu.mall.service.impl;

import com.google.gson.Gson;
import com.nextyu.mall.properties.MallProperties;
import com.nextyu.mall.service.UploadService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 2017-07-10 20:34
 *
 * @author nextyu
 */
@Slf4j
@Service
public class UploadServiceImpl implements UploadService {


    @Autowired
    private MallProperties mallProperties;

    @Autowired
    private Auth auth;

    @Autowired
    private UploadManager uploadManager;

    @Override
    public DefaultPutRet uploadImg(byte[] bytes, String name) {
        try {
            Response response = uploadManager.put(bytes, name, auth.uploadToken(mallProperties.getQiniu().getBucket()));
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet;
        } catch (QiniuException ex) {
//            Response response = ex.response;
            log.error("upload img to qiniu error", ex);
        }
        return null;
    }


    @Override
    public String getImgDomain() {
        return mallProperties.getQiniu().getImgDomain();
    }
}
